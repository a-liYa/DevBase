package com.aliya.base.sample.module.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aliya.base.sample.R;
import com.aliya.base.sample.ui.activity.SecondActivity;
import com.aliya.base.sample.ui.activity.launch.SingleInstanceActivity;
import com.aliya.base.sample.ui.activity.thread.ThreadPoolActivity;
import com.aliya.base.sample.util.Utils;

import java.util.Map;
import java.util.Set;

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
        view.findViewById(R.id.tv_launch_mode).setOnClickListener(this);
        view.findViewById(R.id.tv_thread_pool).setOnClickListener(this);
        view.findViewById(R.id.tv_handler).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title:
                startActivity(new Intent(getContext(), SecondActivity.class));
                break;
            case R.id.tv_launch_mode:
                startActivity(new Intent(getContext(), SingleInstanceActivity.class));
                break;
            case R.id.tv_thread_pool:
                getActivity().startActivity(new Intent(getContext(), ThreadPoolActivity.class));
                break;
            case R.id.tv_handler:
//                getActivity().startActivity(new Intent(getContext(), HandlerActivity.class));
                showDialog();
                break;
        }
    }

    private void showDialog() {
        Dialog dialog = new Dialog(getActivity());
        TextView textView = new TextView(getActivity());
        textView.setText("我是Dialog");
        dialog.setContentView(textView);
        Utils.printViewTree(dialog.getWindow());
        dialog.show();
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        Log.e("TAG", "showDialog: " + allStackTraces.size());
        Set<Thread> threadSet = allStackTraces.keySet();
        for (Thread thread : threadSet) {
            Log.e("TAG", "thread name : " + thread.getName());
        }

    }
}
