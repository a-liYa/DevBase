package com.aliya.base.util;

/**
 * Singleton
 * 参考自 { @link android.util.Singleton}
 *
 * Use examples are as follows:
 *
 * public class SingletonClass {
 *
 *     static final Singleton<SingletonClass> sSingleton = new Singleton<SingletonClass>() {
 *         @Override
 *         protected SingletonClass create() {
 *             return new SingletonClass();
 *         }
 *     };
 *
 *     public static SingletonClass getInstance(){
 *         return sSingleton.get();
 *     }
 * }
 *
 * =================================================================================================
 *
 * // 静态内部类实现单例模式:
 * public class SingletonClass {
 *     private static class Singleton {
 *         private static Singleton sSingleton = new Singleton();
 *     }
 *
 *     private SingletonClass(){}
 *
 *     public static Singleton getSingleton(){
 *         return Singleton.sSingleton;
 *     }
 * }
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