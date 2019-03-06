package com.aliya.base.sample;

import android.support.multidex.MultiDexApplication;

import com.aliya.base.AppUtils;

/**
 * application
 *
 * @author a_liYa
 * @date 2018/9/11 18:33.
 */
public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);

    }

}
