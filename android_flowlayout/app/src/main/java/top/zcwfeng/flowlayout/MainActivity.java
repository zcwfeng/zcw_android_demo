package top.zcwfeng.flowlayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import top.zcwfeng.flowlayout.anim.AnimationActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void TOFlowLayout(View view) {
        Intent intent = new Intent(MainActivity.this, FlowlayoutActivity.class);
        startActivity(intent);
    }

    public void TOFlexBoxLayout(View view) {
        Intent intent = new Intent(MainActivity.this, FlexBoxLayoutActivity.class);
        startActivity(intent);
    }
    public void TOFlexBoxLayoutManager(View view) {
        Intent intent = new Intent(MainActivity.this, FlexBoxLayoutManagerActivity.class);
        startActivity(intent);
    }
    public void TOTagFlowLayout(View view) {
        Intent intent = new Intent(MainActivity.this, TagFlowLayoutActivity.class);
        startActivity(intent);
    }
    public void TOAnimator(View view) {
        Intent intent = new Intent(MainActivity.this, AnimationActivity.class);
        startActivity(intent);
    }

}
