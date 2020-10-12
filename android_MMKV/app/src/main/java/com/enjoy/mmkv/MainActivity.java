package com.enjoy.mmkv;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;


public class MainActivity extends AppCompatActivity {


    private SharedPreferences sharedPreferences;
    private MMKV multiMMKV;
    private MMKV singleMMKV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("test", MODE_PRIVATE);

        MMKV.initialize("/sdcard/mmkv");
        MMKV.defaultMMKV().putInt("ammkvtest",1);

        multiMMKV = MMKV.mmkvWithID("id",MMKV.MULTI_PROCESS_MODE);

        {
            Log.e("MMKV", "页面1，读取int数据:" + multiMMKV.getInt("int", 0));
            Log.e("MMKV", "页面1, 设置float数据:" + multiMMKV.getFloat("float", 0));
            Log.e("MMKV", "页面1，读取long数据:" + multiMMKV.getLong("long", 0));


            multiMMKV.putInt("int", 1);
            Log.e("MMKV", "页面1, 设置int数据:" + multiMMKV.getInt("int", 0));

            multiMMKV.putFloat("float", 1.1f);
            Log.e("MMKV", "页面1, 设置float数据:" + multiMMKV.getFloat("float", 0));

            multiMMKV.putLong("long", 10000000L);
            Log.e("MMKV", "页面1, 设置long数据:" + multiMMKV.getLong("long", 0));

        }
        singleMMKV = MMKV.mmkvWithID("id2",MMKV.SINGLE_PROCESS_MODE);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e("MMKV", "页面1，读取int数据:" + multiMMKV.getInt("int", 0));
        Log.e("MMKV", "页面1, 设置float数据:" + multiMMKV.getFloat("float", 0));
        Log.e("MMKV", "页面1，读取long数据:" + multiMMKV.getLong("long", 0));
    }

    public void testProcess(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }

    public void testPrefrence(View view) {
        Random r = new Random();
        Log.e("MMKV", "SharedPreferences开始写入int");
        SharedPreferences.Editor edit = sharedPreferences.edit();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            edit.putInt(String.valueOf(i), r.nextInt()).apply();
        }
        long time = System.currentTimeMillis() - start;
        Log.e("MMKV", "SharedPreferences写入int耗时:" + time + "ms");

    }

    public void testMMKV(View view) {
        Random r = new Random();
        Log.e("MMKV", "MMKV开始写入int");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            singleMMKV.putInt(String.valueOf(i), r.nextInt());
        }
        long time = System.currentTimeMillis() - start;
        Log.e("MMKV", "MMKV写入int耗时:" + time + "ms");
    }


    public void testReadPrefrence(View view) {
        Log.e("MMKV", "SharedPreferences开始读取int");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            sharedPreferences.getInt(String.valueOf(i), -1);
        }
        long time = System.currentTimeMillis() - start;
        Log.e("MMKV", "SharedPreferences读取int耗时:" + time + "ms");
    }

    public void testReadMMKV(View view) {
        Log.e("MMKV", "MMKV开始读取int");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            singleMMKV.getInt(String.valueOf(i), -1);
        }
        long time = System.currentTimeMillis() - start;
        Log.e("MMKV", "MMKV读取int耗时:" + time + "ms");
    }


}
