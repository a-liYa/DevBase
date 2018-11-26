package com.aliya.base.manager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.aliya.base.AppUtils;

import java.util.HashSet;
import java.util.Set;
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
    private Set<AppFrontBackCallback> mCallbacks = new HashSet<>();

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

                // 打开的Activity数量统计
                private int activityStartCount = 0;

                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    get().addActivity(activity);
                }

                @Override
                public void onActivityStarted(Activity activity) {
                    // 数值从 0 -> 1 说明是从后台切到前台
                    if (++activityStartCount == 1) {
                        get().dispatchOnFront();
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
                        get().dispatchOnBack();
                    }
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    get().removeActivity(activity);
                }
            };

    private void dispatchOnFront() {
        for (AppFrontBackCallback callback : mCallbacks) {
            if (callback != null) callback.onFront();
        }
    }

    private void dispatchOnBack() {
        for (AppFrontBackCallback callback : mCallbacks) {
            if (callback != null) callback.onBack();
        }
    }

    public void registerAppFrontBackCallback(AppFrontBackCallback callback) {
        mCallbacks.add(callback);
    }

    public void unregisterAppFrontBackCallback(AppFrontBackCallback callback) {
        mCallbacks.remove(callback);
    }

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


    /**
     * 前后台切换回调接口
     */
    public interface AppFrontBackCallback {

        void onFront();

        void onBack();
    }

}
