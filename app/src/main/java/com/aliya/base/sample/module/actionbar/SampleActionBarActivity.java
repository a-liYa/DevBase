package com.aliya.base.sample.module.actionbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.aliya.base.AppUtils;
import com.aliya.base.sample.R;
import com.aliya.base.sample.base.ActionBarActivity;
import com.aliya.base.sample.databinding.ActivitySampleActionBarBinding;
import com.aliya.base.widget.T;

public class SampleActionBarActivity extends ActionBarActivity implements View.OnClickListener {

    private ActivitySampleActionBarBinding mBinding;

    private ImageButtonAction mHelpAction;
    private ImageButtonAction mShareAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySampleActionBarBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.tvAddHelp.setOnClickListener(this);
        mBinding.tvAddShare.setOnClickListener(this);
        mBinding.tvRemoveHelp.setOnClickListener(this);
        mBinding.tvRemoveShare.setOnClickListener(this);

        mHelpAction = new ImageButtonAction(R.mipmap.abc_action_bar_help_icon,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T.showShort(getActivity(), "onClick: Help!");
                    }
                });
        mShareAction = new ImageButtonAction(R.mipmap.abc_action_bar_share_forward_icon,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T.showShort(getActivity(), "onClick: Share!");
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_share:
                mActionBar.addRightAction(mShareAction,0);
                mShareAction.getView().setBackgroundColor(Color.parseColor("#f5f5f5"));
                break;
            case R.id.tv_remove_share:
                mActionBar.removeRightAction(mShareAction);
                break;
            case R.id.tv_add_help:
                mActionBar.addRightAction(mHelpAction, -1);
                mHelpAction.getView().getLayoutParams().width = AppUtils.dp2px(35);
                mHelpAction.getView().setBackgroundColor(Color.parseColor("#e5e5e5"));
                break;
            case R.id.tv_remove_help:
                mActionBar.removeRightAction(mHelpAction);
                break;
        }
    }
}