package com.example.android_fresco.image;

import android.graphics.drawable.Animatable;

public interface FrescoLoadCallback <T> {

    void onSuccess(T result, Animatable animatable);

    void onFailure(Throwable throwable);

}