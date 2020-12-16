package com.aliya.base.sample.module.power;

import android.annotation.SuppressLint;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.View;
import android.widget.Toast;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.ActionBarActivity;
import com.aliya.base.sample.databinding.ActivityPowerSampleBinding;

/**
 * 熄屏与亮屏控制
 *
 * @author a_liYa
 * @date 2020/12/16 15:09.
 */
public class PowerSampleActivity extends ActionBarActivity implements View.OnClickListener {

    private ActivityPowerSampleBinding mBinding;

    private DevicePolicyManager policyManager;
    private ComponentName adminReceiver;
    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityPowerSampleBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.btnCheckScreen.setOnClickListener(this);
        mBinding.btnScreenOn.setOnClickListener(this);
        mBinding.btnScreenOff.setOnClickListener(this);
        mBinding.btnScreenOffDelayOn.setOnClickListener(this);
        mBinding.btnStartDeviceManager.setOnClickListener(this);

        adminReceiver = new ComponentName(this, ScreenOffAdminReceiver.class);
        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        checkAndTurnOnDeviceManager();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_check_screen:
                checkScreen();
                break;
            case R.id.btn_screen_on:
                checkScreenOn();
                break;
            case R.id.btn_screen_off:
                checkScreenOff();
                break;
            case R.id.btn_screen_off_delay_on:
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkScreenOffAndDelayOn();
                    }
                }, 10000);
                break;
            case R.id.btn_start_device_manager:
                checkAndTurnOnDeviceManager();
                break;
        }
    }

    public void checkScreen() {
        showToast(mPowerManager.isScreenOn() ? "屏幕是亮屏" : "屏幕是息屏");
    }

    @SuppressLint("InvalidWakeLockTag")
    public void checkScreenOn() {
        mWakeLock =
                mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "tag");
        mWakeLock.acquire();
        mWakeLock.release();
    }

    public void checkScreenOff() {
        boolean admin = policyManager.isAdminActive(adminReceiver);
        if (admin) {
            policyManager.lockNow();
        } else {
            showToast("没有设备管理权限");
        }
    }

    /**
     * 延迟熄屏并延时亮屏
     */
    public void checkScreenOffAndDelayOn() {
        boolean admin = policyManager.isAdminActive(adminReceiver);
        if (admin) {
            policyManager.lockNow();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkScreenOn();
                }
            }, 10000);
        } else {
            showToast("没有设备管理权限");
        }
    }

    /**
     * 检测并去激活设备管理器权限
     */
    public void checkAndTurnOnDeviceManager() {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminReceiver);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "激活后才能使用锁屏功能！");
        startActivity(intent);
    }

    private void showToast(String Str) {
        Toast.makeText(this, Str, Toast.LENGTH_SHORT).show();
    }
}