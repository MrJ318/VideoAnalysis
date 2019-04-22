package com.jevon.videoanalysis;

import android.databinding.DataBindingUtil;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jevon.videoanalysis.databinding.ActivityMainBinding;
import com.jevon.videoanalysis.utils.WebProgressBar;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private WebView webView;
    private RelativeLayout layout;
    private RelativeLayout progresslayout;
    private WebProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setVm(this);

        //网页中的视频，上屏幕的时候，可能出现闪烁的情况，需要如下设置
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        initWebView();
        webView.loadUrl("file:///android_asset/Nav/index.html");
    }

    private void initWebView() {

        // 设置toolbar
        setSupportActionBar(binding.toolbarMain);

        //初始化webview
        layout = binding.layoutRoot;
        webView = new WebView(this, null);
        layout.addView(webView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // 加载进度条
        progresslayout = binding.progresslayout;
        progressBar = new WebProgressBar(this);
        progresslayout.addView(progressBar, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        //websetting
        WebSettings webSetting = webView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setDomStorageEnabled(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();

        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
    }

    public void viewOnClick(View v) {

        switch (v.getId()) {
//            case R.id.text_serach:
//                Toast.makeText(AppApplication.context, "搜索", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.btnBack:
                if (webView.canGoBack()) {
                    webView.goBack();
                }
                break;
            case R.id.btnForward:
                if (webView.canGoForward()) {
                    webView.goForward();
                }
                break;
            case R.id.btnRefresh:
                webView.reload();
                break;
            case R.id.btnHome:
                webView.loadUrl("file:///android_asset/Nav/index.html");
                break;
            case R.id.btnExit:
                this.finish();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_address, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ad1:
                webView.loadUrl("https://jx.618g.com/?url=" + webView.getUrl());
                break;

            case R.id.ad2:
                webView.loadUrl("https://660e.com/?url=" + webView.getUrl());
                break;

            case R.id.ad3:
                webView.loadUrl("http://jx.618ge.com/?url=" + webView.getUrl());
                break;
            case R.id.ad4:
                webView.loadUrl("http://www.fantee.net/fantee/?url=" + webView.getUrl());
                break;
            case R.id.ad5:
                webView.loadUrl("http://jx.aeidu.cn/index.php?url=" + webView.getUrl());
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView != null && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null)
            webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null)
            webView.onPause();
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    /*************************************************************************************************/

    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String s) {
            if (s.startsWith("http:") || s.startsWith("https:")) {
                return false;
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
        }
    }

    class MyWebChromeClient extends WebChromeClient {

        View myVideoView;
        IX5WebChromeClient.CustomViewCallback callback;

        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            if (i >= 100) {
                progressBar.setProgress(0);
            } else {
                progressBar.setProgress(i);
            }
        }

        @Override
        public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
            layout.addView(view);
            myVideoView = view;
            callback = customViewCallback;
        }

        @Override
        public void onHideCustomView() {
            if (callback != null) {
                callback.onCustomViewHidden();
                callback = null;
            }
            if (myVideoView != null) {
                ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                viewGroup.removeView(myVideoView);
            }
        }
    }
}

