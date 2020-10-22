package com.aliya.base.sample.module.image;

import android.os.Bundle;
import android.view.View;

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
public class BigImageActivity extends BaseActivity {

    private ActivityBigImageBinding mViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityBigImageBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());

        try {
            InputStream mOpenIS = getAssets().open("image/long_image_2019.jpg");
            mViewBinding.bigImage.setImage(mOpenIS);
            mViewBinding.bigImage1.setImage(getAssets().open("image/long_image_2019.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mViewBinding.bigImage.setMinBitmapScaleForView(0.75f);

        mViewBinding.tvLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewBinding.bigImage.requestLayout();
                mViewBinding.bigImage.invalidate();
            }
        });

    }
}