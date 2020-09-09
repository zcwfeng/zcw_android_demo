package top.zcwfeng.base;

import android.app.Application;

public class BaseApplication extends Application{
    // OOM won't happen.
    public static Application sApplication;

    public void setsDebug(boolean isDebug) {
        sDebug = isDebug;
    }

    public static boolean sDebug;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}
