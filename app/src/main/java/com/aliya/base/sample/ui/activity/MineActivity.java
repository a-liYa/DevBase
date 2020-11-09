package com.aliya.base.sample.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewParent;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.ActionBarActivity;

/**
 * 我的页面
 *
 * @author a_liYa
 * @date 2019/3/7 下午7:41.
 */
public class MineActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewParent parent = findViewById(android.R.id.content);
        do {
            Log.e("TAG", "onCreate: " + parent);
            parent = parent.getParent();
        } while (parent != null);
    }
}
