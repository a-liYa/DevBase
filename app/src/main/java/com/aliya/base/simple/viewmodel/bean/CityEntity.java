package com.aliya.base.simple.viewmodel.bean;

import java.io.Serializable;

/**
 * 城市信息
 *
 * @author a_liYa
 * @date 2019/2/26 16:45.
 */
public class CityEntity implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
