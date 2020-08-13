package top.zcwfeng.webview.webviewprocess.webchromeclient;

import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import top.zcwfeng.webview.WebViewCallBack;

// 修改title ，用到浏览器内核chromeium
public class MyWebViewChromeClient extends WebChromeClient {
    private WebViewCallBack mWebViewCallback;
    final String TAG = MyWebViewChromeClient.class.getName();
    public MyWebViewChromeClient(WebViewCallBack mWebViewCallback) {
        this.mWebViewCallback = mWebViewCallback;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if(mWebViewCallback != null) {
            mWebViewCallback.updateTitle(title);
        } else {
            Log.e(TAG,"WebView onReceivedTitle callback is null");
        }
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        Log.d(TAG,consoleMessage.message());
        return super.onConsoleMessage(consoleMessage);
    }
}
