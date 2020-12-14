package com.aliya.base.sample.module.image;

import android.os.Bundle;
import android.view.View;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.BaseActivity;
import com.aliya.base.sample.databinding.ActivityBigImageBinding;
import com.aliya.monitor.LifecycleFpsMonitorCompat;
import com.aliya.monitor.FpsMonitor;

import java.io.IOException;
import java.io.InputStream;

import androidx.lifecycle.Lifecycle;

/**
 * 大图展示 示例页
 *
 * @author a_liYa
 * @date 2020/10/20 23:19.
 */
public class BigImageActivity extends BaseActivity implements View.OnClickListener {

    private ActivityBigImageBinding mViewBinding;

    private FpsMonitor.FpsListener mFpsListener = new FpsMonitor.FpsListener() {
        @Override
        public void invoke(int fps) {
            mViewBinding.tvFps.setText("fps : " + fps);
        }
    };

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

        LifecycleFpsMonitorCompat.addObserver(getLifecycle(), Lifecycle.State.RESUMED, mFpsListener);
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