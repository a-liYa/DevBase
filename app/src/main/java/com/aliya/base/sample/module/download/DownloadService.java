package com.aliya.base.sample.module.download;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;

import com.aliya.base.AppUtils;

import java.io.File;
import java.net.URLConnection;

/**
 * 管理下载任务的 Service
 *
 * @author a_liYa
 * @date 2020/12/21 14:20.
 */
public class DownloadService extends Service {

    static final String KEY_URL = "key_url";
    private DownloadCompleteBroadcastReceiver broadcastReceiver;

    public static Intent newIntent(String url) {
        Intent intent = new Intent(AppUtils.getContext(), DownloadService.class);
        intent.putExtra(KEY_URL, url);
        return intent;
    }

    public DownloadService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String url = intent.getStringExtra(KEY_URL);
            if (!TextUtils.isEmpty(url)) {
                download(getApplicationContext(), url);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (broadcastReceiver == null) {
            broadcastReceiver = new DownloadCompleteBroadcastReceiver();
            // 注册广播监听系统的下载结束事件
            registerReceiver(broadcastReceiver,
                    new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void download(Context context, String url) {
        try {
            Uri uri = Uri.parse(url);
            String path = uri.getPath();
            if (TextUtils.isEmpty(path)) {
                return;
            }
            String filename = path.substring(path.lastIndexOf("/") + 1);
            String extensionName = filename.substring(filename.lastIndexOf(".") + 1);

            DownloadManager.Request request = new DownloadManager.Request(uri);
            // request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);
            // 指定下载文件的保存路径
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                    filename);

            request.setDestinationUri(Uri.fromFile(file));
            // 表示允许MediaScanner扫描到这个文件，默认不允许
            request.allowScanningByMediaScanner();
            // 设置显示下载界面
            request.setVisibleInDownloadsUi(true);
            request.setTitle(AppUtils.getAppName());
            request.setDescription(extensionName + "下载");
            request.setMimeType(URLConnection.guessContentTypeFromName(extensionName));
            // Notification一直显示，直到用户点击该 Notification 或者 消除该 Notification
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            long downloadId =
                    ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class DownloadCompleteBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            DownloadManager.Query query = new DownloadManager.Query().setFilterById(id);
            Cursor c = null;
            try {
                c = ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).query(query);
                if (c != null && c.moveToFirst()) {
                    // 下载成功
                    if (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))
                            == DownloadManager.STATUS_SUCCESSFUL) {
                        String filePath;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            String fileUri =
                                    c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                            filePath = Uri.parse(fileUri).getPath();
                        } else {
                            filePath =
                                    c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                        }
                        // apk 则执行安装
                        String extensionName = filePath.substring(filePath.lastIndexOf(".") + 1);
                        if ("apk".equalsIgnoreCase(extensionName)) {
                            AppUtils.installApk(new File(filePath));
                        }
                    }
                }
            } catch (Exception e) {
                // ignore it.
            } finally {
                if (c != null) c.close();
            }

            checkShouldStopService(context);
        }

        private void checkShouldStopService(Context context) {
            DownloadManager.Query query = new DownloadManager.Query().setFilterByStatus(
                    DownloadManager.STATUS_RUNNING | DownloadManager.STATUS_PAUSED | DownloadManager.STATUS_PENDING);
            Cursor c =
                    ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).query(query);
            if (c != null && c.getCount() <= 0) {
                // 无暂停、等待、正在下载的任务，则关闭 DownloadService
                context.stopService(new Intent(context, DownloadService.class));
            }
        }
    }
}
