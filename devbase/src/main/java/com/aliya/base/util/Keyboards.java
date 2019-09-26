package com.aliya.base.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 软键盘相关操作
 *
 * @author a_liYa
 * @date 2019-09-26 10:35.
 */
public final class Keyboards {

    /**
     * 显示软键盘
     *
     * @param view 希望接收软键盘输入的视图
     */
    public static void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param view 接收软键盘输入的视图
     */
    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            // 实际上传递一个存在于当前布局 ViewTree 中，随意一个 View 的 windowToken 就可以了
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 切换软键盘(若正在显示, 则隐藏, 否则反之)
     *
     * @param view 任意一个 View
     */
    public static void toggleSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, 0);
        }
    }
}
