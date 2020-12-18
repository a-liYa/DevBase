package com.aliya.base.sample.module.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.aliya.base.AppUtils;
import com.aliya.base.sample.R;
import com.aliya.base.sample.base.ActionBarActivity;
import com.aliya.base.sample.databinding.ActivityDownloadBinding;
import com.aliya.base.widget.T;

import java.io.File;
import java.net.URLConnection;

/**
 * 使用系统下载管理器下载文件
 *
 * @author a_liYa
 * @date 2020/12/18 10:27.
 */
public class DownloadActivity extends ActionBarActivity implements View.OnClickListener {

    ActivityDownloadBinding mBinding;

    String pdfUrl = "https://download.brother.com/welcome/docp000648/cv_pt3600_schn_sig_lad962001" +
            ".pdf";
    String apkUrl = "https://fga1.market.xiaomi" +
            ".com/download/AppStore/0f974b54777404f7339c3cc3f8179d7a6805656d1/com.mazda_china" +
            ".operation.imazda.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityDownloadBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.tvDownloadPdf.setOnClickListener(this);
        mBinding.tvDownloadApk.setOnClickListener(this);

        Uri uri = Uri.parse(apkUrl);
        String path = uri.getPath();
        String filename = path.substring(path.lastIndexOf("/") + 1);
        String extensionName = filename.substring(filename.lastIndexOf(".") + 1);
        String contentType = URLConnection.getFileNameMap().getContentTypeFor(extensionName);
        Log.e("TAG", "onCreate: " + contentType);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_download_pdf:
                download(this, pdfUrl);
                break;
            case R.id.tv_download_apk:
                download(this, apkUrl);
                break;
        }
    }

    private void download(Context context, String url) {
        try {
            Uri uri = Uri.parse(url);
            String path = uri.getPath();
            if (TextUtils.isEmpty(path)) {
                T.showShort(context, "无效的url");
                return;
            }
            String filename = path.substring(path.lastIndexOf("/") + 1);
            String extensionName = filename.substring(filename.lastIndexOf(".") + 1);

            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);
            // 指定下载文件的保存路径
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename);

            request.setDestinationUri(Uri.fromFile(file));
            // 表示允许MediaScanner扫描到这个文件，默认不允许
            request.allowScanningByMediaScanner();
            // 设置显示下载界面
            request.setVisibleInDownloadsUi(true);
            request.setTitle(AppUtils.getAppName());
            request.setDescription(extensionName + "下载");
            request.setMimeType(URLConnection.getFileNameMap().getContentTypeFor(extensionName));
            // Notification一直显示，直到用户点击该 Notification 或者 消除该 Notification
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            long downloadId =
                    ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);
            listener(downloadId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BroadcastReceiver broadcastReceiver;

    private void listener(final long id) {
        // 注册广播监听系统的下载结束事件
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long ID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (ID == id) {
                    DownloadManager.Query query = new DownloadManager.Query().setFilterById(id);
                    Cursor c = null;
                    try {
                        c = ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).query(query);
                        if (c != null && c.moveToFirst()) {
                            switch (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                                case DownloadManager.STATUS_FAILED:
                                    T.showShort(getActivity(), "任务:" + id + " 下载失败!");
                                    break;
                                case DownloadManager.STATUS_SUCCESSFUL:
                                    T.showShort(getActivity(), "任务:" + id + " 下载成功!");
                                    break;
                            }
                        }
                    } catch (Exception e) {
                    } finally {
                        c.close();
                    }
                }
            }
        };

        registerReceiver(broadcastReceiver, intentFilter);
    }
}