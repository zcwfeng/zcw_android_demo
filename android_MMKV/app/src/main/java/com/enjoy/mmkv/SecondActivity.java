package com.enjoy.mmkv;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * @author Lance
 * @date 2019-11-04
 */
public class SecondActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MMKV.initialize("/sdcard/mmkv");
        MMKV mmkv = MMKV.mmkvWithID("id",MMKV.MULTI_PROCESS_MODE);

        Log.e("MMKV", "页面2，读取int数据:" + mmkv.getInt("int", 0));
        Log.e("MMKV", "页面2, 读取float数据:" + mmkv.getFloat("float", 0));
        Log.e("MMKV", "页面2, 读取long数据:" + mmkv.getLong("long", 0));

        mmkv.putInt("int", -1);
        Log.e("MMKV", "页面2, 设置int数据:" + mmkv.getInt("int", 0));

        mmkv.putFloat("float", -1.1f);
        Log.e("MMKV", "页面2, 设置float数据:" + mmkv.getFloat("float", 0));

        mmkv.putLong("long", -10000000L);
        Log.e("MMKV", "页面2, 设置long数据:" + mmkv.getLong("long", 0));

    }
}
