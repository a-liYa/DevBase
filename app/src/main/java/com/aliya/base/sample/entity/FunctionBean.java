package com.aliya.base.sample.entity;

import java.io.Serializable;

import androidx.annotation.DrawableRes;

/**
 * ItemFunction
 *
 * @author a_liYa
 * @date 2020/10/30 22:22.
 */
public final class FunctionBean implements Serializable {

    @DrawableRes
    public int icon;
    public String title;
    public Class clazz;

    public FunctionBean() {
    }

    public FunctionBean(String title, Class clazz, int icon) {
        this.icon = icon;
        this.title = title;
        this.clazz = clazz;
    }
}
