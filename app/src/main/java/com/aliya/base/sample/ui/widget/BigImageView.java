package com.aliya.base.sample.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Scroller;

import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import static java.lang.Math.round;

/**
 * BigImageView
 *
 * @author a_liYa
 * @date 2020/10/20 21:54.
 */
public class BigImageView extends AppCompatImageView {

    private Rect mRect;
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
            mRect.offset(0, round(distanceY));

            // 移动超出范围，纠正
            if (mRect.bottom > mImageHeight) {
                mRect.bottom = mImageHeight;
                mRect.top = mImageHeight - round(mViewHeight / mScale);
            }
            if (mRect.top < 0) {
                mRect.top = 0;
                mRect.bottom = round(mViewHeight / mScale);
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
            mScroller.fling(0, mRect.top, 0, round(-velocityY),
                    0, 0,
                    0, mImageHeight - round(mViewHeight / mScale));
            return false;
        }

    };


    public BigImageView(Context context) {
        this(context, null);
    }

    public BigImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BigImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRect = new Rect();
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
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewHeight = getMeasuredHeight();
        mViewWidth = getMeasuredWidth();

        mRect.left = 0;
        mRect.top = 0;
        mRect.right = mImageWidth;
        mScale = (float) mViewWidth / mImageWidth;
        mRect.bottom = round(mViewHeight / mScale);
        mMatrix.setScale(mScale, mScale);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mRegionDecoder != null) {
            mOptions.inBitmap = mRegionBitmap;
//            mOptions.inSampleSize = 2;
            mRegionBitmap = mRegionDecoder.decodeRegion(mRect, mOptions);
            canvas.drawBitmap(mRegionBitmap, mMatrix, null);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (!mScroller.isFinished()) {
            if (mScroller.computeScrollOffset()) {
                mRect.top = mScroller.getCurrY();
                mRect.bottom = mRect.top + round(mViewHeight / mScale);
                invalidate();
            }
        }
    }
}
