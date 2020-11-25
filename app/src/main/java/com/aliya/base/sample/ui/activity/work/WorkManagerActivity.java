package com.aliya.base.sample.ui.activity.work;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.ActionBarActivity;
import com.aliya.base.sample.databinding.ActivityWorkManagerBinding;

import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Operation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

/**
 * WorkManager 使用简介
 *
 * @author a_liYa
 * @date 2020/10/31 11:26.
 */
public class WorkManagerActivity extends ActionBarActivity implements View.OnClickListener {

    private ActivityWorkManagerBinding mViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityWorkManagerBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());

        mViewBinding.tvWorkEnqueue.setOnClickListener(this);
        mViewBinding.tvWorkNetwork.setOnClickListener(this);
        mViewBinding.tvWorkUnique.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_work_enqueue:
                workEnqueue();
                break;
            case R.id.tv_work_network:
                workNetwork();
                break;

            case R.id.tv_work_unique:
                workUnique();
                break;
        }
    }

    public void workUnique() {
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(ThreadWorker.class).build();
        // 同一个 uniqueWorkName 只能执行一个任务
        WorkManager.getInstance(this).enqueueUniqueWork("unique", ExistingWorkPolicy.REPLACE, workRequest);
    }

    public void workNetwork() {
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(ThreadWorker.class)
                .setConstraints(new Constraints.Builder() // 指定约束条件
                        .setRequiredNetworkType(NetworkType.CONNECTED) // 网络状态
                        .setRequiresBatteryNotLow(true)                // 不在电量不足时执行
                        .setRequiresCharging(true)                     // 在充电时执行
                        .setRequiresStorageNotLow(true)                // 不在存储容量不足时执行
                        // .setRequiresDeviceIdle(true)                // 在待机状态下执行，需要 API 23
                        .build())
                .build();
        WorkManager.getInstance(this).enqueue(workRequest);
        Log.e("TAG", "onClick: Work enqueue");
    }

    private void workEnqueue() {
        Data data = new Data.Builder().putString("key", "go work data").build();
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(ThreadWorker.class)
                .setInputData(data) // 从 Activity 传数据给 Worker
                .build();
        // 可监听任务排队情况，是否开始执行
        Operation enqueue = WorkManager.getInstance(this).enqueue(workRequest);
        enqueue.getState().observe(this, new Observer<Operation.State>() {
            @Override
            public void onChanged(Operation.State state) {
                Log.e("TAG", "添加任务状态: " + state);
            }
        });

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        WorkInfo.State state = workInfo.getState();
                        Log.e("TAG", "Work 执行状态: " + state);
                        String outputData = workInfo.getOutputData().getString("output_data");
                        Log.e("TAG", "Work 返回结果: " + outputData);
                    }
                });
    }
}