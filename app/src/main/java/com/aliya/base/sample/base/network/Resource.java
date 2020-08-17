package com.aliya.base.sample.base.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Resource 网络数据和状态的封装
 *
 * @author a_liYa
 * @date 2019/3/16 07:50.
 */
public class Resource<T> {

    @NonNull
    public final Status status;
    public final T data;
    public final String message;

    private Resource(@NonNull Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null);
    }

    public enum Status {SUCCESS, ERROR, LOADING}

}
