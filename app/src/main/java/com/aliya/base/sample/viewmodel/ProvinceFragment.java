package com.aliya.base.sample.viewmodel;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
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
import com.aliya.base.sample.util.JsonUtils;
import com.aliya.base.sample.viewmodel.bean.ProvinceEntity;
import com.aliya.base.util.IOs;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 省份数据展示 Fragment
 *
 * @author a_liYa
 * @date 2019/2/26 下午4:15.
 */
public class ProvinceFragment extends BaseFragment implements OnItemClickListener {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private Adapter mAdapter;
    private AreaSelectViewModel mViewModel;

    public ProvinceFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.province_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(AreaSelectViewModel.class);

        String json = IOs.getAssetsText(getContext(), "city_json.json");
        List<ProvinceEntity> list = JsonUtils.parseArray(json, ProvinceEntity.class);
        mRecycler.setAdapter(mAdapter = new Adapter(list, mViewModel));
        mAdapter.setOnItemClickListener(this);
        mViewModel.selectProvince(mAdapter.getData(0));
    }

    @Override
    public void onItemClick(View itemView, int position) {
        mViewModel.selectProvince(mAdapter.getData(position));
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

        @BindView(R.id.tv_name)
        TextView mTvName;

        private AreaSelectViewModel mSelected;

        public ViewHolder(@NonNull ViewGroup parent, AreaSelectViewModel selected) {
            super(parent, R.layout.item_province);
            ButterKnife.bind(this, itemView);
            mSelected = selected;
        }

        @Override
        public void bindData(ProvinceEntity data) {
            super.bindData(data);
            mTvName.setText(data.getName());
            mTvName.setSelected(mSelected.getSelectedProvince().getValue() == data);
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
