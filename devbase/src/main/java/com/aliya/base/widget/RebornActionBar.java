package com.aliya.base.widget;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.aliya.base.R;

import androidx.core.view.ViewCompat;

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
    private View mActionBarStub;

    public RebornActionBar(Activity activity) {
        mActivity = activity;
    }

    public void createView() {
        if (mActionBarStub == null) {
            mActionBarStub = mActivity.findViewById(R.id.action_mode_bar_stub);
        }
        ViewParent viewParent = mActionBarStub.getParent();
        if (viewParent != null && viewParent instanceof ViewGroup) {
            mView = onCreateView(mActivity.getLayoutInflater(), (ViewGroup) viewParent);
            onViewCreated(mView);
        }
    }

    public void attachToWindow() {
        if (mView == null) createView();

        if (mView != null && mView.getParent() == null) {
            if (mActionBarStub == null) {
                mActionBarStub = mActivity.findViewById(R.id.action_mode_bar_stub);
            }
            ViewParent viewParent = mActionBarStub.getParent();
            if (viewParent != null && viewParent instanceof ViewGroup) {
                ViewGroup parent = (ViewGroup) viewParent;
                int indexOfViewStub = parent.indexOfChild(mActionBarStub);
                parent.removeViewInLayout(mActionBarStub);
                parent.addView(mView, indexOfViewStub);
                ViewCompat.requestApplyInsets(mView);
            }
            mActionBarStub = null;
        }
    }

    protected abstract View onCreateView(LayoutInflater inflater, ViewGroup parent);

    protected void onViewCreated(View view) {
    }
}
