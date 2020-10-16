package com.aliya.base.sample.ui.activity.launch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.ActionBarActivity;

/**
 *  SingleInstance
 *
 * @author a_liYa
 * @date 2020/1/5 22:11.
 */
public class SingleInstanceActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity_single_instance);

        findViewById(R.id.tv_standard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StandardActivity.class));
            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("TAG", "SingleInstanceActivity#onNewIntent()");
    }
}
