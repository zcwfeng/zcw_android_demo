package top.zcwfeng.android_effect.refresh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.wuyr.coffeedrawable.CoffeeDrawable;

import top.zcwfeng.android_effect.R;

public class CoffeeEffect extends AppCompatActivity {
    CoffeeDrawable drawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_effect);

        ViewGroup vg = findViewById(R.id.container);

        drawable = CoffeeDrawable.create(vg);

        drawable.setStirringDuration(500);
        drawable.setProgress(500);
        findViewById(R.id.img).setBackground(drawable);


    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    public void start(View view){
        drawable.start();



    }

    public void stop(View view){
        drawable.finish();
    }

}
