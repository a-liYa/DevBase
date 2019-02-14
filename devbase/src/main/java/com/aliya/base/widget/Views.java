package com.aliya.base.widget;

import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.Queue;

/**
 * view 相关处理工具类
 *
 * @author a_liYa
 * @date 2019/2/14 11:05.
 */
public final class Views {

    /**
     * 设置View以及子view的状态（可用/不可用）<br/>
     * 广度优先遍历
     *
     * @param view    Assign view
     * @param enabled {@link View#setEnabled(boolean)}
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

}
