package top.zcwfeng.use_workmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import top.zcwfeng.common.constants.Config;

public
class WorkManager3 extends Worker {
    private Context context;
    private WorkerParameters workerParams;

    public WorkManager3(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        this.workerParams = workerParams;
    }
    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    public Result doWork() {
        Log.d(Config.TAG_WORKMANAGER, "doWork 后台任务执行3");
        return new Result.Success();

    }
}
