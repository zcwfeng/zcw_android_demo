package top.zcwfeng.arch_demo.application;

import com.xiangxue.network.base.NetworkApi;

import top.zcwfeng.base.customview.BaseApplication;
import top.zcwfeng.base.customview.preference.PreferencesUtil;

/**
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class ArchDemoApplication extends BaseApplication {
    public ArchDemoApplication() {
//        Debug.startMethodTracing("zcwfeng");
//        Debug.startMethodTracingSampling(new File(Environment.getExternalStorageDirectory(),
//                        "zcwfeng").getAbsolutePath(), 8 * 1024 * 1024, 1_000);
//        Debug.startMethodTracing(new File(Environment.getExternalStorageDirectory(),
//                "zcwfeng").getAbsolutePath());
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        if (BuildConfig.DEBUG) {
//            //线程检测策略
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                    .detectDiskReads() //读、写操作
//                    .detectDiskWrites()
//                    .detectNetwork() // or .detectAll() for all detectable problems .penaltyLog()
//                    .build());
//            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                    .detectLeakedSqlLiteObjects()// sqlite 对象泄露
//
//                    .detectLeakedClosableObjects() //未关闭的Closable对象泄露 .penaltyLog() //违规打印日志
//                    .penaltyDeath() //违规崩溃
//                    .build());
//        }

        NetworkApi.init(new NetworkRequestInfo(this));
        PreferencesUtil.init(this);


//        LoadSir.beginBuilder()
//                .addCallback(new ErrorCallback())//添加各种状态页
//                .addCallback(new EmptyCallback())
//                .addCallback(new LoadingCallback())
//                .addCallback(new TimeoutCallback())
//                .addCallback(new CustomCallback())
//                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
//                .commit();
    }
}
