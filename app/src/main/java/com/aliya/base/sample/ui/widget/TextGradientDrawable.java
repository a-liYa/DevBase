package com.aliya.base.sample.ui.widget;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.text.BoringLayout;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.aliya.base.AppUtils;

/**
 * TextGradientDrawable
 *
 * @author a_liYa
 * @date 2020/6/24 15:31.
 */
public class TextGradientDrawable extends GradientDrawable {

    private CharSequence mText;
    private TextPaint mPaint;
    private Layout mLayout;
    private DisplayMetrics mDisplayMetrics;

    private int mWidth, mHeight;
    private int mMaxWidth;

    public TextGradientDrawable(CharSequence text, TextView referTo, int maxWidth) {
        this.mText = text;
        this.mMaxWidth = maxWidth;
        this.mPaint = new TextPaint(referTo.getPaint());
        this.mDisplayMetrics = referTo.getResources().getDisplayMetrics();
        onMeasure(referTo);
    }

    public void setTextColor(@ColorInt int color) {
        mPaint.setColor(color);
    }

    /**
     * 代码参考自 TextView#onMeasure(widthMeasureSpec, heightMeasureSpec)
     */
    private void onMeasure(TextView referTo) {
        BoringLayout.Metrics boring = BoringLayout.isBoring(referTo.getText(), mPaint);
        if (boring != null) {
            mWidth = boring.width;
        }
        mWidth += referTo.getCompoundPaddingLeft() + referTo.getCompoundPaddingRight();
        mWidth = Math.min(mWidth, mMaxWidth);

        int wantWidth =
                mWidth - referTo.getCompoundPaddingLeft() - referTo.getCompoundPaddingRight();
        makeNewLayout(referTo, wantWidth);

        mHeight = mLayout.getHeight();
        mHeight += referTo.getCompoundPaddingTop() + referTo.getCompoundPaddingBottom();
        setBounds(0, 0, mWidth, mHeight);
    }

    private void makeNewLayout(TextView referTo, int wantWidth) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder builder = StaticLayout.Builder.obtain(mText,
                    0, mText.length(), mPaint, wantWidth)
                    .setAlignment(getLayoutAlignment(referTo))
                    .setTextDirection(TextDirectionHeuristics.FIRSTSTRONG_LTR)
                    .setLineSpacing(referTo.getLineSpacingExtra(),
                            referTo.getLineSpacingMultiplier())
                    .setIncludePad(referTo.getIncludeFontPadding())
                    .setBreakStrategy(referTo.getBreakStrategy())
                    .setHyphenationFrequency(referTo.getHyphenationFrequency())
                    .setMaxLines(referTo.getMaxLines() == -1 ? Integer.MAX_VALUE :
                            referTo.getMaxLines());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder.setJustificationMode(referTo.getJustificationMode());
            }
            if (referTo.getEllipsize() != null && referTo.getKeyListener() == null) {
                builder.setEllipsize(referTo.getEllipsize())
                        .setEllipsizedWidth(wantWidth);
            }
            mLayout = builder.build();
        } else {
            mLayout = new StaticLayout(mText,
                    0, mText.length(),
                    mPaint, wantWidth, getLayoutAlignment(referTo),
                    referTo.getLineSpacingMultiplier(),
                    referTo.getLineSpacingExtra(), referTo.getIncludeFontPadding(),
                    referTo.getEllipsize(),
                    wantWidth);
        }
    }

    @Override
    public int getIntrinsicWidth() {
        return mWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return mHeight;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.translate(AppUtils.dp2px(8), AppUtils.dp2px(6));
        mLayout.draw(canvas);
    }

    /**
     * 代码参考自 {@link Bitmap#createBitmap(Bitmap, int, int, int, int, Matrix, boolean) }
     */
    public Bitmap getDrawingBitmap(Matrix matrix) {
        int newWidth = mWidth;
        int newHeight = mHeight;
        Bitmap bitmap;

        RectF dstR = new RectF(0, 0, mWidth, mHeight);
        RectF deviceR = new RectF();

        if (matrix == null || matrix.isIdentity()) {
            bitmap = Bitmap.createBitmap(mDisplayMetrics, newWidth, newHeight, Config.ARGB_8888);
        } else {
            matrix.mapRect(deviceR, dstR);

            newWidth = Math.round(deviceR.width());
            newHeight = Math.round(deviceR.height());

            bitmap = Bitmap.createBitmap(mDisplayMetrics, newWidth, newHeight, Config.ARGB_8888);
        }

        Canvas canvas = null;
        if (canvas == null) {
            canvas = new Canvas(bitmap);
        } else {
            canvas.setBitmap(bitmap);
        }
        canvas.translate(-deviceR.left, -deviceR.top);
        canvas.concat(matrix);

        final int restoreCount = canvas.save();
        this.draw(canvas);
        canvas.restoreToCount(restoreCount);
        canvas.setBitmap(null);
        return bitmap;
    }

    private Layout.Alignment getLayoutAlignment(TextView referTo) {
        Layout.Alignment alignment = null;
        switch (referTo.getTextAlignment()) {
            case View.TEXT_ALIGNMENT_GRAVITY:
                switch (referTo.getGravity() & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) {
                    case Gravity.START:
                        alignment = Layout.Alignment.ALIGN_NORMAL;
                        break;
                    case Gravity.END:
                        alignment = Layout.Alignment.ALIGN_OPPOSITE;
                        break;
                    case Gravity.LEFT:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            alignment = Layout.Alignment.ALIGN_LEFT;
                        }
                        break;
                    case Gravity.RIGHT:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            alignment = Layout.Alignment.ALIGN_RIGHT;
                        }
                        break;
                    case Gravity.CENTER_HORIZONTAL:
                        alignment = Layout.Alignment.ALIGN_CENTER;
                        break;
                    default:
                        alignment = Layout.Alignment.ALIGN_NORMAL;
                        break;
                }
                break;
            case View.TEXT_ALIGNMENT_TEXT_START:
                alignment = Layout.Alignment.ALIGN_NORMAL;
                break;
            case View.TEXT_ALIGNMENT_TEXT_END:
                alignment = Layout.Alignment.ALIGN_OPPOSITE;
                break;
            case View.TEXT_ALIGNMENT_CENTER:
                alignment = Layout.Alignment.ALIGN_CENTER;
                break;
            case View.TEXT_ALIGNMENT_VIEW_START:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    alignment = (referTo.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
                            ? Layout.Alignment.ALIGN_RIGHT : Layout.Alignment.ALIGN_LEFT;
                }
                break;
            case View.TEXT_ALIGNMENT_VIEW_END:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    alignment = (referTo.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
                            ? Layout.Alignment.ALIGN_LEFT : Layout.Alignment.ALIGN_RIGHT;
                }
                break;
            case View.TEXT_ALIGNMENT_INHERIT:
                // This should never happen as we have already resolved the text alignment
                // but better safe than sorry so we just fall through
            default:
                alignment = Layout.Alignment.ALIGN_NORMAL;
                break;
        }
        return alignment;
    }
}
