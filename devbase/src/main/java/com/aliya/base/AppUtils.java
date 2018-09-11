package com.aliya.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 工具类
 *
 * @author a_liYa
 * @date 2018/7/30 14:48.
 */
public class AppUtils {

    /**
     * @see /build.gradle文件 属性android.buildTypes.(release/debug)#debuggable true/false 来决定
     */
    private static boolean debuggable = true;

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
        }
    }

    public static Context getContext() {
        return sContext;
    }

    public static boolean debuggable() {
        return debuggable;
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
     * 获取当前进程的名字
     *
     * @param context context
     * @return process name
     */
    public static String getProcessName(Context context) {
        final int pid = android.os.Process.myPid();

        ActivityManager manager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo>
                processes = manager != null ? manager.getRunningAppProcesses() : null;

        if (processes != null) {
            for (ActivityManager.RunningAppProcessInfo info : processes) {
                if (info.pid == pid) return info.processName;
            }
        }
        return null;
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
