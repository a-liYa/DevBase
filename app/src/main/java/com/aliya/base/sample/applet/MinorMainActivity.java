package com.aliya.base.sample.applet;

import android.os.Bundle;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.BaseActivity;

/**
 * 次要的 MainActivity
 *
 * @author a_liYa
 * @date 2020/10/8 21:01.
 */
public class MinorMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minor_main);
    }
}