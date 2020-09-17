package com.aliya.base.sample.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;

import com.aliya.base.sample.databinding.AbcActionModeBarLayoutBinding;
import com.aliya.base.util.StatusBars;
import com.aliya.base.widget.RebornActionBar;

import androidx.annotation.Nullable;

/**
 * ActionBar 简单实现：左返回 + 中间标题 + 右边动态组合添加 action
 * 主题白色：（状态栏透明文字暗色 / 状态栏半透明文字白色）
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
        if (StatusBars.setDarkMode(activity.getWindow(), true)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }
        createView();
        setTitle(title);
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        mBinding = AbcActionModeBarLayoutBinding.inflate(inflater, parent, false);
        return mBinding.getRoot();
    }

    @Override
    protected void onViewCreated(View view) {
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

    /**
     * 往右侧添加操作按钮
     *
     * @param index 0,表示最左边，-1 表示最右边
     * @return this
     */
    public AppActionBar addRightAction(Action action, int index) {
        if (mRightParent == null) {
            inflateRightFrame();
        }
        if (action.view == null)
            action.view = action.onCreateView(mActivity.getLayoutInflater(), mRightParent);
        addRightView(action.view, index);
        return this;
    }

    public ViewGroup inflateRightFrame() {
        mRightParent = (ViewGroup) mBinding.abcRightFrameStub.inflate();
        mRightParent.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                MarginLayoutParams lp = (MarginLayoutParams) mBinding.abcTvTitle.getLayoutParams();
                lp.leftMargin = lp.rightMargin =
                        Math.max(v.getWidth(), mBinding.abcIvBack.getWidth());
                mBinding.abcTvTitle.setLayoutParams(lp);
            }
        });
        return mRightParent;
    }

    private void addRightView(View view, int index) {
        if (view.getParent() == null) {
            if (index < -1) index = -1;
            else if (index > mRightParent.getChildCount())
                index = mRightParent.getChildCount();
            mRightParent.addView(view, index);
        }
    }

    public AppActionBar removeRightAction(Action action) {
        if (action != null) {
            View view = action.getView();
            if (view != null && view.getParent() == mRightParent) {
                if (mRightParent != null) mRightParent.removeView(view);
            }
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
