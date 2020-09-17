package com.aliya.base.sample.base.actionbar;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aliya.base.sample.databinding.AbcActionModeBarImageItemBinding;

/**
 * Image 按钮布局
 *
 * @author a_liYa
 * @date 2020/9/17 14:25.
 */
public class ImageButtonAction extends AppActionBar.Action {

    int mSrcID;
    Drawable mSrcDrawable;
    OnClickListener mClickListener;

    AbcActionModeBarImageItemBinding mBinding;

    public ImageButtonAction(int srcID, OnClickListener clickListener) {
        mSrcID = srcID;
        mClickListener = clickListener;
    }

    public ImageButtonAction(Drawable drawable, OnClickListener clickListener) {
        mSrcDrawable = drawable;
        mClickListener = clickListener;
    }

    public ImageView getImageView() {
        return mBinding.ivButton;
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        mBinding = AbcActionModeBarImageItemBinding.inflate(inflater, parent, false);
        mBinding.ivButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchClickListener(v);
            }
        });
        if (mSrcID > 0) mBinding.ivButton.setImageResource(mSrcID);
        else if (mSrcDrawable != null) mBinding.ivButton.setImageDrawable(mSrcDrawable);
        return mBinding.getRoot();
    }

    public void dispatchClickListener(View v) {
        if (mClickListener != null) {
            mClickListener.onClick(v);
        }
        handleClick(v);
    }

    public void handleClick(View v) {
    }

}
