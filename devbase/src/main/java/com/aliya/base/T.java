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
     * @return toast, {@link #isShow} = false 或 message == null 时返回 null.
     */
    public static Toast showShort(Context context, CharSequence message) {
        return show(context, message, Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast {@link Toast#LENGTH_SHORT}
     *
     * @param context context
     * @param resId   Resource id for the string.
     * @return toast, {@link #isShow} = false 时返回 null.
     */
    public static Toast showShort(Context context, int resId) {
        return show(context, resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast {@link Toast#LENGTH_LONG}
     *
     * @param context context
     * @param message 显示内容, 为空不显示
     * @return toast, {@link #isShow} = false 或 message == null 时返回 null.
     */
    public static Toast showLong(Context context, CharSequence message) {
        return show(context, message, Toast.LENGTH_LONG);
    }

    /**
     * 显示Toast {@link Toast#LENGTH_LONG}
     *
     * @param context context
     * @param resId   Resource id for the string.
     * @return toast, {@link #isShow} = false 时返回 null.
     */
    public static Toast showLong(Context context, int resId) {
        return show(context, resId, Toast.LENGTH_LONG);
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context  context
     * @param message  显示内容, 为空不显示
     * @param duration 时长类型
     * @return toast, {@link #isShow} = false 或 message == null 时返回 null.
     */
    public static Toast show(Context context, CharSequence message, int duration) {
        Toast toast = null;
        if (isShow && !TextUtils.isEmpty(message)) {
            toast = Toast.makeText(context, message, duration);
            toast.show();
        }
        return toast;
    }


    /**
     * 自定义显示Toast时间
     *
     * @param context  context
     * @param resId    Resource id for the string.
     * @param duration 时长类型
     * @return toast, {@link #isShow} = false 时返回 null.
     */
    public static Toast show(Context context, int resId, int duration) {
        Toast toast = null;
        if (isShow) {
            toast = Toast.makeText(context, resId, duration);
            toast.show();
        }
        return toast;
    }

    /**
     * 注解,限定取值范围
     */
    @IntDef({Toast.LENGTH_SHORT, Toast.LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

}
