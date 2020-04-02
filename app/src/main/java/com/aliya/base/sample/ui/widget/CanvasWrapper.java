package com.aliya.base.sample.ui.widget;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.DrawFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Canvas 包装器
 *
 * @author a_liYa
 * @date 2016/12/1 11:30.
 */
public class CanvasWrapper extends Canvas {
    private Canvas mCanvas;
    private Paint mPaint = new Paint();
    private ColorMatrixColorFilter mColorFilter;

    public CanvasWrapper() {
        super();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        mColorFilter = new ColorMatrixColorFilter(colorMatrix);
        mPaint.setColorFilter(mColorFilter);
    }

    /**
     * 注入
     *
     * @param canvas 被注入对象
     */
    public void inject(Canvas canvas) {
        mCanvas = canvas;
    }

    /**
     * 取消注入
     */
    public void clearInject() {
        mCanvas = null;
    }

    public Paint wrapPaint(Paint paint) {
        if (paint == null) {
            paint = mPaint;
        } else {
            paint.setColorFilter(mColorFilter);
        }
        return paint;
    }

    @Override
    public boolean isHardwareAccelerated() {
        if (mCanvas != null) {
            return mCanvas.isHardwareAccelerated();
        }
        return super.isHardwareAccelerated();
    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        if (mCanvas != null)
            mCanvas.setBitmap(bitmap);
        else {
            super.setBitmap(bitmap);
        }
    }

    @Override
    public boolean isOpaque() {
        if (mCanvas != null)
            return mCanvas.isOpaque();
        return super.isOpaque();
    }

    @Override
    public int getWidth() {
        if (mCanvas != null)
            return mCanvas.getWidth();
        return super.getWidth();
    }

    @Override
    public int getHeight() {
        if (mCanvas != null)
            return mCanvas.getHeight();
        return super.getHeight();
    }

    @Override
    public int getDensity() {
        if (mCanvas != null)
            return mCanvas.getDensity();
        return super.getDensity();
    }

    @Override
    public void setDensity(int density) {
        if (mCanvas != null)
            mCanvas.setDensity(density);
        super.setDensity(density);
    }

    @Override
    public int getMaximumBitmapWidth() {
        if (mCanvas != null)
            return mCanvas.getMaximumBitmapWidth();
        return super.getMaximumBitmapWidth();
    }

    @Override
    public int getMaximumBitmapHeight() {
        if (mCanvas != null)
            return mCanvas.getMaximumBitmapHeight();
        return super.getMaximumBitmapHeight();
    }

    @Override
    public int saveLayer(RectF bounds, Paint paint, int saveFlags) {
        if (mCanvas != null) {
            return mCanvas.saveLayer(bounds, wrapPaint(paint), saveFlags);
        }
        return super.saveLayer(bounds, paint, saveFlags);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int saveLayer(RectF bounds, Paint paint) {
        if (mCanvas != null) {
            return mCanvas.saveLayer(bounds, wrapPaint(paint));
        }
        return super.saveLayer(bounds, paint);
    }

    @Override
    public int saveLayer(float left, float top, float right, float bottom, Paint paint, int
            saveFlags) {
        if (mCanvas != null) {
            return mCanvas.saveLayer(left, top, right, bottom, wrapPaint(paint), saveFlags);
        }
        return super.saveLayer(left, top, right, bottom, paint, saveFlags);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int saveLayer(float left, float top, float right, float bottom, Paint paint) {
        if (mCanvas != null) {
            mCanvas.saveLayer(left, top, right, bottom, wrapPaint(paint));
        }
        return  super.saveLayer(left, top, right, bottom, paint);
    }

    @Override
    public int saveLayerAlpha(RectF bounds, int alpha, int saveFlags) {
        if (mCanvas != null) {
            mCanvas.saveLayerAlpha(bounds, alpha, saveFlags);
        }
        return super.saveLayerAlpha(bounds, alpha, saveFlags);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int saveLayerAlpha(RectF bounds, int alpha) {
        int saveLayerAlpha = super.saveLayerAlpha(bounds, alpha);
        if (mCanvas != null) {
            mCanvas.saveLayerAlpha(bounds, alpha);
        }
        return saveLayerAlpha;
    }

    @Override
    public int saveLayerAlpha(float left, float top, float right, float bottom, int alpha, int
            saveFlags) {
        int saveLayerAlpha = super.saveLayerAlpha(left, top, right, bottom, alpha, saveFlags);
        if (mCanvas != null) {
            return mCanvas.saveLayerAlpha(left, top, right, bottom, alpha, saveFlags);
        }
        return saveLayerAlpha;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int saveLayerAlpha(float left, float top, float right, float bottom, int alpha) {
        int saveLayerAlpha = super.saveLayerAlpha(left, top, right, bottom, alpha);
        if (mCanvas != null) {
            return mCanvas.saveLayerAlpha(left, top, right, bottom, alpha);
        }
        return saveLayerAlpha;
    }

    @Override
    public void restore() {
        if (mCanvas != null)
            mCanvas.restore();
        super.restore();
    }

    @Override
    public int getSaveCount() {
        int saveCount = super.getSaveCount();
        if (mCanvas != null)
            return mCanvas.getSaveCount();
        return saveCount;
    }

    @Override
    public void restoreToCount(int saveCount) {
        if (mCanvas != null) {
            mCanvas.restoreToCount(saveCount);
        } else {
            super.restoreToCount(saveCount);
        }
    }

    @Override
    public void translate(float dx, float dy) {
        super.translate(dx, dy);
        if (mCanvas != null) {
            mCanvas.translate(dx, dy);
        }
    }

    @Override
    public void scale(float sx, float sy) {
        super.scale(sx, sy);
        if (mCanvas != null) {
            mCanvas.scale(sx, sy);
        }
    }

    @Override
    public void rotate(float degrees) {
        super.rotate(degrees);
        if (mCanvas != null) {
            mCanvas.rotate(degrees);
        }
    }

    @Override
    public void skew(float sx, float sy) {
        super.skew(sx, sy);
        if (mCanvas != null) {
            mCanvas.skew(sx, sy);
        }
    }

    @Override
    public void concat(Matrix matrix) {
        super.concat(matrix);
    }

    @Override
    public void setMatrix(Matrix matrix) {
        super.setMatrix(matrix);
        if (mCanvas != null) {
            mCanvas.setMatrix(matrix);
        }
    }

    @Override
    public void getMatrix(Matrix ctm) {
        super.getMatrix(ctm);
        if (mCanvas != null) {
            mCanvas.getMatrix(ctm);
        }
    }

    @Override
    public boolean clipRect(RectF rect, Region.Op op) {
        boolean clipRect = super.clipRect(rect, op);
        if (mCanvas != null) {
            return mCanvas.clipRect(rect, op);
        }
        return clipRect;
    }

    @Override
    public boolean clipRect(Rect rect, Region.Op op) {
        boolean clipRect = super.clipRect(rect, op);
        if (mCanvas != null) {
            mCanvas.clipRect(rect, op);
        }
        return clipRect;
    }

    @Override
    public boolean clipRect(RectF rect) {
        boolean clipRect = super.clipRect(rect);
        if (mCanvas != null) {
            mCanvas.clipRect(rect);
        }
        return clipRect;
    }

    @Override
    public boolean clipRect(Rect rect) {
        boolean clipRect = super.clipRect(rect);
        if (mCanvas != null) {
            mCanvas.clipRect(rect);
        }
        return clipRect;
    }

    @Override
    public boolean clipRect(float left, float top, float right, float bottom, Region.Op op) {
        boolean clipRect = super.clipRect(left, top, right, bottom, op);
        if (mCanvas != null) {
            return mCanvas.clipRect(left, top, right, bottom, op);
        }
        return clipRect;
    }

    @Override
    public boolean clipRect(float left, float top, float right, float bottom) {
        boolean clipRect = super.clipRect(left, top, right, bottom);
        if (mCanvas != null) {
            return mCanvas.clipRect(left, top, right, bottom);
        }
        return clipRect;
    }

    @Override
    public boolean clipRect(int left, int top, int right, int bottom) {
        boolean clipRect = super.clipRect(left, top, right, bottom);
        if (mCanvas != null) {
            return mCanvas.clipRect(left, top, right, bottom);
        }
        return clipRect;
    }

    @Override
    public boolean clipPath(Path path, Region.Op op) {
        boolean clipPath = super.clipPath(path, op);
        if (mCanvas != null) {
            return mCanvas.clipPath(path, op);
        }
        return clipPath;
    }

    @Override
    public boolean clipPath(Path path) {
        boolean clipPath = super.clipPath(path);
        if (mCanvas != null) {
            return mCanvas.clipPath(path);
        }
        return clipPath;
    }

    @Nullable
    @Override
    public DrawFilter getDrawFilter() {
        DrawFilter drawFilter = super.getDrawFilter();
        if (mCanvas != null) {
            return mCanvas.getDrawFilter();
        }
        return drawFilter;
    }

    @Override
    public void setDrawFilter(DrawFilter filter) {
        super.setDrawFilter(filter);
        if (mCanvas != null) {
            mCanvas.setDrawFilter(filter);
        }
    }

    @Override
    public boolean quickReject(RectF rect, EdgeType type) {
        boolean quickReject = super.quickReject(rect, type);
        if (mCanvas != null) {
            return mCanvas.quickReject(rect, type);
        }
        return quickReject;
    }

    @Override
    public boolean quickReject(Path path, EdgeType type) {
        boolean quickReject = super.quickReject(path, type);
        if (mCanvas != null) {
            return mCanvas.quickReject(path, type);
        }
        return quickReject;
    }

    @Override
    public boolean quickReject(float left, float top, float right, float bottom, EdgeType
            type) {
        boolean quickReject = super.quickReject(left, top, right, bottom, type);
        if (mCanvas != null) {
            return mCanvas.quickReject(left, top, right, bottom, type);
        }
        return quickReject;
    }

    @Override
    public boolean getClipBounds(Rect bounds) {
        boolean getClipBounds = super.getClipBounds(bounds);
        if (mCanvas != null) {
            return mCanvas.getClipBounds(bounds);
        }
        return getClipBounds;
    }

    @Override
    public void drawRGB(int r, int g, int b) {
        if (mCanvas != null) {
            mCanvas.drawRGB(r, g, b);
        }
        super.drawRGB(r, g, b);
    }

    @Override
    public void drawARGB(int a, int r, int g, int b) {
        if (mCanvas != null) {
            mCanvas.drawARGB(a, r, g, b);
        }
        super.drawARGB(a, r, g, b);
    }

    @Override
    public void drawColor(int color) {
        if (mCanvas != null) {
            mCanvas.drawColor(color);
        }
        super.drawColor(color);
    }

    @Override
    public void drawColor(int color, PorterDuff.Mode mode) {
        if (mCanvas != null) {
            mCanvas.drawColor(color, mode);
        }
        super.drawColor(color, mode);
    }

    @Override
    public void drawPaint(Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawPaint(paint);
        }
        super.drawPaint(paint);
    }

    @Override
    public void drawPoints(float[] pts, int offset, int count, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawPoints(pts, offset, count, wrapPaint(paint));
        }
        super.drawPoints(pts, offset, count, paint);
    }

    @Override
    public void drawPoints(float[] pts, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawPoints(pts, wrapPaint(paint));
        }
        super.drawPoints(pts, paint);
    }

    @Override
    public void drawPoint(float x, float y, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawPoint(x, y, wrapPaint(paint));
        }
        super.drawPoint(x, y, paint);
    }

    @Override
    public void drawLine(float startX, float startY, float stopX, float stopY, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawLine(startX, startY, stopX, stopY, wrapPaint(paint));
        }
        super.drawLine(startX, startY, stopX, stopY, paint);
    }

    @Override
    public void drawLines(float[] pts, int offset, int count, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawLines(pts, offset, count, wrapPaint(paint));
        }
        super.drawLines(pts, offset, count, paint);
    }

    @Override
    public void drawLines(float[] pts, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawLines(pts, wrapPaint(paint));
        }
        super.drawLines(pts, paint);
    }

    @Override
    public void drawRect(RectF rect, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawRect(rect, wrapPaint(paint));
        }
        super.drawRect(rect, paint);
    }

    @Override
    public void drawRect(Rect r, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawRect(r, wrapPaint(paint));
        }
        super.drawRect(r, paint);
    }

    @Override
    public void drawRect(float left, float top, float right, float bottom, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawRect(left, top, right, bottom, wrapPaint(paint));
        }
        super.drawRect(left, top, right, bottom, paint);
    }

    @Override
    public void drawOval(RectF oval, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawOval(oval, wrapPaint(paint));
        }
        super.drawOval(oval, paint);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void drawOval(float left, float top, float right, float bottom, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawOval(left, top, right, bottom, wrapPaint(paint));
        }
        super.drawOval(left, top, right, bottom, paint);
    }

    @Override
    public void drawCircle(float cx, float cy, float radius, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawCircle(cx, cy, radius, wrapPaint(paint));
        }
        super.drawCircle(cx, cy, radius, paint);
    }

    @Override
    public void drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint
            paint) {
        if (mCanvas != null) {
            mCanvas.drawArc(oval, startAngle, sweepAngle, useCenter, wrapPaint(paint));
        }
        super.drawArc(oval, startAngle, sweepAngle, useCenter, paint);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void drawArc(float left, float top, float right, float bottom,
                        float startAngle, float
                                sweepAngle, boolean useCenter, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawArc(left, top, right, bottom, startAngle, sweepAngle, useCenter, wrapPaint(paint));
        }
        super.drawArc(left, top, right, bottom, startAngle, sweepAngle, useCenter, paint);
    }

    @Override
    public void drawRoundRect(RectF rect, float rx, float ry, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawRoundRect(rect, rx, ry, wrapPaint(paint));
        }
        super.drawRoundRect(rect, rx, ry, paint);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void drawRoundRect(float left, float top, float right, float bottom, float rx, float
            ry, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawRoundRect(left, top, right, bottom, rx, ry, wrapPaint(paint));
        }
        super.drawRoundRect(left, top, right, bottom, rx, ry, paint);
    }

    @Override
    public void drawPath(Path path, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawPath(path, wrapPaint(paint));
        }
        super.drawPath(path, paint);
    }

    @Override
    public void drawBitmap(Bitmap bitmap, float left, float top, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawBitmap(bitmap, left, top, wrapPaint(paint));
        }
        super.drawBitmap(bitmap, left, top, paint);
    }

    @Override
    public void drawBitmap(Bitmap bitmap, Rect src, RectF dst, Paint paint) {
        Log.e("TAG", "drawBitmap: " + paint);
        if (mCanvas != null) {
            mCanvas.drawBitmap(bitmap, src, dst, wrapPaint(paint));
        }
        super.drawBitmap(bitmap, src, dst, paint);
    }

    @Override
    public void drawBitmap(Bitmap bitmap, Rect src, Rect dst, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawBitmap(bitmap, src, dst, wrapPaint(paint));
        }
        super.drawBitmap(bitmap, src, dst, paint);
    }

    @Override
    public void drawBitmap(int[] colors, int offset, int stride, float x, float y,
                           int width, int
                                   height, boolean hasAlpha, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawBitmap(colors, offset, stride, x, y, width, height, hasAlpha, paint);
        }
        super.drawBitmap(colors, offset, stride, x, y, width, height, hasAlpha, paint);
    }

    @Override
    public void drawBitmap(int[] colors, int offset, int stride, int x, int y, int width, int
            height, boolean hasAlpha, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawBitmap(colors, offset, stride, x, y, width, height, hasAlpha, wrapPaint(paint));
        }
        super.drawBitmap(colors, offset, stride, x, y, width, height, hasAlpha, paint);
    }

    @Override
    public void drawBitmap(Bitmap bitmap, Matrix matrix, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawBitmap(bitmap, matrix, wrapPaint(paint));
        }
        super.drawBitmap(bitmap, matrix, paint);
    }

    @Override
    public void drawBitmapMesh(Bitmap bitmap, int meshWidth, int meshHeight, float[] verts, int
            vertOffset, int[] colors, int colorOffset, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawBitmapMesh(bitmap, meshWidth, meshHeight, verts, vertOffset, colors,
                    colorOffset, wrapPaint(paint));
        }
        super.drawBitmapMesh(bitmap, meshWidth, meshHeight, verts, vertOffset, colors,
                colorOffset, paint);
    }

    @Override
    public void drawVertices(VertexMode mode, int vertexCount, float[] verts, int vertOffset,
                             float[] texs, int texOffset, int[] colors, int colorOffset, short[]
                                     indices, int indexOffset, int indexCount, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawVertices(mode, vertexCount, verts, vertOffset, texs, texOffset, colors,
                    colorOffset, indices, indexOffset, indexCount, wrapPaint(paint));
        }
        super.drawVertices(mode, vertexCount, verts, vertOffset, texs, texOffset, colors,
                colorOffset, indices, indexOffset, indexCount, paint);
    }

    @Override
    public void drawText(char[] text, int index, int count, float x, float y, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawText(text, index, count, x, y, wrapPaint(paint));
        }
        super.drawText(text, index, count, x, y, paint);
    }

    @Override
    public void drawText(String text, float x, float y, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawText(text, x, y, wrapPaint(paint));
        }
        super.drawText(text, x, y, paint);
    }

    @Override
    public void drawText(String text, int start, int end, float x, float y, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawText(text, start, end, x, y, wrapPaint(paint));
        }
        super.drawText(text, start, end, x, y, paint);
    }

    @Override
    public void drawText(CharSequence text, int start, int end, float x, float y, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawText(text, start, end, x, y, wrapPaint(paint));
        }
        super.drawText(text, start, end, x, y, paint);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void drawTextRun(char[] text, int index, int count, int contextIndex, int
            contextCount, float x, float y, boolean isRtl, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawTextRun(text, index, count, contextIndex, contextCount, x, y, isRtl, wrapPaint(paint));
        }
        super.drawTextRun(text, index, count, contextIndex, contextCount, x, y, isRtl, paint);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void drawTextRun(CharSequence text, int start, int end, int contextStart, int
            contextEnd, float x, float y, boolean isRtl, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawTextRun(text, start, end, contextStart, contextEnd, x, y, isRtl, wrapPaint(paint));
        }
        super.drawTextRun(text, start, end, contextStart, contextEnd, x, y, isRtl, paint);
    }

    @Override
    public void drawPosText(char[] text, int index, int count, float[] pos, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawPosText(text, index, count, pos, wrapPaint(paint));
        }
        super.drawPosText(text, index, count, pos, paint);
    }

    @Override
    public void drawPosText(String text, float[] pos, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawPosText(text, pos, wrapPaint(paint));
        }
        super.drawPosText(text, pos, paint);
    }

    @Override
    public void drawTextOnPath(char[] text, int index, int count, Path path,
                               float hOffset, float vOffset, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawTextOnPath(text, index, count, path, hOffset, vOffset, wrapPaint(paint));
        }
        super.drawTextOnPath(text, index, count, path, hOffset, vOffset, paint);
    }

    @Override
    public void drawTextOnPath(String text, Path path, float hOffset, float vOffset, Paint paint) {
        if (mCanvas != null) {
            mCanvas.drawTextOnPath(text, path, hOffset, vOffset, wrapPaint(paint));
        }
        super.drawTextOnPath(text, path, hOffset, vOffset, paint);
    }

    @Override
    public void drawPicture(Picture picture) {
        if (mCanvas != null) {
            mCanvas.drawPicture(picture);
        }
        super.drawPicture(picture);
    }

    @Override
    public void drawPicture(Picture picture, RectF dst) {
        if (mCanvas != null) {
            mCanvas.drawPicture(picture, dst);
        }
        super.drawPicture(picture, dst);
    }

    @Override
    public void drawPicture(Picture picture, Rect dst) {
        if (mCanvas != null) {
            mCanvas.drawPicture(picture, dst);
        }
        super.drawPicture(picture, dst);
    }
}
