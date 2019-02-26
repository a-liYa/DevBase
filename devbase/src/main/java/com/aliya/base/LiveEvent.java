package com.aliya.base;

import android.arch.lifecycle.LiveData;
import android.os.Looper;

/**
 * 实现 EventBus的功能
 * <p>
 * 1 注册事件：LiveEvent.liveData().observe(owner, observer)
 * 2 注销事件：LiveEvent.liveData().removeObserver(observer)
 * 3 发送事件：LiveEvent.post(event)
 *
 * @author a_liYa
 * @date 2018/3/28 22:49.
 */
public class LiveEvent extends LiveData {

    private static volatile LiveEvent sInstance;

    private LiveEvent() {
    }

    public static LiveEvent get() {
        if (sInstance == null)
            synchronized (LiveEvent.class) {
                if (sInstance == null)
                    sInstance = new LiveEvent();
            }
        return sInstance;
    }

    /**
     * 发送事件
     *
     * @param event 事件
     */
    public static void post(Object event) {
        if (isMainThread())
            get().setValue(event);
        else
            get().postValue(event);
    }

    public static LiveData liveData() {
        return get();
    }

    private static boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

}
