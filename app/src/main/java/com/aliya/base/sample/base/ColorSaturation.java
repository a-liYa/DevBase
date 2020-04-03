package com.aliya.base.sample.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import java.util.HashSet;
import java.util.Set;

/**
 * 修改Activity颜色饱和度(黑白模式 - 类似清明节使用)
 *
 * @author a_liYa
 * @date 2020/4/3 0:11.
 */
public class ColorSaturation {

    public static void init(Context context) {
        get().inject(context);
    }

    public static ColorSaturation get() {
        return Singleton.sInstance;
    }

    private static class Singleton {
        private static final ColorSaturation sInstance = new ColorSaturation();
    }

    private Context mContext;
    private final Paint mPaint;
    private boolean mBlackWhiteMode = false;
    private Set<Activity> mActivitySets = new HashSet<>();

    private ColorSaturation() {
        mPaint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    private void inject(Context context) {
        if (mContext == null) {
            mContext = context.getApplicationContext();
            ((Application) mContext)
                    .registerActivityLifecycleCallbacks(mLifecycleCallbacks);
        }
    }

    public boolean isBlackWhiteMode() {
        return mBlackWhiteMode;
    }

    public void setBlackWhiteMode(boolean mode) {
        if (mBlackWhiteMode != mode) {
            mBlackWhiteMode = mode;
            for (Activity activity : mActivitySets) {
                applyActivity(activity, false);
            }
        }
    }

    /**
     * 应用黑白模式
     *
     * @param activity
     * @param isFirst  Activity 是否未初次使用
     */
    private void applyActivity(Activity activity, boolean isFirst) {
        final View decorView = activity.getWindow().getDecorView();
        if (mBlackWhiteMode) {
            decorView.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);
        } else if (!isFirst) {
            decorView.setLayerType(View.LAYER_TYPE_NONE, null);
        }
        if (!isFirst) decorView.invalidate();
    }

    private Application.ActivityLifecycleCallbacks mLifecycleCallbacks =
            new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle bundle) {
                    mActivitySets.add(activity);
                    applyActivity(activity, true);
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
                    mActivitySets.remove(activity);
                }
            };
}
