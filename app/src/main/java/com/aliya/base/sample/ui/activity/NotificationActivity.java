package com.aliya.base.sample.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.ActionBarActivity;
import com.aliya.base.sample.common.NotificationHelper;
import com.aliya.base.sample.databinding.ActivityNotificationBinding;
import com.aliya.base.sample.ui.receiver.NotificationClickReceiver;

/**
 * 演示通知 - Activity
 *
 * @author a_liYa
 * @date 2020/4/28 13:45.
 */
public class NotificationActivity extends ActionBarActivity implements View.OnClickListener {

    ActivityNotificationBinding mViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());

        mViewBinding.tvActivity.setOnClickListener(this);
        mViewBinding.tvBroadcast.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_activity: {
                Intent intent = new Intent(this, MineActivity.class);
                NotificationHelper.notify("标题 - 开启我的页面",
                        "通知内容 - 今天天气不错哦！",
                        intent, NotificationHelper.Component.ACTIVITY);
            }
            break;
            case R.id.tv_broadcast: {
                Intent intent = new Intent(this, NotificationClickReceiver.class);
                NotificationHelper.notify("标题 - 发送广播",
                        "通知内容 - 点我发送广播哦！",
                        intent, NotificationHelper.Component.BROADCAST);
            }
            break;
        }
    }
}
