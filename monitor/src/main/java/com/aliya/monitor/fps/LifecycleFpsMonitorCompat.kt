package com.aliya.monitor.fps

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.aliya.monitor.fps.FpsMonitor.FpsListener

/**
 * LifecycleFpsMonitor
 *
 * @author a_liYa
 * @date 2020/11/10 22:51.
 */
object LifecycleFpsMonitorCompat {
    fun addObserver(lifecycle: Lifecycle, state: Lifecycle.State,
                    listener: FpsListener) {
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            fun onCreate() {
                if (Lifecycle.State.CREATED.isAtLeast(state)) {
                    FpsMonitor.registerMonitor(listener)
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStart() {
                if (Lifecycle.State.STARTED == state) {
                    FpsMonitor.registerMonitor(listener)
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onResume() {
                if (Lifecycle.State.RESUMED == state) {
                    FpsMonitor.registerMonitor(listener)
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun onPause() {
                if (Lifecycle.State.RESUMED == state) {
                    FpsMonitor.unregisterMonitor(listener)
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onStop() {
                if (Lifecycle.State.STARTED == state) {
                    FpsMonitor.unregisterMonitor(listener)
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                if (Lifecycle.State.CREATED.isAtLeast(state)) {
                    FpsMonitor.unregisterMonitor(listener)
                }
            }
        })
    }
}