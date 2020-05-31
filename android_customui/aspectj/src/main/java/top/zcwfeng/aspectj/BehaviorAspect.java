package top.zcwfeng.aspectj;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 作者：zcw on 2020-01-17
 */
@Aspect
public class BehaviorAspect {
    private static final String POINTCUT_METHOD =
            "execution(@top.zcwfeng.aspectj.BehaviorTrace * *(..))";

    //任何一个包下面的任何一个类下面的任何一个带有BehaviorTrace的方法，构成了这个切面
    @Pointcut(POINTCUT_METHOD)
    public void annoHaviorTrace() {
        Log.i("zcw", "annoHaviorTrace");
    }

    //拦截方法
    @Around("annoHaviorTrace()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.e("zcw", "weaveJoinPoint");
        //拿到方法的签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //类名
        String className = methodSignature.getDeclaringType().getSimpleName();
        //方法名
        String methodName = methodSignature.getName();
        //功能名
        BehaviorTrace behaviorTrace = methodSignature.getMethod().getAnnotation(BehaviorTrace.class);
        String fun = behaviorTrace.value();

        //方法执行前
        long begin = System.currentTimeMillis();

        //执行拦截方法
        Object result = joinPoint.proceed();

        //方法执行后
        long duration = System.currentTimeMillis() - begin;
        Log.e("zcw", String.format("功能：%s，%s的%s方法执行，耗时：%d ms", fun, className, methodName, duration));
        return result;
    }
}
