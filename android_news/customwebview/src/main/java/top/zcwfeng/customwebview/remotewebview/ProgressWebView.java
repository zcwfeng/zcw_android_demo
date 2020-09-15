package top.zcwfeng.customwebview.remotewebview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.ViewGroup;

import top.zcwfeng.customwebview.remotewebview.progressbar.IndicatorHandler;
import top.zcwfeng.customwebview.remotewebview.progressbar.WebProgressBar;
import top.zcwfeng.customwebview.remotewebview.webchromeclient.ProgressWebChromeClient;


public class ProgressWebView extends BaseWebView {

    public ProgressWebView(Context context) {
        super(context);
        init();
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private IndicatorHandler indicatorHandler;
    private WebProgressBar progressBar;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int progress = (int) msg.obj;
            indicatorHandler.progress(progress);
        }
    };

    @Override
    public Handler getHandler() {
        return handler;
    }

    private void init() {
        progressBar = new WebProgressBar(context);
        progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        progressBar.setVisibility(GONE);
        addView(progressBar);
        indicatorHandler = IndicatorHandler.getInstance().inJectProgressView(progressBar);
        setWebChromeClient(new ProgressWebChromeClient(handler));
    }
}
