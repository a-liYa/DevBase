package com.aliya.base.simple.db;

import com.aliya.base.SPHelper;
import com.aliya.base.simple.constant.Key;

/**
 * AppSetting
 *
 * @author a_liYa
 * @date 2019/3/6 11:10.
 */
public final class AppSetting {

    private static volatile AppSetting sInstance;

    public static AppSetting get() {
        if (sInstance == null) {
            synchronized (AppSetting.class) {
                if (sInstance == null) {
                    sInstance = new AppSetting();
                }
            }
        }
        return sInstance;
    }

    private boolean backToBackground;

    private AppSetting() {
        backToBackground = SPHelper.get().get(Key.BACK_TO_BACKGROUND, false);
    }

    public boolean isBackToBackground() {
        return backToBackground;
    }

    public void setBackToBackground(boolean backToBackground) {
        if (this.backToBackground != backToBackground) {
            this.backToBackground = backToBackground;
            SPHelper.get().put(Key.BACK_TO_BACKGROUND, backToBackground).commit();
        }
    }

}
