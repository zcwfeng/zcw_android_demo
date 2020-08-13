package top.zcwfeng.webview;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.google.auto.service.AutoService;

import top.zcwfeng.common.autoservice.IWebViewService;
import top.zcwfeng.webview.utils.Constants;

@AutoService(IWebViewService.class)
public class WebViewServiceImpl implements IWebViewService {
    @Override
    public void startWebViewActivity(Context context, String url, String title, boolean isShowActionBar) {
        if(context != null) {
            Intent intent = new Intent(context, WebviewActivity.class);
            intent.putExtra(Constants.URL,url);
            intent.putExtra(Constants.IS_SHOW_ACTION_BAR,isShowActionBar);
            intent.putExtra(Constants.TITLE,title);
            context.startActivity(intent);
        }
    }

    @Override
    public void startDemoHtml(Context context) {
        Intent intent = new Intent(context,WebviewActivity.class);
        intent.putExtra(Constants.URL,Constants.ANDROID_ASSET_URI + "demo.html");
        intent.putExtra(Constants.TITLE,"本地页面测试");
        context.startActivity(intent);
    }

    @Override
    public Fragment getWebViewFragment(String url, boolean canNativeRefresh) {
        return MyWebViewFragment.newInstance(url,canNativeRefresh);
    }


}
