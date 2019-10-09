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

    protected View mView;
    protected Activity mActivity;

    public RebornActionBar(Activity activity) {
        mActivity = activity;
        View viewStub = mActivity.findViewById(R.id.action_mode_bar_stub);
        if (viewStub != null) {
            ViewParent viewParent = viewStub.getParent();
            if (viewParent != null && viewParent instanceof ViewGroup) {
                ViewGroup parent = (ViewGroup) viewParent;
                mView = onCreateView(LayoutInflater.from(viewStub.getContext()), parent);
                onViewCreated(mView);
            }
        }
    }

    public void inflateActionBar() {
        if (mView != null && mView.getParent() == null) {
            View viewStub = mActivity.findViewById(R.id.action_mode_bar_stub);
            if (viewStub != null) {
                ViewParent viewParent = viewStub.getParent();
                if (viewParent != null && viewParent instanceof ViewGroup) {
                    ViewGroup parent = (ViewGroup) viewParent;
                    int indexOfViewStub = parent.indexOfChild(viewStub);
                    parent.removeViewInLayout(viewStub);
                    parent.addView(mView, indexOfViewStub);
                }
            }
        }
    }

    protected View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        return null;
    }

    protected void onViewCreated(View view) {
    }
}
