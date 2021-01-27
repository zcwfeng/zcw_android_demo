package top.zcwfeng.use_workmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

import top.zcwfeng.common.constants.Config;

// 场景：非及时任务 （例如：每天同步数据，每天上传一次日志）
// Room数据库管理（持久性）
public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    Button mbtn6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbtn6 = findViewById(R.id.btn6);


        // 绑定监听
        SharedPreferences sp = getSharedPreferences(MainWorker6.SP_NAME, Context.MODE_PRIVATE);
        sp.registerOnSharedPreferenceChangeListener(this); // 给SP绑定监听
        updateToUI(); // 第一次初始化一把
    }

    /**
     * 测试任务一，单个任务执行
     *
     * @param view
     */
    public void testBackgroundWork1(View view) {
        // 单一的任务 一次
        OneTimeWorkRequest oneTimeWorkRequest1;
        // 模拟数据
        Data sendData = new Data.Builder().putString("David", "九阳神功").build();

        //请求对象初始化
        oneTimeWorkRequest1 = new OneTimeWorkRequest
                .Builder(WorkManager1.class)
                .setInputData(sendData)
                .build();
        // 获取LiveData by UUId 建立Observe
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeWorkRequest1.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                Log.d(Config.TAG_WORKMANAGER, "Activity取到了任务回传的数据: "
                        + workInfo.getOutputData().getString("David"));
                Log.d(Config.TAG_WORKMANAGER, "状态：" + workInfo.getState().name());
                if (workInfo.getState().isFinished()) {
                    Log.d(Config.TAG_WORKMANAGER, "状态：isFinished=true 后台任务已经完成了...");
                }

            }
        });

        //请求对象加入队列
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest1);


    }

    public void testBackgroundWork2(View view) {

        // 单一的任务  一次
        OneTimeWorkRequest oneTimeWorkRequest2 = new OneTimeWorkRequest.Builder(WorkManager2.class).build();
        OneTimeWorkRequest oneTimeWorkRequest3 = new OneTimeWorkRequest.Builder(WorkManager3.class).build();
        OneTimeWorkRequest oneTimeWorkRequest4 = new OneTimeWorkRequest.Builder(WorkManager4.class).build();
        OneTimeWorkRequest oneTimeWorkRequest5 = new OneTimeWorkRequest.Builder(WorkManager5.class).build();

        // 顺序执行
        WorkManager.getInstance(this).beginWith(oneTimeWorkRequest2)
                .then(oneTimeWorkRequest3)
                .then(oneTimeWorkRequest4)
                .then(oneTimeWorkRequest5)
                .enqueue();


        // 把Request 存入集合 TODO 并发执行
//        List<OneTimeWorkRequest> oneTimeWorkRequests = new ArrayList<>();
//        oneTimeWorkRequests.add(oneTimeWorkRequest2); // 测试：没有并行
//        oneTimeWorkRequests.add(oneTimeWorkRequest3); // 测试：没有并行
//        WorkManager.getInstance(this).beginWith(oneTimeWorkRequests)
//                .then(oneTimeWorkRequest4)
//                .then(oneTimeWorkRequest5)
//                .enqueue();

    }

    public void testBackgroundWork3(View view) {
        // 重复的任务  多次  循环  , 最少循环重复 15分钟
        PeriodicWorkRequest periodicWorkRequest =
                new PeriodicWorkRequest.Builder(WorkManager2.class, 10, TimeUnit.SECONDS).build();

        // 监听状态
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(periodicWorkRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        Log.d(Config.TAG_WORKMANAGER, "状态：" + workInfo.getState().name());
                        if (workInfo.getState().isFinished()) {
                            Log.d(Config.TAG_WORKMANAGER,  "状态：isFinished=true 同学们注意：后台任务已经完成了...");
                        }
                    }
                });

        WorkManager.getInstance(this).enqueue(periodicWorkRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void testBackgroundWork4(View view) {
        // 约束条件，必须满足我的条件，才能执行后台任务 （在连接网络，插入电源 并且 处于空闲时）  内部做了电量优化（Android App 不耗电）
        Constraints myConstraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED) // 网络链接中...
                .setRequiresCharging(true) // 充电中..
                .setRequiresDeviceIdle(true) // 空闲时.. (没有玩游戏)
                .build();

        /**
         * 除了上面设置的约束外，WorkManger还提供了以下的约束作为Work执行的条件：
         *  setRequiredNetworkType：网络连接设置
         *  setRequiresBatteryNotLow：是否为低电量时运行 默认false
         *  setRequiresCharging：是否要插入设备（接入电源），默认false
         *  setRequiresDeviceIdle：设备是否为空闲，默认false
         *  setRequiresStorageNotLow：设备可用存储是否不低于临界阈值
         */

        // 请求对象
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(WorkManager2.class)
                .setConstraints(myConstraints)
                .build();

        // 测试：监听状态
        // 监听状态
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        Log.d(Config.TAG_WORKMANAGER,  "状态：" + workInfo.getState().name());
                        if (workInfo.getState().isFinished()) {
                            Log.d(Config.TAG_WORKMANAGER,  "状态：isFinished=true 同学们注意：后台任务已经完成了...");
                        }
                    }
                });

        // 加入队列
        WorkManager.getInstance(this).enqueue(request);
    }

    public void testBackgroundWork5(View view) {
    }

    /**
     * （你怎么知道，他被杀掉后，还在后台执行？）写入文件的方式（SP）
     * @param view
     */
    public void testBackgroundWork6(View view) {
        // 约束条件
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED) // 约束条件，必须是网络连接
                .build();

        // 构建Request
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MainWorker6.class)
                .setConstraints(constraints)
                .build();

        // 加入队列
        WorkManager.getInstance(this).enqueue(request);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void testBackgroundWork7(View view) {
        // 约束条件，必须满足我的条件，才能执行后台任务 （在连接网络，插入电源 并且 处于空闲时）  内部做了电量优化（Android App 不耗电）
        Constraints myConstraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED) // 网络链接中...
                .setRequiresCharging(true) // 充电中..
                .setRequiresDeviceIdle(true) // 空闲时.. (没有玩游戏)
                .build();

        // 请求对象
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(WorkManager2.class)
                .setConstraints(myConstraints) // TODO 3 约束条件的执行
                .build();


        WorkManager.getInstance(this) // TODO 1 初始化工作源码


                .enqueue(request); // TODO 2 加入队列执行
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updateToUI();
    }

    // 从SP里面获取值，显示到界面给用户看就行了
    private final void updateToUI() {
        SharedPreferences sp = getSharedPreferences(MainWorker6.SP_NAME, Context.MODE_PRIVATE);
        int resultValue = sp.getInt(MainWorker6.SP_KEY, 0);
        mbtn6.setText("测试后台任务666 -- " + resultValue);

    }

    // SP归零
    public void spReset(View view) {
        SharedPreferences sp = getSharedPreferences(MainWorker6.SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(MainWorker6.SP_KEY, 0).apply();
        updateToUI();
    }
}
