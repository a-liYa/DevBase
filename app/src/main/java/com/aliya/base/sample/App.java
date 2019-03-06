package com.aliya.base.sample;

import android.support.multidex.MultiDexApplication;

import com.aliya.base.AppUtils;
import com.aliya.base.sample.db.AppDatabase;

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

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

}
