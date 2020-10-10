package top.zcwfeng.retrofitdemo.retrofit

import androidx.lifecycle.LiveData
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import top.zcwfeng.retrofitdemo.BuildConfig
import top.zcwfeng.retrofitdemo.retrofit.calladapter.LiveDataCallAdapterFactory
import top.zcwfeng.retrofitdemo.retrofit.converter.MyGsonConvertFactory

class RetrofitClient private constructor(){
    lateinit var retrofit: Retrofit

    companion object{
        const val TAG = "zcwfeng"
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            RetrofitClient()
        }
    }

    init {
        createRetrofit()
    }

    private fun createRetrofit(){
        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(MyGsonConvertFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    inline fun <reified T> getService(service:Class<T>):T = retrofit.create(service)
}