package com.aliya.base.util;

import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.LinkedList;
import java.util.Queue;

import androidx.annotation.IntDef;

/**
 * view 相关处理工具类
 *
 * @author a_liYa
 * @date 2019/2/14 11:05.
 */
public final class Views {

    /**
     * (广度优先遍历) 设置 view 以及子view的状态 {@link View#setEnabled(boolean)}
     */
    public static void setViewEnabled(View view, boolean enabled) {
        if (view != null) {
            Queue<View> queue = new LinkedList();
            queue.add(view);
            while (!queue.isEmpty()) {
                View poll = queue.poll();
                if (poll != null) {
                    poll.setEnabled(enabled);
                    if (poll instanceof ViewGroup) {
                        ViewGroup parent = (ViewGroup) poll;
                        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
                            queue.add(parent.getChildAt(i));
                        }
                    }
                }
            }
        }
    }

    public static void setViewVisibility(@Visibility int visibility, View... views) {
        if (views != null) {
            for (View v : views) {
                v.setVisibility(visibility);
            }
        }
    }

    @IntDef({View.VISIBLE, View.INVISIBLE, View.GONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {
    }

}
