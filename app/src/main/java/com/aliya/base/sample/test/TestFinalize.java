package com.aliya.base.sample.test;

import android.util.Log;

import java.lang.reflect.Field;

/**
 * TestFinalize 模拟 finalize() 超时
 *
 * @author a_liYa
 * @date 2019/5/19 11:07.
 */
public class TestFinalize {

    public void printMaxFinalizeNanos() {
        try {
            Class<?> clazz = Class.forName("java.lang.Daemons");
            Field maxField = clazz.getDeclaredField("MAX_FINALIZE_NANOS");
            maxField.setAccessible(true);
            Object o = maxField.get(null);
            Log.e("TAG", "MAX_FINALIZE_NANOS: " + o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.e("TAG", "finalize: start");
        Thread.sleep(100 * 1000);
        Log.e("TAG", "finalize: end");
    }
}
