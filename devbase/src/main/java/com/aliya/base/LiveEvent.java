package com.aliya.base;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Looper;

/**
 * 实现 EventBus的功能
 *
 * @author a_liYa
 * @date 2018/3/28 22:49.
 */
public class LiveEvent {

    private static volatile LiveEvent sInstance;
    private MutableLiveData<Object> mLiveData = new MutableLiveData<>();

    private LiveEvent() {
    }

    private static LiveEvent get() {
        if (sInstance == null) {
            synchronized (LiveEvent.class) {
                if (sInstance == null) {
                    sInstance = new LiveEvent();
                }
            }
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
            get().mLiveData.setValue(event);
        else
            get().mLiveData.postValue(event);
    }

    public static LiveData liveData() {
        return get().mLiveData;
    }

    private static boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

}
