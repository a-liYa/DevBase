package com.aliya.base.sample.ui.widget.drawable;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * DrawableWrapper
 *
 * @author a_liYa
 * @date 2020/5/27 17:22.
 */
public class DrawableWrapper extends Drawable implements Drawable.Callback {

    private Drawable mDrawable;

    public DrawableWrapper(Drawable drawable) {
        this.setWrappedDrawable(drawable);
    }

    public void draw(Canvas canvas) {
        this.mDrawable.draw(canvas);
    }

    protected void onBoundsChange(Rect bounds) {
        this.mDrawable.setBounds(bounds);
    }

    public void setChangingConfigurations(int configs) {
        this.mDrawable.setChangingConfigurations(configs);
    }

    public int getChangingConfigurations() {
        return this.mDrawable.getChangingConfigurations();
    }

    public void setDither(boolean dither) {
        this.mDrawable.setDither(dither);
    }

    public void setFilterBitmap(boolean filter) {
        this.mDrawable.setFilterBitmap(filter);
    }

    public void setAlpha(int alpha) {
        this.mDrawable.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter cf) {
        this.mDrawable.setColorFilter(cf);
    }

    public boolean isStateful() {
        return this.mDrawable.isStateful();
    }

    public boolean setState(int[] stateSet) {
        return this.mDrawable.setState(stateSet);
    }

    public int[] getState() {
        return this.mDrawable.getState();
    }

    public void jumpToCurrentState() {
        DrawableCompat.jumpToCurrentState(this.mDrawable);
    }

    public Drawable getCurrent() {
        return this.mDrawable.getCurrent();
    }

    public boolean setVisible(boolean visible, boolean restart) {
        return super.setVisible(visible, restart) || this.mDrawable.setVisible(visible, restart);
    }

    public int getOpacity() {
        return this.mDrawable.getOpacity();
    }

    public Region getTransparentRegion() {
        return this.mDrawable.getTransparentRegion();
    }

    public int getIntrinsicWidth() {
        return this.mDrawable.getIntrinsicWidth();
    }

    public int getIntrinsicHeight() {
        return this.mDrawable.getIntrinsicHeight();
    }

    public int getMinimumWidth() {
        return this.mDrawable.getMinimumWidth();
    }

    public int getMinimumHeight() {
        return this.mDrawable.getMinimumHeight();
    }

    public boolean getPadding(Rect padding) {
        return this.mDrawable.getPadding(padding);
    }

    public void invalidateDrawable(Drawable who) {
        this.invalidateSelf();
    }

    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        this.scheduleSelf(what, when);
    }

    public void unscheduleDrawable(Drawable who, Runnable what) {
        this.unscheduleSelf(what);
    }

    protected boolean onLevelChange(int level) {
        return this.mDrawable.setLevel(level);
    }

    public void setAutoMirrored(boolean mirrored) {
        DrawableCompat.setAutoMirrored(this.mDrawable, mirrored);
    }

    public boolean isAutoMirrored() {
        return DrawableCompat.isAutoMirrored(this.mDrawable);
    }

    public void setTint(int tint) {
        DrawableCompat.setTint(this.mDrawable, tint);
    }

    public void setTintList(ColorStateList tint) {
        DrawableCompat.setTintList(this.mDrawable, tint);
    }

    public void setTintMode(PorterDuff.Mode tintMode) {
        DrawableCompat.setTintMode(this.mDrawable, tintMode);
    }

    public void setHotspot(float x, float y) {
        DrawableCompat.setHotspot(this.mDrawable, x, y);
    }

    public void setHotspotBounds(int left, int top, int right, int bottom) {
        DrawableCompat.setHotspotBounds(this.mDrawable, left, top, right, bottom);
    }

    public Drawable getWrappedDrawable() {
        return this.mDrawable;
    }

    public void setWrappedDrawable(Drawable drawable) {
        if (this.mDrawable != null) {
            this.mDrawable.setCallback((Callback) null);
        }

        this.mDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }

    }
}
