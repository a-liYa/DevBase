package com.aliya.base.sample.module.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliya.adapter.divider.GridBuilder;
import com.aliya.base.sample.R;
import com.aliya.base.sample.databinding.FragmentRecyclerLayoutBinding;
import com.aliya.base.sample.entity.FunctionBean;
import com.aliya.base.sample.module.GridFunctionAdapter;
import com.aliya.base.sample.module.actionbar.SampleActionBarActivity;
import com.aliya.base.sample.ui.activity.SecondActivity;
import com.aliya.base.sample.ui.activity.handler.HandlerActivity;
import com.aliya.base.sample.ui.activity.launch.SingleInstanceActivity;
import com.aliya.base.sample.ui.activity.thread.ThreadPoolActivity;

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

//        LinearDrawable linearDrawable = new LinearDrawable();
//        linearDrawable.addDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.ic_icon_01));
//        linearDrawable.addDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.ic_icon_02));
//        linearDrawable.addDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.ic_icon_03));
//        linearDrawable.setBounds(0, 0, linearDrawable.getMinimumWidth(),
//                linearDrawable.getMinimumHeight());
//        mBinding.tvIcon.setCompoundDrawables(null, null, linearDrawable, null);
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.tv_icon:
//                Drawable[] drawables = mBinding.tvIcon.getCompoundDrawables();
//                if (drawables[2] instanceof LinearDrawable) {
//                    LinearDrawable linearDrawable = (LinearDrawable) drawables[2];
//                    ScaleDrawable scaleDrawable = new ScaleDrawable(
//                            ContextCompat.getDrawable(getContext(), R.mipmap.ic_icon_04), 1.5f);
//                    linearDrawable.addDrawable(scaleDrawable);
//                    linearDrawable.setBounds(0, 0, linearDrawable.getMinimumWidth(),
//                            linearDrawable.getMinimumHeight());
//                    mBinding.tvIcon.setCompoundDrawables(null, null, linearDrawable, null);
//                }
//                break;
//            case R.id.tv_text:
//                TextView textView = (TextView) v;
//                textView.setText(textView.getText() + "+");
//
//                TextGradientDrawable drawable =
//                        new TextGradientDrawable(textView.getText(), textView,
//                                ((View) v.getParent()).getWidth());
//                drawable.setColor(getResources().getColor(android.R.color.holo_purple));
//                drawable.setCornerRadius(AppUtils.dp2px(4));
//                drawable.setTextColor(Color.WHITE);
//
//                Matrix matrix = new Matrix();
//                matrix.setScale(1.5f, 1.5f,
//                        drawable.getIntrinsicWidth() / 2f, drawable.getIntrinsicHeight() / 2f);
//                matrix.postRotate(0 % 360,
//                        drawable.getIntrinsicWidth() / 2f, drawable.getIntrinsicHeight() / 2f);
//
//                Bitmap rawBitmap = drawable.getDrawingBitmap(matrix);
//
//                mBinding.ivText.setImageBitmap(rawBitmap);
//
//                break;
//        }
//    }
}
