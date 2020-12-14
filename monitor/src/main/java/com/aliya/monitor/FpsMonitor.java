package com.aliya.monitor;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
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
    static final int WHAT_INTERVAL = 1;
    static final int WHAT_START = 2;
    static final int WHAT_STOP = 3;
    static final long FPS_INTERVAL_TIME = 1000L;
    private int count = 0;
    private FpsRunnable fpsRunnable = new FpsRunnable();
    private List<FpsListener> listeners = new ArrayList<>();

    private Handler handler = new Handler(Looper.getMainLooper()) {

        long timestamp;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_INTERVAL:
                    int fps =
                            Math.round((float) count * FPS_INTERVAL_TIME / (SystemClock.uptimeMillis() - timestamp));
                    // 倒序遍历，防止 ConcurrentModificationException
                    for (int i = listeners.size() - 1; i >= 0; i--) {
                        if (i >= listeners.size()) // 防止 IndexOutOfBoundsException
                            continue;
                        listeners.get(i).invoke(fps);
                    }
                    count = 0;
                    timestamp = SystemClock.uptimeMillis();
                    handler.sendEmptyMessageDelayed(WHAT_INTERVAL, FPS_INTERVAL_TIME);
                    break;
                case WHAT_START:
                    count = 0;
                    timestamp = SystemClock.uptimeMillis();
                    handler.sendEmptyMessageDelayed(WHAT_INTERVAL, FPS_INTERVAL_TIME);
                    Choreographer.getInstance().postFrameCallback(fpsRunnable);
                    break;
                case WHAT_STOP:
                    handler.removeMessages(WHAT_INTERVAL);
                    Choreographer.getInstance().removeFrameCallback(fpsRunnable);
                    break;

            }
        }
    };

    public static FpsMonitor get() {
        return Singleton.sInstance;
    }

    private static class Singleton {
        private static final FpsMonitor sInstance = new FpsMonitor();
    }

    private FpsMonitor() {
    }

    public void registerMonitor(FpsListener listener) {
        synchronized (listeners) {
            if (listeners.isEmpty()) {
                handler.sendEmptyMessage(WHAT_START);
            }
            listeners.add(listener);
        }
    }

    public void unregisterMonitor(FpsListener listener) {
        synchronized (listeners) {
            while (listeners.remove(listener)) ;
            if (listeners.isEmpty()) {
                handler.sendEmptyMessage(WHAT_STOP);
            }
        }
    }

    private class FpsRunnable implements Choreographer.FrameCallback {

        @Override
        public void doFrame(long frameTimeNanos) {
            count++;
            Choreographer.getInstance().postFrameCallback(this);
        }

    }

    public interface FpsListener {

        void invoke(int fps);
    }
}
