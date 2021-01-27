package top.zcwfeng.use_workmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * （你怎么知道，他被杀掉后，还在后台执行？）写入文件的方式，向同学们证明 Derry说的 所言非虚
 * 后台任务7
 */
public class MainWorker6 extends Worker {

    public final static String TAG = MainWorker6.class.getSimpleName();

    public static final String SP_NAME = "spNAME"; // SP name
    public static final String SP_KEY = "spKEY"; // KEY


    private Context mContext;
    private WorkerParameters workerParams;

    // 有构造函数
    public MainWorker6(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.mContext = context;
        this.workerParams = workerParams;
    }

    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "MainWorker6 doWork: 后台任务执行了 started");

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 获取SP
        SharedPreferences sp = getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);

        // 获取 sp 里面的值
        int spIntValue = sp.getInt(SP_KEY, 0);

        sp.edit().putInt(SP_KEY, ++spIntValue).apply();

        Log.d(TAG, "MainWorker6 doWork: 后台任务执行了 end");

        return new Result.Success(); // 本地执行 doWork 任务时 成功 执行任务完毕
    }


}