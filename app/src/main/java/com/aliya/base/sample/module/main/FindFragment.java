package com.aliya.base.sample.module.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliya.adapter.divider.GridBuilder;
import com.aliya.base.sample.R;
import com.aliya.base.sample.applet.MinorMainActivity;
import com.aliya.base.sample.databinding.FragmentRecyclerLayoutBinding;
import com.aliya.base.sample.entity.FunctionBean;
import com.aliya.base.sample.module.image.BigImageActivity;
import com.aliya.base.sample.module.listen.ListenNewsActivity;
import com.aliya.base.sample.module.nested.NestedViewActivity;
import com.aliya.base.sample.module.reference.WeakReferenceActivity;
import com.aliya.base.sample.ui.activity.LiveEventActivity;
import com.aliya.base.sample.ui.activity.MineActivity;
import com.aliya.base.sample.ui.activity.NotificationActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

/**
 * 发现页
 *
 * @author a_liYa
 * @date 2020-02-28 15:58.
 */
public class FindFragment extends Fragment {

    private FragmentRecyclerLayoutBinding mViewBinding;

    private List<FunctionBean> mFunctions = new ArrayList<>();

    {
        mFunctions.add(new FunctionBean(
                "我的", MineActivity.class, R.mipmap.icon_function_home));
        mFunctions.add(new FunctionBean(
                "听新闻", ListenNewsActivity.class, R.mipmap.icon_function_launch));
        mFunctions.add(new FunctionBean(
                "模拟通知", NotificationActivity.class, R.mipmap.icon_function_thread));
        mFunctions.add(new FunctionBean(
                "LiveEvent", LiveEventActivity.class, R.mipmap.icon_function_handler));
        mFunctions.add(new FunctionBean(
                "弱引用", WeakReferenceActivity.class, R.mipmap.icon_function_bar));
        mFunctions.add(new FunctionBean(
                "小程序", MinorMainActivity.class, R.mipmap.icon_function_ui));
        mFunctions.add(new FunctionBean(
                "大图", BigImageActivity.class, R.mipmap.icon_function_ui));
        mFunctions.add(new FunctionBean(
                "嵌套滑动", NestedViewActivity.class, R.mipmap.icon_function_ui));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewBinding = FragmentRecyclerLayoutBinding.bind(view);
        mViewBinding.recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));

        mViewBinding.recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mViewBinding.recycler.addItemDecoration(
                new GridBuilder(getContext())
                        .setSpace(1)
                        .setColorRes(R.color.color_divider)
                        .setIncludeLineBlank(true)
                        .setIncludeEdge(true).build()
        );
        mViewBinding.recycler.setAdapter(new GridFunctionAdapter(mFunctions));
    }
}
