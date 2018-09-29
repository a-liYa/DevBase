package com.aliya.base.simpe.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.ViewGroup;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Base activity
 *
 * @author a_liYa
 * @date 2018/9/19 15:28.
 */
public class BaseActivity extends SwipeBackActivity {

    @CallSuper
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ((ViewGroup) getWindow().getDecorView()).getChildAt(0).setFitsSystemWindows(false);
    }
}
