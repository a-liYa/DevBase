package com.aliya.base.simple.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.View;
import android.view.ViewGroup;

import com.aliya.base.simple.R;

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
        ((ViewGroup) getWindow().getDecorView()).getChildAt(0).setFitsSystemWindows(false);
        View view = findViewById(R.id.action_bar_root);
        if (view != null) {
            view.setFitsSystemWindows(false);
        }
        super.onPostCreate(savedInstanceState);
    }

}
