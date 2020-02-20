package com.aliya.base.sample.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliya.base.sample.R;
import com.aliya.base.sample.ui.activity.launch.SingleInstanceActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.tv_title).setOnClickListener(this);
        view.findViewById(R.id.tv_content).setOnClickListener(this);
        view.findViewById(R.id.tv_launch_mode).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title:
                break;
            case R.id.tv_launch_mode:
                startActivity(new Intent(getContext(), SingleInstanceActivity.class));
                break;
        }
    }

}
