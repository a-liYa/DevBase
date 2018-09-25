package com.aliya.base.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aliya.base.R;

/**
 * 自定义任务执行提示Dialog 双击back键取消操作
 *
 * @author a_liYa
 * @date 16/8/23 11:15.
 */
public class TaskAlertDialog extends Dialog {

    private TextView mTvToast;
    private ImageView mIvIcon;

    private String alertText = "正在处理";
    private String cancelText = "双击撤销!";    // 撤销提醒内容
    private CancelListener mCancelListener;

    public static final String TEXT_SUCCESS = "成功";
    public static final String TEXT_FAILURE = "失败";
    private static final int FINISH_DELAYED = 1200;

    private Toast mToast;

    public TaskAlertDialog(Context context) {
        super(context, android.R.style.Theme_Dialog);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND); // 清除背景变暗
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
    }

    public TaskAlertDialog setCancelListener(CancelListener listener) {
        this.mCancelListener = listener;
        return this;
    }

    /**
     * 设置进行中显示的内容
     *
     * @param text 文本
     * @return this
     */
    public TaskAlertDialog setText(String text) {
        alertText = text;
        if (mTvToast != null) {
            mTvToast.setText(text);
        }
        return this;
    }

    /**
     * 设置取消提醒内容
     *
     * @param cancelText .
     * @return this
     */
    public TaskAlertDialog setCancelText(String cancelText) {
        this.cancelText = cancelText;
        return this;
    }

    public void finish() {
        try {
            dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void finish(boolean isSuccess) {
        finishDelay(isSuccess ? TEXT_SUCCESS : TEXT_FAILURE,
                isSuccess ? R.mipmap.icon_task_alert_operate_success
                        : R.mipmap.icon_task_alert_operate_failure);
    }

    /**
     * 显示结果，延迟 {@link #FINISH_DELAYED} 时间后关闭
     *
     * @param text  结果显示的文本
     * @param resId 结果显示的图标资源
     */
    public void finishDelay(String text, @DrawableRes int resId) {
        mTvToast.setText(text);
        mIvIcon.setImageResource(resId);
        mIvIcon.clearAnimation();
        mTvToast.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, FINISH_DELAYED);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = View.inflate(getContext(),
                R.layout.layout_task_alert_dialog, null);

        setContentView(contentView);
        mIvIcon = findViewById(R.id.iv_icon);
        mTvToast = findViewById(R.id.tv_toast);

        bindData();
    }

    private void bindData() {
        if (mTvToast != null) {
            mTvToast.setText(alertText);
        }
        if (mIvIcon != null) {
            mIvIcon.setImageResource(R.mipmap.icon_task_alert_operate);
            RotateAnimation animation = new RotateAnimation(0, 360,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setRepeatCount(Animation.INFINITE);
            animation.setDuration(1200);
            mIvIcon.startAnimation(animation);
        }
    }

    @Override
    public void show() {
        super.show();
        bindData();
    }

    private long clickTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if ((System.currentTimeMillis() - clickTime) > 1000) {
                mToast = Toast.makeText(getContext(), cancelText, Toast.LENGTH_SHORT);
                mToast.show();
                clickTime = System.currentTimeMillis();
                return true;
            } else {
                if (mToast != null) mToast.cancel();
                if (mCancelListener != null) mCancelListener.onCancel();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public interface CancelListener {
        /**
         * 撤销操作回调
         */
        void onCancel();
    }

}
