package com.aliya.base.sample.util;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.aliya.base.sample.R;

import java.util.Map;
import java.util.Set;
import java.io.File;
import java.util.Stack;

/**
 * 工具类
 *
 * @author a_liYa
 * @date 2018/9/29 14:50.
 */
public class Utils {

    public static void printViewTree(View view) {
        Stack<View> stack = new Stack<>();
        view.setTag(R.id.all, 0);
        stack.add(view);
        while (!stack.isEmpty()) { // 深度优先遍历
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

    public static void printSDCardInfo() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs statFs = new StatFs(sdcardDir.getPath());

            long totalBytes;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                totalBytes = statFs.getTotalBytes();
            } else {
                // 区块数量 * 区块大小
                totalBytes = statFs.getBlockSize() * statFs.getBlockCount();
            }

            long availableBytes;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                availableBytes = statFs.getAvailableBytes();
            } else {
                // 剩余区块数量 * 区块大小
                availableBytes = statFs.getBlockSize() * statFs.getAvailableBlocks();
            }

            Log.e("TAG", "SD卡总空间:" + totalBytes + "Byte");
            Log.e("TAG", "剩余空间:" + availableBytes + "Byte");
            // statFs.getTotalBytes() >= statFs.getFreeBytes() >= statFs.getAvailableBytes()
        }
    }
    public static void printAllThreadName() {
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        Set<Thread> threadSet = allStackTraces.keySet();
        int index = 0;
        for (Thread thread : threadSet) {
            Log.e("TAG", index + " thread name : " + thread.getName());
        }
    }

}
