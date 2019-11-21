package com.aliya.base.util;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * StatusBars
 *
 * @author a_liYa
 * @date 2019-10-16 17:31.
 */
public final class StatusBars {

    /**
     * 设置 DarkMode
     *
     * @param dark 是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean setDarkMode(final Window window, final boolean dark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int visibility = window.getDecorView().getSystemUiVisibility();
            if (dark) {
                visibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                visibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            window.getDecorView().setSystemUiVisibility(visibility);
            return true;
        } else if (Roms.isFlyme()) {
            return fitDarkModeFlyme(window, dark);
        } else if (Roms.isMiui()) {
            return fitDarkModeMIUI(window, dark);
        }
        return false;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上可以用来判断是否为Flyme用户
     * Flyme Android 7.0版本以上需要使用原生适配方法
     */
    static boolean fitDarkModeFlyme(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null); // 获取的静态字段
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏字体图标为深色，需要 MIUI-V6 以上, MIUI-Android-6.0以上使用原生适配方法
     */
    static boolean fitDarkModeMIUI(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag); // 状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag); // 清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }
}
