package com.aliya.base.sample.module.listen;

import android.os.Bundle;
import android.view.View;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.ActionBarActivity;

/**
 * 听新闻 - 示例页
 *
 * @author a_liYa
 * @date 2020-02-28 15:38.
 */
public class ListenNewsActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_news);
        findViewById(R.id.tv_open).setOnClickListener(this);
        findViewById(R.id.tv_open_enable).setOnClickListener(this);
        findViewById(R.id.tv_close).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_open:
                SideFloatHelper.getSideFloat().attachToActivity(this);
                break;
            case R.id.tv_open_enable:
                SideFloatHelper.getSideFloat().setShouldShow(true);
                break;
            case R.id.tv_close:
                SideFloatHelper.getSideFloat().removeSideFloat();
                break;
        }
    }
}
