package top.zcwfeng.customui.baseui.spantext;

import android.graphics.Color;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

/**
 * 作者：zcw on 2019-10-30
 */
public class SpanTextUtils {

    public static void setTextSpannableCombin(List<SpanObj> spanObjs, TextView tv) {

        SpannableStringBuilder ssb = new SpannableStringBuilder();
        for(SpanObj spanObj:spanObjs) {
            ssb.append(spanObj.getText());
        }
        int start = 0;
        int end = 0;
        for(int i=0;i<spanObjs.size();i++) {
            if(i == 0) {
                start = 0;
                end = spanObjs.get(i).getText().length();
            } else {
                start = start + spanObjs.get(i-1).getText().length();
                end = end +  spanObjs.get(i).getText().length();
            }

            AbsoluteSizeSpan AbsoluteSizeSpan = new AbsoluteSizeSpan(spanObjs.get(i).getSize());
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor(spanObjs.get(i).getColor()));
            ssb.setSpan(colorSpan, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            ssb.setSpan(AbsoluteSizeSpan,start,end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }

        tv.setText(ssb);

        Log.e("toHtml-> " , ssb.toString() + "\n" + Html.toHtml(ssb));

    }
}
