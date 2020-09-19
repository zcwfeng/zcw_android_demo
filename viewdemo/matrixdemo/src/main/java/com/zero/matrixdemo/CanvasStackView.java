package com.zero.matrixdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class CanvasStackView extends View {

    private Paint mPaint;
    public CanvasStackView(Context context) {
        super(context);
        mPaint = new Paint();
    }

    public CanvasStackView(Context context, AttributeSet set) {
        super(context, set);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        
        super.onDraw(canvas);
        
        canvas.drawColor(Color.RED);

//        //保存当前画布大小即整屏
        canvas.save();
//
        canvas.clipRect(new Rect(100, 100, 800, 800));
        canvas.drawColor(Color.GREEN);

//        // 去锯齿
//        mPaint.setAntiAlias(true);
//        mPaint.setStyle(Paint.Style.STROKE);//画线
//
//        mPaint.setColor(Color.BLUE);
//        mPaint.setStrokeWidth(3);//设置线的宽度
//        canvas.drawCircle(100,100,50,mPaint);

        ////        恢复整屏画布
        canvas.restore();
        canvas.drawColor(Color.BLUE);
//
//        ////////////--------------------------------------------
        canvas.drawColor(Color.RED);
//        //保存的画布大小为全屏幕大小
        canvas.save();

        canvas.clipRect(new Rect(100, 100, 800, 800));
        canvas.drawColor(Color.GREEN);
        //保存画布大小为Rect(100, 100, 800, 800)
        canvas.save();

        canvas.clipRect(new Rect(200, 200, 700, 700));
        canvas.drawColor(Color.BLUE);
        //保存画布大小为Rect(200, 200, 700, 700)
        canvas.save();
//
        canvas.clipRect(new Rect(300, 300, 600, 600));
        canvas.drawColor(Color.BLACK);
        //保存画布大小为Rect(300, 300, 600, 600)
        canvas.save();
//
        canvas.clipRect(new Rect(400, 400, 500, 500));
        canvas.drawColor(Color.WHITE);

//        //将栈顶的画布状态取出来，作为当前画布，并画成黄色背景
        canvas.restore();
        canvas.restore();
        canvas.restore();
        canvas.restore();
        canvas.drawColor(Color.YELLOW);

    }
    
    

}
