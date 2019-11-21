package com.aliya.base.sample.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.aliya.base.compat.WindowCompat;
import com.aliya.base.sample.R;
import com.aliya.base.sample.base.BaseActivity;
import com.aliya.base.util.Roms;

public class SecondActivity extends BaseActivity {

    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Log.e("TAG", "onCreate: " + Roms.getVersion());

        mWebView = findViewById(R.id.web_view);
        mWebView.loadUrl("https://mp.weixin.qq.com/s?__biz=MzIyMjQ0MTU0NA==&mid=2247494299&idx=3&sn=0dd4932da201cdc88e2c387081bf7db2&chksm=e82fd3bcdf585aaad566bcc4b6283f785ee862ab7e536a1f45f15304a9780de1c1f57c83b56a&mpshare=1&scene=23&srcid=&sharer_sharetime=1573733607034&sharer_shareid=1760a1eeb048331105b4abc5053e15d5#rd");

        View decorView = getWindow().getDecorView();
        decorView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                WindowCompat.openDrawDuringWindowsAnimating(getActivity());
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });
    }
}
