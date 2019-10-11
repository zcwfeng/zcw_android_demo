package com.example.android_fresco;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.View;

import com.example.android_fresco.image.FrescoHelper;
import com.example.android_fresco.image.MeMeDraweeView;
import com.example.android_fresco.image.ObjectAnimListenerAdapter;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.fresco.animation.drawable.AnimatedDrawable2;

public class MainActivity extends AppCompatActivity {
    MeMeDraweeView miaoWebpView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        miaoWebpView = findViewById(R.id.first_webp_view);
//        FrescoHelper.create(miaoWebpView)
//                .controllerListener(new WebPControllerAdapter())
//                .load(R.mipmap.grab_hot_rank_webp);

        FrescoHelper.create(miaoWebpView).load("http://img3.imgtn.bdimg.com/it/u=33335323,2012764520&fm=26&gp=0.jpg");

    }


    private class WebPControllerAdapter extends BaseControllerListener {

        @Override
        public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
            super.onFinalImageSet(id, imageInfo, animatable);
            if (animatable != null) {
                AnimatedDrawable2 animatedDrawable2 = (AnimatedDrawable2) animatable;
                animatedDrawable2.setAnimationListener(new AnimListenerAdapter());
            }
        }
    }


    private class AnimListenerAdapter extends ObjectAnimListenerAdapter {
        @Override
        public void onAnimationStop(AnimatedDrawable2 drawable) {
//            miaoWebpView.setVisibility(View.GONE);
        }
    }
}
