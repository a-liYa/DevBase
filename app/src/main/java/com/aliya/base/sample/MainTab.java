package com.aliya.base.sample;

import com.aliya.base.sample.ui.main.FindFragment;
import com.aliya.base.sample.ui.main.HomeFragment;
import com.aliya.base.sample.ui.main.VideoFragment;

/**
 * 主界面 tab 对应的 Fragment 枚举
 *
 * @author a_liYa
 * @date 2018/9/29 14:13.
 */
public enum MainTab {

    HOMEPAGE(0, "首页", R.drawable.main_tab_home, HomeFragment.class),

    SHARE(1, "视频", R.drawable.main_tab_video, VideoFragment.class),

    ISSUE(2, "发现", R.drawable.main_tab_find, FindFragment.class);

    private int idx;
    private String title;
    private int resIcon;
    private Class<?> clz;

    MainTab(int idx, String title, int resIcon, Class<?> clz) {
        this.idx = idx;
        this.title = title;
        this.resIcon = resIcon;
        this.clz = clz;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}