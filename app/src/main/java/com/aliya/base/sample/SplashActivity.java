package com.aliya.base.sample;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.ImageView;

import com.aliya.base.sample.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

/**
 * 启动页
 *
 * @author a_liYa
 * @date 2018/9/12 下午4:34.
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.iv_logo)
    ImageView mIvLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) { // 防止App重复启动
            finish();
            return;
        }
        initFullWindow();
        setSwipeBackEnable(false);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        mIvLogo.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.splash_alpha_in, R.anim.splash_alpha_out);
                finish();
            }
        }, 1500);
        fitNotchScreen();
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
                            Log.e("TAG", "onApplyWindowInsets: " + insets);
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

    @OnClick({R.id.iv_logo})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_logo:
                mIvLogo.setImageResource(R.mipmap.splash_slogan);
                break;
        }
    }

    /**
     * 配置窗口属性 设置全屏
     */
    private void initFullWindow() {
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= 16) {
            View decorView = getWindow().getDecorView();
            // Hide both the navigation bar and the status bar.
            // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
            // a general rule, you should design your app to hide the status bar whenever you
            // hide the navigation bar.
//            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            try {
                int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View
                        .SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View
                        .SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE;
                decorView.setSystemUiVisibility(uiOptions);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
