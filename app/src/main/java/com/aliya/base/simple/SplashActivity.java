package com.aliya.base.simple;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.aliya.base.simple.base.BaseActivity;

/**
 * 启动页
 *
 * @author a_liYa
 * @date 2018/9/12 下午4:34.
 */
public class SplashActivity extends BaseActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) { // 防止App重复启动
            finish();
            return;
        }
        setContentView(R.layout.activity_splash);

        findViewById(android.R.id.content).postDelayed(this, 2000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            return true; // 拦截返回键
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void run() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(0, R.anim.alpha_out);
        finish();
    }

}
