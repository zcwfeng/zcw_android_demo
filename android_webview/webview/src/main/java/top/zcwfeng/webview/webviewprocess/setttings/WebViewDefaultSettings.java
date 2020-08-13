package top.zcwfeng.webview.webviewprocess.setttings;

import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewDefaultSettings {
    private WebSettings mWebSettings;

//    public WebViewDefaultSettings(WebSettings mWebSettings) {
//        this.mWebSettings = mWebSettings;
//    }


    private WebViewDefaultSettings() {
    }

    public static WebViewDefaultSettings getInstance(){
        return new WebViewDefaultSettings();
    }

    // TODO: 2020/8/13 settings
    public void setSettings(WebView webView){
        webView.getSettings().setJavaScriptEnabled(true);

    }
}
