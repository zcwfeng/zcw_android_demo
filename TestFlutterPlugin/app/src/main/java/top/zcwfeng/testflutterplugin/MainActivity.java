package top.zcwfeng.testflutterplugin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import io.flutter.facade.Flutter;

public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void innerFluterFragment(View view){
        View flutterView = Flutter.createView(
                MainActivity.this,
                getLifecycle(),
                "route1"
        );
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                layout.leftMargin = 100;
//                layout.topMargin = 200;
        addContentView(flutterView, layout);
    }


    public void goToFluterPlugin(View view){
        Intent intent = new Intent(MainActivity.this,FlutterTestActivity.class);
        startActivity(intent);
    }

}
