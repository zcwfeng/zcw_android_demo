package top.zcwfeng.dagger2;

import android.util.Log;

import okhttp3.OkHttpClient;

public class ApiService {

//    2 种方法
//    @Inject
//    public ApiService(){
//        Log.e(Config.TAG,"ApiService----constructor");
//
//    }

//    @Inject
//    public ApiService(String url){
//        Log.e(Config.TAG,"ApiService----" + url);
//
//    }


    private OkHttpClient mOkHttpClient;


    public ApiService(OkHttpClient okHttpClient){
        this.mOkHttpClient = okHttpClient;
    }





    public void register() {
        Log.e(Config.TAG,"ApiService----register");
    }
}
