package com.aliya.monitor.fps

import android.app.AppOpsManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.Process
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import com.aliya.monitor.R
import com.aliya.monitor.databinding.MonitorLayoutMonitorOverlayBinding
import com.aliya.monitor.fps.FpsMonitor.FpsListener

class MonitorService : Service(), FpsListener {
    private lateinit var windowManager: WindowManager
    private lateinit var layoutParams: WindowManager.LayoutParams
    private lateinit var mBinding: MonitorLayoutMonitorOverlayBinding

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        initWindowParams()
        mBinding = MonitorLayoutMonitorOverlayBinding.inflate(LayoutInflater.from(application))
        FpsMonitor.registerMonitor(this)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (mBinding.root.parent == null) {
            showView()
        } else {
            hideView()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun initWindowParams() {
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        layoutParams = WindowManager.LayoutParams()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 8.0以上只能使用 TYPE_APPLICATION_OVERLAY窗口类型来创建悬浮窗
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE
        }
        layoutParams.format = PixelFormat.RGBA_8888
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        layoutParams.gravity = Gravity.LEFT or Gravity.BOTTOM
        // 初始位置坐标, 相对于Gravity而言，eg：Gravity = LEFT|TOP 坐标原点在左上角； Gravity = LEFT|BOTTOM 坐标原点在左下角。
        // layoutParams.x = ;
        layoutParams.y = application.resources
                .getDimension(R.dimen.monitor_overlay_bottom_margin).toInt()
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
    }

    override fun onDestroy() {
        FpsMonitor.unregisterMonitor(this)
        hideView()
    }

    var fps = 0
    override fun invoke(fps: Int) {
        if (this.fps != fps) {
            mBinding.tvFps.text = fps.toString() + "fps"
            this.fps = fps
        }
    }

    private fun showView() {
        if (canDrawOverlays()) {
            windowManager.addView(mBinding.root, layoutParams)
        } else {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            intent.data = Uri.parse("package:$packageName")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private fun hideView() {
        if (mBinding.root.parent != null) {
            windowManager.removeView(mBinding.root)
        }
    }

    private fun canDrawOverlays(): Boolean {
        val context: Context = application
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) true else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            try {
                var cls = Class.forName("android.content.Context")
                val declaredField = cls.getDeclaredField("APP_OPS_SERVICE")
                declaredField.isAccessible = true
                var obj: Any? = declaredField[cls] as? String ?: return false
                val str2 = obj as String
                obj = cls.getMethod("getSystemService", String::class.java).invoke(context, str2)
                cls = Class.forName("android.app.AppOpsManager")
                val declaredField2 = cls.getDeclaredField("MODE_ALLOWED")
                declaredField2.isAccessible = true
                val checkOp = cls.getMethod("checkOp", Integer.TYPE, Integer.TYPE, String::class.java)
                val result = checkOp.invoke(obj, 24, Binder.getCallingUid(),
                        context.packageName) as Int
                result == declaredField2.getInt(cls)
            } catch (e: Exception) {
                false
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val appOpsMgr = context.getSystemService(APP_OPS_SERVICE) as AppOpsManager
                        ?: return false
                val mode = appOpsMgr.checkOpNoThrow("android:system_alert_window",
                        Process.myUid(), context.packageName)
                Settings.canDrawOverlays(context) || mode == AppOpsManager.MODE_ALLOWED || mode == AppOpsManager.MODE_IGNORED
            } else {
                Settings.canDrawOverlays(context)
            }
        }
    }
}