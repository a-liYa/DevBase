package com.aliya.base.sample.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
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
public class ImageSurfaceView extends SurfaceView{

    private Rect mRegionRect;
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
    private Handler mWorkHandler;

    static final int WHAT_DRAW = 1;

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
            sendDrawMessage();
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
            invalidate();
            return false;
        }

    };

    public ImageSurfaceView(Context context) {
        this(context, null);
    }

    public ImageSurfaceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageSurfaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRegionRect = new Rect();
        mOptions = new BitmapFactory.Options();
        mGestureDetector = new GestureDetector(context, mOnGestureListener);
        mScroller = new Scroller(context);
        mMatrix = new Matrix();
        getHolder().addCallback(new SurfaceHolder.Callback(){
            Bitmap regionBitmap;
            HandlerThread handlerThread;
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                handlerThread = new HandlerThread(getClass().getName());
                handlerThread.start();
                mWorkHandler = new Handler(handlerThread.getLooper()) {
                    final Rect rect = new Rect();

                    @Override
                    public void handleMessage(Message msg) {
                        if (mRegionDecoder != null && !mRegionRect.equals(rect)) {
                            Canvas canvas = null;
                            try {
                                mOptions.inBitmap = regionBitmap;
                                rect.set(mRegionRect);
                                regionBitmap = mRegionDecoder.decodeRegion(rect, mOptions);

                                // 获得canvas对象
                                canvas = getHolder().lockCanvas();
                                canvas.drawBitmap(regionBitmap, mMatrix, null);
                            } catch (Exception e) {

                            } finally {
                                if (canvas != null) {
                                    // 释放canvas对象并提交画布
                                    getHolder().unlockCanvasAndPost(canvas);
                                }
                            }
                        }
                    }
                };
                sendDrawMessage();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                handlerThread.quit();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private void sendDrawMessage() {
        if (mWorkHandler != null) {
            mWorkHandler.removeMessages(WHAT_DRAW);
            mWorkHandler.sendEmptyMessage(WHAT_DRAW);
        }
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
        sendDrawMessage();
    }

    public void setMinBitmapScaleForView(float scale) {
        mMinBitmapScaleForView = scale;
        measureRect();
        sendDrawMessage();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureRect();
        sendDrawMessage();
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
    public void computeScroll() {
        super.computeScroll();
        if (!mScroller.isFinished()) {
            if (mScroller.computeScrollOffset()) {
                mRegionRect.top = mScroller.getCurrY();
                mRegionRect.bottom = mRegionRect.top + Math.round(mViewHeight / mScale);
                invalidate(); // Scroller 固定用法
                sendDrawMessage();
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
