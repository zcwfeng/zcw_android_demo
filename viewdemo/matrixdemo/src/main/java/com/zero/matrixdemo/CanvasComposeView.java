package com.zero.matrixdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class CanvasComposeView extends View {
    public CanvasComposeView(Context context) {
        super(context);
    }

    public CanvasComposeView(Context context, AttributeSet set) {
        super(context, set);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        //构造两个画笔，一个红色，一个绿色
        Paint paint_green = generatePaint(Color.GREEN, Style.STROKE, 10);
        Paint paint_red   = generatePaint(Color.RED, Style.STROKE, 10);
        
        //构造一个矩形
        Rect rect1 = new Rect(0,0,400,220);

        //在平移画布前用绿色画下边框
        canvas.drawRect(rect1, paint_green);
        
        //平移画布后,再用红色边框重新画下这个矩形
        canvas.translate(100, 100);
        canvas.drawRect(rect1, paint_red);

    }
    private Paint generatePaint(int color, Style style, int width)
    {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(style);
        paint.setStrokeWidth(width);
        return paint;
    }

}
