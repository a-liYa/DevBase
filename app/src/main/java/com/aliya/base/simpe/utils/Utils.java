package com.aliya.base.simpe.utils;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.aliya.base.simpe.R;

import java.util.Stack;

/**
 * 工具类
 *
 * @author a_liYa
 * @date 2018/9/29 14:50.
 */
public class Utils {

    public static void printViews(Window window) {
        Stack<View> stack = new Stack<>();
        View decorView = window.getDecorView();
        decorView.setTag(R.id.all, 0);
        stack.add(decorView);
        while (!stack.isEmpty()) {
            View pop = stack.pop();
            Integer depth = (Integer) pop.getTag(R.id.all);
            Log.e("TAG", String.format("%" + (depth * 4 + 1) + "s", "") + pop);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
            if (pop instanceof ViewGroup) {
                for (int i = ((ViewGroup) pop).getChildCount() - 1; i >= 0 ; i--) {
                    View child = ((ViewGroup) pop).getChildAt(i);
                    child.setTag(R.id.all, depth + 1);
                    stack.add(child);
                }
            }
        }
    }

}
