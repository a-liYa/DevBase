package com.aliya.base.event;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;

import com.aliya.base.util.Generics;

import java.lang.reflect.Type;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

/**
 * 实现 EventBus的功能
 * <p>
 * 1 注册事件：LiveEvent.get().observe(owner, observer)
 * 2 注销事件：LiveEvent.get().removeObserver(observer)
 * 3 发送事件：LiveEvent.post(event)
 *
 * @author a_liYa
 * @date 2019/5/13 22:49.
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

    public <T> void observe(@NonNull Observer<T> observer) {
        if (observer.rawObserver != null) return; // 防止重复注册

        mLiveData.observeForever(new ObserverWrapper(observer));
    }

    public <T> void observe(@NonNull  LifecycleOwner owner, @NonNull Observer<T> observer) {
        if (observer.rawObserver != null) return; // 防止重复注册

        mLiveData.observe(owner, new ObserverWrapper(observer));
    }

    public  <T> void observe(@Nullable View with, @NonNull final Observer<T> observer) {
        if (observer.rawObserver != null) return; // 防止重复注册

        mLiveData.observeForever(new ObserverWrapper(observer));
        with.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                get().removeObserver(observer);
            }
        });

    }

    public void removeObserver(Observer observer) {
        if (observer != null && observer.rawObserver != null)
            mLiveData.removeObserver(observer.rawObserver);
    }

    public void removeObservers(LifecycleOwner owner) {
        mLiveData.removeObservers(owner);
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

    static private class ObserverWrapper<T> implements androidx.lifecycle.Observer<DataWrapper> {

        Observer<T> observer;
        private long timestamp; // 注册时的时间戳

        public ObserverWrapper(Observer<T> observer) {
            this.observer = observer;
            observer.rawObserver = this;
            timestamp = SystemClock.uptimeMillis();
        }

        @Override
        public void onChanged(@Nullable DataWrapper dataWrapper) {
            if (observer != null) {
                Type type = Generics.getGenericType(observer.getClass(), Observer.class);
                if (dataWrapper.data != null && type != null) {
                    if (!((Class) type).isAssignableFrom(dataWrapper.data.getClass())) {
                        return;
                    }
                }

                // 只接收注册之后发出的事件
                if (timestamp <= dataWrapper.timestamp) {
                    observer.onEvent((T) dataWrapper.data);
                } else {
                    // 此处可处理注册前一个事件，暂时不考虑这种情况
                }
            }
        }
    }

    static private class DataWrapper<T> {
        private T data;
        private long timestamp;

        public DataWrapper(T data) {
            this.data = data;
            timestamp = SystemClock.uptimeMillis();
        }
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

        @Override
        public void removeObserver(androidx.lifecycle.Observer<? super DataWrapper> observer) {
            super.removeObserver(observer);
            if (observer instanceof ObserverWrapper) {
                // 置空，表示删除
                ((ObserverWrapper) observer).observer.rawObserver = null;
            }
        }
    }

    /**
     * LiveEvent 事件观察者
     *
     * @param <T> 事件数据
     */
    static public abstract class Observer<T> {

        private ObserverWrapper rawObserver;

        public abstract void onEvent(T event);
    }
}
