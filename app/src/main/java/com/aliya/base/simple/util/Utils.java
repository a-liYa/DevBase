package com.aliya.base.simple.util;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.aliya.base.simple.R;

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
            String msg = String.format("%" + (depth * 4 + 1) + "s", "") + pop;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                msg += " - " + pop.getFitsSystemWindows();
            }
            Log.e("TAG", msg);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
            if (pop instanceof ViewGroup) {
                for (int i = ((ViewGroup) pop).getChildCount() - 1; i >= 0; i--) {
                    View child = ((ViewGroup) pop).getChildAt(i);
                    child.setTag(R.id.all, depth + 1);
                    stack.add(child);
                }
            }
        }
    }

}
