package top.zcwfeng.android_apt.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@OnEvent(listenerType = View.OnClickListener.class,listenerSetter = "setOnClickListener")
public @interface CLick {
    int[] value();
}
