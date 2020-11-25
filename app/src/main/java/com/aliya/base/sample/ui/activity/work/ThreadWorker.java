package com.aliya.base.sample.ui.activity.work;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * ThreadWorker
 *
 * @author a_liYa
 * @date 2020/11/25 15:24.
 */
public class ThreadWorker extends Worker {

    public ThreadWorker(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @WorkerThread
    @NonNull
    @Override
    public Result doWork() {
        // 拿到传递的数据
        String dataValue = getInputData().getString("key");
        Log.e("TAG", "执行 doWork: input data = " + dataValue);

        // doWork 数据传递给外部
        Data outputData = new Data.Builder()
                .putString("output_data", "doWork complete")
                .build();

        return Result.failure(outputData); // 若返回失败，依赖的工作将不被执行
    }
}
