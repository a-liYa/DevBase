package com.aliya.base.simpe.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aliya.base.manager.AppManager;
import com.aliya.base.simpe.R;

public class SecondActivity extends AppCompatActivity {

    private static int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void onViewClick(View v) {
        Activity activity = AppManager.get().currentActivity();
        Log.e("TAG", "onViewClick: " + activity.hashCode());
        if (count++ % 2 == 0) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
//        if (++count < 6) {
//            startActivity(new Intent(this, SecondActivity.class));
//        } else {
//            AppManager.get().finishAllActivity();
//        }
    }

}
