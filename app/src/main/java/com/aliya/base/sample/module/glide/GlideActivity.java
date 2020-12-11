package com.aliya.base.sample.module.glide;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.aliya.base.sample.base.ActionBarActivity;
import com.aliya.base.sample.databinding.ActivityGlideBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Glide v4.x 使用示例
 *
 * @author a_liYa
 * @date 2020/12/11 09:56.
 */
public class GlideActivity extends ActionBarActivity {

    ActivityGlideBinding mBinding;

    // w,h = 1920*1080
    private String imageUrl = "https://image.baidu.com/search/down?tn=download&word" +
            "=download&ie=utf8&fr=detail&url=https%3A%2F%2Ftimgsa.baidu" +
            ".com%2Ftimg%3Fimage%26quality%3D80%26size%3Db9999_10000%26sec%3D1607664473722" +
            "%26di%3Deea0759dcc7951b30269e031206e0128%26imgtype%3D0%26src%3Dhttp%253A%252F" +
            "%252Fattachments.gfan.com%252Fforum%252F201503%252F19%252F211608ztcq7higicydxhsy" +
            ".jpg&thumburl=https%3A%2F%2Fss3.bdstatic" +
            ".com%2F70cFv8Sh_Q1YnxGkpoWK1HF6hhy%2Fit%2Fu%3D1705581946%2C4177791147%26fm%3D26" +
            "%26gp%3D0.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityGlideBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        Glide.with(mBinding.ivImageScale).load(imageUrl).into(mBinding.ivImageScale);
        Glide.with(mBinding.ivImageSource).load(imageUrl).into(mBinding.ivImageSource);

        Glide.with(this).asBitmap().load(imageUrl).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<?
                    super Bitmap> transition) {
                Log.e("TAG", "Bitmap w,h: " + resource.getWidth() + "," + resource.getHeight());
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });

        Glide.with(this).load(imageUrl).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<?
                    super Drawable> transition) {
                Log.e("TAG", "Drawable w,h: " + resource.getMinimumWidth() + "," + resource.getMinimumHeight());
                Log.e("TAG", "onResourceReady: " + ((BitmapDrawable)resource).getBitmap());
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
    }
}