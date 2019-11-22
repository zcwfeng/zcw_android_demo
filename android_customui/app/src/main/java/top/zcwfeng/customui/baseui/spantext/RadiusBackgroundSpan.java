package top.zcwfeng.customui.baseui.spantext;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

/**
 * Created by guokun on 2018/5/24.
 * 书籍和节目详情目录：title 加上 精品等tag。使用RadiusBackgroundSpan可以使用一个TextView，避免打开后重绘导致闪动tag的bug.
 */

public class RadiusBackgroundSpan extends ReplacementSpan {

    private int mSize;
    private int mColor;
    private int mRadius;
    private float mTextSize;
    private int mTextColor;
    private int mTextMargin;
    private int mTopMargin;
    private int mDivide;

    /**
     *
     * @param color
     * @param radius
     * @param textSize
     * @param textColor
     * @param textMargin
     * @param topMargin
     * @param divide
     */
    public RadiusBackgroundSpan(int color, int radius, int textSize, int textColor, int textMargin, int topMargin, int divide) {
        mColor = color;
        mRadius = radius;
        mTextSize = textSize;
        mTextColor = textColor;
        mTextMargin = textMargin;
        mTopMargin = topMargin;
        mDivide = divide;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        float oldTextSize = paint.getTextSize();
        paint.setTextSize(mTextSize);  //更改字体大小，重新测试宽度
        mSize = (int) (paint.measureText(text, start, end) + 2 * mRadius + 2 * mTextMargin);
        //mSize就是span的宽度，span有多宽，开发者可以在这里随便定义规则
        //我的规则：这里text传入的是SpannableString，start，end对应setSpan方法相关参数
        //可以根据传入起始截至位置获得截取文字的宽度，最后加上左右两个圆角的半径得到span宽度
        paint.setTextSize(oldTextSize);
        return mSize;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        float oldTextSize = paint.getTextSize();  //保存文字大小
        int color = paint.getColor();//保存文字颜色

        //x移动mDivide
        x = x + mDivide;
        paint.setTextSize(mTextSize); //设置字体大小
        paint.setColor(mColor);//设置背景颜色
        paint.setAntiAlias(true);// 设置画笔的锯齿效果

        //oval就是文字背景矩形。这里在宽度为mSize，高度为bottom-top的矩形内新建了一个文字需要的矩形
        //mTextSize为文字高度，这里加上了字体周围间距mRadius,mTextMargin,mTopMargin
        RectF oval = new RectF(x, (top + bottom - mTextSize - mRadius - mTopMargin) / 2, x + mSize,
                (top + bottom + mTextSize + mRadius + mTopMargin) / 2);
        canvas.drawRoundRect(oval, mRadius, mRadius, paint);//绘制圆角矩形，第二个参数是x半径，第三个参数是y半径

        paint.setColor(mTextColor);//设置画笔的文字颜色

        //根据oval大小和字体，将文字绘制在oval居中位置
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float topTx = fontMetrics.top;//为基线到字体上边框的距离
        float bottomTx = fontMetrics.bottom;//为基线到字体下边框的距离
        int baseLineY = (int) (oval.centerY() - topTx / 2 - bottomTx / 2);//基线中间点的y轴计算公式

        canvas.drawText(text, start, end, x + mRadius + mTextMargin, baseLineY, paint);//绘制文字

        //恢复paint字体大小和颜色
        paint.setTextSize(oldTextSize);
        paint.setColor(color);
    }
}