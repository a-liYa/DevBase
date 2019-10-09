package com.aliya.base.sample.ui.activity;

import android.os.Bundle;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.ActionBarActivity;
import com.aliya.base.sample.util.Utils;

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
        findViewById(R.id.action_mode_bar_stub);
        setContentView(R.layout.activity_mine);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Utils.printViewTree(getWindow());
    }
}
