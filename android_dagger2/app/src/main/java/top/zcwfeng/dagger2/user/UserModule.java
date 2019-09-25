package top.zcwfeng.dagger2.user;

import android.content.Context;
import android.util.Log;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import top.zcwfeng.dagger2.ApiService;
import top.zcwfeng.dagger2.Config;
import top.zcwfeng.dagger2.ano.Release;
import top.zcwfeng.dagger2.ano.Test;

//@Module(includes = {HttpModule.class})//子模块(Component 指定也行)
@Module//单独模块(Component 指定也行)
public class UserModule {

    private Context mContext;

    public UserModule(Context context) {
        mContext = context;
    }

    //    @Singleton
    //    @Provides
    //    public OkHttpClient provideOkhttp(){
    //        return new OkHttpClient().newBuilder().build();
    //    }


    // 第一种方法
    @Provides
    public ApiService getApiService(OkHttpClient okHttpClient) {
        Log.e(Config.TAG, "ApiService----UserModule");

        return new ApiService(okHttpClient);
    }

    //    @Provides
    //    public String url(){
    //        return "https://zcwfeng.top";
    //    }

    //    @Named("dev")
    @Test
    @Provides
    public ApiService getApiServiceForDev(OkHttpClient okHttpClient) {
        ApiService apiService = new ApiService(okHttpClient);
        Log.e(Config.TAG, "UserModule----getApiServiceForDev" + apiService);

        return apiService;
    }

    //    @Named("release")
    @Release
    @Provides
    public ApiService getApiServiceForFelease(OkHttpClient okHttpClient) {
        ApiService apiService = new ApiService(okHttpClient);
        Log.e(Config.TAG, "UserModule----getApiServiceForFelease" + apiService);

        return apiService;
    }


    @Provides
    public UserStore provideUserStore() {
        Log.e(Config.TAG, "ApiService----provideUserStore");

        return new UserStore(this.mContext);
    }

    @Provides
    public UserManager provideUserManager(ApiService apiService, UserStore userStore) {
        Log.e(Config.TAG, "ApiService----provideUserManager");
        return new UserManager(apiService, userStore);

    }
}