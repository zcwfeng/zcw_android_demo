package top.zcwfeng.android_effect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import top.zcwfeng.android_effect.refresh.CoffeeEffect;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void coffeedrawable(View view) {
        Intent intent = new Intent(this, CoffeeEffect.class);
        startActivity(intent);
    }
}
