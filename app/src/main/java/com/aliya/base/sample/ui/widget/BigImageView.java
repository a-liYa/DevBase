package com.aliya.base.sample.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.Nullable;

/**
 * BigImageView
 *
 * @author a_liYa
 * @date 2020/10/20 21:54.
 */
public class BigImageView extends View {

    private Rect mRegionRect;
    private Bitmap mRegionBitmap;
    private BitmapFactory.Options mOptions;
    private GestureDetector mGestureDetector;
    private BitmapRegionDecoder mRegionDecoder;

    private int mImageWidth;
    private int mImageHeight;
    private int mViewHeight;
    private int mViewWidth;
    private float mScale;
    private Matrix mMatrix;
    private float mMinBitmapScaleForView = 1; // 局部加载的 Bitmap / View

    private Scroller mScroller;

    private GestureDetector.OnGestureListener
            mOnGestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            if (!mScroller.isFinished()) {
                mScroller.forceFinished(true);
            }
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                float distanceY) {
            mRegionRect.offset(0, Math.round(distanceY));

            // 移动超出范围，纠正
            if (mRegionRect.bottom > mImageHeight) {
                mRegionRect.bottom = mImageHeight;
                mRegionRect.top = mImageHeight - Math.round(mViewHeight / mScale);
            }
            if (mRegionRect.top < 0) {
                mRegionRect.top = 0;
                mRegionRect.bottom = Math.round(mViewHeight / mScale);
            }
            invalidate();
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            mScroller.fling(0, mRegionRect.top, 0, Math.round(-velocityY),
                    0, 0,
                    0, mImageHeight - Math.round(mViewHeight / mScale));
            return false;
        }

    };
    private long mUpMs;


    public BigImageView(Context context) {
        this(context, null);
    }

    public BigImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BigImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        mRegionRect = new Rect();
        mOptions = new BitmapFactory.Options();
        mGestureDetector = new GestureDetector(context, mOnGestureListener);
        mScroller = new Scroller(context);
        mMatrix = new Matrix();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    public void setImage(InputStream is) {
        mOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, mOptions);
        mImageWidth = mOptions.outWidth;
        mImageHeight = mOptions.outHeight;

        // 开启复用
        mOptions.inMutable = true;
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        mOptions.inJustDecodeBounds = false;

        // 区域解码器
        try {
            mRegionDecoder = BitmapRegionDecoder.newInstance(is, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 重置 top
        mRegionRect.top = 0;
        if (getMeasuredHeight() != 0 && getMeasuredWidth() != 0) {
            measureRect();
        }
        invalidate();
    }

    public void setMinBitmapScaleForView(float scale) {
        mMinBitmapScaleForView = scale;
        measureRect();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureRect();
    }

    // 测量局部区域相关信息
    private void measureRect() {
        mViewHeight = getMeasuredHeight();
        mViewWidth = getMeasuredWidth();

        mRegionRect.right = mImageWidth;
        mScale = (float) mViewWidth / mImageWidth;

        // 初步计算
        int inSampleSize = (int) (1 / (mMinBitmapScaleForView * mScale));
        if (inSampleSize < 1) inSampleSize = 1;

        int sampleSize = Bitmaps.prevPowerOf2(inSampleSize);
        mOptions.inSampleSize = sampleSize; // 四舍五入至2的幂的最终值
        mRegionRect.bottom = mRegionRect.top + Math.round(mViewHeight / mScale);

        mMatrix.setScale(mScale * sampleSize, mScale * sampleSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // TODO 耗时待优化
        if (mRegionDecoder != null) {
            mOptions.inBitmap = mRegionBitmap;
            mRegionBitmap = mRegionDecoder.decodeRegion(mRegionRect, mOptions);
            mUpMs = SystemClock.uptimeMillis();
            canvas.drawBitmap(mRegionBitmap, mMatrix, null);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (!mScroller.isFinished()) {
            if (mScroller.computeScrollOffset()) {
                mRegionRect.top = mScroller.getCurrY();
                mRegionRect.bottom = mRegionRect.top + Math.round(mViewHeight / mScale);
                invalidate();
            }
        }
    }


    /**
     * 参考：com.android.gallery3d.common.BitmapUtils
     */
    public static class Bitmaps {

        /**
         * 获取 <= n 最接近 2 的幂值
         *
         * @param n
         * @return return int <= n
         */
        public static int prevPowerOf2(int n) {
            if (n <= 0) throw new IllegalArgumentException();
            return Integer.highestOneBit(n);
        }
    }
}
