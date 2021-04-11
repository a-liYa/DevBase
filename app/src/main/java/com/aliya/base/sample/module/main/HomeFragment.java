package com.aliya.base.sample.module.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliya.adapter.divider.GridBuilder;
import com.aliya.base.sample.R;
import com.aliya.base.sample.databinding.FragmentRecyclerLayoutBinding;
import com.aliya.base.sample.entity.FunctionBean;
import com.aliya.base.sample.module.actionbar.SampleActionBarActivity;
import com.aliya.base.sample.module.anim.AnimationActivity;
import com.aliya.base.sample.module.autoicon.AutoLauncherIconActivity;
import com.aliya.base.sample.module.download.DownloadActivity;
import com.aliya.base.sample.module.font.FontActivity;
import com.aliya.base.sample.module.locale.LanguageActivity;
import com.aliya.base.sample.module.power.PowerSampleActivity;
import com.aliya.base.sample.ui.activity.SecondActivity;
import com.aliya.base.sample.ui.activity.UIDemoActivity;
import com.aliya.base.sample.ui.activity.handler.HandlerActivity;
import com.aliya.base.sample.ui.activity.launch.SingleInstanceActivity;
import com.aliya.base.sample.ui.activity.photo.PhotoActivity;
import com.aliya.base.sample.ui.activity.thread.ThreadPoolActivity;
import com.aliya.base.sample.ui.activity.work.WorkManagerActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

/**
 * 首页
 *
 * @author a_liYa
 * @date 2020/10/30 22:14.
 */
public class HomeFragment extends Fragment {

    private FragmentRecyclerLayoutBinding mBinding;

    private List<FunctionBean> mFunctions = new ArrayList<>();

    {
        mFunctions.add(new FunctionBean(
                "主页", SecondActivity.class, R.mipmap.icon_function_home));
        mFunctions.add(new FunctionBean(
                "启动模式", SingleInstanceActivity.class, R.mipmap.icon_function_launch));
        mFunctions.add(new FunctionBean(
                "线程池", ThreadPoolActivity.class, R.mipmap.icon_function_thread));
        mFunctions.add(new FunctionBean(
                "Handler", HandlerActivity.class, R.mipmap.icon_function_handler));
        mFunctions.add(new FunctionBean(
                "ActionBar", SampleActionBarActivity.class, R.mipmap.icon_function_bar));
        mFunctions.add(new FunctionBean(
                "UI Demo", UIDemoActivity.class, R.mipmap.icon_function_ui));
        mFunctions.add(new FunctionBean(
                "Work Manager", WorkManagerActivity.class, R.mipmap.icon_function_work_manager));
        mFunctions.add(new FunctionBean(
                "相册", PhotoActivity.class, R.mipmap.icon_function_photo));
        mFunctions.add(new FunctionBean(
                "字体", FontActivity.class, R.mipmap.icon_function_font));
        mFunctions.add(new FunctionBean(
                "多语言", LanguageActivity.class, R.mipmap.icon_function_language));
        mFunctions.add(new FunctionBean(
                "切换启动图标", AutoLauncherIconActivity.class, R.mipmap.icon_function_switch));
        mFunctions.add(new FunctionBean(
                "熄屏亮屏", PowerSampleActivity.class, R.mipmap.icon_function_screen_off));
        mFunctions.add(new FunctionBean(
                "下载器", DownloadActivity.class, R.mipmap.icon_function_download));
        mFunctions.add(new FunctionBean(
                "动画展示", AnimationActivity.class, R.mipmap.icon_function_animation));
    }

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentRecyclerLayoutBinding.bind(view);

        mBinding.recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mBinding.recycler.addItemDecoration(
                new GridBuilder(getContext())
                        .setSpace(1)
                        .setColorRes(R.color.color_divider)
                        .setIncludeLineBlank(true)
                        .setIncludeEdge(true).build()
        );
        mBinding.recycler.setAdapter(new GridFunctionAdapter(mFunctions));


    }
}
