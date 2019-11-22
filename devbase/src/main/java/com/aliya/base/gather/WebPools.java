package com.aliya.base.gather;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.webkit.WebView;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * WebView 复用池
 *
 * @author a_liYa
 * @date 2019-11-22 09:18.
 */
public final class WebPools {

    private final Queue<WebView> mWebQueue;

    private static WebPools sInstance;

    private WebPools() {
        mWebQueue = new LinkedBlockingQueue<>();
    }

    public static WebPools getInstance() {
        if (sInstance == null) {
            synchronized (WebPools.class) {
                if (sInstance == null) {
                    sInstance = new WebPools();
                }
            }
        }
        return sInstance;
    }

    public void recycle(WebView webView) {
        if (webView == null) return;

        if (webView.getContext() instanceof MutableContextWrapper) {
            MutableContextWrapper context = (MutableContextWrapper) webView.getContext();
            context.setBaseContext(context.getApplicationContext());
            mWebQueue.offer(webView);
        }
    }

    public WebView acquireWebView(Context context) {
        WebView webView = mWebQueue.poll();
        if (webView == null) {
            synchronized (mWebQueue) {
                return new WebView(new MutableContextWrapper(context));
            }
        } else {
            MutableContextWrapper mMutableContextWrapper =
                    (MutableContextWrapper) webView.getContext();
            mMutableContextWrapper.setBaseContext(context);
            return webView;
        }
    }
}
