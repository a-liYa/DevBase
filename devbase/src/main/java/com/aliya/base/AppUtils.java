package com.aliya.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.aliya.base.manager.AppManager;

import java.util.List;

/**
 * 工具类
 *
 * @author a_liYa
 * @date 2018/7/30 14:48.
 */
public final class AppUtils {

    /**
     * @see /build.gradle文件 属性android.buildTypes.(release/debug)#debuggable true/false 来决定, 默认为true
     */
    private static boolean debuggable = true;
    private static boolean isDebug = true;

    private static Context sContext;

    public static void init(Context context) {
        if (context != null && sContext == null) {
            sContext = context;
            try {
                debuggable =
                        (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            } catch (Exception e) {
                debuggable = false;
            }
            AppManager.get();
        }
    }

    public static void setIsDebug(boolean isDebug) {
        AppUtils.isDebug = isDebug;
    }

    public static Context getContext() {
        return sContext;
    }

    public static boolean isDebuggable() {
        return isDebug && debuggable;
    }

    /**
     * dp 转 px
     *
     * @param dp dp value.
     * @return The converted pixel value.
     */
    public static int dp2px(float dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getContext().getResources().getDisplayMetrics()));
    }

    /**
     * sp 转 px
     *
     * @param sp sp value.
     * @return The converted pixel value.
     */
    public static int sp2px(float sp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getContext().getResources().getDisplayMetrics()));
    }

    /**
     * px 转 dp
     *
     * @param px px value.
     * @return The converted device independent pixels value.
     */
    public static float px2dp(int px) {
        return px / getContext().getResources().getDisplayMetrics().density;
    }

    /**
     * px 转 sp
     *
     * @param px px value.
     * @return The converted scale independent pixels value.
     */
    public static float px2sp(float px) {
        return (px / getContext().getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * 获取App名称
     *
     * @return app name
     */
    public static String getAppName() {
        String appName = null;
        if (getContext() != null) {
            appName = getContext().getApplicationInfo()
                    .loadLabel(getContext().getPackageManager()).toString();
        }
        return appName;
    }

    /**
     * 获取当前 App 版本名称
     *
     * @return version name, fetch failure returns null.
     */
    public static String getVersion() {
        try {
            return sContext.getPackageManager()
                    .getPackageInfo(sContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /**
     * 获取当前 App 版本code
     *
     * @return version code, fetch failure returns -1.
     */
    public static int getVersionCode() {
        try {
            return sContext.getPackageManager()
                    .getPackageInfo(sContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    /**
     * 获取屏幕的像素大小.
     *
     * @param point A {@link Point} object to receive the size information.
     * @return Math.max(point.x, point.y) 表示屏幕的高.
     */
    public static Point getScreenSize(Point point) {
        /*
        // 获取 window 宽高
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);

        // 等同于上面代码
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        */

        WindowManager windowManager = (WindowManager)
                getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) display.getRealSize(point);
        else display.getSize(point);

        return point;
    }

    /**
     * 获取 Activity 通过 Context.
     *
     * @param context context shouldn't instanceof application.
     * @return activity
     */
    public static Activity getActivityByContext(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    /**
     * 获取当前进程的名字
     *
     * @return process name
     */
    public static String getProcessName() {
        final int pid = android.os.Process.myPid();

        ActivityManager manager = (ActivityManager)
                sContext.getSystemService(Context.ACTIVITY_SERVICE);

        if (manager != null) {
            List<ActivityManager.RunningAppProcessInfo>
                    processes = manager.getRunningAppProcesses();
            if (processes != null) {
                for (ActivityManager.RunningAppProcessInfo info : processes) {
                    if (info.pid == pid) return info.processName;
                }
            }
        }
        return null;
    }

    /**
     * 获取 MetaData 数据
     *
     * @param name key name.
     * @return value string.
     */
    public static String getMetaData(String name) {
        try {
            return sContext.getPackageManager().getApplicationInfo(sContext.getPackageName(),
                    PackageManager.GET_META_DATA).metaData.getString(name);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /**
     * 通过包名开启应用
     *
     * @param packageName 包名
     */
    public static void startApp(String packageName) {
        Intent intent = sContext.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            sContext.startActivity(intent);
        }
    }

    /**
     * Inflate a new view hierarchy from the specified xml resource.
     *
     * @param resource     ID for an XML layout
     * @param root         用于生成层次结构的父视图
     * @param attachToRoot 是否附加到root
     * @return The root view of the inflated hierarchy.
     */
    public static View inflate(@LayoutRes int resource, @NonNull ViewGroup root,
                               boolean attachToRoot) {
        return LayoutInflater.from(root.getContext()).inflate(resource, root, attachToRoot);
    }

}
