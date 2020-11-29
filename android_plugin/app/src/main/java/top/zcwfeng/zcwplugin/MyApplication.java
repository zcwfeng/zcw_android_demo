package top.zcwfeng.zcwplugin;

import android.app.Application;

public
class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LoadUtil.load(this);
        HookUtils.hookAMS();
        HookUtils.hookHandler();
    }
}
