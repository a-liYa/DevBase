package com.aliya.base.sample.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliya.base.sample.R;
import com.aliya.base.sample.databinding.FragmentFindBinding;
import com.aliya.base.sample.module.listen.ListenNewsActivity;
import com.aliya.base.sample.module.reference.WeakReferenceActivity;
import com.aliya.base.sample.ui.activity.LiveEventActivity;
import com.aliya.base.sample.ui.activity.MineActivity;
import com.aliya.base.sample.ui.activity.NotificationActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 发现页
 *
 * @author a_liYa
 * @date 2020-02-28 15:58.
 */
public class FindFragment extends Fragment implements View.OnClickListener {

    private FragmentFindBinding mViewBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewBinding = FragmentFindBinding.inflate(inflater, container, false);
        return mViewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewBinding.tvMine.setOnClickListener(this);
        mViewBinding.tvListen.setOnClickListener(this);
        mViewBinding.tvNotify.setOnClickListener(this);
        mViewBinding.tvEvent.setOnClickListener(this);
        mViewBinding.tvWeak.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_mine:
                startActivity(new Intent(getContext(), MineActivity.class));
                break;
            case R.id.tv_listen:
                startActivity(new Intent(getContext(), ListenNewsActivity.class));
                break;
            case R.id.tv_notify:
                startActivity(new Intent(getContext(), NotificationActivity.class));
                break;
            case R.id.tv_event:
                startActivity(new Intent(getContext(), LiveEventActivity.class));
                break;
            case R.id.tv_weak:
                startActivity(new Intent(getContext(), WeakReferenceActivity.class));
                break;
        }
    }
}
