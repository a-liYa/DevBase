package com.aliya.base.event;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.aliya.base.util.Generics;

import java.lang.reflect.Type;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

/**
 * 实现 EventBus的功能
 * <p>
 * 1 注册事件：LiveEvent.get().observe(owner, observer)
 * 2 注销事件：LiveEvent.get().removeObserver(observer)
 * 3 发送事件：LiveEvent.post(event)
 *
 * @author a_liYa
 * @date 2018/3/28 22:49.
 */
public final class LiveEvent {

    private static volatile LiveEvent sInstance;

    private LiveDataImpl mLiveData;
    private Handler mHandler;

    private LiveEvent() {
        mLiveData = new LiveDataImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static LiveEvent get() {
        if (sInstance == null)
            synchronized (LiveEvent.class) {
                if (sInstance == null)
                    sInstance = new LiveEvent();
            }
        return sInstance;
    }

    public <T> void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
        mLiveData.observe(owner, new ObserverWrapper(observer));
    }

    public <T> void observe(@NonNull LifecycleOwner owner, @NonNull StickyObserver<T> observer) {
        mLiveData.observe(owner, new ObserverWrapper(observer));
    }

    /**
     * 发送事件
     *
     * @param event 事件
     */
    public void post(final Object event) {
        if (isMainThread())
            get().mLiveData.setValue(new DataWrapper(event));
        else
            // 如果使用 postValue() 系统慢时会导致Event丢失
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    get().mLiveData.setValue(new DataWrapper(event));
                }
            });
    }

    private static boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    static private class LiveDataImpl extends LiveData<DataWrapper> {
        @Override
        public void postValue(DataWrapper value) {
            super.postValue(value);
        }

        @Override
        public void setValue(DataWrapper value) {
            super.setValue(value);
        }
    }

    static private class ObserverWrapper<T> implements Observer<DataWrapper> {

        Observer<T> observer;
        private long timestamp;

        public ObserverWrapper(Observer<T> observer) {
            this.observer = observer;
            timestamp = SystemClock.uptimeMillis();
        }

        @Override
        public void onChanged(@Nullable DataWrapper wrapper) {
            if (observer != null) {
                Type type = Generics.getGenericType(observer.getClass(), Observer.class);
                if (wrapper.data != null && type != null) {
                    if (!((Class)type).isAssignableFrom(wrapper.data.getClass())) {
                        return;
                    }
                }
                if (observer instanceof StickyObserver) {
                    observer.onChanged((T) wrapper.data);
                } else if (timestamp <= wrapper.timestamp){
                    observer.onChanged((T) wrapper.data);
                }
            }
        }
    }

    static private class DataWrapper<T>{
        private T data;
        private long timestamp;

        public DataWrapper(T data) {
            this.data = data;
            timestamp = SystemClock.uptimeMillis();
        }
    }
}
