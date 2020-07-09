package top.zcwfeng.skin;

import android.app.Application;

import top.zcwfeng.lib.SkinManager;

public
class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.init(this);
    }
}
