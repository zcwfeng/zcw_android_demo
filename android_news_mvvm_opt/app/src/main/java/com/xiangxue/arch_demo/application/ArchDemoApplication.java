package com.xiangxue.arch_demo.application;

import com.xiangxue.network.base.NetworkApi;

import top.zcwfeng.base.customview.BaseApplication;
import top.zcwfeng.base.customview.preference.PreferencesUtil;

/**
 
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class ArchDemoApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkApi.init(new NetworkRequestInfo(this));
        PreferencesUtil.init(this);

//        LoadSir.beginBuilder()
//                .addCallback(new ErrorCallback())//添加各种状态页
//                .addCallback(new EmptyCallback())
//                .addCallback(new LoadingCallback())
//                .addCallback(new TimeoutCallback())
//                .addCallback(new CustomCallback())
//                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
//                .commit();
    }
}
