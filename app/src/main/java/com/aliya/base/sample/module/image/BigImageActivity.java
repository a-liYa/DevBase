package com.aliya.base.sample.module.image;

import android.os.Bundle;

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

    private ActivityBigImageBinding mImageBinding;
    private InputStream mOpenIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageBinding = ActivityBigImageBinding.inflate(getLayoutInflater());
        setContentView(mImageBinding.getRoot());

        try {
            mOpenIS = getAssets().open("image/long_image_2019.jpg");
            mImageBinding.bigImage.setImage(mOpenIS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}