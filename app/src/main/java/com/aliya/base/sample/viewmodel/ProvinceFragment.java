package com.aliya.base.sample.viewmodel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.aliya.adapter.RecyclerAdapter;
import com.aliya.adapter.RecyclerViewHolder;
import com.aliya.adapter.click.ItemClickCallback;
import com.aliya.adapter.click.OnItemClickListener;
import com.aliya.base.sample.R;
import com.aliya.base.sample.base.BaseFragment;
import com.aliya.base.sample.databinding.ItemProvinceBinding;
import com.aliya.base.sample.databinding.ProvinceFragmentBinding;
import com.aliya.base.sample.viewmodel.bean.ProvinceEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;


/**
 * 省份数据展示 Fragment
 *
 * @author a_liYa
 * @date 2019/2/26 下午4:15.
 */
public class ProvinceFragment extends BaseFragment implements OnItemClickListener,
        Observer<List<ProvinceEntity>> {

    private Adapter mAdapter;
    private AreaSelectViewModel mViewModel;
    private ProvinceFragmentBinding mViewBinding;

    public ProvinceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewBinding = ProvinceFragmentBinding.inflate(inflater, container, false);
        return mViewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewBinding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(AreaSelectViewModel.class);
        mViewModel.getProvinces().observe(this, this);
    }

    @Override
    public void onItemClick(View itemView, int position) {
        mViewModel.selectProvince(mAdapter.getData(position));
    }

    @Override
    public void onChanged(@Nullable List<ProvinceEntity> provinces) {
        mViewBinding.recycler.setAdapter(mAdapter = new Adapter(provinces, mViewModel));
        mAdapter.setOnItemClickListener(this);
        if (mViewModel.getSelectedProvince().getValue() == null) {
            mViewModel.selectProvince(mAdapter.getData(0));
        }
    }

    static class Adapter extends RecyclerAdapter<ProvinceEntity> {

        private AreaSelectViewModel mSelected;

        public Adapter(List<ProvinceEntity> data, AreaSelectViewModel selected) {
            super(data);
            mSelected = selected;
        }

        @Override
        public RecyclerViewHolder onAbsCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(parent, mSelected);
        }
    }

    static class ViewHolder extends RecyclerViewHolder<ProvinceEntity> implements
            ItemClickCallback {

        final ItemProvinceBinding mViewBinding;
        private AreaSelectViewModel mSelected;

        public ViewHolder(@NonNull ViewGroup parent, AreaSelectViewModel selected) {
            super(parent, R.layout.item_province);
            mViewBinding = ItemProvinceBinding.bind(itemView);
            mSelected = selected;
        }

        @Override
        public void bindData(ProvinceEntity data) {
            super.bindData(data);
            mViewBinding.tvName.setText(data.getName());
            mViewBinding.tvName.setSelected(mSelected.getSelectedProvince().getValue() == data);
        }

        @Override
        public void onItemClick(View itemView, int position) {
            // 选中逻辑内部处理
            ViewParent parent = itemView.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).dispatchSetSelected(false);
            }
            itemView.setSelected(true);
        }
    }

}
