package com.aliya.base.sample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;

import com.aliya.base.AppUtils;

/**
 * application
 *
 * @author a_liYa
 * @date 2018/9/11 18:33.
 */
public class App extends MultiDexApplication {

    private boolean isMainProcess; // 是否为主进程

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);

        isMainProcess = TextUtils.equals(getPackageName(), AppUtils.getProcessName());

        if (isMainProcess) { // 主进程需初始化
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 此处 - 第三方初始化
                }
            }).start();
        }

        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                Log.e("TAG", "queueIdle: ");
                return true;
            }
        });

        registerActivityLifecycleCallbacks(mLifecycleCallbacks);
    }

    private ActivityLifecycleCallbacks mLifecycleCallbacks = new ActivityLifecycleCallbacks() {

        private boolean printLog = true;

        private void e(Activity activity, String msg) {
            if (printLog)
                Log.e("TAG", msg + activity.getClass().getSimpleName());
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            e(activity, "onActivityCreated: ");
        }

        @Override
        public void onActivityStarted(Activity activity) {
            e(activity, "onActivityStarted: ");
        }

        @Override
        public void onActivityResumed(Activity activity) {
            e(activity, "onActivityResumed: ");
        }

        @Override
        public void onActivityPaused(Activity activity) {
            e(activity, "onActivityPaused: ");
        }

        @Override
        public void onActivityStopped(Activity activity) {
            e(activity, "onActivityStopped: ");
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            e(activity, "onActivitySaveInstanceState: ");
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            e(activity, "onActivityDestroyed: ");
        }
    };

}
