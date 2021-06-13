package com.aliya.base.sample.module.mvvm.model;

/**
 * ResultBean
 *
 * @author a_liYa
 * @date 2021/6/12 19:52.
 */
public class ResultData<T> {

    public T data;
    public int code; // 0 成功; 其他为失败
    public String msg;

    public ResultData(T data) {
        this.data = data;
    }

    public ResultData(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return code == 0;
    }
}
