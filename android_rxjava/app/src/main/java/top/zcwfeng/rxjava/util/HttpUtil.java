package top.zcwfeng.rxjava.util;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public
class HttpUtil {
    private static final String TAG = "HttpUtil";
    private static  String BASE_URL = "https://www.wanandroid.com";


    public static void setBaseUrl(String baseUrl){
        BASE_URL = baseUrl;

    }

    public static Retrofit getOnLindeCookieRetrofit(){
        // 创建OKHttp
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient client = httpBuilder
                .addNetworkInterceptor(new StethoInterceptor())
                .readTimeout(1000, TimeUnit.SECONDS)
                .connectTimeout(1000,TimeUnit.SECONDS)
                .writeTimeout(1000,TimeUnit.SECONDS)
                .build();

        // 响应
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .client(client)// 请求
                .addConverterFactory(GsonConverterFactory.create())//响应
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }



}
