package top.zcwfeng.permission_aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import top.zcwfeng.permission_aop.MyPermissionActivity;

/**
 * 权限取消注解
 */
@Target(ElementType.METHOD)  // 方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时
public @interface PermissionCancel {

    int requestCode() default MyPermissionActivity.PARAM_PERMSSION_CODE_DEFAULT;

}
