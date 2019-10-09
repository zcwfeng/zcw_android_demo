package com.example.android_lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        MainPresenter mainPresenter = new MainPresenter(TAG);
        //把MainPresenter交给LifeCycle管理就OK了
        getLifecycle().addObserver(mainPresenter);
    }
    @Override protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
