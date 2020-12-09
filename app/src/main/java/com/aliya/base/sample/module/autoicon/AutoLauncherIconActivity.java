package com.aliya.base.sample.module.autoicon;

import android.os.Bundle;
import android.view.View;

import com.aliya.base.sample.R;
import com.aliya.base.sample.SplashActivity;
import com.aliya.base.sample.base.ActionBarActivity;
import com.aliya.base.sample.databinding.ActivityAutoLauncherIconBinding;

/**
 * 自动替换启动图标 - 示例页
 *
 * 采用 activity-alias 方案，切换图标不杀死进程，会延迟到 launcher 刷新，延迟期间点击旧图标未安装该应用。
 *
 * Android 10 会杀死进程，大概2秒左右更新图标，位置不变；
 * Android 9 不会杀死进程，原生位置变化，三星位置不变；
 * Android 8.1.0 不会杀死进程，原生位置会变；
 * Android 8.0.0 不会杀死进程，位置会变；
 *
 * @author a_liYa
 * @date 2020/12/9 11:08.
 */
public class AutoLauncherIconActivity extends ActionBarActivity implements View.OnClickListener {

    private ActivityAutoLauncherIconBinding mViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityAutoLauncherIconBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());

        mViewBinding.tvSwitch11.setOnClickListener(this);
        mViewBinding.tvSwitchDefault.setOnClickListener(this);
        mViewBinding.tvSwitch12.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String launcherActivity = LauncherManager.getLauncherActivity(this);
        LauncherManager.setComponentEnabled(this, launcherActivity, false);
        switch (v.getId()) {
            case R.id.tv_switch_11:
                String splash11 = getPackageName() + ".Splash11Activity";
                LauncherManager.setComponentEnabled(this, splash11, true);
                break;
            case R.id.tv_switch_default:
                LauncherManager.setComponentEnabled(this, SplashActivity.class.getName(), true);
                break;
            case R.id.tv_switch_12:
                String splash12 = getPackageName() + ".Splash12Activity";
                LauncherManager.setComponentEnabled(this, splash12, true);
                break;
        }
    }
}