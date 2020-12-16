package top.zcwfeng.arch_demo.application;


import android.app.Application;

import androidx.databinding.library.baseAdapters.BuildConfig;

import com.xiangxue.network.base.INetworkRequiredInfo;

/**
 
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class NetworkRequestInfo implements INetworkRequiredInfo {
    private final Application mApplication;
    public NetworkRequestInfo(Application application){
        this.mApplication = application;
    }

    @Override
    public String getAppVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    @Override
    public String getAppVersionCode() {
        return String.valueOf(BuildConfig.VERSION_CODE);
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    @Override
    public Application getApplicationContext() {
        return mApplication;
    }

}
