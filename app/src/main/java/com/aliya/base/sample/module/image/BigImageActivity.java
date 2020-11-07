package com.aliya.base.sample.module.image;

import android.os.Bundle;
import android.view.View;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.BaseActivity;
import com.aliya.base.sample.databinding.ActivityBigImageBinding;

import java.io.IOException;
import java.io.InputStream;

/**
 * 大图展示 示例页
 *
 * @author a_liYa
 * @date 2020/10/20 23:19.
 */
public class BigImageActivity extends BaseActivity implements View.OnClickListener {

    private ActivityBigImageBinding mViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityBigImageBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());

        try {
            InputStream mOpenIS = getAssets().open("image/long_image_2019.jpg");
            mViewBinding.bigImage.setImage(mOpenIS);
            InputStream mOpenIS1 = getAssets().open("image/long_image_2019.jpg");
            mViewBinding.bigSurface.setImage(mOpenIS1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mViewBinding.tvScaleAdd.setOnClickListener(this);
        mViewBinding.tvScaleMinus.setOnClickListener(this);

    }

    private float mMinScale = 1;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_scale_add:
                mMinScale += 0.1f;
                bindScale();
                break;
            case R.id.tv_scale_minus:
                mMinScale -= 0.1f;
                bindScale();
                break;
        }
    }

    private void bindScale() {
        if (mMinScale < 0)
            mMinScale = 0;
        else if (mMinScale > 1)
            mMinScale = 1;
        else
            mMinScale = (int) (mMinScale * 10) / 10f;

        mViewBinding.bigImage.setMinBitmapScaleForView(mMinScale);
        mViewBinding.tvScaleAdd.setText("+ 0.1\nscale=" + mMinScale);
        mViewBinding.tvScaleMinus.setText("- 0.1\nscale=" + mMinScale);
    }
}