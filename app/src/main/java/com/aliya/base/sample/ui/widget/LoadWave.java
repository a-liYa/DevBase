package com.aliya.base.sample.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;

import com.aliya.base.sample.R;

/**
 * 加载水波纹 - View
 *
 * @author a_liYa
 * @date 2016/12/13 00:25.
 */
public class LoadWave extends android.support.v7.widget.AppCompatImageView {

    private PorterDuffXfermode mMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP); // 目标覆盖

    private Paint mWavePaint;

    private int mWidth;
    private int mHeight;

    private Path mPath;
    private int mOffset;

    private int mCenterY;
    private int mWaveCount;
    //浪宽
    private int mWaveLength = 700;
    //波浪振幅高
    private int mWaveHeight = 80;
    //波浪比例
    private float waveBit = 1 / 4f;
    private int mOverColorId;
    private ValueAnimator mAnimator;
    public static final long DURATION = 2500;

    private static final String NAME_ATTR_OVER_COLOR = "overColor";

    public LoadWave(Context context) {
        this(context, null);
    }

    public LoadWave(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadWave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LoadWave);
            mWavePaint.setColor(ta.getColor(R.styleable.LoadWave_overColor, Color.TRANSPARENT));
            ta.recycle();

            final int N = attrs.getAttributeCount();
            for (int i = 0; i < N; i++) {
                String attrName = attrs.getAttributeName(i);
                if (NAME_ATTR_OVER_COLOR.equals(attrName)) {
                    String attrVal = attrs.getAttributeValue(i);
                    if (!TextUtils.isEmpty(attrVal) && attrVal.startsWith("@")) {
                        String subStr = attrVal.substring(1);
                        try {
                            mOverColorId = Integer.valueOf(subStr);
                        } catch (Exception e) {
                            // no-op
                        }
                    }
                    break;
                }
            }
        }
        mPath = new Path();
        mWavePaint.setXfermode(mMode);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mWidth = getWidth();
            mHeight = getHeight();
            mWaveCount = (int) Math.round(mHeight / mWaveLength + 1.5);
        }
    }

    /**
     * 开启Load动画
     *
     * @param start true 开启； false 关闭
     */
    private void setLoadState(boolean start) {
        if (start && mAnimator == null) {
            mAnimator = ValueAnimator.ofInt(0, mWaveLength);
            mAnimator.setDuration(DURATION);
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mOffset = (int) animation.getAnimatedValue() * 3 % mWaveLength;
                    mCenterY = Math.round(mHeight * (1f - animation.getAnimatedFraction()));
                    postInvalidate();
                }
            });
        }
        if (mAnimator != null) {
            if (start) {
                if (!mAnimator.isRunning()) {
                    mAnimator.start();
                }
            } else {
                if (mAnimator.isStarted()) {
                    mAnimator.cancel();
                }
            }
        }
    }

    private ViewTreeObserver.OnPreDrawListener mOnPreDrawListener =
            new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    setLoadState(isShown());
                    return true;
                }
            };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnPreDrawListener(mOnPreDrawListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnPreDrawListener(mOnPreDrawListener);
        setLoadState(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int layer = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), null,
                Canvas.ALL_SAVE_FLAG);
        super.onDraw(canvas);

        calcPath();
        canvas.drawPath(mPath, mWavePaint);

        canvas.restoreToCount(layer);
    }

    private void calcPath() {
        mPath.reset();
        mPath.moveTo(-mWaveLength + mOffset, mCenterY);
        for (int i = 0; i < mWaveCount; i++) { // 50是波纹的大小
            mPath.quadTo((-mWaveLength * (1 - waveBit)) + (i * mWaveLength) + mOffset, mCenterY +
                    mWaveHeight, (-mWaveLength / 2) + (i * mWaveLength) + mOffset, mCenterY);

            mPath.quadTo((-mWaveLength * waveBit) + (i * mWaveLength) + mOffset, mCenterY -
                    mWaveHeight, i * mWaveLength + mOffset, mCenterY);
        }

        mPath.lineTo(mWidth, 0);
        mPath.lineTo(0, 0);
        mPath.close();
    }
}
