package top.zcwfeng.kotlin.project.net

import okhttp3.OkHttp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import top.zcwfeng.kotlin.project.config.Flag
import java.util.concurrent.TimeUnit

class APIClient {
    private object Holder {
        val INSTANCE = APIClient()
    }

    companion object {
        val instance = Holder.INSTANCE
    }

    fun <T> instanceRetrofit(apiInterface: Class<T>): T {
        var okHttpclient = OkHttpClient().newBuilder()
            .readTimeout(10000, TimeUnit.MICROSECONDS)
            .connectTimeout(10000, TimeUnit.MICROSECONDS)
            .writeTimeout(10000, TimeUnit.MICROSECONDS)
            .build()

        var retrofit = Retrofit.Builder()
            .baseUrl(Flag.BASE_URL1)
//            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        return retrofit.create(apiInterface)
    }
}