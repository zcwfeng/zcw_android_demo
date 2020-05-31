package com.example.android_fresco;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.View;

import com.example.android_fresco.image.FrescoHelper;
import com.example.android_fresco.image.ImagePipelineConfigFactory;
import com.example.android_fresco.image.MeMeDraweeView;
import com.example.android_fresco.image.ObjectAnimListenerAdapter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.fresco.animation.drawable.AnimatedDrawable2;

public class MainActivity extends AppCompatActivity {
    MeMeDraweeView miaoWebpView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fresco.initialize(this, ImagePipelineConfigFactory.getOkHttpImagePipelineConfig(this));
        MeMeDraweeView.initialize(Fresco.getDraweeControllerBuilderSupplier());
        miaoWebpView = (MeMeDraweeView) findViewById(R.id.simple);


        FrescoHelper.create(miaoWebpView)
                .controllerListener(new WebPControllerAdapter())
                .load(R.mipmap.grab_hot_rank_webp);
//        FrescoHelper.create(miaoWebpView).load("http://bpic.588ku.com/element_origin_min_pic/01/61/73/385748db4cb62e0.jpg");


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
            miaoWebpView.setVisibility(View.GONE);
        }
    }
}
