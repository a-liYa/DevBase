package com.aliya.base.widget;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.aliya.base.R;

/**
 * 伪复兴的ActionBar
 * <p>
 * 若实现悬浮功能，在 Activity#onCreate() 内部使用如下代码
 * <code>
 * AppCompatActivity#getDelegate().requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
 * </code>
 *
 * @author a_liYa
 * @date 2019-10-08 18:49.
 */
public abstract class RebornActionBar {

    protected Activity mActivity;

    public RebornActionBar(Activity activity) {
        mActivity = activity;
        View viewStub = mActivity.findViewById(R.id.action_mode_bar_stub);
        if (viewStub != null) {
            ViewParent viewParent = viewStub.getParent();
            if (viewParent != null && viewParent instanceof ViewGroup) {
                ViewGroup parent = (ViewGroup) viewParent;
                View view = onCreateView(LayoutInflater.from(viewStub.getContext()), parent);
                onViewCreated(view);
                int indexOfViewStub = parent.indexOfChild(viewStub);
                parent.removeViewInLayout(viewStub);
                parent.addView(view, indexOfViewStub);
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        return null;
    }

    public void onViewCreated(View view) {
    }
}
