package com.aliya.monitor

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.aliya.monitor.fps.MonitorService

/**
 * AppFpsMonitor 单例
 *
 * @author a_liYa
 * @date 2021/5/4 11:17.
 *
 */
object AppFpsMonitor {

    private lateinit var context: Context

    @JvmStatic
    fun init(context: Context) {
        if (!this::context.isInitialized) { // 判断是否初始化，对应 Java 就是判空
            this.context = context.applicationContext
            (this.context as Application).registerActivityLifecycleCallbacks(
                    object : Application.ActivityLifecycleCallbacks {

                        private var activityStartCount = 0

                        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                        }

                        override fun onActivityStarted(activity: Activity) {
                            // 数值从 0 -> 1 说明是从后台切到前台
                            if (++activityStartCount == 1) {
                                showMonitor()
                            }
                        }

                        override fun onActivityResumed(activity: Activity) {
                        }

                        override fun onActivityPaused(activity: Activity) {
                        }

                        override fun onActivityStopped(activity: Activity) {
                            // 数值从 1 -> 0 说明是从前台切到后台
                            if (--activityStartCount == 0) {
                                hideMonitor()
                            }
                        }

                        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                        }

                        override fun onActivityDestroyed(activity: Activity) {
                        }

                    })
        }
    }

    fun showMonitor() {
        val intent = Intent(context, MonitorService::class.java)
        context.startService(intent)
    }

    fun hideMonitor() {
        context.stopService(Intent(context, MonitorService::class.java))
    }

}