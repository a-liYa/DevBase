package com.aliya.base.sample.util;

import android.os.Handler;
import android.os.Looper;
import android.view.Choreographer;

import java.util.ArrayList;
import java.util.List;

/**
 * FpsMonitor
 *
 * @author a_liYa
 * @date 2020/11/9 19:42.
 */
public final class FpsMonitor {
    private static final long FPS_INTERVAL_TIME = 1000L;
    private int count = 0;
    private boolean isFpsOpen = false;
    private FpsRunnable fpsRunnable = new FpsRunnable();
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private List<FpsCallback> listeners = new ArrayList<>();

    public void startMonitor(FpsCallback callback) {
        // 防止重复开启
        if (!isFpsOpen) {
            isFpsOpen = true;
            listeners.add(callback);
            mainHandler.postDelayed(fpsRunnable, FPS_INTERVAL_TIME);
            Choreographer.getInstance().postFrameCallback(fpsRunnable);
        }
    }

    public void stopMonitor() {
        count = 0;
        mainHandler.removeCallbacks(fpsRunnable);
        Choreographer.getInstance().removeFrameCallback(fpsRunnable);
        isFpsOpen = false;
    }

    class FpsRunnable implements Choreographer.FrameCallback, Runnable {

        @Override
        public void doFrame(long frameTimeNanos) {
            count++;
            Choreographer.getInstance().postFrameCallback(this);
        }

        @Override
        public void run() {
            for (FpsCallback callback: listeners) {
                callback.invoke(count);
            }
            count = 0;
            mainHandler.postDelayed(this, FPS_INTERVAL_TIME);
        }
    }

    public interface FpsCallback {

        void invoke(int fps);
    }
}
