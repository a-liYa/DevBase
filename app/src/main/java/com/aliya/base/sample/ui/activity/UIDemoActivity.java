package com.aliya.base.sample.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aliya.base.AppUtils;
import com.aliya.base.sample.R;
import com.aliya.base.sample.base.ActionBarActivity;
import com.aliya.base.sample.databinding.ActivityUiDemoBinding;
import com.aliya.base.sample.ui.widget.drawable.LinearDrawable;
import com.aliya.base.sample.ui.widget.drawable.ScaleDrawable;
import com.aliya.base.sample.ui.widget.drawable.TextGradientDrawable;

import androidx.core.content.ContextCompat;

/**
 * UI Demo
 *
 * @author a_liYa
 * @date 2020/10/31 11:26.
 */
public class UIDemoActivity extends ActionBarActivity implements View.OnClickListener {

    private ActivityUiDemoBinding mViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityUiDemoBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());

        mViewBinding.tvIcon.setOnClickListener(this);
        mViewBinding.tvText.setOnClickListener(this);

        LinearDrawable linearDrawable = new LinearDrawable();
        linearDrawable.addDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_icon_01));
        linearDrawable.addDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_icon_02));
        linearDrawable.addDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_icon_03));
        linearDrawable.setBounds(0, 0, linearDrawable.getMinimumWidth(),
                linearDrawable.getMinimumHeight());
        mViewBinding.tvIcon.setCompoundDrawables(null, null, linearDrawable, null);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_icon:
                Drawable[] drawables = mViewBinding.tvIcon.getCompoundDrawables();
                if (drawables[2] instanceof LinearDrawable) {
                    LinearDrawable linearDrawable = (LinearDrawable) drawables[2];
                    ScaleDrawable scaleDrawable = new ScaleDrawable(
                            ContextCompat.getDrawable(getActivity(), R.mipmap.ic_icon_04), 1.5f);
                    linearDrawable.addDrawable(scaleDrawable);
                    linearDrawable.setBounds(0, 0, linearDrawable.getMinimumWidth(),
                            linearDrawable.getMinimumHeight());
                    mViewBinding.tvIcon.setCompoundDrawables(null, null, linearDrawable, null);
                }
                break;
            case R.id.tv_text:
                TextView textView = (TextView) v;
                textView.setText(textView.getText() + "+");

                // 矩形圆角背景+文字,相当于 TextView.getDrawingCache(), 但 Drawable 是矢量图
                TextGradientDrawable drawable =
                        new TextGradientDrawable(textView.getText(), textView,
                                ((View) v.getParent()).getWidth());
                drawable.setColor(getResources().getColor(android.R.color.holo_purple));
                drawable.setCornerRadius(AppUtils.dp2px(4));
                drawable.setTextColor(Color.WHITE);

                Matrix matrix = new Matrix();
                matrix.setScale(1.5f, 1.5f,
                        drawable.getIntrinsicWidth() / 2f, drawable.getIntrinsicHeight() / 2f);
                matrix.postRotate(0 % 360,
                        drawable.getIntrinsicWidth() / 2f, drawable.getIntrinsicHeight() / 2f);

                Bitmap rawBitmap = drawable.getDrawingBitmap(matrix);

                mViewBinding.ivText.setImageBitmap(rawBitmap);
                break;
        }
    }
}