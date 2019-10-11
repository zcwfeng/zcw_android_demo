package com.example.android_fresco.image;

import android.graphics.drawable.Animatable;

import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.fresco.animation.drawable.AnimatedDrawable2;
import com.facebook.fresco.animation.drawable.AnimationListener;
import com.facebook.imagepipeline.image.ImageInfo;

public class DraweeControllerListener extends BaseControllerListener<ImageInfo> {

    private FrescoLoadCallback<ImageInfo> loadCallback;
    private AnimationListener animationListener;

    public DraweeControllerListener(FrescoLoadCallback<ImageInfo> loadCallback) {
        this.loadCallback = loadCallback;
    }

    @Override
    public void onFailure(String id, Throwable throwable) {
        super.onFailure(id, throwable);

        if (loadCallback != null) {
            loadCallback.onFailure(throwable);
        }
    }

    public void setAnimationListener(AnimationListener animationListener) {
        this.animationListener = animationListener;
    }


    @Override
    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
        super.onFinalImageSet(id, imageInfo, animatable);
        if (animatable != null && animationListener != null) {
            AnimatedDrawable2 animatedDrawable = (AnimatedDrawable2) animatable;
            animatedDrawable.setAnimationListener(animationListener);
        }
        if (loadCallback != null) {
            loadCallback.onSuccess(imageInfo, animatable);
        }
    }
}
