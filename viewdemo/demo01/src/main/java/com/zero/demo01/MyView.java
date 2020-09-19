package com.zero.demo01;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyView extends View {

    private static final String TAG = "Zero";

    private Paint mPaint = new Paint();

    private int mScreenWidth = 0;
    private int mScreenHeight = 0;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mScreenWidth = Utils.getScreenWidth(context);
        mScreenHeight = Utils.getScreenHeight(context);
        Log.i(TAG, "init: w= " + mScreenWidth + " h= " + mScreenHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        if (heightMeasureSpec == 0) {
//            heightMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.AT_MOST);
//        }

        int heigthMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heigthMode == MeasureSpec.UNSPECIFIED) {//ScrollView需要特殊处理
            setMeasuredDimension(widthSize, mScreenHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //1. 坐标系
        //Paint 常用方法
//        Paint.setStyle(Style style) 设置绘制模式，默认是FILL
//        Style.FILL             填充模式
//        Style.STROKE           画线模式
//        Style.FILL_AND_STROKE  即画线又填充
//        Paint.setColor(int color) 设置颜色
//        Paint.setStrokeWidth(float width) 设置线条宽度
//        Paint.setTextSize(float textSize) 设置文字大小
//        Paint.setAntiAlias(boolean aa) 设置抗锯齿开关
        canvas.drawCircle(200, 200, 100, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);//修改为画线模式
        canvas.drawCircle(450,200,100,mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);//设置为红色
        canvas.drawCircle(700,200,100,mPaint);
        canvas.drawColor(Color.parseColor("#88E0FFFF"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(30);
        canvas.drawCircle(950,200,100,mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(950,200,100,mPaint);

        //画线
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(1f);
        canvas.drawLine(0,300,mScreenWidth,300,mPaint);//画一条

        //画几条线
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(50f);
        float[] linePts = new float[]{
                100,350,400,350
                ,250,350,250,650
                ,100,650,400,650
                ,450,350,450,650
                ,450,350,750,350
                ,750,350,750,650
                ,450,650,750,650
        };//必须是2的倍数
        canvas.drawLines(linePts,0,linePts.length,mPaint);


    }
}
