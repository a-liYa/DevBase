package com.aliya.monitor.fps

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.SystemClock
import android.view.Choreographer

/**
 * FpsMonitor
 *
 * @author a_liYa
 * @date 2020/11/9 19:42.
 */
object FpsMonitor {
    private val WHAT_INTERVAL = 1
    private val WHAT_START = 2
    private val WHAT_STOP = 3
    private val FPS_INTERVAL_TIME = 1000L

    private var count = 0
    private val fpsRunnable = FpsRunnable()
    private val listeners: MutableList<FpsListener> = ArrayList()
    private val handler: Handler = object : Handler(Looper.getMainLooper()) {
        var timestamp: Long = 0
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                WHAT_INTERVAL -> {
                    val fps = Math.round(count.toFloat() * FPS_INTERVAL_TIME / (SystemClock.uptimeMillis() - timestamp))
                    // 倒序遍历，防止 ConcurrentModificationException
                    for (index in listeners.lastIndex downTo 0) {
                        // 防止 IndexOutOfBoundsException
                        if (index < listeners.size) {
                            listeners[index].invoke(fps)
                        }
                    }
                    count = 0
                    timestamp = SystemClock.uptimeMillis()
                    sendEmptyMessageDelayed(WHAT_INTERVAL, FPS_INTERVAL_TIME)
                }
                WHAT_START -> {
                    count = 0
                    timestamp = SystemClock.uptimeMillis()
                    sendEmptyMessageDelayed(WHAT_INTERVAL, FPS_INTERVAL_TIME)
                    Choreographer.getInstance().postFrameCallback(fpsRunnable)
                }
                WHAT_STOP -> {
                    this.removeMessages(WHAT_INTERVAL)
                    Choreographer.getInstance().removeFrameCallback(fpsRunnable)
                }
            }
        }
    }


    fun registerMonitor(listener: FpsListener) {
        synchronized(listeners) {
            if (listeners.isEmpty()) {
                handler.sendEmptyMessage(WHAT_START)
            }
            listeners.add(listener)
        }
    }

    fun unregisterMonitor(listener: FpsListener) {
        synchronized(listeners) {
            while (listeners.remove(listener));
            if (listeners.isEmpty()) {
                handler.sendEmptyMessage(WHAT_STOP)
            }
        }
    }

    private class FpsRunnable : Choreographer.FrameCallback {
        override fun doFrame(frameTimeNanos: Long) {
            count++
            Choreographer.getInstance().postFrameCallback(this)
        }
    }

    interface FpsListener {
        operator fun invoke(fps: Int)
    }
}