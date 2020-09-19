package com.zero.myfish.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyCircle extends View {
    private int mHeight;
    private int mWidth;
    private int radio;
    private int mWeight;
    private float sweepAngle;

    public MyCircle(Context context) {
        this(context, null);
    }

    public MyCircle(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        //外圆半径
        radio = mHeight / 2;
        //选中区域角度
        sweepAngle = 0f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置画布的位置
        canvas.translate(mWidth / 2, mHeight / 2);

        //外圆画笔对象
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);

        //内圆画笔对象
        Paint wPaint = new Paint();
        wPaint.setColor(Color.WHITE);
        wPaint.setStyle(Paint.Style.FILL);

        //画外圆
        canvas.drawCircle(0, 0, radio, paint);

        //画选中的轮盘颜色
        Paint mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        RectF rectF = new RectF(-radio, -radio, radio, radio);
        canvas.drawArc(rectF, -90, sweepAngle, true, mPaint);

        //设定环的宽度
        mWeight = 40;
        //画內圆
        canvas.drawCircle(0, 0, radio - mWeight, wPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //将下（x,y）移动至圆心，及现在的（0，0）
                float x = event.getX() - radio;
                float y = event.getY() - radio;
                //判断触摸点是否在环上
                if (x * x + y * y <= radio * radio && x * x + y * y >= (radio - mWeight) * (radio - mWeight)) {
                    //根据坐标点求出当前触摸点与x轴的夹角
                    float oldSweepAngle = (float) (Math.atan(y / x) * 180 / Math.PI);
                    if (x > 0)
                        //一四象限需要在原始角度上加90
                        sweepAngle = oldSweepAngle + 90;
                    else {
                        //二三象限需要在原始角度上加270
                        sweepAngle = oldSweepAngle + 270;
                    }
                    invalidate();
                }
                break;
        }
        return true;
    }
}