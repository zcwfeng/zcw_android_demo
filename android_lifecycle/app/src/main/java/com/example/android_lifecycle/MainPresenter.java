package com.example.android_lifecycle;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 作者：zcw on 2019-10-09
 */
public class MainPresenter implements LifecycleObserver {
    public static final String TAG = "MainPresenter";
    public String name;

    public MainPresenter(String name) {
        this.name = name;
    }

    //通过运行时注解的方式实现生命周期，是不是很像EventBus
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE) public void onCreate() {
        Log.d(TAG, name + "onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START) public void onStart() {
        Log.d(TAG, name + "onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME) public void onResume() {
        Log.d(TAG, name + "onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE) public void onPause() {
        Log.d(TAG, name + "onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP) public void onStop() {
        Log.d(TAG, name + "onStop");
    }
    //比较特殊的一个回调会在任何生命周期回调的时候都回调这里
    //@OnLifecycleEvent(Lifecycle.Event.ON_ANY) public void onDestroy() {
    //  Log.d(TAG, "ON_ANY");
    //}
}
