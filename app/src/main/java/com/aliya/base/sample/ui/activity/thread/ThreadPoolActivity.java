package com.aliya.base.sample.ui.activity.thread;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.ActionBarActivity;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池相关演示
 *
 * @author a_liYa
 * @date 2020/2/20 23:32.
 */
public class ThreadPoolActivity extends ActionBarActivity implements View.OnClickListener {

    private ThreadPoolExecutor mThreadPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);

        findViewById(R.id.open_thread).setOnClickListener(this);
        findViewById(R.id.open_thread_1).setOnClickListener(this);

        int corePoolSize = 1;
        int maximumPoolSize = 2;
        int keepAliveTime = 10 * 1000;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue(1);
        mThreadPool = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveTime, TimeUnit.MILLISECONDS,
                workQueue,
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_thread:
                Runnable runnable1 = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(10 * 60 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.e("TAG", "open_thread run: " + Thread.currentThread());
                    }
                };
                mThreadPool.execute(runnable1);

            break;
            case R.id.open_thread_1: {

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.e("TAG", "open_thread_1 run: " + Thread.currentThread());
                    }
                };

                mThreadPool.execute(runnable);
            }
            break;
        }
    }
}
