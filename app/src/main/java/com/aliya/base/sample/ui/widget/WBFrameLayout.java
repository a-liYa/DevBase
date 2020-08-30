package com.aliya.base.sample.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 黑白模式() FrameLayout
 *
 * @author a_liYa
 * @date 2020/4/2 14:59.
 */
public class WBFrameLayout extends FrameLayout {

    private Paint mWBPaint; // 黑白模式画笔

    public WBFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public WBFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mWBPaint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        mWBPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int saveCount;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            saveCount = canvas.saveLayer(getLeft(), getTop(), getRight(), getBottom(), mWBPaint);
        } else {
            saveCount = canvas.saveLayer(getLeft(), getTop(), getRight(), getBottom(), mWBPaint,
                    Canvas.ALL_SAVE_FLAG);
        }
        super.dispatchDraw(canvas);
        canvas.restoreToCount(saveCount);
    }
}
