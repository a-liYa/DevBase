package com.aliya.monitor;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * AppMonitor
 *
 * @author a_liYa
 * @date 2020/12/14 14:15.
 */
public final class AppMonitor {

    static Context sContext;

    private AppMonitor() {
    }

    public static void init(Context context) {
        if (sContext == null) {
            sContext = context.getApplicationContext();
            ((Application) sContext).registerActivityLifecycleCallbacks(
                    new Application.ActivityLifecycleCallbacks() {

                        // 打开的Activity数量统计
                        private int activityStartCount = 0;

                        @Override
                        public void onActivityCreated(Activity activity,
                                                      Bundle savedInstanceState) {
                        }

                        @Override
                        public void onActivityStarted(Activity activity) {
                            // 数值从 0 -> 1 说明是从后台切到前台
                            if (++activityStartCount == 1) {
                                showMonitor();
                            }
                        }

                        @Override
                        public void onActivityResumed(Activity activity) {
                        }

                        @Override
                        public void onActivityPaused(Activity activity) {
                        }

                        @Override
                        public void onActivityStopped(Activity activity) {
                            // 数值从 1 -> 0 说明是从前台切到后台
                            if (--activityStartCount == 0) {
                                hideMonitor();
                            }
                        }

                        @Override
                        public void onActivitySaveInstanceState(Activity activity,
                                                                Bundle outState) {
                        }

                        @Override
                        public void onActivityDestroyed(Activity activity) {
                        }
                    });
        }
    }

    private static class Singleton {
        static AppMonitor sInstance = new AppMonitor();
    }

    public static AppMonitor get() {
        return Singleton.sInstance;
    }

    public static void showMonitor() {
        Intent intent = new Intent(sContext, MonitorService.class);
        sContext.startService(intent);
    }

    public static void hideMonitor() {
        sContext.stopService(new Intent(sContext, MonitorService.class));
    }

}
