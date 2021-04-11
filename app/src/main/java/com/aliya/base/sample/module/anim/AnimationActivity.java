package com.aliya.base.sample.module.anim;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.ActionBarActivity;
import com.aliya.base.sample.databinding.ActivityAnimationBinding;

/**
 * 动画示例 - 页面
 *
 * @author a_liYa
 * @date 2021/4/11 10:16.
 */
public class AnimationActivity extends ActionBarActivity implements View.OnClickListener,
        View.OnLongClickListener {

    private ActivityAnimationBinding mViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityAnimationBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());

        mViewBinding.viewTarget1.setOnClickListener(this);
        mViewBinding.viewTarget1.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_target_1:
                performAnimByXML(v);
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.view_target_1:
                performAnimByCode(v);
                return true;
        }
        return false;
    }

    private void performAnimByXML(View v) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anims_set_sample);
        v.startAnimation(animation);
    }

    private void performAnimByCode(View v) {
        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(true);
        TranslateAnimation translate;

        // xml 三种写法：
        // fromXDelta="10" ; // ABSOLUTE
        // fromXDelta="10%"; // RELATIVE_TO_SELF 单位：sp
        // fromXDelta="10%p"; // RELATIVE_TO_PARENT
        translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 5/*500%*/,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        translate.setDuration(1000);
        set.addAnimation(translate);

        translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 5/*500%*/,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        translate.setDuration(1000);
        translate.setStartOffset(1500);
        set.addAnimation(translate);

        ScaleAnimation scale = new ScaleAnimation(
                1f, 0.5f, 1f, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f/*50%*/);
        scale.setDuration(1000);
        scale.setStartOffset(3000);
        set.addAnimation(scale);

        scale = new ScaleAnimation(
                1f, 2f, 1f, 2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f/*50%*/);
        scale.setDuration(1000);
        scale.setStartOffset(4500);
        set.addAnimation(scale);

        v.startAnimation(set);
    }
}