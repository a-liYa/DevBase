package com.aliya.base.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliya.base.simple.ui.MainTabLayout;
import com.aliya.base.simple.viewmodel.CitySelectActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tab_layout)
    MainTabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getDecorChildView(0).setFitsSystemWindows(false);
        mTabLayout.setupBind(this, getSupportFragmentManager(), R.id.frame_layout);
        mTabLayout.setAdapter(new MainTabAdapterImpl());
        startActivity(new Intent(this, CitySelectActivity.class));
        finish();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true); // 返回键切至后台不关闭页面
    }

    public View getDecorChildView(int index) {
        View v = ((ViewGroup) getWindow().getDecorView()).getChildAt(index);
        return v;
    }

    class MainTabAdapterImpl implements MainTabLayout.TabAdapter {

        private MainTab[] tabs = MainTab.values();

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public MainTabLayout.TabInfo getTab(int position, ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_main_tab_layout, parent, false);

            MainTab tab = tabs[position];

            ImageView imageView = view.findViewById(R.id.iv_tab);
            imageView.setImageResource(tab.getResIcon());

            TextView textView = view.findViewById(R.id.tv_tab);
            textView.setText(tab.getTitle());

            return new MainTabLayout.TabInfo(view, tab.getClz(), null);
        }

    }

}
