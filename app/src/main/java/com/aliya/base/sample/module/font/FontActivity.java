package com.aliya.base.sample.module.font;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextPaint;
import android.widget.TextView;

import com.aliya.base.AppUtils;
import com.aliya.base.sample.base.ActionBarActivity;
import com.aliya.base.sample.databinding.ActivityFontBinding;

/**
 * 字体示例
 *
 * @author a_liYa
 * @date 2020/12/2 09:44.
 */
public class FontActivity extends ActionBarActivity {

    private ActivityFontBinding mViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityFontBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());

        setStyleMediumBold(mViewBinding.tv2);
        setStyleMediumBold(mViewBinding.tv20);
    }

    /*
        setTypeface一共有两种形式:
            1、只设置字体类型：setTypeface(Typeface tf)
            2、setTypeface(Typeface tf，int style)：参数一字体类型，参数二字体风格

        注意：字体类型在自定义字体中可能无效，字体风格有效
     */

    /**
     * 设置 TextView style 为中粗
     * @param textView .
     */
    public static void setStyleMediumBold(TextView textView) {
        TextPaint paint = textView.getPaint();
        // tv2Paint.setFakeBoldText(true); // 粗体
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(paint.getTextSize() / (float) AppUtils.sp2px(18));
        textView.invalidate();
    }
}