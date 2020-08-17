package top.zcwfeng.base;

import android.app.Application;

public class BaseApplication extends Application{
    public static Application sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}
