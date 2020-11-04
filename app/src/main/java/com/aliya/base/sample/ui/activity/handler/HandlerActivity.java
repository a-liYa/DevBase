package com.aliya.base.sample.ui.activity.handler;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Log;
import android.view.View;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.ActionBarActivity;
import com.aliya.base.sample.databinding.ActivityHandlerBinding;

import java.lang.reflect.Method;

import androidx.annotation.RequiresApi;

/**
 * Handler 测试
 *
 * @author a_liYa
 * @date 2020/3/1 21:00.
 */
public class HandlerActivity extends ActionBarActivity implements View.OnClickListener {

    private Handler mHandler;
    private ActivityHandlerBinding mViewBinding;

    static final int MESSAGE_TYPE_SYNC = 1;
    static final int MESSAGE_TYPE_ASYNC = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityHandlerBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());

        initView();

        new Thread(new Runnable() {

            @Override
            public void run() {
                Looper.prepare();
                mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        Log.e("TAG", "handleMessage: " + msg.what + " - " + Thread.currentThread());
                        switch (msg.what) {
                            case MESSAGE_TYPE_SYNC:
                                Log.e("TAG", "收到普通消息");
                                break;
                            case MESSAGE_TYPE_ASYNC:
                                Log.e("TAG", "收到异步消息");
                                break;
                        }
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

    private void initView() {
        mViewBinding.sendMessage.setOnClickListener(this);
        mViewBinding.sendMessageDelay.setOnClickListener(this);
        mViewBinding.sendSyncBarrier.setOnClickListener(this);
        mViewBinding.removeSyncBarrier.setOnClickListener(this);
        mViewBinding.sendSyncMessage.setOnClickListener(this);
        mViewBinding.sendAsyncMessage.setOnClickListener(this);
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
            case R.id.send_sync_barrier:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    sendSyncBarrier();
                }
                break;
            case R.id.remove_sync_barrier:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    removeSyncBarrier();
                }
                break;
            case R.id.send_sync_message:
                sendSyncMessage();
                break;
            case R.id.send_async_message:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    sendAsyncMessage();
                }
                break;

        }
    }

    private int token;

    // 往消息队列插入同步屏障
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void sendSyncBarrier() {
        try {
            MessageQueue queue = mHandler.getLooper().getQueue();
            Method method = MessageQueue.class.getDeclaredMethod("postSyncBarrier");
            method.setAccessible(true);
            token = (int) method.invoke(queue);
            Log.e("TAG", "插入同步屏障");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 移除屏障
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void removeSyncBarrier() {
        try {
            MessageQueue queue = mHandler.getLooper().getQueue();
            Method method = MessageQueue.class.getDeclaredMethod("removeSyncBarrier", int.class);
            method.setAccessible(true);
            method.invoke(queue, token);
            Log.e("TAG", "移除屏障");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 往消息队列插入普通消息
    public void sendSyncMessage() {
        Log.e("TAG", "插入普通消息");
        Message message = Message.obtain();
        message.what = MESSAGE_TYPE_SYNC;
        mHandler.sendMessageDelayed(message, 1000);
    }

    // 往消息队列插入异步消息
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void sendAsyncMessage() {
        Log.e("TAG", "插入异步消息");
        Message message = Message.obtain();
        message.what = MESSAGE_TYPE_ASYNC;
        message.setAsynchronous(true);
        mHandler.sendMessageDelayed(message, 1000);
    }
}
