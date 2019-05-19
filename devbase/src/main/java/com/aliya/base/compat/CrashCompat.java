package com.aliya.base.compat;

import java.util.concurrent.TimeoutException;

/**
 * CrashCompat
 *
 * @author a_liYa
 * @date 2019/5/19 10:12.
 */
public class CrashCompat {

    public static void init() {
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtHandler());
    }

    private static class UncaughtHandler implements Thread.UncaughtExceptionHandler {

        Thread.UncaughtExceptionHandler mDefaultUncaughtHandler;

        public UncaughtHandler() {
            mDefaultUncaughtHandler = Thread.getDefaultUncaughtExceptionHandler();
        }

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            if ("FinalizerWatchdogDaemon".equals(t.getName()) && e instanceof TimeoutException) {
                // ignore it. java.util.concurrent.TimeoutException - xxx.finalize()
            } else {
                mDefaultUncaughtHandler.uncaughtException(t, e);
            }
        }
    }

}
