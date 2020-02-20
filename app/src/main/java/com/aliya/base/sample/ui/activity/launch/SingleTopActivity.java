package com.aliya.base.sample.ui.activity.launch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.ActionBarActivity;

/**
 * SingleTop
 *
 * @author a_liYa
 * @date 2020/2/20 22:25.
 */
public class SingleTopActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity_single_top);

        findViewById(R.id.open_single_top).setOnClickListener(new View.OnClickListener() {

            private Intent mIntent;

            @Override
            public void onClick(View v) {
                mIntent = new Intent(getApplicationContext(), SingleTopActivity.class);
                startActivity(mIntent);
            }
        });
    }
}
