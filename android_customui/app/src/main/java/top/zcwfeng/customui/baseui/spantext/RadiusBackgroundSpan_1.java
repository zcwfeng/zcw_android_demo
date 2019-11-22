package top.zcwfeng.customui.baseui.spantext;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

public class RadiusBackgroundSpan_1 extends ReplacementSpan {

    private int mColor;

    private int mTvColor;

    /**
     * @param color 背景颜色
     * @param tvColor 需要改变文字颜色吗
     */

    public RadiusBackgroundSpan_1(int color, int tvColor) {

        mColor = color;

        mTvColor = tvColor;


    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {

        //mSize就是span的宽度，span有多宽，开发者可以在这里随便定义规则

        //我的规则：这里text传入的是SpannableString，start，end对应setSpan方法相关参数

        //可以根据传入起始截至位置获得截取文字的宽度，最后加上左右两个圆角的半径得到span宽度


//        float oldTextSize = paint.getTextSize();
//        paint.setTextSize(mTextSize);  //更改字体大小，重新测试宽度
        int mSize = (int) (paint.measureText(text, start, end));
        //mSize就是span的宽度，span有多宽，开发者可以在这里随便定义规则
        //我的规则：这里text传入的是SpannableString，start，end对应setSpan方法相关参数
        //可以根据传入起始截至位置获得截取文字的宽度，最后加上左右两个圆角的半径得到span宽度
//        paint.setTextSize(oldTextSize);
        return mSize;

//        return (int) paint.measureText(text,start, end);


    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {

        paint.setColor(mColor);//设置背景颜色

//paint.setAntiAlias(true);// 设置画笔的锯齿效果

        int bgPainth = Math.round(paint.getFontMetrics().descent - paint.getFontMetrics().ascent);

        int bgSize = getSize(paint, text.subSequence(start, end), start, end, paint.getFontMetricsInt());

        RectF oval = new RectF(x , y + paint.ascent(), x + bgSize, y + paint.descent() / 2.0f);

//设置文字背景矩形，x为span其实左上角相对整个TextView的x值，y为span左上角相对整个View的y值。paint.ascent()获得文字上边缘，paint.descent()获得文字下边缘

        canvas.drawRoundRect(oval, (y + paint.descent()) / 2.0f, (y + paint.descent()) / 2.0f, paint);//绘制圆角矩形，第二个参数是x半径，第三个参数是y半径

//我这里是全圆。 你可以自己去根据你需要的。去除以

        paint.setColor(mTvColor);//你需要的画笔文字颜色


        int tvPainth = Math.round(paint.getFontMetrics().descent - paint.getFontMetrics().ascent);

        canvas.drawText(text, start, end, x , y - (bgPainth - tvPainth) / 2.0f+ (bgPainth - y) / 2.0f, paint);//绘制文字

    }

}