package com.enjoy.enjoymmkv;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;

public class MyService extends Service {

    private static final String TAG = "EnjoyMMKV";
    private MMKV mmkv;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MMKV.initialize("/sdcard/mmkv");
        mmkv = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"Service读取数据================================");
        Log.e(TAG, "a=" + mmkv.getInt("a", 0));

        Log.e(TAG,"Service修改数据================================");
        //修改
        Random random = new Random();
        mmkv.putInt("a", random.nextInt());



        Log.e(TAG, "a=" + mmkv.getInt("a", 0));
        return super.onStartCommand(intent, flags, startId);
    }
}
