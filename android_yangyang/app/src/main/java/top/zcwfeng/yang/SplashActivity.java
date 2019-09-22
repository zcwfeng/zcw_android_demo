package top.zcwfeng.yang;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity {
    private WeakReference<Handler> handlerRef;
    private final int SPLASH_DISPLAY_LENGHT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handlerRef=new WeakReference<Handler>(new Handler());


        if(handlerRef.get() != null) {
            //延迟 SPLASH_DISPLAY_LENGHT时间然后跳转到MainActivity
            handlerRef.get().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();

                }
            }, SPLASH_DISPLAY_LENGHT);
        }



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerRef.clear();
    }
}
