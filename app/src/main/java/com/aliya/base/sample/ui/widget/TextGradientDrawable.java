package com.aliya.base.sample.ui.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.util.Log;
import android.widget.TextView;

import com.aliya.base.AppUtils;

/**
 * TextGradientDrawable
 *
 * @author a_liYa
 * @date 2020/6/24 15:31.
 */
public class TextGradientDrawable extends GradientDrawable {

    private String mText;
    private TextPaint mPaint;
    private Layout mLayout;

    public TextGradientDrawable(String text, TextView referTo) {
        this.mText = text;
        this.mPaint = new TextPaint(referTo.getPaint());
        mPaint.setColor(Color.parseColor("#4f000000"));
        int width = referTo.getWidth() - referTo.getCompoundPaddingLeft() - referTo.getCompoundPaddingRight();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mLayout = buildStaticLayout23(referTo, width);
        } else {
            mLayout = buildStaticLayout(referTo, width);
        }
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.translate(AppUtils.dp2px(8), AppUtils.dp2px(6));
        mLayout.draw(canvas);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private StaticLayout buildStaticLayout23(TextView textView, int width) {
        StaticLayout.Builder builder = StaticLayout.Builder.obtain(textView.getText(),
                0, textView.getText().length(), mPaint, width)
                .setAlignment(Layout.Alignment.ALIGN_CENTER)
                .setTextDirection(TextDirectionHeuristics.FIRSTSTRONG_LTR)
                .setLineSpacing(textView.getLineSpacingExtra(), textView.getLineSpacingMultiplier())
                .setIncludePad(textView.getIncludeFontPadding())
                .setBreakStrategy(textView.getBreakStrategy())
                .setHyphenationFrequency(textView.getHyphenationFrequency())
                .setMaxLines(textView.getMaxLines() == -1 ? Integer.MAX_VALUE : textView.getMaxLines());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setJustificationMode(textView.getJustificationMode());
        }
        if (textView.getEllipsize() != null && textView.getKeyListener() == null) {
            builder.setEllipsize(textView.getEllipsize())
                    .setEllipsizedWidth(width);
        }
        return builder.build();
    }

    private StaticLayout buildStaticLayout(TextView textView, int width) {
        return new StaticLayout(textView.getText(),
                0, textView.getText().length(),
                mPaint, width, Layout.Alignment.ALIGN_NORMAL,
                textView.getLineSpacingMultiplier(),
                textView.getLineSpacingExtra(), textView.getIncludeFontPadding(), textView.getEllipsize(),
                width);
    }
}
