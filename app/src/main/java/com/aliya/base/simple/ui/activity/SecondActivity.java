package com.aliya.base.simple.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aliya.base.simple.R;
import com.aliya.base.simple.base.BaseActivity;
import com.aliya.base.simple.utils.Utils;

public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void onViewClick(View v) {

//        Utils.printViews(getWindow());
        startActivity(new Intent(this, FullscreenPageActivity.class));

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Utils.printViews(getWindow());
    }



}
