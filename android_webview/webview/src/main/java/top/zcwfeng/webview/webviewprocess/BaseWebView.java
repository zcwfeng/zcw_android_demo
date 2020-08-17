package top.zcwfeng.webview.webviewprocess;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.google.gson.Gson;

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
        // 千万注意这里别忘记
        WebViewProcessCommandDispatcher.getInstance().initAidlConnect();
        WebViewDefaultSettings.getInstance().setSettings(this);
        addJavascriptInterface(this, "customwebview");
    }

    public void registerWebViewCallback(WebViewCallBack webViewCallBack) {
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
                WebViewProcessCommandDispatcher.getInstance().executeCommand(jsParamObject.name
                        , new Gson().toJson(jsParamObject.param),this);

            }
        }
    }

    public void handleCallBack(final String callbackName,final String response){
        if(!TextUtils.isEmpty(callbackName) && !TextUtils.isEmpty(response)){
            post(new Runnable() {
                @Override
                public void run() {
                    String jscode = "Javascript:customjs.callback('" + callbackName + "'," + response + ")";
                    Log.e(TAG,"Android->JS handleCallBack:" + jscode);
                    evaluateJavascript(jscode,null);
                }
            });
        }
    }
}
