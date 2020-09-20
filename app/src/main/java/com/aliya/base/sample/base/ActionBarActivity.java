package com.aliya.base.sample.base;

import android.os.Bundle;

import androidx.annotation.CallSuper;

/**
 * ActionBarActivity
 *
 * @author a_liYa
 * @date 2019-10-08 20:18.
 */
public class ActionBarActivity extends BaseActivity {

    protected AppActionBar mActionBar;

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar = onCreateActionBar();
        if (mActionBar != null) {
            mActionBar.attachToWindow();
        }
    }

    // 钩子函数，子类重写返回 null, 可抑制 ActionBar 的创建，实现 Lazy Loading
    protected AppActionBar onCreateActionBar() {
        return new AppActionBar(this);
    }

    /**
     * 借助 Android Api 同步修改我们的 title
     */
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (mActionBar != null) {
            mActionBar.setTitle(title);
        }
    }
}
