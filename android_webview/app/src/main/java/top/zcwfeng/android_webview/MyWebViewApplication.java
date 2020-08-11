package top.zcwfeng.android_webview;

import com.kingja.loadsir.core.LoadSir;

import top.zcwfeng.base.BaseApplication;
import top.zcwfeng.base.loadsir.CustomCallback;
import top.zcwfeng.base.loadsir.EmptyCallback;
import top.zcwfeng.base.loadsir.ErrorCallback;
import top.zcwfeng.base.loadsir.LoadingCallback;
import top.zcwfeng.base.loadsir.TimeoutCallback;

public class MyWebViewApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .commit();
    }
}
