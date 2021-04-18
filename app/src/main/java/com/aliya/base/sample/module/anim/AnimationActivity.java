package com.aliya.base.sample.module.anim;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.aliya.base.AppUtils;
import com.aliya.base.sample.R;
import com.aliya.base.sample.base.ActionBarActivity;
import com.aliya.base.sample.databinding.ActivityAnimationBinding;

/**
 * 动画示例 - 页面
 * <p>
 * 三种动画类型：
 * 1. View(Tween) 动画
 * 2. 帧动画
 * 3. 属性动画
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
        mViewBinding.viewTarget2.setOnClickListener(this);
        mViewBinding.viewTarget2.setOnLongClickListener(this);

        mViewBinding.tvPerformAnim.setOnClickListener(v -> {
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_target_1:
                Animation animation =
                        AnimationUtils.loadAnimation(this, R.anim.anims_set_sample);
                v.startAnimation(animation);
                break;
            case R.id.view_target_2:
                // 如果当前View的属性等于动画结束时的属性，则动画不会执行。

                // 设置 scale pivot
                v.setPivotX(-AppUtils.dp2px(300) + v.getWidth() / 2f);
                v.setPivotY(v.getHeight() / 2f);

                Animator animator =
                        AnimatorInflater.loadAnimator(this, R.animator.animator_set_sample);
                animator.setTarget(v);
                animator.start();
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.view_target_1:
                performAnimByCode(v);
                return true;
            case R.id.view_target_2:
                performAnimatorByCode(v);
                return true;
        }
        return false;
    }

    private void performAnimatorByCode(View targetView) {

        ObjectAnimator transX1 = ObjectAnimator.ofFloat(targetView, "translationX", 0, AppUtils.dp2px(150));
        transX1.setDuration(1000);

        ObjectAnimator transX2 = ObjectAnimator.ofFloat(targetView, "translationX", AppUtils.dp2px(150), AppUtils.dp2px(300));
        transX2.setStartDelay(500);
        transX2.setDuration(1000);

        // 组合 并行两个缩小动画
        AnimatorSet scaleSet1 = new AnimatorSet();
        ObjectAnimator scaleX1 = ObjectAnimator.ofFloat(targetView, "scaleX", 1, 0.5f);
        scaleX1.setStartDelay(500);
        scaleX1.setDuration(1000);

        ObjectAnimator scaleY1 = ObjectAnimator.ofFloat(targetView, "scaleY", 1, 0.5f);
        scaleY1.setStartDelay(500);
        scaleY1.setDuration(1000);
        scaleSet1.playTogether(scaleX1, scaleY1);

        // 组合 并行两个放大动画
        AnimatorSet scaleSet2 = new AnimatorSet();
        ObjectAnimator scaleX2 = ObjectAnimator.ofFloat(targetView, "scaleX", 0.5f, 1);
        scaleX2.setStartDelay(500);
        scaleX2.setDuration(1000);

        ObjectAnimator scaleY2 = ObjectAnimator.ofFloat(targetView, "scaleY", 0.5f, 1);
        scaleY2.setStartDelay(500);
        scaleY2.setDuration(1000);
        scaleSet2.playTogether(scaleX2, scaleY2);

        // 组合 顺序以上四个动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(transX1, transX2, scaleSet1, scaleSet2);

        // 设置 scale pivot
        targetView.setPivotX(-AppUtils.dp2px(300) + targetView.getWidth() / 2f);
        targetView.setPivotY(targetView.getHeight() / 2f);

        animatorSet.start();
    }

    private void performAnimByCode(View targetView) {
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.setFillAfter(true);

        // xml 三种写法：
        // fromXDelta="10" ;    // ABSOLUTE 单位：sp
        // fromXDelta="10%";    // RELATIVE_TO_SELF
        // fromXDelta="10%p";   // RELATIVE_TO_PARENT
        TranslateAnimation translate;
        translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 5/*500%*/,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        translate.setDuration(1000);
        animationSet.addAnimation(translate);

        translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 5/*500%*/,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        translate.setDuration(1000);
        translate.setStartOffset(1500);
        animationSet.addAnimation(translate);

        ScaleAnimation scale = new ScaleAnimation(
                1f, 0.5f, 1f, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f/*50%*/);
        scale.setDuration(1000);
        scale.setStartOffset(3000);
        animationSet.addAnimation(scale);

        scale = new ScaleAnimation(
                1f, 2f, 1f, 2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f/*50%*/);
        scale.setDuration(1000);
        scale.setStartOffset(4500);
        animationSet.addAnimation(scale);

        targetView.startAnimation(animationSet);
    }
}