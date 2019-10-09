package com.aliya.base.sample.base;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliya.base.sample.R;
import com.aliya.base.widget.RebornActionBar;

/**
 * ActionBar 简单实现：左返回 + 中间标题
 *
 * @author a_liYa
 * @date 2019-10-08 19:13.
 */
public class AppActionBar extends RebornActionBar {

    ImageView ivBack;
    TextView tvTitle;

    public AppActionBar(Activity activity) {
        this(activity, activity.getTitle());
    }

    public AppActionBar(Activity activity, CharSequence title) {
        super(activity);
        setTitle(title);
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.abc_action_mode_bar_layout, parent, false);
    }

    @Override
    protected void onViewCreated(View view) {
        ivBack = view.findViewById(R.id.abc_iv_back);
        tvTitle = view.findViewById(R.id.abc_tv_title);

        if (ivBack != null) {
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.onBackPressed();
                }
            });
        }
    }

    public void setTitle(CharSequence title) {
        if (tvTitle != null)
            tvTitle.setText(title);
    }
}
