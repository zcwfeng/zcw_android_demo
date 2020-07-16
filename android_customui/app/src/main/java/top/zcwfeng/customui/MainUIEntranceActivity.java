package top.zcwfeng.customui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import top.zcwfeng.aspectj.BehaviorTrace;
import top.zcwfeng.customui.baseui.dispatch.DispatchActivity;
import top.zcwfeng.customui.baseui.srl_vp.SRL_VP_main;
import top.zcwfeng.customui.baseui.view.ViewEntranceActivity;
import top.zcwfeng.customui.demo.DemoSpan;
import top.zcwfeng.customui.demo.MyQueryHandler;
import top.zcwfeng.customui.http.IDataListener;
import top.zcwfeng.customui.http.NetFramework;
import top.zcwfeng.customui.http.TestBean;
import top.zcwfeng.customui.leakcanarytest.TestLeakCanary;

public class MainUIEntranceActivity extends AppCompatActivity {
    private TextView mTextView;
    private final String TAG = "zcw";

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.colorText);
        testMyQueryHandler();

        // 测试时间冲突原理
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "事件冲突测试onClick");

            }
        });


        mTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e(TAG, "时间冲突onTouch");
                return false;
            }
        });
    }

    //语音消息
    @BehaviorTrace("语音消息")
    public void mAudio(View view) {
        Log.d(TAG, "123123132");
    }

    //视频通话
    @BehaviorTrace("视频通话")
    public void mVideo(View view) {
    }

    //发表说说
    @BehaviorTrace("发表说说")
    public void saySomething(View view) {
    }


    @BehaviorTrace("TestAspectj")
    private void testMyQueryHandler() {
        Log.d(TAG, "AspectJ...");
        MyQueryHandler queryHandler = new MyQueryHandler(getContentResolver());
        String projection[] = {"a", "b"};
        queryHandler.startQuery(234, "adapter", Uri.parse("uri"),
                projection, null, null, "data desc");
        SystemClock.sleep(20);
    }


    public void BottomSheetDialogFragment(View view) {
        Intent intent = new Intent(getApplicationContext(), DemoSpan.class);
        startActivity(intent);
    }

    public void leakCanaryTest(View view) {
        innerStartActivity(TestLeakCanary.class);
    }

    public void testDispatchEvent(View view) {
        innerStartActivity(DispatchActivity.class);
    }

    public void testSRLVPEvent(View view) {
        innerStartActivity(SRL_VP_main.class);
    }

    public void innerStartActivity(Class clazz) {
        Intent intent = new Intent(getApplicationContext(), clazz);
        startActivity(intent);
    }

    public void viewEntrance(View view) {
        innerStartActivity(ViewEntranceActivity.class);
    }

    public void httpTest(View view) {
//        String url ="https://www.baidu.com";
//        String url = "http://www.httpbin.org/get";
        String url = "http://www.httpbin.org/post";
        NetFramework.sendJsonRequest(url, "", TestBean.class, new IDataListener<TestBean>() {

            @Override
            public void onSuccess(TestBean s) {
                Log.i("zcw", s.toString());
            }

            @Override
            public void onFailed() {
                Log.e("zcw", "--------onFailed");
            }
        });
    }
}
