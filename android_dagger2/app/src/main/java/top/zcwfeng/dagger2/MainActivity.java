package top.zcwfeng.dagger2;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import top.zcwfeng.dagger2.user.DaggerUserComponent;

public class MainActivity extends AppCompatActivity {

//    private UserManager userManager;
//    @Inject
//    ApiService apiService;
//    @Inject
//    UserManager userManager;

//    @Named("dev")
//    @Test
//    @Inject
//    ApiService apiService_dev;

//    @Named("release")
//    @Release
//    @Inject
//    ApiService apiService_release;

    @Inject
    OkHttpClient httpModul1;
    @Inject
    OkHttpClient httpModule2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DaggerUserComponent.builder().userModule(new UserModule(this))
//                .httpModule(new HttpModule()).build().inject(this);
//
//        Log.e(Config.TAG,"apiService_dev " + apiService_dev);
//        Log.e(Config.TAG,"apiService_release  " + apiService_release);


        DaggerUserComponent.create().inject(this);
        Log.e(Config.TAG,"httpModul1 " + httpModul1);
        Log.e(Config.TAG,"httpModule2  " + httpModule2);


// 传统最基本的方式，结构性解决部分耦合
//        userManager = new UserManager(new ApiService(),new UserStore());
//        userManager.register();

// Dagger 节本的方式
//        DaggerUserComponent.create().inject(this);
//        apiService.register();
//
//        DaggerUserComponent.create().inject(this);
//        userManager.register();

//        Dagger传参的方式
//        DaggerUserComponent.builder().userModule(new UserModule(this))
//                .build().inject(this);
//        userManager.register();

//        DaggerUserComponent.builder().userModule(new UserModule(this))
//                .httpModule(new HttpModule())
//                .build().inject(this);
//        userManager.register();



    }
}
