package com.aliya.base;

import android.util.Log;

/**
 * Log日志类
 *
 * @author a_liYa
 * @date 2016-3-30 下午11:44:55
 */
public final class L {

    private static long startMillis;

    private L() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static final String TAG = AppUtils.getAppName();

    /* 下面四个是默认tag的函数 */
    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void v(String msg) {
        v(TAG, msg);
    }

    /* 下面是传入自定义tag的函数 */
    public static void i(String tag, String msg) {
        if (AppUtils.isDebuggable())
            Log.i(tag == null ? TAG : tag, msg);
    }

    public static void d(String tag, String msg) {
        if (AppUtils.isDebuggable())
            Log.d(tag == null ? TAG : tag, msg);
    }

    public static void e(String tag, String msg) {
        if (AppUtils.isDebuggable())
            Log.e(tag == null ? TAG : tag, msg);
    }

    public static void v(String tag, String msg) {
        if (AppUtils.isDebuggable())
            Log.v(tag == null ? TAG : tag, msg);
    }

    public static void markStart() {
        startMillis = System.currentTimeMillis();
    }

    public static void markEnd(String tag) {
        e(tag, "时长：" + (System.currentTimeMillis() - startMillis) + "毫秒");
    }
}
