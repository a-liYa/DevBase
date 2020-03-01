package com.aliya.base.sample;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aliya.base.click.ClickTracker;
import com.aliya.base.manager.AppManager;
import com.aliya.base.sample.base.BaseActivity;
import com.aliya.base.sample.common.AppSetting;
import com.aliya.base.sample.ui.MainTabLayout;
import com.aliya.base.sample.ui.SideFloatHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主界面
 *
 * @author a_liYa
 * @date 2019/3/11 下午4:28.
 */
public class MainActivity extends BaseActivity implements SideFloatHelper.FloatMark {

    @BindView(R.id.tab_layout)
    MainTabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSwipeBackEnable(false);

        bindData();

//        startActivity(new Intent(this, LiveEventActivity.class));
    }

    private void bindData() {
        mTabLayout.setupBind(this, getSupportFragmentManager(), R.id.frame_layout);
        mTabLayout.setAdapter(new MainTabAdapterImpl());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (AppSetting.get().isBackToBackground()) {
                moveTaskToBack(true); // 返回键切至后台不关闭页面
                return true;
            } else {
                if (!quitApp()) return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean quitApp() {
        if (ClickTracker.isDoubleClick(1000)) {
            AppManager.get().finishAllActivity();
            return true;
        } else {
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public boolean isDisable() {
        return false;
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
