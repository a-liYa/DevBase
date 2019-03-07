package com.aliya.base.sample.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.BaseActivity;
import com.aliya.base.sample.mock.Colors;

/**
 * 普通页面示例
 *
 * @author a_liYa
 * @date 2018/11/26 下午3:11.
 */
public class NormalPageActivity extends BaseActivity implements View.OnClickListener {

    public int mColorIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_page);
        findViewById(R.id.tv_status_bar_color).setOnClickListener(this);
        findViewById(R.id.tv_navigation_bar_color).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_status_bar_color:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(obtainColor());
                }
                break;
            case R.id.tv_navigation_bar_color:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setNavigationBarColor(obtainColor());
                }
                break;
        }
    }

    public int obtainColor() {
        if (mColorIndex >= Colors.COLOR_IDS.length) {
            mColorIndex = 0;
        }
        return ContextCompat.getColor(this, Colors.COLOR_IDS[mColorIndex++]);
    }

}
