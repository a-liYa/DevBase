package com.aliya.base.sample.base;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliya.base.sample.R;
import com.aliya.base.sample.databinding.AbcActionModeBarLayoutBinding;
import com.aliya.base.widget.RebornActionBar;

import androidx.annotation.Nullable;

/**
 * ActionBar 简单实现：左返回 + 中间标题
 *
 * @author a_liYa
 * @date 2019-10-08 19:13.
 */
public class AppActionBar extends RebornActionBar {

    private AbcActionModeBarLayoutBinding mBinding;
    private ViewGroup mRightParent;

    public AppActionBar(Activity activity) {
        this(activity, activity.getTitle());
    }

    public AppActionBar(Activity activity, CharSequence title) {
        super(activity);
        createView();
        setTitle(title);
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.abc_action_mode_bar_layout, parent, false);
    }

    @Override
    protected void onViewCreated(View view) {
        mBinding = AbcActionModeBarLayoutBinding.bind(view);

        if (mBinding.abcIvBack != null) {
            mBinding.abcIvBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.onBackPressed();
                }
            });
        }
    }

    public void setTitle(CharSequence title) {
        if (mBinding.abcTvTitle != null)
            mBinding.abcTvTitle.setText(title);
    }

    public AppActionBar addRightAction(Action action) {
        if (mRightParent == null) {
            mRightParent = (ViewGroup) mBinding.abcRightFrameStub.inflate();
        }
        if (action.view == null)
            action.view = action.onCreateView(mActivity.getLayoutInflater(), mRightParent);
        addRightView(action.view);
        return this;
    }

    private void addRightView(View view) {
        if (mRightParent == null) {
            mRightParent = (ViewGroup) mBinding.abcRightFrameStub.inflate();
        }
        if (view.getParent() == null)
            mRightParent.addView(view);
    }

    public AppActionBar removeRightAction(Action action) {
        View view = action.getView();
        if (view != null && view.getParent() == mRightParent) {
            if (mRightParent != null) mRightParent.removeView(view);
        }
        return this;
    }

    public static abstract class Action {
        View view;

        @Nullable // 在 addRightAction 之前返回 null
        public View getView() {
            return view;
        }

        protected abstract View onCreateView(LayoutInflater inflater, ViewGroup parent);
    }

}
