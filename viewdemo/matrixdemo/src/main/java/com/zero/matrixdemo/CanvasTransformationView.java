package com.zero.matrixdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class CanvasTransformationView extends View {
    private final Rect mRect = new Rect(0, 0, 200, 200);
    private Paint mPaint;

    public CanvasTransformationView(Context context) {
        super(context);
        init();
    }

    public CanvasTransformationView(Context context, AttributeSet set) {
        super(context, set);
        init();
    }
    public void init(){
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        mPaint.setColor(Color.YELLOW);
        
        canvas.translate(100, 100);  
        canvas.drawColor(Color.GRAY);//可以看到，整个屏幕依然填充为灰色  
        
        canvas.drawRect(mRect, mPaint);
//
        canvas.translate(0, 300);
        canvas.scale(0.5f, 0.5f);  //缩放了
        canvas.drawRect(mRect, mPaint);
//
        canvas.translate(0, 300);
        canvas.rotate(30);  //旋转了
        canvas.drawRect(new Rect(0, 0, 200, 200), mPaint);
//
        canvas.translate(0, 300);
        canvas.skew(.5f, .5f);//扭曲了
        canvas.drawRect(new Rect(0, 0, 200, 200), mPaint);
    }
}
