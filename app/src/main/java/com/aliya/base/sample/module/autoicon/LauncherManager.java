package com.aliya.base.sample.module.autoicon;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * LauncherManager
 *
 * @author a_liYa
 * @date 2020/12/9 10:31.
 */
public class LauncherManager {

    public static void setComponentEnabled(Context context, String className, boolean enable) {
        ComponentName componentName = new ComponentName(context, className);
        if (true) {
            context.getPackageManager().setComponentEnabledSetting(
                    componentName,
                    enable ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED :
                            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP); // 参数 flags = 0 时，会杀死App立刻生效
        }
    }

    public static boolean isComponentEnabled(Context context, ComponentName componentName) {
        int state = context.getPackageManager().getComponentEnabledSetting(componentName);
        return PackageManager.COMPONENT_ENABLED_STATE_ENABLED == state;
    }

    public static String getLauncherActivity(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setPackage(context.getPackageName());
        List<ResolveInfo> infos = context.getPackageManager().queryIntentActivities(intent, 0);
        return !infos.isEmpty() ? infos.get(0).activityInfo.name : null;
    }
}
