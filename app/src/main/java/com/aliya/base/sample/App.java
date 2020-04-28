package com.aliya.base.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.SystemClock;
import android.support.multidex.MultiDexApplication;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.util.Log;

import com.aliya.base.AppUtils;
import com.aliya.base.gather.WebPools;
import com.aliya.base.sample.base.ColorSaturation;
import com.aliya.base.sample.module.listen.SideFloatHelper;
import com.aliya.compat.CrashCompat;

/**
 * application
 *
 * @author a_liYa
 * @date 2018/9/11 18:33.
 */
public class App extends MultiDexApplication {

    private boolean isMainProcess; // 是否为主进程

    public static long sMillis;

    // 发送广播崩溃
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(new ContextWrapper(base) {
//            @Override
//            public void startActivity(Intent intent) {
//                /**
//                 * @see android.app.ContextImpl#startActivity(Intent, Bundle)
//                 * throw new AndroidRuntimeException("Calling startActivity() from outside of an
//                 * Activity context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what
//                 * you want?").
//                 */
//                if (intent != null) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                super.startActivity(intent);
//            }
//
//            @Override
//            public void startActivity(Intent intent, Bundle options) {
//                if (intent != null) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                super.startActivity(intent, options);
//            }
//        });
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        sMillis = SystemClock.uptimeMillis();

        isMainProcess = TextUtils.equals(getPackageName(), AppUtils.getProcessName());

        if (isMainProcess) { // 主进程需初始化
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Application#onCreate() 执行了耗时操作时，子线程提前加载Class，加快启动页开启速度3ms
                    Class clazz = SplashActivity.class;

                    // 此处 - 第三方初始化

                    // Application应用字体Style
                    getTheme().applyStyle(R.style.FontFamilyCustom, false);
                    // 加载字体耗时 150ms
                    ResourcesCompat.getFont(App.this, R.font.fzbiaoysk_zbjt);
                }
            }).start();
            SideFloatHelper.init(this);
        }

        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {

            boolean isInitWeb;

            @Override
            public boolean queueIdle() { // 应用场景待调研
                if (!isInitWeb) {
                    isInitWeb = true;
                    long ms = SystemClock.uptimeMillis();
                    WebPools.get().recycle(WebPools.get().acquireWebView(getApplicationContext()));
                    Log.e("TAG", "首次初始化WebView耗时: " + (SystemClock.uptimeMillis() - ms));
                }
                return true; // false 不会有下次回调
            }
        });

        CrashCompat.fixBug(); // 在Bug统计库之后调用
        ColorSaturation.init(this);
        registerActivityLifecycleCallbacks(mLifecycleCallbacks);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent, Bundle options) {
        super.startActivity(intent, options);
    }

    @Override
    public void startActivities(Intent[] intents) {
        super.startActivities(intents);
    }

    private ActivityLifecycleCallbacks mLifecycleCallbacks = new ActivityLifecycleCallbacks() {

        private boolean printLog = false;

        private void e(Activity activity, String msg) {
            if (printLog) Log.e("TAG", msg + activity.getClass().getSimpleName());
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            e(activity, "onActivityCreated: ");
            // 全局修改TextView字体，参数:false 表示不强制覆盖原本设置过的字体
            activity.getTheme().applyStyle(R.style.FontFamilyFangZheng, false);
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
