package com.aliya.base.sample.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * FontTextView
 *
 * @author a_liYa
 * @date 2019-12-27 08:58.
 */
public class FontTextView extends TextView {

    public FontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setTypeface(@Nullable Typeface tf) {
        super.setTypeface(tf);
    }
}
