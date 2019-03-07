package com.aliya.base.sample;

import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.aliya.base.AppUtils;

/**
 * application
 *
 * @author a_liYa
 * @date 2018/9/11 18:33.
 */
public class App extends MultiDexApplication {

    private boolean isMainProcess; // 是否为主进程

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);

        isMainProcess = TextUtils.equals(getPackageName(), AppUtils.getProcessName());

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 此处 - 第三方初始化
            }
        }).start();

    }

}
