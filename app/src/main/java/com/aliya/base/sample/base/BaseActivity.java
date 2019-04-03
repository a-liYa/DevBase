package com.aliya.base.sample.base;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.View;
import android.view.ViewGroup;

import com.aliya.base.compat.ActivityOrientationCompat;
import com.aliya.base.compat.DensityCompat;
import com.aliya.base.sample.R;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Base activity
 *
 * @author a_liYa
 * @date 2018/9/19 15:28.
 */
public class BaseActivity extends SwipeBackActivity {

    boolean fitDensity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (ActivityOrientationCompat.allowableRequestedOrientation(this)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 强制竖屏
        }
        super.onCreate(savedInstanceState);
    }

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

    @Override
    public Resources getResources() {
        if (fitDensity) {
            return DensityCompat.forceDensityDpiByResources(super.getResources());
        }
        return super.getResources();
    }
}
