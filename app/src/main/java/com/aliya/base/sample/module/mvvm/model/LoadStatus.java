package com.aliya.base.sample.module.mvvm.model;

/**
 * LoadStatus
 *
 * @author a_liYa
 * @date 2021/6/12 20:41.
 */
public class LoadStatus {
    public int code; // -1:加载中；0:成功；大于0:失败
    public String msg;

    public LoadStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public boolean isLoading() {
        return code == -1;
    }

    public boolean isFinished() {
        return code == 0;
    }

    public boolean isFailed() {
        return code > 0;
    }
}
