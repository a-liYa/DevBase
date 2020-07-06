package com.aliya.base.sample.ui.widget.drawable;

import android.graphics.drawable.Drawable;

/**
 * ScaleDrawable
 *
 * @author a_liYa
 * @date 2020/5/27 17:18.
 */
public class ScaleDrawable extends DrawableWrapper {

    public float mScale;

    public ScaleDrawable(Drawable drawable, float scale) {
        super(drawable);
        mScale = scale;
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) (super.getIntrinsicHeight() * mScale + 0.5f);
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) (super.getIntrinsicWidth() * mScale + 0.5f);
    }

    @Override
    public int getMinimumWidth() {
        return (int) (super.getMinimumWidth() * mScale + 0.5f);
    }

    @Override
    public int getMinimumHeight() {
        return (int) (super.getMinimumHeight() * mScale + 0.5f);
    }
}
