package top.zcwfeng.webview.webviewprocess;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Map;

import top.zcwfeng.webview.WebViewCallBack;
import top.zcwfeng.webview.bean.JsParam;
import top.zcwfeng.webview.webviewprocess.setttings.WebViewDefaultSettings;
import top.zcwfeng.webview.webviewprocess.webchromeclient.MyWebViewChromeClient;
import top.zcwfeng.webview.webviewprocess.webviewclient.MyWebViewClient;

public class BaseWebView extends WebView {
    final private String TAG = BaseWebView.class.getName();

    public BaseWebView(Context context) {
        super(context);
        init();
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        WebViewDefaultSettings.getInstance().setSettings(this);
        addJavascriptInterface(this, "customwebview");
    }

    public void registerWebViewCallback(WebViewCallBack webViewCallBack){
        setWebViewClient(new MyWebViewClient(webViewCallBack));
        setWebChromeClient(new MyWebViewChromeClient(webViewCallBack));
    }

    @JavascriptInterface
    public void takeNativeAction(final String jsParam) {
        Log.i(TAG, jsParam);
        if (!TextUtils.isEmpty(jsParam)) {
            final JsParam jsParamObject =
                    new Gson().fromJson(jsParam, JsParam.class);
            if (jsParamObject != null) {
                if ("showToast".equalsIgnoreCase(jsParamObject.name)) {
                    Toast.makeText(getContext(), String.valueOf(
                            new Gson().fromJson(jsParamObject.param, Map.class)
                    ), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
