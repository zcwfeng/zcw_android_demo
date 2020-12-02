package top.zcwfeng.plugin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

//public class MainActivity extends AppCompatActivity {
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// TODO: 1.测试启动Activity 加载资源先注释

        // TODO: 2020/12/1 启动资源
//                setContentView(R.layout.activity_main);
        Log.e("zcw_plugin" , "onCreate（），启动插件的Activity");

        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_main, null);
        setContentView(view);

        Log.e("zcw_plugin" , "插件application:" + getApplication());


    }
}