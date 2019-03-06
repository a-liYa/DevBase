package com.aliya.base.sample.viewmodel.bean;

import java.io.Serializable;

/**
 * 数据临时仓储
 *
 * @author a_liYa
 * @date 2019/2/28 14:39.
 */
public class DataStore<T> implements Serializable {

    private T mData;

    public DataStore() {
    }

    public DataStore(T data) {
        mData = data;
    }

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
    }

}
