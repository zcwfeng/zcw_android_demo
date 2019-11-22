package top.zcwfeng.customui.baseui.spantext;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

/**
 * 作者：zcw on 2019-11-01
 */
public class CustomClickableSpan extends ClickableSpan {

    private String content;
    public CustomClickableSpan(String content) {
        this.content = content;
    }




    @Override
    public void onClick(@NonNull View widget) {
        Log.e("CustomClickableSpan","CustomClickableSpan"+content);
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        super.updateDrawState(ds);

    }
}
