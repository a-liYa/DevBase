package com.aliya.base.simpe.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aliya.base.simpe.R;

public class SecondActivity extends AppCompatActivity {

    private static int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void onViewClick(View v) {
//        if (++count < 6) {
//            startActivity(new Intent(this, SecondActivity.class));
//        } else {
//            AppManager.get().finishAllActivity();
//        }

    }

}
