package com.aliya.base.sample.ui.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import com.aliya.base.event.LiveEvent;
import com.aliya.base.sample.R;
import com.aliya.base.sample.base.BaseActivity;
import com.aliya.base.sample.databinding.ActivityLiveEventBinding;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

/**
 * LiveEvent 使用示例
 *
 * @author a_liYa
 * @date 2019/2/26 上午9:51.
 */
public class LiveEventActivity extends BaseActivity implements Observer, View.OnClickListener {

    ActivityLiveEventBinding mViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityLiveEventBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());

        mViewBinding.tvObserve.setOnClickListener(this);
        mViewBinding.tvRemove.setOnClickListener(this);
        mViewBinding.tvSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_observe:
                LiveEvent.get().observe(this, this);
                break;
            case R.id.tv_remove:
//                LiveEvent.get().removeObserver(this);
                break;
            case R.id.tv_send:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Long value = Long.valueOf(SystemClock.uptimeMillis());
                        LiveEvent.get().post(value);
                        LiveEvent.get().post(value + 1);
                    }
                }).start();
            break;
        }
    }

    @Override
    public void onChanged(@Nullable Object o) {
        Log.e("TAG", "onChanged: " + o);
    }

}
