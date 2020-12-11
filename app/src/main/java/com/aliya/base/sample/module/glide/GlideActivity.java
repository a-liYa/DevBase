package com.aliya.base.sample.module.glide;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aliya.base.sample.base.ActionBarActivity;
import com.aliya.base.sample.databinding.ActivityGlideBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.ImageViewTarget;
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

    private String gifUrl = "https://stcbeta.8531" +
            ".cn/assets/20171128/1511851486824_5a1d05de03dafc0b3f94627b.gif";

    private String gif_1 = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=105958458," +
            "281032896&fm=26&gp=0.jpg";
    private String gif_2 = "https://image.baidu.com/search/down?tn=download&word=download&ie=utf8" +
            "&fr=detail&url=https%3A%2F%2Ftimgsa.baidu" +
            ".com%2Ftimg%3Fimage%26quality%3D80%26size%3Db9999_10000%26sec%3D1607682361573%26di" +
            "%3D1b259a26077e356a6a9ef165443aac20%26imgtype%3D0%26src%3Dhttp%253A%252F%252Fimg" +
            ".zcool.cn%252Fcommunity%252F01fc9b58ae8007a801219c774529c5" +
            ".gif&thumburl=https%3A%2F%2Fss0.bdstatic" +
            ".com%2F70cFvHSh_Q1YnxGkpoWK1HF6hhy%2Fit%2Fu%3D1582187301%2C3192576259%26fm%3D26%26gp" +
            "%3D0.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityGlideBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        Glide.with(mBinding.ivImageScale).load(imageUrl).into(new ImageViewTarget<Drawable>(mBinding.ivImageScale) {
            @Override
            protected void setResource(@Nullable Drawable resource) {
                view.setImageDrawable(resource);
                Log.e("TAG", "setResource: " + resource);
            }
        });

        Glide.with(mBinding.ivImageSource).load(imageUrl).transform(new GlideBlurTransform(100)).into(mBinding.ivImageSource);

        loadSelectedGif(gif_1, gif_2);
    }

    private void loadSelectedGif(String gif_1, String gif_2) {
        mBinding.ivGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });
        final int resId = android.R.attr.state_selected;
        final StateListDrawable stateList = new StateListDrawable();
        Glide.with(mBinding.ivGif)
                .load(gif_1)
//                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource,
                                                @Nullable Transition<? super Drawable> transition) {
                        if (resource instanceof GifDrawable) {
                            ((GifDrawable) resource).start();
                        }
                        stateList.addState(new int[]{resId}, resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
        Glide.with(mBinding.ivGif)
                .load(gif_2)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource,
                                                @Nullable Transition<? super Drawable> transition) {
                        if (resource instanceof GifDrawable) {
                            ((GifDrawable) resource).start();
                        }
                        stateList.addState(new int[]{-resId}, resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });

        mBinding.ivGif.setImageDrawable(stateList);
    }

    public void clearTarget() {
        Glide.with(mBinding.ivImageScale).clear(mBinding.ivImageScale);
    }

    public void loadDrawable(String url) {
        Glide.with(this).load(url).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<?
                    super Drawable> transition) {
                Log.e("TAG", "Drawable onResourceReady: " + resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                Log.e("TAG", "Drawable onLoadCleared: ");
            }
        });
    }

    public void loadAsBitmap(String url) {
        Glide.with(this).asBitmap().load(url).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<?
                    super Bitmap> transition) {
                Log.e("TAG", "Bitmap w,h: " + resource.getWidth() + "," + resource.getHeight());
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                Log.e("TAG", "Bitmap onLoadCleared: ");
            }
        });
    }

    public void loadAsGif(String url) {
        Glide.with(this).asGif().load(url).into(new CustomTarget<GifDrawable>() {
            @Override
            public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<?
                    super GifDrawable> transition) {
                Log.e("TAG", "Gif onResourceReady: " + resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }
        });
    }
}