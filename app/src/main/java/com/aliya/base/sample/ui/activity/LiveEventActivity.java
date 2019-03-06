package com.aliya.base.sample.ui.activity;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aliya.base.LiveEvent;
import com.aliya.base.sample.R;
import com.aliya.base.sample.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * LiveEvent 使用示例
 *
 * @author a_liYa
 * @date 2019/2/26 上午9:51.
 */
public class LiveEventActivity extends BaseActivity implements Observer {

    @BindView(R.id.tv_bind_size)
    TextView mTvBindSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_event);
        ButterKnife.bind(this);
        LiveEvent.liveData();
    }

    @OnClick({R.id.tv_observe, R.id.tv_remove, R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_observe:
                LiveEvent.liveData().observe(this, this);
                break;
            case R.id.tv_remove:
                LiveEvent.liveData().removeObserver(this);
                break;
            case R.id.tv_send:
                Long value = Long.valueOf(SystemClock.uptimeMillis());
                LiveEvent.post(value);

            break;
        }
    }

    @Override
    public void onChanged(@Nullable Object o) {
        Log.e("TAG", "onChanged: " + o);
    }


}
