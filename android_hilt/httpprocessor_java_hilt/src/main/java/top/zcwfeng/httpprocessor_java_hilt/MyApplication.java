package top.zcwfeng.httpprocessor_java_hilt;

import android.app.Application;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;
import top.zcwfeng.httpprocessor_java_hilt.annotation.BindOkHttp;

@HiltAndroidApp
public class MyApplication extends Application {

    @BindOkHttp
    @Inject
    IHttpProcessor iHttpProcessor;

    public IHttpProcessor getiHttpProcessor(){
        return iHttpProcessor;
    }

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        HttpHelper.init(new VolleyProcessor(this));
//        HttpHelper.init(new XUtilsProcessor(this));
//        HttpHelper.init(new OkHttpProcessor());
//    }


}
