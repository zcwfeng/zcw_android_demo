package top.zcwfeng.customui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import top.zcwfeng.aspectj.BehaviorTrace;
import top.zcwfeng.customui.demo.DemoSpan;
import top.zcwfeng.customui.demo.LayoutActivity;
import top.zcwfeng.customui.demo.MyQueryHandler;

public class MainUIEntranceActivity extends AppCompatActivity {
    private TextView mTextView;
    private Spanned mSpanned;
    private final String TAG = "zcw";
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.colorText);

//        mSpanned = HtmlCompat.fromHtml("<font size='25' >David Text</font><font size='40' color='#1Aff0000'>David Text</font>",HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS);
//        mTextView.setText(mSpanned);
//        mTextView.setTextColor(Color.parseColor("#1A000000"));
//        SpannableString spannableString = new SpannableString("设置文字的前景色为淡蓝色");
//
//
//        SpannableString spannableString2 = new SpannableString("dddddd");
//
//        AbsoluteSizeSpan AbsoluteSizeSpan1 = new AbsoluteSizeSpan(10);
//
//        SpannableStringBuilder ssb = new SpannableStringBuilder();
//        ssb.append(spannableString);
//        ssb.append(spannableString2);
//
//        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#0099EE"));
//
//        ssb.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        ssb.setSpan(AbsoluteSizeSpan1,0,spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//
//
//        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(Color.parseColor("#1Aff0000"));
//        ssb.setSpan(colorSpan2, spannableString.length(), spannableString2.length() + spannableString.length() , Spanned.SPAN_INCLUSIVE_EXCLUSIVE);


        // test
        LayoutActivity.LayoutActivityInstance(this);

        testMyQueryHandler();
    }

    //语音消息
    @BehaviorTrace("语音消息")
    public void mAudio(View view) {
        Log.d(TAG,"123123132");
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
        Log.d(TAG,"AspectJ...");
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
}
