package com.aliya.base.manager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.aliya.base.util.L;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * App 的 Activity 管理类.
 *
 * @author a_liYa
 * @date 2015/10/4 01:06.
 */
public final class AppManager {

    private volatile static AppManager sInstance;

    private Stack<Activity> mCreatedStack = new Stack<>();
    private Stack<Activity> mStartedStack = new Stack<>();
    private Queue<Activity> mResumedQueue = new LinkedList<>();

    private Set<AppFrontBackCallback> mCallbacks = new HashSet<>();

    private AppManager(Context context) {
        Context app = context.getApplicationContext();
        if (app instanceof Application) {
            ((Application) app).registerActivityLifecycleCallbacks(lifecycleCallbacks);
        }
    }

    static final Application.ActivityLifecycleCallbacks lifecycleCallbacks =
            new Application.ActivityLifecycleCallbacks() {

                // 打开的Activity数量统计
                private int activityStartCount = 0;

                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    get().mCreatedStack.push(activity);
                }

                @Override
                public void onActivityStarted(Activity activity) {
                    get().mStartedStack.push(activity);
                    // 数值从 0 -> 1 说明是从后台切到前台
                    if (++activityStartCount == 1) {
                        get().dispatchOnFront();
                    }
                }

                @Override
                public void onActivityResumed(Activity activity) {
                    get().mResumedQueue.offer(activity);
                }

                @Override
                public void onActivityPaused(Activity activity) {
                    get().mResumedQueue.remove(activity);
                }

                @Override
                public void onActivityStopped(Activity activity) {
                    get().mStartedStack.remove(activity);
                    // 数值从 1 -> 0 说明是从前台切到后台
                    if (--activityStartCount == 0) {
                        get().dispatchOnBack();
                    }
                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    get().mCreatedStack.remove(activity);
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                }
            };

    public static void init(Context context) {
        if (sInstance == null) {
            synchronized (AppManager.class) {
                if (sInstance == null)
                    sInstance = new AppManager(context);
            }
        }
    }

    public static AppManager get() {
        if (sInstance == null) {
            L.e(AppManager.class.getSimpleName(), "AppManager 没有初始化");
        }
        return sInstance;
    }

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
     * 获取栈中所有Activity.
     *
     * @return 返回 stack.
     */
    @Nullable
    public Stack<Activity> getAllActivity() {
        return mCreatedStack;
    }

    /**
     * 判断是否包含指定 class 的 Activity.
     *
     * @param clazz activity class.
     * @return true : 包含
     */
    public boolean contains(Class<? extends Activity> clazz) {
        if (mCreatedStack != null)
            for (Activity activity : mCreatedStack) {
                if (activity.getClass().equals(clazz)) return true;
            }
        return false;
    }

    @Nullable
    public Activity getTopActivity() {
        Activity peek = mResumedQueue.peek();
        if (peek == null && !mStartedStack.empty()) {
            peek = mStartedStack.peek();
        }
        if (peek == null && !mCreatedStack.isEmpty()) {
            peek = mCreatedStack.peek();
        }
        return peek;
    }

    /**
     * 获取指定的 class 的 Activity.
     *
     * @param clazz activity class.
     * @return activity instance.
     */
    @Nullable
    public Activity getActivity(Class<? extends Activity> clazz) {
        if (mCreatedStack != null)
            for (Activity activity : mCreatedStack) {
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
        if (mCreatedStack != null) {
            for (Activity activity : mCreatedStack) {
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
        return mCreatedStack != null ? mCreatedStack.size() : 0;
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (mCreatedStack != null) {
            for (Activity activity : mCreatedStack) {
                if (activity != null) activity.finish();
            }
            mCreatedStack.clear();
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
