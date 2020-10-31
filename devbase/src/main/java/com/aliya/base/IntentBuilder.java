package com.aliya.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Intent 增加类, 方便数据传递.
 *
 * @author a_liYa
 * @date 2016/5/21 21:17.
 */
public class IntentBuilder {

    private Intent mIntent;

    private Bundle mBundle;

    private IntentBuilder(Context context, Class<? extends Activity> clazz) {
        super();
        mIntent = new Intent(context, clazz);
    }

    public static IntentBuilder get(Class<? extends Activity> clazz) {
        return new IntentBuilder(AppUtils.getContext(), clazz);
    }

    /**
     * 设置数据
     *
     * @param key   key 关键字
     * @param value 值
     * @param <T>   泛型可为：int、float、boolean、String、long、byte、Serializable
     * @return return this.
     */
    public <T> IntentBuilder put(String key, T value) {
        if (mBundle == null) {
            mBundle = new Bundle();
        }
        if (value instanceof Integer) {
            mBundle.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            mBundle.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            mBundle.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            mBundle.putLong(key, (Long) value);
        } else if (value instanceof String) {
            mBundle.putString(key, (String) value);
        } else if (value instanceof Byte) {
            mBundle.putByte(key, (Byte) value);
        } else if (value instanceof ArrayList) {
            // 必须在 Serializable 之前判断
            mBundle.putParcelableArrayList(key, (ArrayList) value);
        } else if (value instanceof Serializable) {
            mBundle.putSerializable(key, (Serializable) value);
        } else if (value instanceof Parcelable) {
            mBundle.putParcelable(key, (Parcelable) value);
        }

        return this;
    }

    /**
     * 设置 Flags
     *
     * @param flags The desired flags.
     * @return Return this.
     * @see Intent#setFlags(int)
     */
    public IntentBuilder setFlags(int flags) {
        mIntent.setFlags(flags);
        return this;
    }

    /**
     * 设置 Bundle
     *
     * @param bundle Bundle
     * @return Return this.
     */
    public IntentBuilder putAll(Bundle bundle) {
        mBundle.putAll(bundle);
        return this;
    }

    /**
     * 获取 Intent
     *
     * @return Return intent.
     */
    public Intent intent() {
        if (mBundle != null) {
            mIntent.putExtras(mBundle);
        }
        return mIntent;
    }

    public void startActivity() {
        Intent intent = intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppUtils.getContext().startActivity(intent);
    }

}
