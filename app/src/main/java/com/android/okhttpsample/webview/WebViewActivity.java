package com.android.okhttpsample.webview;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.android.okhttpsample.R;
import com.android.okhttpsample.permission.PermissionsManager;

/**
 * 浏览器测试
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 18/3/12 上午11:24
 */
public class WebViewActivity extends AppCompatActivity {
    private static String umengMac;
    TextView textView;
    private static final String TAG = "WebViewActivity";
    private PermissionsManager permissionsManager;
    private Context context;
    private boolean success;
    private java.lang.String url = "http://www.baidu.com";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        WebView webView = (WebView) findViewById(R.id.webview);

        webView.loadUrl(url);
        webView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

            }
        });

        webView.setFindListener(new WebView.FindListener() {
            @Override
            public void onFindResultReceived(int activeMatchOrdinal, int numberOfMatches, boolean isDoneCounting) {

            }
        });

//        webView.set

    }


}
