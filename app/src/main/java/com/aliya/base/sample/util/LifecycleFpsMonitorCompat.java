package com.aliya.base.sample.util;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * LifecycleFpsMonitor
 *
 * @author a_liYa
 * @date 2020/11/10 22:51.
 */
public final class LifecycleFpsMonitorCompat {

    public static void addObserver(final Lifecycle lifecycle, final Lifecycle.State state,
                                   final FpsMonitor.FpsListener listener) {
        lifecycle.addObserver(new LifecycleObserver() {

            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            public void onCreate() {
                if (Lifecycle.State.CREATED.isAtLeast(state)) {
                    FpsMonitor.get().registerMonitor(listener);
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            public void onStart() {
                if (Lifecycle.State.STARTED == state) {
                    FpsMonitor.get().registerMonitor(listener);
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            public void onResume() {
                if (Lifecycle.State.RESUMED == state) {
                    FpsMonitor.get().registerMonitor(listener);
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            public void onPause() {
                if (Lifecycle.State.RESUMED == state) {
                    FpsMonitor.get().unregisterMonitor(listener);
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            public void onStop() {
                if (Lifecycle.State.STARTED == state) {
                    FpsMonitor.get().unregisterMonitor(listener);
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            public void onDestroy() {
                if (Lifecycle.State.CREATED.isAtLeast(state)) {
                    FpsMonitor.get().unregisterMonitor(listener);
                }
            }
        });
    }

}
