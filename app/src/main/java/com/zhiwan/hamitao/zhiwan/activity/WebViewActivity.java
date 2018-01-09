package com.zhiwan.hamitao.zhiwan.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhiwan.hamitao.zhiwan.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends AppCompatActivity {

    // 扫描二维码后跳转到该页面获得的结果
    private String result;


    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView tvTitle;
    @BindView(R.id.more)
    TextView more;


    @BindView(R.id.webView)
    WebView mWebview;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        result = getIntent().getStringExtra("result");
    }

    private void initView() {
        tvTitle.setVisibility(View.VISIBLE);
        initWebView();
    }

    private void initWebView() {


        mWebview.getSettings().setJavaScriptEnabled(true);  //设置WebView属性,运行执行js脚本

        mWebview.setDownloadListener(new MyWebViewDownloadListener());

        mWebview.loadUrl(result);

        /**
         * 不调用系统的浏览器
         */
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mWebview.setWebChromeClient(new WebChromeClient() {

            //网页标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }


            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressbar.setProgress(newProgress);
            }
        });


        mWebview.setWebViewClient(new WebViewClient() {
            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressbar.setVisibility(View.VISIBLE);  //开始加载
            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
                progressbar.setVisibility(View.GONE);      //加载完成
            }
        });


    }


    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mWebview != null) {
            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();

            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
    }

    @OnClick({R.id.back, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                break;
        }
    }


    private class MyWebViewDownloadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(it);
        }
    }
}
