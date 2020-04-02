package com.aliya.base.sample.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

/**
 * 修改Activity颜色饱和度
 *
 * @author a_liYa
 * @date 2020/4/3 0:11.
 */
public class ColorSaturation {

    private static Context sContext;

    public static void init(Context context) {
        sContext = context.getApplicationContext();
        ColorSaturation sInstance = Singleton.sInstance;
    }

    private final Paint mPaint;

    private ColorSaturation() {
        mPaint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

        ((Application) sContext).registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                applyActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public void applyActivity(Activity activity) {
        final View decorView = activity.getWindow().getDecorView();
        decorView.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);
    }


    private static class Singleton {
        private static final ColorSaturation sInstance = new ColorSaturation();
    }

}
