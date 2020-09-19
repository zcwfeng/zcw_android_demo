package com.zero.flowlayoutdemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Zero";

    private LinearLayout root;

    private Button mBtnScollTo;
    private Button mBtnScrooBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout1);

//        root = findViewById(R.id.root);

//        findViewById(R.id.btn_scollto).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "移动前scrollTo: scrollx= " + root.getScrollX() + " scrollY= " + root.getScrollY() );
//                root.scrollTo(-50,-50);
//                Log.i(TAG, "移动后scrollTo: scrollx= " + root.getScrollX() + " scrollY= " + root.getScrollY() );
//            }
//        });
//
//        findViewById(R.id.btn_scollby).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "移动后scrollBy: scrollx= " + root.getScrollX() + " scrollY= " + root.getScrollY() );
//                root.scrollBy(-50,-50);
//                Log.i(TAG, "移动后scrollBy: scrollx= " + root.getScrollX() + " scrollY= " + root.getScrollY() );
//            }
//        });


    }
}
