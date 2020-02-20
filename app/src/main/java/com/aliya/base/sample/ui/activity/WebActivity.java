package com.aliya.base.sample.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.aliya.base.compat.WindowCompat;
import com.aliya.base.gather.WebPools;
import com.aliya.base.sample.R;
import com.aliya.base.sample.base.ActionBarActivity;

/**
 * 承载网页的 Activity
 *
 * @author a_liYa
 * @date 2019-12-26 16:54.
 */
public class WebActivity extends ActionBarActivity {

    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mWebView = WebPools.get().acquireWebView(this);
        WebPools.replaceWithWebView(mWebView, findViewById(R.id.view_stub));

        WindowCompat.openDrawDuringWindowsAnimating(getActivity());

        mWebView.loadUrl("https://github.com/");
    }
}
