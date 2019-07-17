package com.aliya.base.compat;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_BEHIND;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_FULL_USER;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LOCKED;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_NOSENSOR;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_SENSOR;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT;
import static android.os.Build.VERSION_CODES.O;

/**
 * 兼容处理 targetSDK > 26(8.0) Activity 屏幕方向设置问题
 * <p/>
 * Android 24(7.0)及以后版本屏幕方向设置建议在Manifest, 否则导致启动Activity短暂显示重力感应方向
 * 
 * @author a_liYa
 * @date 2019/3/9 22:25.
 */
public final class ActivityOrientationCompat {

    @IntDef({
            SCREEN_ORIENTATION_UNSPECIFIED,
            SCREEN_ORIENTATION_LANDSCAPE,
            SCREEN_ORIENTATION_PORTRAIT,
            SCREEN_ORIENTATION_USER,
            SCREEN_ORIENTATION_BEHIND,
            SCREEN_ORIENTATION_SENSOR,
            SCREEN_ORIENTATION_NOSENSOR,
            SCREEN_ORIENTATION_SENSOR_LANDSCAPE,
            SCREEN_ORIENTATION_SENSOR_PORTRAIT,
            SCREEN_ORIENTATION_REVERSE_LANDSCAPE,
            SCREEN_ORIENTATION_REVERSE_PORTRAIT,
            SCREEN_ORIENTATION_FULL_SENSOR,
            SCREEN_ORIENTATION_USER_LANDSCAPE,
            SCREEN_ORIENTATION_USER_PORTRAIT,
            SCREEN_ORIENTATION_FULL_USER,
            SCREEN_ORIENTATION_LOCKED
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ScreenOrientation {
    }

    public static void setRequestedOrientation(Activity activity,
                                               @ScreenOrientation int orientation,
                                               boolean force) {
        if (!fixOrientationByOreo(activity)) {
            final int _orientation = activity.getRequestedOrientation();
            if (force || _orientation == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
                if (orientation != _orientation) {
                    activity.setRequestedOrientation(orientation);
                }
            }
        }
    }

    public static boolean fixOrientationByOreo(Activity activity) {
        if (activity.getApplicationInfo().targetSdkVersion > O
                && Build.VERSION.SDK_INT == O
                && isTranslucentOrFloating(activity)) {
            boolean isFixedOrientation = isFixedOrientation(activity.getRequestedOrientation());
            if (isFixedOrientation) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                try {
                    // 防止异常 "Only fullscreen opaque activities can request orientation"
                    Field field = Activity.class.getDeclaredField("mActivityInfo");
                    field.setAccessible(true);
                    ActivityInfo o = (ActivityInfo) field.get(activity);
                    o.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
                    field.setAccessible(false);
                } catch (Exception e) {
                    // no-op
                }
            }
            return true; // true表示：设置固定方向，会崩溃
        }
        return false;
    }

    static int[] window_attrs;

    /**
     * {@see ActivityInfo#isTranslucentOrFloating(TypedArray)}
     */
    static boolean isTranslucentOrFloating(Context context) {
        if (window_attrs == null) {
            window_attrs = new int[]{
                    Resources.getSystem().getIdentifier("windowIsTranslucent", "attr", "android"),
                    Resources.getSystem().getIdentifier("windowSwipeToDismiss", "attr", "android"),
                    Resources.getSystem().getIdentifier("windowIsFloating", "attr", "android")
            };
        }
        final TypedArray ta = context.obtainStyledAttributes(window_attrs);
        boolean isTranslucent = ta.getBoolean(0, false);
        boolean isSwipeToDismiss = !ta.hasValue(0) && ta.getBoolean(1, false);
        boolean isFloating = ta.getBoolean(2, false);
        ta.recycle();
        return isFloating || isTranslucent || isSwipeToDismiss;
    }

    /**
     * {@see ActivityInfo#isFixedOrientation(int)}
     */
    static boolean isFixedOrientation(@ScreenOrientation int orientation) {
        return isFixedOrientationLandscape(orientation) || isFixedOrientationPortrait(orientation)
                || orientation == SCREEN_ORIENTATION_LOCKED;
    }

    /**
     * {@see ActivityInfo#isFixedOrientationLandscape(int)}
     */
    static boolean isFixedOrientationLandscape(@ScreenOrientation int orientation) {
        return orientation == SCREEN_ORIENTATION_LANDSCAPE
                || orientation == SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                || orientation == SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                || orientation == SCREEN_ORIENTATION_USER_LANDSCAPE;
    }

    /**
     * {@see ActivityInfo#isFixedOrientationPortrait(int)}
     */
    static boolean isFixedOrientationPortrait(@ScreenOrientation int orientation) {
        return orientation == SCREEN_ORIENTATION_PORTRAIT
                || orientation == SCREEN_ORIENTATION_SENSOR_PORTRAIT
                || orientation == SCREEN_ORIENTATION_REVERSE_PORTRAIT
                || orientation == SCREEN_ORIENTATION_USER_PORTRAIT;
    }
}
