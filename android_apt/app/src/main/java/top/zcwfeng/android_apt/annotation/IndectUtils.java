package top.zcwfeng.android_apt.annotation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class IndectUtils {
    static class ListenerInvocationHandler<T> implements InvocationHandler{
        private Method mMethod;
        private T target;

        public ListenerInvocationHandler(T target,Method method) {
            this.mMethod = method;
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return mMethod.invoke(target,args);
        }
    }

    public static void injectClick(Activity activity) {
        Class<? extends Activity> cls = activity.getClass();
        Method[] methods = cls.getDeclaredMethods();

        for (Method method : methods) {
            // 获取方法所有注解
            Annotation annotations[] = method.getAnnotations();
            for (Annotation an : annotations) {
                // 注解类型
                Class<? extends Annotation> annotationType = an.annotationType();
                // 注解的注解
                if (annotationType.isAnnotationPresent(OnEvent.class)) {
                    OnEvent onEvent = annotationType.getAnnotation(OnEvent.class);
                    //setOnClickListener
                    String listenerSetter = onEvent.listenerSetter();
                    //OnClickListener.class
                    Class listenerType = onEvent.listenerType();

                    try {
                        Method valueMethod = annotationType.getDeclaredMethod("value");
                        int[] viewIds = (int[]) valueMethod.invoke(an);
                        method.setAccessible(true);

                        ListenerInvocationHandler<Activity> handler = new ListenerInvocationHandler(activity,method);

                        Object listenerProxy = Proxy.newProxyInstance(listenerType.getClassLoader(),
                                new Class[]{listenerType},handler);
                        for(int viewId:viewIds){
                            View view = activity.findViewById(viewId);
                            Method setter = view.getClass().getMethod(listenerSetter,listenerType);
                            setter.invoke(view,listenerProxy);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }


    public static void injectView(Activity activity) {
        Class<? extends Activity> cls = activity.getClass();
        Field[] declaredFields = cls.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(InjectView.class)) {
                InjectView injectView = field.getAnnotation(InjectView.class);
                int id = injectView.id();
                field.setAccessible(true);
                View view = activity.findViewById(id);
                try {
                    field.set(activity, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    public static void injectExtras(Activity activity) {
        Class<? extends Activity> cls = activity.getClass();
        Intent intent = activity.getIntent();
        Bundle extras = intent.getExtras();
        if (extras == null)
            return;

        Field[] declaredFields = cls.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                Autowired autowired = field.getAnnotation(Autowired.class);
                String key = TextUtils.isEmpty(autowired.value()) ? field.getName() : autowired.value();
                if (extras.containsKey(key)) {
                    Object obj = extras.get(key);
                    //Parcebale 数组类型不能直接设置，其他的可以
                    // 获取数组单个元素类型
                    Class<?> componentType = field.getType().getComponentType();
                    if (field.getType().isArray() && Parcelable.class.isAssignableFrom(componentType)) {
                        Object[] objs = (Object[]) obj;
//                        Parcelable[].class -> (Class<? extends Object[]>) field.getType()
                        Object[] objects = Arrays.copyOf(objs, objs.length, (Class<? extends Object[]>) field.getType());
                        obj = objects;
                    }

                    field.setAccessible(true);

                    try {
                        field.set(activity, obj);

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}
