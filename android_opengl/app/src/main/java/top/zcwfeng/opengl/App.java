package top.zcwfeng.opengl;

import android.app.Application;

public class App extends Application {

    public static App getmInstance() {
        return mInstance;
    }

    public static App mInstance;




    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
