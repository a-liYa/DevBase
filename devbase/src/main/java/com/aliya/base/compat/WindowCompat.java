package com.aliya.base.compat;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewParent;

import java.lang.reflect.Method;

/**
 * WindowCompat
 *
 * @author a_liYa
 * @date 2019-11-19 11:23.
 */
public class WindowCompat {

    /**
     * 让 activity transition 动画过程中可以正常渲染页面
     */
    public static void openDrawDuringWindowsAnimating(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        if (decorView.getParent() == null) {
            decorView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {
                    v.removeOnAttachStateChangeListener(this);
                    setDrawDuringWindowsAnimating(v.getRootView());
                }

                @Override
                public void onViewDetachedFromWindow(View v) {
                }
            });
        } else {
            setDrawDuringWindowsAnimating(decorView);
        }
    }

    /**
     * 反射修改内部字段
     */
    private static void setDrawDuringWindowsAnimating(View decorView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            // 4.2及以下版本 不存在setDrawDuringWindowsAnimating，需要特殊处理
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                // android4.2可以反射handleDispatchDoneAnimating来解决
                try {
                    ViewParent viewRoot = decorView.getParent();
                    Method method = viewRoot.getClass()
                            .getDeclaredMethod("handleDispatchDoneAnimating");
                    method.setAccessible(true);
                    method.invoke(viewRoot);
                } catch (Exception e) {
                    // ignore it.
                }
            } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                // 4.3及以上，反射setDrawDuringWindowsAnimating来实现动画过程中渲染
                try {
                    ViewParent viewRoot = decorView.getParent();
                    Method method = viewRoot.getClass()
                            .getDeclaredMethod("setDrawDuringWindowsAnimating", boolean.class);
                    method.setAccessible(true);
                    method.invoke(viewRoot, true);
                } catch (Exception e) {
                    // ignore it.
                }
            }
        }
    }
}
