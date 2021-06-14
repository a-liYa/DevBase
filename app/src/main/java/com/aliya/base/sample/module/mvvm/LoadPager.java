package com.aliya.base.sample.module.mvvm;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.aliya.base.sample.R;
import com.aliya.base.sample.databinding.AbcPageLoadBinding;
import com.aliya.base.util.L;
import com.aliya.base.util.Views;

/**
 * LoadPager
 *
 * @author a_liYa
 * @date 2021/6/12 12:12.
 */
public class LoadPager {

    /*
     * Bug：
     *  1. 当 mPageParent = ConstraintLayout 时，存在问题
     */
    private static final String TAG = "LoadPager";
    private View mPageView;
    private ViewGroup mPageParent;
    private ViewGroup.LayoutParams mPageViewLayoutParams;
    private int mPageViewIndex = -1;

    private AbcPageLoadBinding mViewBinding;

    private View mFailedLayout;


    private PagerAction mAction;

    public LoadPager(View pageView) {
        this.mPageView = pageView;

         mPageParent = (ViewGroup) pageView.getParent();

        mPageViewLayoutParams = mPageView.getLayoutParams();

        if (mPageParent == null) {
            L.e(TAG, "LoadPager pageView.getParent() can't be null");
            return;
        }

        mViewBinding = AbcPageLoadBinding.bind(
                Views.inflate(mPageParent, R.layout.abc_page_load, false));
    }


    public void setAction(PagerAction action) {
        mAction = action;
    }

    // 显示加载中
    public void showLoading() {
        // 确保 mPageView 从原来父布局上删除
        if (mPageView.getParent() == mPageParent) {
            mPageViewIndex = mPageParent.indexOfChild(mPageView);
            mPageParent.removeView(mPageView);
        }

        // 确保 LoadPager 添加到父布局上
        if (mViewBinding.getRoot().getParent() != mPageParent) {
            mPageParent.addView(mViewBinding.getRoot(), mPageViewIndex, mPageViewLayoutParams);
        }


        mViewBinding.loadingLayout.setVisibility(View.VISIBLE);
        if (mFailedLayout != null) {
            mFailedLayout.setVisibility(View.GONE);
        }
    }

    // 显示失败页
    public void showFailed() {
        mViewBinding.loadingLayout.setVisibility(View.GONE);
        if (mFailedLayout == null) {
            mFailedLayout = mViewBinding.vsFailedLayout.inflate();
            mFailedLayout.findViewById(R.id.reload).setOnClickListener(v -> {
                if (mAction != null) mAction.onRetry();
            });
        } else {
            mFailedLayout.setVisibility(View.VISIBLE);
        }
    }

    // 完成页面加载
    public void finishLoad() {
        final ViewGroup parent = (ViewGroup) mViewBinding.getRoot().getParent();
        if (parent != null) {
            // 先原页面恢复位置，其次动画消失加载页面
            parent.addView(mPageView, mPageViewIndex, mPageViewLayoutParams);

            mViewBinding.getRoot().animate().alpha(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    parent.removeView(mViewBinding.getRoot());
                    mViewBinding.getRoot().setAlpha(1);
                }
            });
        }
    }

    public interface PagerAction {

        void onRetry();

    }

}
