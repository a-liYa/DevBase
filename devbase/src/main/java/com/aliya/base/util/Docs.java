package com.aliya.base.util;

/**
 * Docs 文档
 *
 * @author a_liYa
 * @date 2019-11-13 17:07.
 */
public final class Docs {

    /**
     * 设置Activity动画有三种方式：
     *
     * 1. Theme <item name="android:windowAnimationStyle">@style/自定义</item>
     * 2. getWindow().getAttributes().windowAnimations = R.style.自定义;
     * 3. overridePendingTransition(int enterAnim, int exitAnim)。
     *
     * 优先级
     * overridePendingTransition(enterAnim, exitAnim) > windowAnimations > windowAnimationStyle
     *
     * 比如 Activity 页面开启顺序为 A -> T -> B, T <- B
     * 通过1、2两种方式设置动画给页面T，则动画属性对应映射如下：
     * A(T:activityOpenExitAnimation) -> T(T:activityOpenEnterAnimation)
     * T(T:activityCloseEnterAnimation) <- B(T:activityCloseExitAnimation)
     */
}
