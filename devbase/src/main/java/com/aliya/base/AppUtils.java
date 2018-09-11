package com.aliya.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
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

    public static String getProcessName(Context context) {
        final int pid = android.os.Process.myPid();

        ActivityManager manager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo>
                processInfos = manager != null ? manager.getRunningAppProcesses() : null;

        if (processInfos != null) {
            for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
                if (processInfo.pid == pid) return processInfo.processName;
            }
        }

        return null;
    }

    /**
     * Inflate a new view hierarchy from the specified xml resource.
     *
     * @param root         用于生成层次结构的父视图
     * @param resource     layout id
     * @param attachToRoot 是否附加到root
     * @return
     */
    public static View inflate(@NonNull ViewGroup root, @LayoutRes int resource,
                               boolean attachToRoot) {
        return LayoutInflater.from(root.getContext()).inflate(resource, root, false);
    }

}
