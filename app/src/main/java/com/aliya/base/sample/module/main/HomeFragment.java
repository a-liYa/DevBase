package com.aliya.base.sample.module.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aliya.base.AppUtils;
import com.aliya.base.sample.R;
import com.aliya.base.sample.databinding.FragmentHomeBinding;
import com.aliya.base.sample.ui.activity.SecondActivity;
import com.aliya.base.sample.ui.activity.handler.HandlerActivity;
import com.aliya.base.sample.ui.activity.launch.SingleInstanceActivity;
import com.aliya.base.sample.ui.activity.thread.ThreadPoolActivity;
import com.aliya.base.sample.ui.widget.LinearDrawable;
import com.aliya.base.sample.ui.widget.ScaleDrawable;
import com.aliya.base.sample.ui.widget.TextGradientDrawable;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding mBinding;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.tvTitle.setOnClickListener(this);
        mBinding.tvLaunchMode.setOnClickListener(this);
        mBinding.tvThreadPool.setOnClickListener(this);
        mBinding.tvHandler.setOnClickListener(this);
        mBinding.tvIcon.setOnClickListener(this);
        mBinding.tvText.setOnClickListener(this);

        LinearDrawable linearDrawable = new LinearDrawable();
        linearDrawable.addDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.ic_icon_01));
        linearDrawable.addDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.ic_icon_02));
        linearDrawable.addDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.ic_icon_03));
        linearDrawable.setBounds(0, 0, linearDrawable.getMinimumWidth(),
                linearDrawable.getMinimumHeight());
        mBinding.tvIcon.setCompoundDrawables(null, null, linearDrawable, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title:
                startActivity(new Intent(getContext(), SecondActivity.class));
                break;
            case R.id.tv_launch_mode:
                startActivity(new Intent(getContext(), SingleInstanceActivity.class));
                break;
            case R.id.tv_thread_pool:
                getActivity().startActivity(new Intent(getContext(), ThreadPoolActivity.class));
                break;
            case R.id.tv_handler:
                getActivity().startActivity(new Intent(getContext(), HandlerActivity.class));
                break;
            case R.id.tv_icon:
                Drawable[] drawables = mBinding.tvIcon.getCompoundDrawables();
                if (drawables[2] instanceof LinearDrawable) {
                    LinearDrawable linearDrawable = (LinearDrawable) drawables[2];
                    ScaleDrawable scaleDrawable = new ScaleDrawable(
                            ContextCompat.getDrawable(getContext(), R.mipmap.ic_icon_04), 1.5f);
                    linearDrawable.addDrawable(scaleDrawable);
                    linearDrawable.setBounds(0, 0, linearDrawable.getMinimumWidth(),
                            linearDrawable.getMinimumHeight());
                    mBinding.tvIcon.setCompoundDrawables(null, null, linearDrawable, null);
                }
                break;
            case R.id.tv_text:
                TextView textView = (TextView)v;
                textView.setText(textView.getText() + "我");

                TextGradientDrawable drawable =
                        new TextGradientDrawable(textView.getText().toString(), textView);
                drawable.setColor(getResources().getColor(android.R.color.holo_purple));
                drawable.setCornerRadius(AppUtils.dp2px(4));
                drawable.setTextColor(Color.BLUE);
                mBinding.ivText.setImageDrawable(drawable);
                break;
        }
    }
}
