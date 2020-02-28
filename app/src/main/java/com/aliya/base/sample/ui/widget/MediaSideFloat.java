package com.aliya.base.sample.ui.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.aliya.base.AppUtils;
import com.aliya.base.sample.R;

/**
 * MediaSideFloat 侧边悬浮
 *
 * @author a_liYa
 * @date 2020-02-27 16.
 */
public class MediaSideFloat {

    Context mContext;

    View mView;

    ImageView mIcIcon;
    ImageView mIcPlay;
    ImageView mIcNext;
    private ValueAnimator mAnimator;
    private ObjectAnimator mIconAnimator;

    private boolean mShouldShow = false;

    public MediaSideFloat(Context context) {
        mContext = context.getApplicationContext();
        mView = onCreateView(LayoutInflater.from(mContext));
    }

    private View onCreateView(LayoutInflater inflater) {
        FrameLayout leaseRoot = new FrameLayout(inflater.getContext());
        View inflate = inflater.inflate(R.layout.layout_media_slide_float, leaseRoot, false);
        mIcIcon = inflate.findViewById(R.id.iv_icon);
        mIcPlay = inflate.findViewById(R.id.iv_play);
        mIcNext = inflate.findViewById(R.id.iv_next);

        mIcIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFold) {
                    // 折叠 -> 开启
                    unfoldFloat();
                } else {
                    // 开启 -> 折叠
                    foldFloat();
                    // todo 可能需要跳转
                }
            }
        });
        mIcPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlayStateChange(!mIcPlay.isSelected());
            }
        });

        inflate.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeSideFloat();
            }
        });
        return inflate;
    }

    /**
     * 隐藏删除悬浮窗
     */
    public void removeSideFloat() {
        ViewParent parent = mView.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(mView);
            mShouldShow = false;
        }
    }

    private void onPlayStateChange(boolean isPlay) {
        mIcPlay.setSelected(isPlay);
        animateIcon(isPlay);
    }

    public void setShouldShow(boolean shouldShow) {
        mShouldShow = shouldShow;
    }

    public boolean isShouldShow() {
        return mShouldShow;
    }

    public void attachToActivity(Activity activity) {
        ViewParent parent = mView.getParent();
        FrameLayout targetParent = activity.getWindow().findViewById(android.R.id.content);

        // 当前父布局不等于被添加父布局
        if (parent != targetParent) {
            // 1. 先从父布局删除
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(mView);
            }
            // 2. 然后添加至新Activity
            targetParent.addView(mView);
        }
        mShouldShow = true;
    }

    boolean isFold = false; // 是否为折叠状态

    private void foldFloat() {
        isFold = true;
        animateByWidth(mView.getMinimumWidth());
    }

    private void unfoldFloat() {
        isFold = false;
        animateByWidth(AppUtils.dp2px(202));
    }

    private void animateByWidth(final int end) {
        if (mAnimator != null) mAnimator.cancel();

        final int start = mView.getWidth();
        if (start == end) return;
        mAnimator = ObjectAnimator.ofInt(start, end);
        mAnimator.setDuration(200);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mView.getLayoutParams().width = (int) animation.getAnimatedValue();
                mView.requestLayout();
            }
        });
        mAnimator.start();
    }

    private void animateIcon(boolean open) {
        if (open) {
            if (mIconAnimator == null) {
                mIconAnimator = ObjectAnimator.ofFloat(mIcIcon, View.ROTATION.getName(), 0, 360);
                mIconAnimator.setRepeatCount(ObjectAnimator.INFINITE);
                mIconAnimator.setDuration(5000);
                mIconAnimator.setInterpolator(new LinearInterpolator());
                mIconAnimator.start();
            } else {
                mIconAnimator.resume();
            }
        } else {
            if (mIconAnimator != null) {
                mIconAnimator.pause();
            }
        }
    }

}
