package com.aliya.base.manager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aliya.base.AppUtils;

import java.util.Stack;

/**
 * App 的 Activity 管理类.
 *
 * @author a_liYa
 * @date 2015/10/4 01:06.
 */
public class AppManager {

    private volatile static AppManager sInstance;

    private Stack<Activity> mActivityStack;

    private AppManager() {
        Context app = AppUtils.getContext().getApplicationContext();
        if (app instanceof Application) {
            ((Application) app).registerActivityLifecycleCallbacks(lifecycleCallbacks);
        }
    }

    public static AppManager get() {
        // 单例  懒汉式
        if (sInstance == null) {
            synchronized (AppManager.class) {
                if (sInstance == null)
                    sInstance = new AppManager();
            }
        }
        return sInstance;
    }

    private static final Application.ActivityLifecycleCallbacks lifecycleCallbacks =
            new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    Log.e("TAG", "onActivityCreated: " + activity.getClass().getSimpleName() + activity.hashCode());
                    get().addActivity(activity);
                }

                @Override
                public void onActivityStarted(Activity activity) {
                    Log.e("TAG", "onActivityStarted: " + activity.getClass().getSimpleName() + activity.hashCode());
                }

                @Override
                public void onActivityResumed(Activity activity) {

                    Log.e("TAG", "onActivityResumed: " + activity.getClass().getSimpleName() + activity.hashCode());
                }

                @Override
                public void onActivityPaused(Activity activity) {
                    Log.e("TAG", "onActivityPaused: " + activity.getClass().getSimpleName() + activity.hashCode());
                }

                @Override
                public void onActivityStopped(Activity activity) {
                    Log.e("TAG", "onActivityStopped: " + activity.getClass().getSimpleName() + activity.hashCode());
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    get().removeActivity(activity);
                    Log.e("TAG", "onActivityDestroyed: " + activity.getClass().getSimpleName() + activity.hashCode());
                }
            };

    /**
     * 添加Activity到堆栈.
     *
     * @param activity activity.
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null)
            mActivityStack = new Stack<>();

        if (activity != null)
            mActivityStack.push(activity);
    }

    /**
     * 删除Activity从堆栈.
     *
     * @param activity activity.
     * @return false : 删除失败（当不存在时）.
     */
    public boolean removeActivity(Activity activity) {
        if (mActivityStack != null && activity != null) {
            return mActivityStack.remove(activity);
        }
        return false;
    }

    /**
     * 获取栈中所有Activity.
     *
     * @return 返回 stack.
     */
    public @Nullable
    Stack<Activity> getAllActivity() {
        return mActivityStack;
    }

    /**
     * 判断是否包含指定 class 的 Activity.
     *
     * @param clazz activity class.
     * @return true : 包含
     */
    public boolean contains(Class<? extends Activity> clazz) {
        if (mActivityStack != null)
            for (Activity activity : mActivityStack) {
                if (activity.getClass().equals(clazz)) return true;
            }
        return false;
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     * TODO 待验证这个问题
     *
     * @return .
     */
    public @Nullable
    Activity currentActivity() {
        if (mActivityStack == null || mActivityStack.isEmpty()) return null;
        return mActivityStack.peek();
    }

    /**
     * 获取指定的 class 的 Activity.
     *
     * @param clazz activity class.
     * @return activity instance.
     */
    public @Nullable
    Activity getActivity(Class<? extends Activity> clazz) {
        if (mActivityStack != null)
            for (Activity activity : mActivityStack) {
                if (activity.getClass().equals(clazz)) return activity;
            }
        return null;
    }

    /**
     * 结束指定类名的Activity，如果存在多个，删除全部。
     *
     * @param clazz activity.
     */
    public void finishActivity(Class<?> clazz) {
        if (mActivityStack != null) {
            for (Activity activity : mActivityStack) {
                if (activity.getClass().equals(clazz)) activity.finish();
            }
        }
    }

    /**
     * 获取栈大小
     *
     * @return activity count.
     */
    public int getCount() {
        return mActivityStack != null ? mActivityStack.size() : 0;
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (mActivityStack != null) {
            for (Activity activity : mActivityStack) {
                if (activity != null) activity.finish();
            }
            mActivityStack.clear();
        }
    }

    /**
     * 退出应用程序
     */
    public void exitApp() {
        try {
            finishAllActivity();

            //  以下代码不建议使用,会把后台服务、推送一并杀死;
//            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context
// .ACTIVITY_SERVICE);
//            activityMgr.killBackgroundProcesses(context.getPackageName());
//            System.exit(0);
//            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
