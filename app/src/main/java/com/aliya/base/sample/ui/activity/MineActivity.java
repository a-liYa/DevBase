package com.aliya.base.sample.ui.activity;

import android.os.Bundle;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.AppActionBar;
import com.aliya.base.sample.base.BaseActivity;
import com.aliya.base.sample.util.Utils;

/**
 * 我的页面
 *
 * @author a_liYa
 * @date 2019/3/7 下午7:41.
 */
public class MineActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        new AppActionBar(this);

        Utils.printViewTree(getWindow());
    }

}
