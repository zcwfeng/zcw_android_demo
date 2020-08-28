package top.zcwfeng.use_workmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import top.zcwfeng.common.constants.Config;

public
class WorkManager1 extends Worker {
    private Context context;
    private WorkerParameters workerParams;

    public WorkManager1(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        this.workerParams = workerParams;
    }
    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    public Result doWork() {
        Log.d(Config.TAG_WORKMANAGER, "doWork 后台任务执行1");
        // 接受Activity 穿过来的数据
        String dataString = workerParams.getInputData().getString("David");
        Log.d(Config.TAG_WORKMANAGER, "接受Activity传过来数据" + dataString);

        // 反馈数据给Activity
        Data outputData = new Data.Builder().putString("David","三分归元气-返回给Activity").build();
        Result.Success success = new Result.Success(outputData);
        // return new Result.Failure(); // 本地执行 doWork 任务时 失败
        // return new Result.Retry(); // 本地执行 doWork 任务时 重试
        // return new Result.Success(); // 本地执行 doWork 任务时 成功 执行任务完毕
        return success;
    }
}
