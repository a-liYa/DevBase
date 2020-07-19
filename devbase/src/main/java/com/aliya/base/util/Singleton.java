package com.aliya.base.util;

/**
 * Singleton
 * 参考自 {Android-23 @link android.util.Singleton}
 *
 * @author a_liYa
 * @date 2020/7/19 10:25.
 */
public abstract class Singleton<T> {

    private volatile T mInstance;

    protected abstract T create();

    public final T get() {
        if (mInstance == null) {
            synchronized (this) {
                if (mInstance == null) {
                    mInstance = create();
                }
            }
        }
        return mInstance;
    }
}
