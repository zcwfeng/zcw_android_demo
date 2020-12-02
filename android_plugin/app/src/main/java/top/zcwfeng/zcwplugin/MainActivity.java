package top.zcwfeng.zcwplugin;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("zcw_plugin" , "宿主application:" + getApplication());


//        DexClassLoader classLoader = new DexClassLoader();
//        PathClassLoader pathClassLoader = new PathClassLoader()
        findViewById(R.id.load).setOnClickListener(v -> {
            // TODO: test 打印类加载器
//            printClassLoader();

//            testLoadDex();


            // TODO: 启动一个Activity
//        startActivity(new Intent(MainActivity.this,ProxyAcitvity.class));

            Intent intent = new Intent();
            intent.setComponent(new ComponentName("top.zcwfeng.plugin",
                    "top.zcwfeng.plugin.MainActivity"));
            startActivity(intent);

        });

        // TODO: 正常情况我们访问资源方式
//        String appName = getResources().getString(R.string.app_name);
//        InputStream is = getAssets().open("icon.png");

        HotFixTest.test();
    }


    private void printClassLoader() {
        ClassLoader classLoader = getClassLoader();
        while (classLoader != null) {
            Log.e("zcw_plugin", "classLoader:" + classLoader);
            classLoader = classLoader.getParent();
        }

        //pathClassLoader 和 BootClassLoader 分别加载什么类
        Log.e("zcw_plugin", "Activity 的 classLoader:" + Activity.class.getClassLoader());
        Log.e("zcw_plugin", "Activity 的 classLoader:" + AppCompatActivity.class.getClassLoader());

    }

    private void testLoadAPK() {
        try {
            // 直接加载
            Class<?> clazz = Class.forName("top.zcwfeng.zcwplugin.Test");
            Method clazzMethod = clazz.getMethod("print");
            clazzMethod.invoke(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void testLoadDex() {
        DexClassLoader dexClassLoader = new DexClassLoader("/sdcard/test.dex",
                MainActivity.this.getCacheDir().getAbsolutePath(),
                null,
                MainActivity.this.getClassLoader());
        try {
            Class<?> clazz = dexClassLoader.loadClass("top.zcwfeng.zcwplugin.Test");
            Method clazzMethod = clazz.getMethod("print");
            clazzMethod.invoke(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}