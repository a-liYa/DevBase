package com.aliya.base.sample.module.nested;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliya.adapter.RecyclerAdapter;
import com.aliya.adapter.RecyclerViewHolder;
import com.aliya.base.sample.R;
import com.aliya.base.sample.databinding.LayoutRecyclerViewBinding;
import com.aliya.base.sample.databinding.NestedItemTextLayoutBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


public class RecyclerViewFragment extends Fragment {

    LayoutRecyclerViewBinding mBinding;
    private RecyclerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mBinding = LayoutRecyclerViewBinding.bind(view);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new Adapter(getData());
        mBinding.recyclerView.setAdapter(mAdapter);
    }


    private List<String> getData() {
        int i = 0;
        List<String> data = new ArrayList<>();
        for(int tempI = i; i < tempI + 40; i ++) {
            data.add("ChildView item " + i);
        }
        return data;
    }

    class Adapter extends RecyclerAdapter<String> {

        public Adapter(List<String> data) {
            super(data);
        }

        @Override
        public RecyclerViewHolder onAbsCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(parent);
        }
    }

    class ViewHolder extends RecyclerViewHolder<String> {

        private final NestedItemTextLayoutBinding mBinding;

        public ViewHolder(@NonNull ViewGroup parent) {
            super(parent, R.layout.nested_item_text_layout);
            mBinding = NestedItemTextLayoutBinding.bind(itemView);
        }

        @Override
        public void bindData(String data) {
            mBinding.tvText.setText(data);
        }
    }
}
