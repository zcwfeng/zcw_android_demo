package top.zcwfeng.jni;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class JavaJNIActivity extends AppCompatActivity {

    private final static String TAG = JavaJNIActivity.class.getSimpleName();
// MainActivity中加载过了
// E/eglCodecCommon: GoldfishAddressSpaceHostMemoryAllocator: ioctl_ping failed for device_type=5, ret=-1
//    static {
//        System.loadLibrary("native-lib");
//    }

    public static final int A = 100;

    //Java 本地方法 实现：native层--->this
    public native String getStringPwd();

    //native static---->class
    public static native String getStringPwd2();

    //我们用native 修改java字段
    public String name = "David";
    public static int age = 0;

    // java交互
    public native void changeName();

    public static native void changeAge();

    public native void callAddMethod();

    // 函数，native调用java
    public int add(int num1, int num2) {
        return num1 + num2;
    }

    // 操作数组 String引用类型，玩数组
    public native void testArrayAction(int count, String textInfo, int[] ints, String[] strs);

    // 传递引用类型，传递对象
    public native void putObject(Student student, String str);

    // 没传如对象，直接创建Java对象
    public native void insertObject();

    // 测试引用
    public native void testQuote();

    // 释放全局引用
    public native void delQuote();

    /**
     * 下面是 点击事件区域
     */

    // 点击事件：操作testArrayAction函数
    public void test01ArrayAction(View view) {
        int[] ints = new int[]{1, 2, 3, 4, 5, 6};
        String[] strs = new String[]{"李小龙", "李连杰", "李元霸"};
        testArrayAction(99, "你好", ints, strs);

        for (int anInt : ints) {
            Log.d(TAG, "test01: anInt:" + anInt);
        }
    }

    // 点击事件：操作putObject函数
    public void test02putObject(View view) {
        Student student = new Student();
        student.name = "史泰龙";
        student.age = 88;
        putObject(student, "九阳神功");
    }

    // 点击事件：操作insertObject函数
    public void test03insertObject(View view) {
        insertObject();
    }

    // 点击事件：两个函数是一起的，操作引用 与 释放引用
    public void test04Quote(View view) {
        testQuote();
    }

    public void test05DelQuote(View view) {
        delQuote();
    }
    //----------------------JNI Base End---------------------

    public native void dynamicJavaM01();

    public native int dynamicJavaM02(String value);
    // 第二部分 JNI线程 区域 =========================================
    public native void naitveThread(); //Java层 调用 Native层 的函数，完成JNI线程
    public native void closeThread(); // 释放全局引用

    // 第三部分 纠结纠结细节 区域 ==================================================
    public native void nativeFun1();
    public native void nativeFun2();
    public static native void staticFun3();
    public static native void staticFun4();

    //----------------------JNI 线程---------------------


    @Override
    protected void onDestroy() {
        super.onDestroy();
        delQuote();
        closeThread();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_jni);

        changeName();

        TextView tv = findViewById(R.id.name_text);
        tv.setText(name);

        changeAge();
        TextView tva = findViewById(R.id.age_text);
        tva.setText(age + "");

        callAddMethod();
    }

    public void jni_click(View view) {
        switch (view.getId()) {
            case R.id.jni_regist_1:
                dynamicJavaM01();
                break;
            case R.id.jni_regist_2:
                dynamicJavaM02("JNI动态注册，JNI传参");
                break;
            case R.id.jni_thread_1:
                naitveThread();
                break;
            case R.id.jni_thread_2:
                nativeFun1();
                nativeFun2();
                staticFun3();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        staticFun4();
                    }
                }.start();
                break;
            case R.id.jni_thread_3:
                startActivity(new Intent(this, JNIActivityThreadTest.class));

                break;
            default:
                break;
        }
    }



    /**
     * 下面是 被native代码调用的 Java方法
     * 第二部分 JNI线程
     */
    public void updateActivityUI() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            new AlertDialog.Builder(JavaJNIActivity.this)
                    .setTitle("UI")
                    .setMessage("updateActivityUI Activity UI ...")
                    .setPositiveButton("Activity UI知道了", null)
                    .show();
        } else {
            Log.d(TAG, "updateActivityUI 所属于子线程，只能打印日志了..");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    new AlertDialog.Builder(JavaJNIActivity.this)
                            .setTitle("updateActivityUI")
                            .setMessage("所属于子线程，只能打印日志了..")
                            .setPositiveButton("Activity UI知道了", null)
                            .show();
                }
            });
        }
    }

}