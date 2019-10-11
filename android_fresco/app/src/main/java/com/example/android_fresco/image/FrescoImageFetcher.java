package com.example.android_fresco.image;

public interface FrescoImageFetcher<T> {

    void onSuccess(T result);

    void onFailure(Throwable throwable);

    void onCancel();

}