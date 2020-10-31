package com.aliya.base.sample.module.main;

import android.view.View;
import android.view.ViewGroup;

import com.aliya.adapter.RecyclerAdapter;
import com.aliya.adapter.RecyclerViewHolder;
import com.aliya.adapter.click.ItemClickCallback;
import com.aliya.base.IntentBuilder;
import com.aliya.base.sample.R;
import com.aliya.base.sample.databinding.ItemFunctionLayoutBinding;
import com.aliya.base.sample.entity.FunctionBean;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * GridAdapter
 *
 * @author a_liYa
 * @date 2020/10/30 22:17.
 */
public class GridFunctionAdapter extends RecyclerAdapter<FunctionBean> {

    public GridFunctionAdapter(List<FunctionBean> data) {
        super(data);
    }

    @Override
    public RecyclerViewHolder onAbsCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    private static class ViewHolder extends RecyclerViewHolder<FunctionBean> implements ItemClickCallback {

        private final ItemFunctionLayoutBinding mViewBinding;

        public ViewHolder(@NonNull ViewGroup parent) {
            super(parent, R.layout.item_function_layout);
            mViewBinding = ItemFunctionLayoutBinding.bind(itemView);
        }

        @Override
        public void bindData(FunctionBean data) {
            mViewBinding.tvTitle.setText(data.title);
            mViewBinding.ivIcon.setImageResource(data.icon);
        }

        @Override
        public void onItemClick(View itemView, int position) {
            IntentBuilder.get(mData.activityClass).startActivity();
        }
    }
}
