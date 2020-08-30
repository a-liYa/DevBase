package com.aliya.base.sample;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;

import com.aliya.base.sample.base.BaseActivity;
import com.aliya.base.sample.databinding.ActivitySplashBinding;

/**
 * 启动页
 *
 * @author a_liYa
 * @date 2018/9/12 下午4:34.
 */
public class SplashActivity extends BaseActivity implements View.OnClickListener {

    ActivitySplashBinding mViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTheme().applyStyle(R.style.FontFamilySystem, true);
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) { // 防止App重复启动
            finish();
            return;
        }
        setSwipeBackEnable(false);
        mViewBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());
        mViewBinding.ivLogo.setOnClickListener(this);
        mViewBinding.ivLogo.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.splash_alpha_in, R.anim.splash_alpha_out);
                finish();
            }
        }, 1500);
        fitNotchScreen();

        Log.e("TAG", "启动页开启耗时: " + (SystemClock.uptimeMillis() - App.sMillis));
    }

    private void fitNotchScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // 全屏适配异面屏(刘海、水滴、黑瞳)
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            getWindow().getDecorView().setOnApplyWindowInsetsListener(
                    new View.OnApplyWindowInsetsListener() {
                        @Override
                        public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                                DisplayCutout displayCutout = insets.getDisplayCutout();
                                if (displayCutout != null) {
                                    displayCutout.getBoundingRects(); // 获取非功能区域集合
                                    displayCutout.getSafeInsetTop(); // 获取安全区域距离屏幕顶部的距离
                                    displayCutout.getSafeInsetBottom(); // 获取安全区域距离屏幕底部的距离
                                }
                            } else {
                                insets.getStableInsetTop(); // 获取距离屏幕顶部的稳定距离
                            }
//                            Log.e("TAG", "onApplyWindowInsets: " + insets);
                            return v.onApplyWindowInsets(insets);
                        }
                    });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            return true; // 拦截返回键
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_logo:
                mViewBinding.ivLogo.setImageResource(R.mipmap.splash_slogan);
                break;
        }
    }
}
