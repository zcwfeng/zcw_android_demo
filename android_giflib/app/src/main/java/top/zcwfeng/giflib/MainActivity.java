package top.zcwfeng.giflib;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import top.zcwfeng.giflib.databinding.ActivityMainBinding;
import top.zcwfeng.giflib.gif.GifDrawable;
import top.zcwfeng.giflib.gif.GifFrame;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }

    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 666);
        }

        GifDrawable gifDrawable=null;
        try {

//            File file = new File("a.txt");
//            FileInputStream fileInputStream = new FileInputStream(file);
//            byte[] buffer = new byte[1024];
//            fileInputStream.read(buffer,0,buffer.length);

//            gifDrawable = new GifDrawable(GifFrame.decodeStream(getAssets().open("fire.gif")));
//            gifDrawable = new GifDrawable(GifFrame.decodeStream(this,"time_1.gif"));
            gifDrawable = new GifDrawable(GifFrame.decodeStream(null,"/sdcard/timg_2.gif"));
            mainBinding.image.setImageDrawable(gifDrawable);
            gifDrawable.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public native String stringFromJNI();

}