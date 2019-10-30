package top.zcwfeng.customui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import top.zcwfeng.customui.demo.DemoSpan;

public class MainUIEntranceActivity extends AppCompatActivity {
    private TextView mTextView;
    private Spanned mSpanned;
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




    }




    public void BottomSheetDialogFragment(View view){
        Intent intent = new Intent(getApplicationContext(), DemoSpan.class);
        startActivity(intent);
    }
}
