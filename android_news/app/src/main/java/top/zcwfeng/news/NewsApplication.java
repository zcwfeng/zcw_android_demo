package top.zcwfeng.news;

import com.billy.cc.core.component.CC;
import com.kingja.loadsir.core.LoadSir;

import top.zcwfeng.base.BaseApplication;
import top.zcwfeng.base.loadsir.CustomCallback;
import top.zcwfeng.base.loadsir.EmptyCallback;
import top.zcwfeng.base.loadsir.ErrorCallback;
import top.zcwfeng.base.loadsir.LoadingCallback;
import top.zcwfeng.base.loadsir.TimeoutCallback;
import top.zcwfeng.base.preference.PreferencesUtil;
import top.zcwfeng.network.ApiBase;

public class NewsApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferencesUtil.init(this);
        ApiBase.setNetworkRequestInfo(new NetworkRequestInfo());

        setsDebug(BuildConfig.DEBUG);


        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .commit();
        CC.enableDebug(true); // 默认是false: 关闭状态
        CC.enableVerboseLog(true);    // 默认是false: 关闭状态
        CC.enableRemoteCC(true); // 默认是false: 关闭状态
    }
}
