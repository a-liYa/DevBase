package com.aliya.monitor.fps;

import android.app.AppOpsManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.aliya.monitor.R;
import com.aliya.monitor.databinding.MonitorLayoutMonitorOverlayBinding;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MonitorService extends Service implements FpsMonitor.FpsListener {

    WindowManager windowManager;
    LayoutParams layoutParams;
    private MonitorLayoutMonitorOverlayBinding mBinding;

    public MonitorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initWindowParams();
        mBinding = MonitorLayoutMonitorOverlayBinding.inflate(LayoutInflater.from(getApplication()));

        FpsMonitor.get().registerMonitor(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mBinding.getRoot().getParent() == null) {
            showView();
        } else {
            hideView();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void initWindowParams() {
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        layoutParams = new LayoutParams();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 8.0以上只能使用 TYPE_APPLICATION_OVERLAY窗口类型来创建悬浮窗
            layoutParams.type = LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.gravity = Gravity.LEFT | Gravity.BOTTOM;
        // 初始位置坐标, 相对于Gravity而言，eg：Gravity = LEFT|TOP 坐标原点在左上角； Gravity = LEFT|BOTTOM 坐标原点在左下角。
        // layoutParams.x = ;
        layoutParams.y = (int) getApplication().getResources()
                .getDimension(R.dimen.monitor_overlay_bottom_margin);
        layoutParams.width = LayoutParams.WRAP_CONTENT;
        layoutParams.height = LayoutParams.WRAP_CONTENT;
    }

    @Override
    public void onDestroy() {
        FpsMonitor.get().unregisterMonitor(this);
        hideView();
    }

    int fps;

    @Override
    public void invoke(int fps) {
        if (this.fps != fps) {
            mBinding.tvFps.setText(fps + "fps");
            this.fps = fps;
        }
    }

    private void showView() {
        if (canDrawOverlays()) {
            windowManager.addView(mBinding.getRoot(), layoutParams);
        } else {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setData(Uri.parse("package:" + getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void hideView() {
        if (mBinding.getRoot().getParent() != null) {
            windowManager.removeView(mBinding.getRoot());
        }
    }

    private boolean canDrawOverlays() {
        Context context = getApplication();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return true;
        else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            try {
                Class cls = Class.forName("android.content.Context");
                Field declaredField = cls.getDeclaredField("APP_OPS_SERVICE");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(cls);
                if (!(obj instanceof String)) {
                    return false;
                }
                String str2 = (String) obj;
                obj = cls.getMethod("getSystemService", String.class).invoke(context, str2);
                cls = Class.forName("android.app.AppOpsManager");
                Field declaredField2 = cls.getDeclaredField("MODE_ALLOWED");
                declaredField2.setAccessible(true);
                Method checkOp = cls.getMethod("checkOp", Integer.TYPE, Integer.TYPE, String.class);
                int result = (Integer) checkOp.invoke(obj, 24, Binder.getCallingUid(),
                        context.getPackageName());
                return result == declaredField2.getInt(cls);
            } catch (Exception e) {
                return false;
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                AppOpsManager appOpsMgr =
                        (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                if (appOpsMgr == null)
                    return false;
                int mode = appOpsMgr.checkOpNoThrow("android:system_alert_window",
                        android.os.Process.myUid(), context.getPackageName());
                return Settings.canDrawOverlays(context) || mode == AppOpsManager.MODE_ALLOWED ||
                        mode == AppOpsManager.MODE_IGNORED;
            } else {
                return Settings.canDrawOverlays(context);
            }
        }
    }
}
