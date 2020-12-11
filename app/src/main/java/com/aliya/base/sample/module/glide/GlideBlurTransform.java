package com.aliya.base.sample.module.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.util.Util;

import java.security.MessageDigest;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

/**
 * Glide 高斯模糊
 *
 * @author a_liYa
 * @date 2016/11/23 23:13.
 */
public class GlideBlurTransform implements Transformation<Bitmap> {

    private static final String ID = GlideBlurTransform.class.getName();

    public static final float MAX_RADIUS = 25.0f;
    private static final float DEFAULT_SAMPLING = 1.0f;

    private float mSampling = DEFAULT_SAMPLING;
    private float mRadius;
    private int mColor;

    /**
     * @param radius The blur's radius.
     * @param color  The color filter for blurring.
     */
    public GlideBlurTransform(@FloatRange(from = 0.0f) float radius, int color) {
        if (radius > MAX_RADIUS) {
            mSampling = radius / 25.0f;
            mRadius = MAX_RADIUS;
        } else {
            mRadius = radius;
        }
        mColor = color;
    }

    public GlideBlurTransform(@FloatRange(from = 0.0f) float radius) {
        this(radius, Color.TRANSPARENT);
    }

    @WorkerThread
    @NonNull
    @Override
    public final Resource<Bitmap> transform(
            @NonNull Context context, @NonNull Resource<Bitmap> resource, int outWidth,
            int outHeight) {
        if (!Util.isValidDimensions(outWidth, outHeight)) {
            throw new IllegalArgumentException(
                    "Cannot apply transformation on width: "
                            + outWidth
                            + " or height: "
                            + outHeight
                            + " less than or equal to zero and not Target.SIZE_ORIGINAL");
        }
        BitmapPool bitmapPool = Glide.get(context).getBitmapPool();
        Bitmap toTransform = resource.get();
        int targetWidth = outWidth == Target.SIZE_ORIGINAL ? toTransform.getWidth() : outWidth;
        int targetHeight = outHeight == Target.SIZE_ORIGINAL ? toTransform.getHeight() : outHeight;
        Bitmap transformed = transform(context, bitmapPool, toTransform, targetWidth, targetHeight);

        final Resource<Bitmap> result;
        if (toTransform.equals(transformed)) {
            result = resource;
        } else {
            result = BitmapResource.obtain(transformed, bitmapPool);
        }
        return result;
    }

    @WorkerThread
    protected Bitmap transform(Context context, BitmapPool pool, Bitmap toTransform,
                               int outWidth, int outHeight) {
        boolean needScaled = mSampling == DEFAULT_SAMPLING;
        int originWidth = toTransform.getWidth();
        int originHeight = toTransform.getHeight();
        int width, height;
        if (needScaled) {
            width = originWidth;
            height = originHeight;
        } else {
            width = (int) (originWidth / mSampling);
            height = (int) (originHeight / mSampling);
        }
        // find a re-use bitmap
        Bitmap bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        if (mSampling != DEFAULT_SAMPLING) {
            canvas.scale(1 / mSampling, 1 / mSampling);
        }
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG);
        PorterDuffColorFilter filter =
                new PorterDuffColorFilter(mColor, PorterDuff.Mode.SRC_OVER);
        paint.setColorFilter(filter);
        canvas.drawBitmap(toTransform, 0, 0, paint);

        try {
            RenderScript rs = RenderScript.create(context);
            Allocation input = Allocation.createFromBitmap(rs, bitmap, Allocation.MipmapControl
                            .MIPMAP_NONE,
                    Allocation.USAGE_SCRIPT);
            Allocation output = Allocation.createTyped(rs, input.getType());
            ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, input.getElement());

            blur.setInput(input);
            blur.setRadius(mRadius);
            blur.forEach(output);
            output.copyTo(bitmap);

            rs.destroy();
        } catch (Exception e) {
            Canvas toCanvas = new Canvas(toTransform);
            toCanvas.drawColor(mColor);
            return toTransform;
        }

        if (needScaled) {
            return bitmap;
        } else {
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, originWidth, originHeight, true);
            bitmap.recycle();
            return scaled;
        }
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof GlideBlurTransform &&
                ((GlideBlurTransform) o).mRadius == mRadius &&
                ((GlideBlurTransform) o).mSampling == mRadius;
    }

    @Override
    public int hashCode() {
        return ID.hashCode() + (int) mRadius * 1000 + (int) mSampling * 10;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update((ID + mRadius + mSampling).getBytes(CHARSET));
    }
}
