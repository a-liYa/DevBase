package com.aliya.base.sample.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliya.base.sample.R;
import com.aliya.base.sample.module.listen.ListenNewsActivity;
import com.aliya.base.sample.ui.activity.MineActivity;

/**
 * 发现页
 *
 * @author a_liYa
 * @date 2020-02-28 15:58.
 */
public class FindFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.tv_mine).setOnClickListener(this);
        view.findViewById(R.id.tv_listen).setOnClickListener(this);
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
        }
    }
}
