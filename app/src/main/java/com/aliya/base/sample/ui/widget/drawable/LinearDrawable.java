package com.aliya.base.sample.ui.widget.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * LinearDrawable
 *
 * @author a_liYa
 * @date 2020/5/27 14:26.
 */
public class LinearDrawable extends Drawable {

    int mDrawableMargin;
    boolean mIsVertical; // true:表示纵向
    final List<Drawable> mDrawables = new ArrayList<>();

    public LinearDrawable() {
    }

    public LinearDrawable(int drawableMargin, boolean isVertical) {
        mDrawableMargin = drawableMargin;
        mIsVertical = isVertical;
    }

    public void addDrawable(Drawable drawable) {
        mDrawables.add(drawable);
    }

    public void removeDrawable(Drawable drawable) {
        mDrawables.remove(drawable);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        int _left ,_top, _right, _bottom;
         _right = _bottom = 0;

        for (int i = 0; i < mDrawables.size(); i++) {
            Drawable d = mDrawables.get(i);
            int offset = 0;
            if (mIsVertical) {
                _left = left;
                _top = _bottom;
                if (i > 0) _top += mDrawableMargin;
                _right = right;
                _bottom = _top + d.getMinimumHeight();
            } else {
                _left = _right;
                if (i > 0) _left += mDrawableMargin;
                if (bottom - top > d.getMinimumHeight())
                    offset = (int)((bottom - top - d.getMinimumHeight()) / 2f + 0.5f);
                _top = top + offset;
                _right = _left + d.getMinimumWidth();
                _bottom = bottom - offset;
            }

            d.setBounds(_left, _top, _right, _bottom);
        }
    }

    @Override
    public int getIntrinsicHeight() {
        int intrinsicHeight = -1;
        for (Drawable d : mDrawables) {
            if (mIsVertical) {
                if (d.getIntrinsicHeight() >= 0) {
                    if (intrinsicHeight == -1)
                        intrinsicHeight = 0;
                    intrinsicHeight += d.getIntrinsicHeight();
                }
            } else {
                intrinsicHeight = Math.max(intrinsicHeight, d.getIntrinsicHeight());
            }
        }
        if (mIsVertical && mDrawables.size() > 1) {
            intrinsicHeight += (mDrawables.size() - 1) * mDrawableMargin;
        }
        return intrinsicHeight;
    }

    @Override
    public int getIntrinsicWidth() {
        int intrinsicWidth = -1;
        for (Drawable d : mDrawables) {
            if (mIsVertical) {
                intrinsicWidth = Math.max(intrinsicWidth, d.getIntrinsicWidth());
            } else {
                if (d.getIntrinsicWidth() >= 0) {
                    if (intrinsicWidth == -1)
                        intrinsicWidth = 0;
                    intrinsicWidth += d.getIntrinsicWidth();
                }
            }
        }
        if (!mIsVertical && mDrawables.size() > 1) {
            intrinsicWidth += (mDrawables.size() - 1) * mDrawableMargin;
        }
        return intrinsicWidth;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        for (Drawable d : mDrawables) {
            d.draw(canvas);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        for (Drawable d : mDrawables) {
            d.setAlpha(alpha);
        }
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        for (Drawable d : mDrawables) {
            d.setColorFilter(colorFilter);
        }
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
