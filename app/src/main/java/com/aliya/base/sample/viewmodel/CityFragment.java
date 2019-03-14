package com.aliya.base.sample.viewmodel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.aliya.adapter.RecyclerAdapter;
import com.aliya.adapter.RecyclerViewHolder;
import com.aliya.adapter.click.ItemClickCallback;
import com.aliya.adapter.click.OnItemClickListener;
import com.aliya.base.sample.R;
import com.aliya.base.sample.base.BaseFragment;
import com.aliya.base.sample.viewmodel.bean.CityEntity;
import com.aliya.base.sample.viewmodel.bean.DataStore;
import com.aliya.base.sample.viewmodel.bean.ProvinceEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 城市展示 Fragment
 *
 * @author a_liYa
 * @date 2019/2/26 下午5:35.
 */
public class CityFragment extends BaseFragment implements Observer<ProvinceEntity>,
        OnItemClickListener {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private Adapter mAdapter;
    private ProvinceEntity mProvince;
    private AreaSelectViewModel mViewModel;

    public CityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(AreaSelectViewModel.class);
        mViewModel.getSelectedProvince().observe(this, this);
    }

    @Override
    public void onChanged(@Nullable ProvinceEntity provinceEntity) {
        if (mProvince != provinceEntity) {
            mProvince = provinceEntity;
            if (mAdapter == null) {
                mAdapter = new Adapter(provinceEntity.getChild(),
                        mViewModel.getSelectedCity().getValue());
                mAdapter.setOnItemClickListener(this);
                mRecycler.setAdapter(mAdapter);
            } else {
                mAdapter.setData(mProvince.getChild(), true);
            }
        }
    }

    @Override
    public void onItemClick(View itemView, int position) {
        mViewModel.selectCity(mAdapter.getData(position));
    }

    static class Adapter extends RecyclerAdapter<CityEntity> {

        private DataStore<CityEntity> mSelected;

        public Adapter(List<CityEntity> data, CityEntity city) {
            super(data);
            mSelected = new DataStore<>(city);
        }

        @Override
        public RecyclerViewHolder onAbsCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(parent, mSelected);
        }
    }

    static class ViewHolder extends RecyclerViewHolder<CityEntity> implements ItemClickCallback {

        @BindView(R.id.tv_name)
        TextView mTvName;

        private DataStore<CityEntity> mStore;

        public ViewHolder(@NonNull ViewGroup parent, DataStore<CityEntity> store) {
            super(parent, R.layout.item_city);
            ButterKnife.bind(this, itemView);
            mStore = store;
        }

        @Override
        public void bindData(CityEntity data) {
            mTvName.setText(data.getName());
            mTvName.setSelected(mStore.getData() == data);
        }

        @Override
        public void onItemClick(View itemView, int position) {
            mStore.setData(mData);
            ViewParent parent = itemView.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).dispatchSetSelected(false);
            }
            itemView.setSelected(true);
        }
    }

}
