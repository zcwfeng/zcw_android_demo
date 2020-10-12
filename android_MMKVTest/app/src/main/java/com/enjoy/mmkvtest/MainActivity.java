package com.enjoy.mmkvtest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    static {

        // libnative-lib.so
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //写 使用 mmap 映射文件 在内存当中 并操作这块内存 往这块内存中写入一块数据
        writeTest();
        readTest();

    }


    public native void writeTest();

    public native void readTest();


}
