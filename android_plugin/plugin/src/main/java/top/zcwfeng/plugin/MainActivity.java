package top.zcwfeng.plugin;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// TODO: 1.测试启动Activity 加载资源先注释
        //        setContentView(R.layout.activity_main);
        Log.e("zcw_plugin" , "onCreate（），启动插件的Activity");

        Log.e("zcw_plugin" , "插件application:" + getApplication());

    }
}