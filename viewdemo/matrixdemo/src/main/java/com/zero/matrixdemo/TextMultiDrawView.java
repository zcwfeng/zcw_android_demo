package com.zero.matrixdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class TextMultiDrawView extends View {

    private Paint mPaint;
    private TextPaint mTextPaint;

    private String text = "澳大利亚曾质疑过日本科研捕鲸的真实性。2010年，澳大利亚政府曾向海牙国际法院提起诉讼，控告日本在南冰洋的“科研”捕鲸活动实则是商业捕鲸。2014年，国际法院对此作出终审裁决，认定日本“出于科研目的”的捕鲸理由不成立，其捕鲸行为违背了《国际捕鲸管制公约》。日本表示尊重国际法院的裁决，并有所收敛了一段时间，但捕鲸活动仍未终止。2018年9月，在IWC的巴西峰会上，日本重提恢复商业捕鲸的诉求，但又一次遭到委员会的否决。这被视为日本最终退出该组织的直接原因被“科研”捕杀的鲸鱼，是如何被送上餐桌的？以科研名义被捕杀的鲸鱼，最后被输送到日本国内，满足人们的口腹之欲。负责执行这一系列动作的是一个名为日本鲸类研究所的机构，其上属机构是日本水产厅。日本鲸类研究所对鲸鱼肉有一个有趣的称呼：科研调查的副产物。他们称，根据《国际捕鲸规则公约》第8条的规定，调查后的鲸鱼体应被尽可能充分地利用。因而在鲸鱼被捕捞到渔船上并完成了对其体型、皮脂、胃内容物等款项的检测后，鲸体即会被拆解，用于鲸肉消费品的生产。当渔船抵达日本后，一块块的鲸肉会被分送给日本各级消费市场，或是以远低于市场价的价格出售给各地政府、供应于日本小学生的午餐中。";

    float[] curWidth = new float[1];
    private int mScreenHeight;
    private Bitmap mBitmap;

    public TextMultiDrawView(Context context) {
        super(context);
        init(context);

    }

    public TextMultiDrawView(Context context, AttributeSet set) {
        super(context, set);
        init(context);

    }
    private void init(Context context){
        mPaint = new Paint();
        mPaint.setTextSize(Utils.dp2px(15));
        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(Utils.dp2px(15));
        mScreenHeight = Utils.getScreenHeight(context);
        mBitmap = Utils.getBitmapByWidth(context.getResources(),R.drawable.girl,Utils.dp2px(100));


    }



    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if(heightMode == MeasureSpec.UNSPECIFIED){//为什么这么写
            setMeasuredDimension(widthSize,mScreenHeight *2);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        StaticLayout staticLayout = new StaticLayout(text,mTextPaint,getWidth(), Layout.Alignment.ALIGN_NORMAL
//        ,1,0,false);
//        staticLayout.draw(canvas);


        canvas.drawBitmap(mBitmap,getWidth() - Utils.dp2px(100),100,mPaint);
//        canvas.drawText(text,0,100,mPaint);
         int index = mPaint.breakText(text,true,getWidth(),curWidth);
        canvas.drawText(text,0,index,0,100,mPaint);
        int oldIndex = index;
        index = mPaint.breakText(text,index,text.length(),true,getWidth() - Utils.dp2px(100),curWidth);
        canvas.drawText(text,oldIndex,oldIndex + index,0,100 + mPaint.getFontSpacing(),mPaint);
        oldIndex = index;
        index = mPaint.breakText(text,index,text.length(),true,getWidth() - Utils.dp2px(100),curWidth);
        canvas.drawText(text,oldIndex,oldIndex + index,0,100 + mPaint.getFontSpacing()*2,mPaint);
    }

}
