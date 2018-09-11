package com.aliya.base.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.aliya.base.R;

import java.util.List;

/**
 * BaseAdapter 的封装, 结合
 *
 * @author a_liYa
 * @date 2016/5/17 20:27.
 * @see BaseAdapter
 */
public abstract class ListAdapter<T> extends BaseAdapter {

    private List<T> datas;

    public ListAdapter(List<T> list) {
        this.datas = list;
    }

    @Override
    public final int getCount() {
        return datas != null ? datas.size() : 0;
    }

    @Override
    public final T getItem(int position) {
        if (datas != null && position >= 0 && position < datas.size())
            return datas.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        ListViewHolder<T> viewHolder = null;
        if (convertView != null) {
            Object tag = convertView.getTag(R.id.tag_view_holder);
            if (tag instanceof ListViewHolder) {
                viewHolder = (ListViewHolder<T>) tag;
            }
        }
        if (viewHolder == null) {
            viewHolder = onCreateViewHolder(parent, position);
            viewHolder.itemView.setTag(R.id.tag_view_holder, viewHolder);
        }
        viewHolder.setPosition(position);
        viewHolder.bindView(getItem(position));
        return viewHolder.itemView;
    }

    protected abstract ListViewHolder onCreateViewHolder(ViewGroup parent, int position);

}
