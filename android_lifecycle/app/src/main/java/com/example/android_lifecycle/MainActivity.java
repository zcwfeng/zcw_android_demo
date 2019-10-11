package com.example.android_lifecycle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.android_lifecycle.viewmodels.MyViewModel;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private MyViewModel viewModel;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        MainPresenter mainPresenter = new MainPresenter(TAG);
        //把MainPresenter交给LifeCycle管理就OK了
        getLifecycle().addObserver(mainPresenter);

//        RelativeLayout mMainLy = (RelativeLayout) findViewById(R.id.main_ly);
//        RPEarnCashEntranceView mCoinView = new RPEarnCashEntranceView(getApplicationContext());
//        mMainLy.addView(mCoinView);

        // 学习 androidx lifecycle ViewModel
        testViewModel();

    }


    public void testViewModel(){
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        viewModel.init();

    }

    // test intent SecondActivity
    public void SecondActivity(View view){
        Intent intent = new Intent(this,SecondActivity.class);
        startActivity(intent);
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
