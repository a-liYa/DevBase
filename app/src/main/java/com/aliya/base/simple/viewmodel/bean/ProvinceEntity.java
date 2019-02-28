package com.aliya.base.simple.viewmodel.bean;

import java.util.List;

/**
 * 省份信息
 *
 * @author a_liYa
 * @date 2019/2/26 16:48.
 */
public class ProvinceEntity extends CityEntity {

    private List<CityEntity> child;

    public List<CityEntity> getChild() {
        return child;
    }

    public void setChild(List<CityEntity> child) {
        this.child = child;
    }

}
