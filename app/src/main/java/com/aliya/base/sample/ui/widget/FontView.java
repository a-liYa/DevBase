package com.aliya.base.sample.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.aliya.base.AppUtils;

/**
 * FontView
 *
 * @author a_liYa
 * @date 2019-12-27 10:08.
 */
public class FontView extends View {

    private final Paint mPaint;

    public FontView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        mPaint = new Paint();
        mPaint.setTextSize(AppUtils.sp2px(18));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLACK);
        Log.e("TAG", "onDraw: " + mPaint.getTypeface());
        canvas.drawText("我是 a_liYa，91年4月出生", getWidth() / 3f, getHeight() * 4f / 5, mPaint);
    }
}
