package top.zcwfeng.worker.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * create on 2020-04-16
 */
public class UploaderWorker extends Worker {
    public UploaderWorker(@NonNull Context context,
                          @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String imageuri = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1564154457,2160326790&fm=26&gp=0.jpg";

        String imageUriInput = getInputData().getString("");

//        Response response = uploadFile(imageuri);
//
//        Data outputData = new Data.Builder
//                .putString(Constants.KEY_IMAGE_URL, response.imageUrl)
//                .build();


        uploadsImages();
        return Result.success(new Data.Builder().build());
    }

    private void uploadsImages() {
        Log.e("zcw","Test UploaderWorker......");
    }
}
