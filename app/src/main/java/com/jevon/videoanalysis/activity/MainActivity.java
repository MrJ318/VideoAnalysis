package com.jevon.videoanalysis.activity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.jevon.videoanalysis.R;
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
    private WebProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setVm(this);

        //网页中的视频，上屏幕的时候，可能出现闪烁的情况，需要如下设置
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        // 状态栏文本改为深色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        initWebView();
        webView.loadUrl("file:///android_asset/Nav/index.html");
//        webView.loadUrl("https://jx.618g.com/?url=https://m.youku.com/video/id_XNDEyOTMxNjM0NA==.html");

    }

    private void initWebView() {

        // 设置toolbar
        setSupportActionBar(binding.toolbarMain);

        //初始化webview
        RelativeLayout layout = binding.layoutWebView;
        webView = new WebView(this, null);
        layout.addView(webView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // 加载进度条
        RelativeLayout progresslayout = binding.layoutProgress;
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
            case R.id.btnMenu:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.btnHome:
                webView.loadUrl("file:///android_asset/Nav/index.html");
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
            return !s.startsWith("http:") && !s.startsWith("https:");
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
            ViewGroup group = (ViewGroup) binding.layoutWebView.getParent();
            group.addView(view);
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

