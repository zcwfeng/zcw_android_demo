package top.zcwfeng.android_webview;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import top.zcwfeng.android_webview.databinding.ActivityMainBinding;
import top.zcwfeng.base.autoservice.CommonServiceLoader;
import top.zcwfeng.common.autoservice.IWebViewService;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mainBinding.webviewDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IWebViewService webViewService = CommonServiceLoader.load(IWebViewService.class);
//                webViewService.startWebViewActivity(
//                        MainActivity.this,
//                        "https://www.baidu.com",
//                        "David baidu",
//                        true);
                webViewService.startDemoHtml(MainActivity.this);
            }
        });
    }
}