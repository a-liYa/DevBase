package com.aliya.base.sample.util;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.aliya.base.sample.R;

import java.util.Stack;

/**
 * 工具类
 *
 * @author a_liYa
 * @date 2018/9/29 14:50.
 */
public class Utils {

    public static void printViewTree(Window window) {
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

    public static void printStackTrace() {
        Log.e("StackTrace", "方法调用栈: ");
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        if (trace != null) {
            for (int i = 3; i < trace.length; i++) {
                Log.e("StackTrace", "\n\tat " + trace[i]);
            }
        }
    }

}
