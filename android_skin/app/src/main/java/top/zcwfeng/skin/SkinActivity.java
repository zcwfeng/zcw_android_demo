package top.zcwfeng.skin;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import top.zcwfeng.lib.SkinManager;

public class SkinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        LayoutInflater.from(this).setFactory2();
        setContentView(R.layout.activity_skin);

//        findViewById(R.id.tabLayout);
//        Resources resources = getResources();
//        new Resources()


    }

    public void change(View view) {
        //换肤，收包裹，皮肤包是独立的apk包，可以来自网络下载
        SkinManager.getInstance().loadSkin("/data/data/top.zcwfeng.skin/skin/skin-debug.apk");
    }

    public void restore(View view) {
        SkinManager.getInstance().loadSkin(null);

    }
}