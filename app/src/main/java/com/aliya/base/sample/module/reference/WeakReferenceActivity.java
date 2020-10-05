package com.aliya.base.sample.module.reference;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.ActionBarActivity;
import com.aliya.base.sample.databinding.ActivityWeakReferenceBinding;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;


/**
 * 弱引用使用示例
 *
 * @author a_liYa
 * @date 2020/10/5 14:40.
 */
public class WeakReferenceActivity extends ActionBarActivity implements View.OnClickListener {

    private ActivityWeakReferenceBinding mViewBinding;

    private Object mObject;

    private WeakReference<Object> mWeakReference;
    private ReferenceQueue<Object> mReferenceQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityWeakReferenceBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());

        mViewBinding.tvClear.setOnClickListener(this);
        mViewBinding.tvGet.setOnClickListener(this);

        mReferenceQueue = new ReferenceQueue();
        mWeakReference = new WeakReference<>(mObject = new Object(), mReferenceQueue);
        Log.e("TAG", "弱引用，引用对象: " + mObject);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 阻塞remove
                    Reference<?> remove = mReferenceQueue.remove();
                    Log.e("TAG", "弱引用引用对象准备回收: " + remove.get() + " - " + mWeakReference.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clear:
                mObject = null;
                System.gc();
                break;
            case R.id.tv_get:
                Log.e("TAG", "获取引用: " + mObject + " - " + mWeakReference.get());
                break;
        }
    }
}