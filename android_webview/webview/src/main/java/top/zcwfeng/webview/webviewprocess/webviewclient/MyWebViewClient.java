package top.zcwfeng.webview.webviewprocess.webviewclient;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import top.zcwfeng.webview.WebViewCallBack;

public class MyWebViewClient extends WebViewClient {
    final String TAG = MyWebViewClient.class.getName();
    private WebViewCallBack mWebViewCallBack;
    public MyWebViewClient(WebViewCallBack webViewCallBack) {
        mWebViewCallBack = webViewCallBack;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if(mWebViewCallBack != null) {
            mWebViewCallBack.pageStarted(url);
        } else {
            Log.e(TAG,"WebView onPageStarted callback is null");
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if(mWebViewCallBack != null) {
            mWebViewCallBack.pageFinished(url);
        } else {
            Log.e(TAG,"WebView onPageFinished callback is null");
        }
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if(mWebViewCallBack != null) {
            mWebViewCallBack.onError();
        } else {
            Log.e(TAG,"WebView callback is null");
        }
    }


}
