package top.zcwfeng.customui.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.ViewStructure;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import top.zcwfeng.customui.R;
import top.zcwfeng.customui.baseui.spantext.SpanObj;
import top.zcwfeng.customui.baseui.spantext.SpanTextUtils;

public class DemoSpan extends AppCompatActivity {

    TextView mTextViewCombin;
    List<SpanObj> mSpanObjs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_span);
        mSpanObjs = new ArrayList<>();
        initSpans();
        mTextViewCombin = findViewById(R.id.colorText);

        SpanTextUtils.setTextSpannableCombin(mSpanObjs,mTextViewCombin);

    }

    private void initSpans() {
        SpanObj spanObj = new SpanObj();
        spanObj.setText("设置文字的前景色为淡蓝色");
        spanObj.setColor("#FFff0000");
        spanObj.setSize(30);

        SpanObj spanObj2 = new SpanObj();
        spanObj2.setText("第二个文本色");
        spanObj2.setColor("#aaff00ff");
        spanObj2.setSize(50);


        mSpanObjs.add(spanObj);
        mSpanObjs.add(spanObj2);



    }



}
