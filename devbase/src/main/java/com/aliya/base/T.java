package com.aliya.base;

import android.content.Context;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.widget.Toast;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Toast工具类
 *
 * @author a_liYa
 * @date 2016-3-30 下午11:46:39
 */
public final class T {

    public static boolean isShow = true;

    private T() {
    }

    /**
     * 显示Toast {@link Toast#LENGTH_SHORT}
     *
     * @param context context
     * @param message 显示内容, 为空不显示
     */
    public static void showShort(Context context, CharSequence message) {
        show(context, message, Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast {@link Toast#LENGTH_SHORT}
     *
     * @param context context
     * @param resId   Resource id for the string.
     */
    public static void showShort(Context context, int resId) {
        show(context, resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast {@link Toast#LENGTH_LONG}
     *
     * @param context context
     * @param message 显示内容, 为空不显示
     */
    public static void showLong(Context context, CharSequence message) {
        show(context, message, Toast.LENGTH_LONG);
    }

    /**
     * 显示Toast {@link Toast#LENGTH_LONG}
     *
     * @param context context
     * @param resId   Resource id for the string.
     */
    public static void showLong(Context context, int resId) {
        show(context, resId, Toast.LENGTH_LONG);
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context  context
     * @param message  显示内容, 为空不显示
     * @param duration 时长类型
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow && !TextUtils.isEmpty(message))
            Toast.makeText(context, message, duration).show();
    }


    /**
     * 自定义显示Toast时间
     *
     * @param context  context
     * @param resId    Resource id for the string.
     * @param duration 时长类型
     */
    public static void show(Context context, int resId, int duration) {
        if (isShow)
            Toast.makeText(context, resId, duration).show();
    }

    /**
     * 注解,限定取值范围
     */
    @IntDef({Toast.LENGTH_SHORT, Toast.LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

}
