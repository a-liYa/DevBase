package com.aliya.base.sample.ui.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import com.aliya.base.event.LiveEvent;
import com.aliya.base.sample.R;
import com.aliya.base.sample.base.BaseActivity;
import com.aliya.base.sample.databinding.ActivityLiveEventBinding;

/**
 * LiveEvent 使用示例
 *
 * @author a_liYa
 * @date 2019/2/26 上午9:51.
 */
public class LiveEventActivity extends BaseActivity implements View.OnClickListener {

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

    private LiveEvent.Observer<Long> mLongObserver = new LiveEvent.Observer<Long>() {

        @Override
        public void onEvent(Long o) {
            Log.e("TAG", "onEvent: Long " + o);
        }
    };

    private LiveEvent.Observer<String> mStringObserver = new LiveEvent.Observer<String>() {
        @Override
        public void onEvent(String event) {
            Log.e("TAG", "onEvent: String " + event);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_observe:
                // 注册且自动反注销
                LiveEvent.get().observe(this, mLongObserver);
                LiveEvent.get().observe(mStringObserver);
                break;
            case R.id.tv_remove:
                LiveEvent.get().removeObserver(mStringObserver);
                break;
            case R.id.tv_send:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Long value = Long.valueOf(SystemClock.uptimeMillis());
                        LiveEvent.get().post(new Long(value));
                        LiveEvent.get().post("This is string type event");
                    }
                }).start();
            break;
        }
    }

}