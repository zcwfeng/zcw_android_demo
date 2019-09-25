package top.zcwfeng.dagger2;

import android.util.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class HttpModule {


    @Singleton
    @Provides
    public OkHttpClient mOkHttpClient(){
        Log.e(Config.TAG,"HttpModule.......provider");
        return new OkHttpClient().newBuilder().build();
    }
}
