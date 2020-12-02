package top.zcwfeng.zcwplugin;

import android.app.Application;

public
class MyApplication extends Application {

//    private Resources mResources;

    @Override
    public void onCreate() {
        super.onCreate();

        // TODO: 2020/12/2 hot fix test
        // True test hotfix true plugin
        if (false) {
            LoadUtil.loadFix(this);

        } else {

            plugin();
        }

    }


    public void plugin() {
        LoadUtil.load(this);


//        mResources = LoadUtil.loadResource(this);


        HookUtils.hookAMS();
        HookUtils.hookHandler();

    }

    // TODO: 2020/12/1 尝试插件资源的加载 方案一
//    @Override
//    public Resources getResources() {
//        return mResources == null ? super.getResources() : mResources;
//    }
}
