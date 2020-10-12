package com.enjoy.enjoymmkv;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        MMKV.initialize(this);
        MMKV.initialize("/sdcard/mmkv_Enjoy");
        MMKV mmkv = MMKV.defaultMMKV();

        Log.e(TAG, "a=" + mmkv.getInt("a", 0));
        Log.e(TAG, "b=" + mmkv.getInt("b", 0));
        Log.e(TAG, "c=" + mmkv.getInt("c", 0));
        Log.e(TAG, "d=" + mmkv.getInt("d", 0));


        mmkv.putInt("a", 1);
        mmkv.putInt("b", -1);
        mmkv.putInt("c", 128);
        mmkv.putInt("d", -128);

        Log.e(TAG, "a=" + mmkv.getInt("a", 0));
        Log.e(TAG, "b=" + mmkv.getInt("b", 0));
        Log.e(TAG, "c=" + mmkv.getInt("c", 0));
        Log.e(TAG, "d=" + mmkv.getInt("d", 0));


    }


}
