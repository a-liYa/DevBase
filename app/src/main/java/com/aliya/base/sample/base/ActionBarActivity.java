package com.aliya.base.sample.base;

import android.os.Bundle;

/**
 * ActionBarActivity
 *
 * @author a_liYa
 * @date 2019-10-08 20:18.
 */
public class ActionBarActivity extends BaseActivity {

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        onCreateActionBar();
    }

    protected void onCreateActionBar() {
        new AppActionBar(this);
    }
}
