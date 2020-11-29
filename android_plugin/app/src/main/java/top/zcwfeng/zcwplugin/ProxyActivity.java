package top.zcwfeng.zcwplugin;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class ProxyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_proxy);
        Log.e("zcw_plugin", "ProxyActivity：宿主的代理Activity");
    }
}