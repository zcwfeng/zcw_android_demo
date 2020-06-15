package top.zcwfeng.android_apt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//@IntDef
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface DavidAnnotation {
    int value();
    String david();
}
