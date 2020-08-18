package top.zcwfeng.activityhookdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import top.zcwfeng.activityhookdemo.databinding.ActivityMainBinding;
import top.zcwfeng.activityhookdemo.hook.HookHelper;
import top.zcwfeng.activityhookdemo.hook.HookHelper2;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        mainBinding.btnHook1.setOnClickListener(v->onBtnHook1Clicked());
        mainBinding.btnHook2.setOnClickListener(v->onBtnHook2Clicked());
        mainBinding.btnHook3.setOnClickListener(v->onBtnHook3Clicked());
        mainBinding.btnHook4.setOnClickListener(v->onBtnHook4Clicked());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
//        HookHelper.hookAMSInterceptStartActivity();
//        HookHelper.hookH();
    }

    private void onBtnHook4Clicked() {
//        HookHelper.hookAMSInterceptStartActivity();
//        HookHelper.hookH();
//        Intent intent = new Intent(this,TargetActivity.class);
//        startActivity(intent);
        HookHelper2.hookIActivityManager();
        HookHelper2.hookHandler();
        Intent intent = new Intent(this,TargetActivity.class);
        startActivity(intent);
    }

    private void onBtnHook3Clicked() {
        HookHelper.hookAMS();
        Intent intent = new Intent(this,SubActivity.class);
        startActivity(intent);
    }

    private void onBtnHook2Clicked() {
        HookHelper.hookActivityThreadInstrumentation();
        Intent intent = new Intent(this,SubActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getApplicationContext().startActivity(intent);

    }

    private void onBtnHook1Clicked() {
        // TODO: 2020/8/16 这个个实验有问题 可以启动getApplicationContext，那个版本的测试？
        HookHelper.hookInstrumentation(this);
        Intent intent = new Intent(this,SubActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        //通过getApplicationContext启动不了
//        getApplicationContext().startActivity(intent);
    }


}
