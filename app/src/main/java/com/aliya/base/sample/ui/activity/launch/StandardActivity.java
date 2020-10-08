package com.aliya.base.sample.ui.activity.launch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.ActionBarActivity;

/**
 * Standard
 *
 * @author a_liYa
 * @date 2020/1/5 22:43.
 */
public class StandardActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity_standard);
        findViewById(R.id.open_single_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SingleTaskActivity.class));
            }
        });
        findViewById(R.id.open_single_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SingleTopActivity.class));
            }
        });
    }
}
