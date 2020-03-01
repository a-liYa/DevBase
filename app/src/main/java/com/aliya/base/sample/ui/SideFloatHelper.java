package com.aliya.base.sample.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.aliya.base.AppUtils;
import com.aliya.base.sample.ui.widget.MediaSideFloat;

/**
 * SideFloatHelper
 *
 * @author a_liYa
 * @date 2020-02-28 11:15.
 */
public class SideFloatHelper {

    private static boolean sInitialized;
    private static MediaSideFloat sSideFloat;

    public static void init(Context context) {
        if (sInitialized) return;

        Application application = (Application) context.getApplicationContext();
        application.registerActivityLifecycleCallbacks(
                new Application.ActivityLifecycleCallbacks() {

                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    }

                    @Override
                    public void onActivityStarted(Activity activity) {
                    }

                    @Override
                    public void onActivityResumed(Activity activity) {

                        if (activity instanceof FloatMark) {
                            if (!((FloatMark) activity).isDisable()) {
                                final MediaSideFloat sideFloat = getSideFloat();
                                if (sideFloat.isShouldShow()) {
                                    sideFloat.attachToActivity(activity);
                                }
                            }
                        }
                    }

                    @Override
                    public void onActivityPaused(Activity activity) {
                    }

                    @Override
                    public void onActivityStopped(Activity activity) {
                    }

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                    }

                    @Override
                    public void onActivityDestroyed(Activity activity) {
                    }
                });
        sInitialized = true;
    }

    public static MediaSideFloat getSideFloat() {
        if (sSideFloat == null)
            sSideFloat = new MediaSideFloat(AppUtils.getContext());
        return sSideFloat;
    }

    public interface FloatMark {

        /**
         * 悬浮窗是否禁用
         *
         * @return false表示不禁用
         */
        boolean isDisable();
    }
}
