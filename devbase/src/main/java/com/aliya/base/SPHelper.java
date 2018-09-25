package com.aliya.base;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.Set;


/**
 * 轻量级数据存储助手
 *
 * @author a_liYa
 * @date 2016-3-28 下午9:06:16
 */
public final class SPHelper {

    private static WeakReference<SPHelper> mSpHelperWeak;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SPHelper() {
        this(AppUtils.getAppName());
    }

    private SPHelper(String name) {
        sharedPreferences = AppUtils.getContext().getSharedPreferences(name, Activity.MODE_PRIVATE);
    }

    /**
     * 获取实例
     *
     * @return {@link #SPHelper()}
     */
    public static SPHelper get() {
        SPHelper spHelper;
        if (mSpHelperWeak == null || (spHelper = mSpHelperWeak.get()) == null) {
            mSpHelperWeak = new WeakReference<>(spHelper = new SPHelper());
        }
        return spHelper;
    }

    /**
     * 获取实例
     *
     * @param spName shared preferences name.
     * @return {@link #SPHelper(String)}
     */
    public static SPHelper get(String spName) {
        return new SPHelper(spName);
    }

    public SharedPreferences.Editor getSharedPreferencesEditor() {
        if (editor == null) {
            editor = sharedPreferences.edit();
        }
        return editor;
    }

    public boolean hasKey(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.contains(key);
        }
        return false;
    }

    /**
     * 保存数据 需要手动commit
     *
     * @param key   关键字
     * @param value 值
     * @param <T>   泛型可为：int、float、boolean、String、long、Set&lt;String&gt;
     * @return this
     */
    public <T> SPHelper put(String key, T value) {
        editor = getSharedPreferencesEditor();
        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);

        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);

        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);

        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);

        } else if (value instanceof String) {
            editor.putString(key, (String) value);

        } else if (value instanceof Set) {
            editor.putStringSet(key, (Set<String>) value);

        } else if (value instanceof Serializable) {
            editor.putString(key, objectToBase64(value));
        }
        return this;
    }

    /**
     * 提交 异步
     *
     * @return 异步永远返回 false.
     */
    public boolean commit() {
        return commit(true);
    }

    /**
     * 提交
     *
     * @param isAsync true : 表示异步, false : 表示同步.
     * @return true : 成功, false : 失败, 异步永远返回 false.
     */
    public boolean commit(boolean isAsync) {
        boolean result = false;
        if (editor != null) {
            if (isAsync) {
                editor.apply();
            } else {
                result = editor.commit();
            }
            editor = null;
        }
        return result;
    }

    public String get(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public long get(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    public int get(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public float get(String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    public boolean get(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public Set<String> get(String key, Set<String> defValue) {
        return sharedPreferences.getStringSet(key, defValue);
    }

    /**
     * 获取保存的bean对象
     *
     * @param key key
     * @param <T> 获取对象的类型
     * @return 返回对应的对象，不存在或出错时返回 null
     */
    public <T> T getObject(String key) {
        try {
            if (sharedPreferences.contains(key)) {
                String string = sharedPreferences.getString(key, "");
                if (!TextUtils.isEmpty(string)) {
                    return (T) new ObjectInputStream(
                            new ByteArrayInputStream(
                                    Base64.decode(string, Base64.DEFAULT))).readObject();
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * 清空所有数据数据
     */
    public void clear() {
        editor = getSharedPreferencesEditor();
        editor.clear().commit();
    }

    /**
     * 清除存储的KEY
     *
     * @param key 存储对应的 key
     */
    public void remove(String key) {
        editor = getSharedPreferencesEditor();
        editor.remove(key).commit();
    }

    private String objectToBase64(Object obj) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            new ObjectOutputStream(outputStream).writeObject(obj);
            return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        } catch (IOException e) {
            return null;
        }

    }
}
