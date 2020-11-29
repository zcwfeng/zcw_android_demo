package top.zcwfeng.zcwplugin;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class HookUtils {
    public final static String TARGET_INTENT = "target_intent";

    /*

    Activity#startActivityForResult

    Instrumentation#execStartActivity

    int result = ActivityManager.getService()
                .startActivity(whoThread, who.getBasePackageName(), intent,
                        intent.resolveTypeIfNeeded(who.getContentResolver()),
                        token, target != null ? target.mEmbeddedID : null,
                        requestCode, 0, null, options);
     */
    public static void hookAMS() {
        try {
            Log.e("zcw_plugin", "动态代理hookAMS");

            Field singletonField = null;
            // 获取 Singleton 对象
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) { // 小于8.0
                Class<?> clazz = Class.forName("android.app.ActivityManagerNative");
                singletonField = clazz.getDeclaredField("gDefault");
            } else {
                Class<?> clazz = Class.forName("android.app.ActivityManager");
                singletonField = clazz.getDeclaredField("IActivityManagerSingleton");
            }

            singletonField.setAccessible(true);
            Object singleton = singletonField.get(null);

            // 获取 IActivityManager 对象
            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            final Object mInstance = mInstanceField.get(singleton);

            Class<?> iActivityManagerClass = Class.forName("android.app.IActivityManager");
            Object proxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class[]{iActivityManagerClass}, new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            //TODO Intent 的修改---进行过滤
//                            Log.e("zcw_plugin", method.getName());
                            if ("startActivity".equals(method.getName())) {
                                int index = -1;
                                for (int i = 0; i < args.length; i++) {
                                    if (args[i] instanceof Intent) {
                                        index = i;
                                        break;
                                    }
                                }

//                                Log.e("zcw_plugin", "动态代理proxyInstance");

                                Intent intent = (Intent) args[index];

                                Intent proxyIntent = new Intent();
                                proxyIntent.setClassName("top.zcwfeng.zcwplugin",
                                        "top.zcwfeng.zcwplugin.ProxyActivity");

                                proxyIntent.putExtra(TARGET_INTENT, intent);
                                args[index] = proxyIntent;
                            }

                            //mInsance--->iActivityManager 对象----不改边原有的执行流程
                            return method.invoke(mInstance, args);
                        }
                    });
            // TODO: 替换
            //proxyInstance.startActivity()
            // ActivityManager.getService() 替换成proxyInstance
            mInstanceField.set(singleton, proxyInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*

     // Subclasses must implement this to receive messages.

    public void handleMessage(Message msg) {
    }


    public void dispatchMessage(Message msg) {
        if (msg.callback != null) {
            handleCallback(msg);
        } else {
            if (mCallback != null) {
                if (mCallback.handleMessage(msg)) {
                    return;
                }
            }
            handleMessage(msg);
        }
    }
     */
    public static void hookHandler() {

        Log.e("zcw_plugin", "反射hookHandler");


        // 拿到handler对象 mh，非静态，拿到ActivityThread是静态的

        try {
            Class<?> clazz = Class.forName("android.app.ActivityThread");

            //获取 ActivityThread 对象
            Field activityThreadField = clazz.getDeclaredField("sCurrentActivityThread");
            activityThreadField.setAccessible(true);
            Object activityThread = activityThreadField.get(null);

            //获取 mh 对象
            Field mHField = clazz.getDeclaredField("mH");
            mHField.setAccessible(true);
            Handler mH = (Handler) mHField.get(activityThread);

            Field callbackField = Handler.class.getDeclaredField("mCallback");
            callbackField.setAccessible(true);


            // 创建callback
            Handler.Callback callback = new Handler.Callback() {
                @Override
                public boolean handleMessage(@NonNull Message msg) {
                    // 通过msg 拿到 Intent 换回 插件执行的Intent


                    switch (msg.what) {
                        case 100:
                            Log.e("zcw_plugin", "100 < 9.0");

                            try {
                                Field intentField = msg.obj.getClass().getDeclaredField("intent");
                                intentField.setAccessible(true);
                                // 启动代理Intent
                                Intent proxyIntent = (Intent) intentField.get(msg.obj);
                                // 启动插件Intent
                                Intent intent = proxyIntent.getParcelableExtra(TARGET_INTENT);
                                if (intent != null) {
                                    intentField.set(msg.obj, intent);
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;

                        case 159:
                            Log.e("zcw_plugin", "159 > 9.0");

                            try {
                                // 获取 mActivityCallbacks 对象
                                Field mActivityCallbacksField = msg.obj.getClass()
                                        .getDeclaredField("mActivityCallbacks");

                                mActivityCallbacksField.setAccessible(true);
                                List mActivityCallbacks = (List) mActivityCallbacksField.get(msg.obj);

                                for (int i = 0; i < mActivityCallbacks.size(); i++) {
                                    if (mActivityCallbacks.get(i).getClass().getName()
                                            .equals("android.app.servertransaction.LaunchActivityItem")) {
                                        Object launchActivityItem = mActivityCallbacks.get(i);

                                        // 获取启动代理的 Intent
                                        Field mIntentField = launchActivityItem.getClass()
                                                .getDeclaredField("mIntent");
                                        mIntentField.setAccessible(true);
                                        Intent proxyIntent = (Intent) mIntentField.get(launchActivityItem);

                                        // 目标 intent 替换 proxyIntent
                                        Intent intent = proxyIntent.getParcelableExtra(TARGET_INTENT);
                                        if (intent != null) {
                                            mIntentField.set(launchActivityItem, intent);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                    }


                    // 必须 return false 保证向下执行handlerMessage
                    return false;
                }
            };

            // 替换系统callback
            callbackField.set(mH, callback);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
