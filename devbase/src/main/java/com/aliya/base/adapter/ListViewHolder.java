package com.aliya.base.adapter;

import android.view.View;
import android.view.ViewGroup;

import static com.aliya.base.AppUtils.inflate;

/**
 * ViewHolder 基类 (主要用于ListView、GridView)
 *
 * @param <T> 数据的泛型
 * @author a_liYa
 * @date 2016-3-1 下午12:51:16
 * @see ListAdapter
 */
public abstract class ListViewHolder<T> {

    public final View itemView;
    public T mData;
    private int position;

    public ListViewHolder(View itemView) {
        this.itemView = itemView;
    }

    public ListViewHolder(ViewGroup parent, int layoutId) {
        this(inflate(parent, layoutId, false));
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * 设置数据
     *
     * @param data item data.
     */
    public void setData(T data) {
        this.mData = data;
        onBindView(data);
    }

    /**
     * bind data to view.
     *
     * @param data item data.
     */
    public abstract void onBindView(T data);

}
