package com.aliya.base.sample.ui.activity.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Log;
import android.view.View;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.ActionBarActivity;

/**
 * Handler 测试
 *
 * @author a_liYa
 * @date 2020/3/1 21:00.
 */
public class HandlerActivity extends ActionBarActivity implements View.OnClickListener {

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        findViewById(R.id.send_message_delay).setOnClickListener(this);
        findViewById(R.id.send_message).setOnClickListener(this);
        new Thread(new Runnable() {

            @Override
            public void run() {
                Looper.prepare();
                mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Log.e("TAG", "handleMessage: " + msg.what + " - " + Thread.currentThread());
                    }
                };
                Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                    @Override
                    public boolean queueIdle() {
                        Log.e("TAG", "queueIdle: " + Thread.currentThread());
                        return true;
                    }
                });
                Looper.loop();
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_message_delay:
                mHandler.sendEmptyMessageDelayed(101, 5 * 60 * 1000);
                break;
                case R.id.send_message:
                mHandler.sendEmptyMessage(100);
                break;
        }
    }
}
