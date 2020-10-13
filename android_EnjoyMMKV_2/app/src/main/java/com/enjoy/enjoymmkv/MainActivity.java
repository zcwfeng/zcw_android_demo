package com.enjoy.enjoymmkv;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "EnjoyMMKV";
    private MMKV mmkv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        MMKV.initialize(this);
        MMKV.initialize("/sdcard/mmkv");
        mmkv = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE);

    }


    public void testProcess(View view) {
        startService(new Intent(this, MyService.class));
    }

    public void readData(View view) {
        Log.e(TAG, "Activity读取数据================================");
        Log.e(TAG, "a=" + mmkv.getInt("a", 0));
    }

    public void updateData(View view) {
        Log.e(TAG, "Activity修改数据================================");
        //修改
        Random random = new Random();
        mmkv.putInt("a", random.nextInt());


        Log.e(TAG, "Activity读取数据================================");
        Log.e(TAG, "a=" + mmkv.getInt("a", 0));
    }
}
