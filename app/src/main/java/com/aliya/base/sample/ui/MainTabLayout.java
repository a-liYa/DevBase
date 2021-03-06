package com.aliya.base.sample.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * 自定义MainTabLayout
 *
 * @author a_liYa
 * @date 2016-4-23 上午10:49:08
 */
public class MainTabLayout extends LinearLayout implements View.OnClickListener {

    private TabAdapter mAdapter;
    private OnSelectedListener mOnSelectedListener;
    private Context mContext;
    private FragmentManager mFragmentManager;
    private int mContainerId = -1;

    boolean modeDetach = false; // true：detach()

    /**
     * 子条目View集合
     */
    private Map<Integer, TabInfo> mTabs = new HashMap();

    /**
     * 被选中的item对应的Key
     */
    private Integer selectedKey = -1;

    public MainTabLayout(Context context) {
        this(context, null);
    }

    public MainTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MainTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);

    }

    public OnSelectedListener getOnSelectedListener() {
        return mOnSelectedListener;
    }

    /**
     * 设置选中监听
     *
     * @param mOnSelectedListener
     */
    public void setOnSelectedListener(OnSelectedListener mOnSelectedListener) {
        this.mOnSelectedListener = mOnSelectedListener;
    }

    /**
     * 设置适配器
     *
     * @param adapter
     */
    public void setAdapter(TabAdapter adapter) {
        this.mAdapter = adapter;

        initChild();

        setSelected(0);

    }

    /**
     * 获取指定TabView
     *
     * @param index
     * @return
     */
    public View getTabView(int index) {
        TabInfo tabInfo = mTabs.get(new Integer(index));
        if (tabInfo != null)
            return tabInfo.view;
        return null;
    }

    /**
     * 选择指定的页面
     *
     * @param index
     */
    public void setSelected(int index) {
        if (mAdapter != null) {
            index %= mAdapter.getCount();

            if (mContext != null && mFragmentManager != null && mContainerId != -1) {

                FragmentTransaction ft = mFragmentManager.beginTransaction();

                TabInfo tab = mTabs.get(index);
                if (tab.fragment == null) {
                    tab.fragment = Fragment.instantiate(mContext, tab.clss.getName(), tab.args);
                    ft.add(mContainerId, tab.fragment);
                } else {
                    if (modeDetach)
                        ft.attach(tab.fragment);
                    else
                        ft.show(tab.fragment);
                }

                if (selectedKey != -1) { // 隐藏上一个Fragment
                    TabInfo lastTab = mTabs.get(selectedKey);
                    if (lastTab.fragment != null) {
                        if (modeDetach)
                            ft.detach(lastTab.fragment);
                        else
                            ft.hide(lastTab.fragment);
                    }
                }

                ft.commit();
            }
        }

        // 设置当前item为选中状态
        View selectedView = mTabs.get(index).view;
        if (selectedView != null) {
            selectedView.setSelected(true);
        }

        // 取消上个item的选中状态
        View lastView = mTabs.get(selectedKey) == null ? null : mTabs.get(selectedKey).view;
        if (lastView != null) {
            lastView.setSelected(false);
        }
        selectedKey = index;

        if (mOnSelectedListener != null)
            mOnSelectedListener.onSelected(index);
    }

    /**
     * 初始化绑定对象
     *
     * @param context
     * @param manager
     * @param containerId FrameLayout的资源id
     */
    public void setupBind(Context context, FragmentManager manager, int containerId) {
        mContext = context;
        mFragmentManager = manager;
        mContainerId = containerId;
    }

    /**
     * 初始化Child
     */
    private void initChild() {

        if (mAdapter != null && mAdapter.getCount() > 0) {
            for (int i = 0; i < mAdapter.getCount(); i++) {
                if (!mTabs.containsKey(i)) {
                    TabInfo tab = mAdapter.getTab(i,
                            this);

                    mTabs.put(Integer.valueOf(i), tab);
                    LayoutParams lp = (LayoutParams) tab.view
                            .getLayoutParams();

                    if (lp == null) {
                        lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                    }

                    lp.weight = 1;

                    tab.view.setOnClickListener(this);

                    addView(tab.view, i, lp);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        for (Map.Entry<Integer, TabInfo> entry : mTabs.entrySet()) {

            if (v == entry.getValue().view) {
                if (selectedKey != entry.getKey()) {

                    setSelected(entry.getKey());

                }
            }
        }
    }

    /**
     * MainTabLayout适配器接口
     *
     * @author a_liYa
     * @date 2016-4-23 上午10:58:36
     */
    public interface TabAdapter {

        int getCount();

        TabInfo getTab(int position, ViewGroup parent);

    }

    /**
     * 选中监听接口
     *
     * @author a_liYa
     * @date 2016/4/30 11:15.
     */
    public interface OnSelectedListener {
        /**
         * @param selected
         */
        void onSelected(int selected);
    }

    public static final class TabInfo {
        private final View view;
        private final Class<?> clss;
        private final Bundle args;
        private Fragment fragment;

        public TabInfo(View _view, Class<?> _class, Bundle _args) {
            view = _view;
            clss = _class;
            args = _args;
        }
    }

}