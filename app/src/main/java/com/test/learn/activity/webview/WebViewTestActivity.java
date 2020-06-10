package com.test.learn.activity.webview;


import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.test.learn.R;
import com.test.learn.base.BaseActivity;

public class WebViewTestActivity extends BaseActivity {
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_test);
        webView = (WebView) findViewById(R.id.web_view);
        //设置支持javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url); // 根据传入的参数再去加载新的网页
                return true; // 表示当前WebView可以处理打开新网页的请求，不用借助系统浏览器
            }
        });
        webView.loadUrl("https://www.baidu.com");

    }
}
