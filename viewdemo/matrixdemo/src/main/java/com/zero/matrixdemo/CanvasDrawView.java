package com.zero.matrixdemo;

import android.R.color;
import android.R.drawable;
import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region.Op;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class CanvasDrawView extends View {
    private float mDensity;
    private Paint mPaint;

    public CanvasDrawView(Context context) {
        super(context);
        init(context);
    }

    public CanvasDrawView(Context context, AttributeSet set) {
        super(context, set);
        init(context);
    }
    
    private void init(Context context){
        DisplayMetrics displayMetrics = new DisplayMetrics();   
        displayMetrics = context.getResources().getDisplayMetrics();   
        mDensity = displayMetrics.density;
        mPaint = new Paint();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 把整张画布绘制成白色
        canvas.drawColor(Color.WHITE);
        
        // 去锯齿
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Style.STROKE);//画线

        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(15* mDensity);//设置线的宽度
        // 绘制圆形
        canvas.drawCircle(40 * mDensity, 40 * mDensity, 30 * mDensity, mPaint);

        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(1* mDensity);//设置线的宽度
        // 绘制圆形
        canvas.drawCircle(40 * mDensity, 40 * mDensity, 30 * mDensity, mPaint);

        // 绘制正方形
        canvas.drawRect(10 * mDensity, 80 * mDensity, 70 * mDensity, 140 * mDensity, mPaint);

        // 绘制矩形
        canvas.drawRect(10 * mDensity, 150 * mDensity, 70 * mDensity, 190 * mDensity, mPaint);

        // 绘制圆角矩形
        RectF re1 = new RectF(10 * mDensity, 200 * mDensity, 70 * mDensity, 230 * mDensity);
        Rect roundre1 = new Rect((int)(10 * mDensity), (int)(200 * mDensity), (int)(70 * mDensity), (int)(230 * mDensity));
        canvas.drawRect(roundre1,mPaint);
        canvas.drawRoundRect(re1, 15 * mDensity, 15 * mDensity, mPaint);

        // 绘制椭圆
        RectF re11 = new RectF(10 * mDensity, 240 * mDensity, 70 * mDensity, 270 * mDensity);
        canvas.drawOval(re11, mPaint);

        // 根据Path进行绘制，绘制三角形
        Path path1 = new Path();
        path1.moveTo(10 * mDensity, 340 * mDensity);
        path1.lineTo(70 * mDensity, 340 * mDensity);
        path1.lineTo(40 * mDensity, 290 * mDensity);
        path1.close();
        canvas.drawPath(path1, mPaint);

        // 定义一个个Path对象，封闭成五边形
        Path path2 = new Path();
        path2.moveTo(26 * mDensity, 360 * mDensity);
        path2.lineTo(54 * mDensity, 360 * mDensity);
        path2.lineTo(70 * mDensity, 392 * mDensity);
        path2.lineTo(40 * mDensity, 420 * mDensity);
        path2.lineTo(10 * mDensity, 392 * mDensity);
        path2.close();
        // 根据Path进行绘制，绘制五角形
        canvas.drawPath(path2, mPaint);

        RectF re1arc = new RectF(10 * mDensity, 430 * mDensity, 110 * mDensity, 530 * mDensity);
        canvas.drawArc(re1arc,30,225,true,mPaint);

        // ----------设置填充风格----------
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(Color.RED);

        // 绘制圆形
        canvas.drawCircle(120 * mDensity, 40 * mDensity, 30 * mDensity, mPaint);

        // 绘制正方形
        canvas.drawRect(90 * mDensity, 80 * mDensity, 150 * mDensity, 140 * mDensity, mPaint);

        // 绘制矩形
        canvas.drawRect(90 * mDensity, 150 * mDensity, 150 * mDensity, 190 * mDensity, mPaint);

        // 绘制圆角矩形
        RectF re2 = new RectF(90 * mDensity, 200 * mDensity, 150 * mDensity, 230 * mDensity);
        canvas.drawRoundRect(re2, 15 * mDensity, 15 * mDensity, mPaint);

        // 绘制椭圆
        RectF re21 = new RectF(90 * mDensity, 240 * mDensity, 150 * mDensity, 270 * mDensity);
        canvas.drawOval(re21, mPaint);

        // 绘制三角形
        Path path3 = new Path();
        path3.moveTo(90  * mDensity, 340  * mDensity);
        path3.lineTo(150  * mDensity, 340  * mDensity);
        path3.lineTo(120  * mDensity, 290  * mDensity);
        path3.close();
        canvas.drawPath(path3, mPaint);

        // 绘制五角形
        Path path4 = new Path();
        path4.moveTo(106 * mDensity, 360  * mDensity);
        path4.lineTo(134 * mDensity, 360 * mDensity);
        path4.lineTo(150 * mDensity, 392 * mDensity);
        path4.lineTo(120 * mDensity, 420 * mDensity);
        path4.lineTo(90 * mDensity, 392 * mDensity);
        path4.close();
        canvas.drawPath(path4, mPaint);
        
        // ----------设置渐变器后绘制----------
        // 为Paint设置渐变
        Shader mShader = new LinearGradient(0, 0, 40 * mDensity, 60 * mDensity, new int[] {
                Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW }, null,
                Shader.TileMode.MIRROR);

        mShader = new RadialGradient(200 * mDensity, 40 * mDensity,30 * mDensity,Color.RED,Color.YELLOW
                , Shader.TileMode.MIRROR);
        mPaint.setShader(mShader);

        // 绘制圆形
        canvas.drawCircle(200 * mDensity, 40 * mDensity, 30 * mDensity, mPaint);

        // 绘制正方形
        canvas.drawRect(170 * mDensity, 80 * mDensity, 230 * mDensity, 140 * mDensity, mPaint);

        // 绘制矩形
        canvas.drawRect(170 * mDensity, 150 * mDensity, 230 * mDensity, 190 * mDensity, mPaint);

        // 绘制圆角矩形
        RectF re3 = new RectF(170 * mDensity, 200 * mDensity, 230 * mDensity, 230 * mDensity);
        canvas.drawRoundRect(re3, 15 * mDensity, 15 * mDensity, mPaint);

        // 绘制椭圆
        RectF re31 = new RectF(170 * mDensity, 240 * mDensity, 230 * mDensity, 270 * mDensity);
        canvas.drawOval(re31, mPaint);

        // 根据Path进行绘制，绘制三角形
        Path path5 = new Path();
        path5.moveTo(170 * mDensity, 340 * mDensity);
        path5.lineTo(230 * mDensity, 340 * mDensity);
        path5.lineTo(200 * mDensity, 290 * mDensity);
        path5.close();
        canvas.drawPath(path5, mPaint);

        // 根据Path进行绘制，绘制五角形
        Path path6 = new Path();
        path6.moveTo(186 * mDensity, 360 * mDensity);
        path6.lineTo(214 * mDensity, 360 * mDensity);
        path6.lineTo(230 * mDensity, 392 * mDensity);
        path6.lineTo(200 * mDensity, 420 * mDensity);
        path6.lineTo(170 * mDensity, 392 * mDensity);
        path6.close();
        canvas.drawPath(path6, mPaint);

        // ----------设置字符大小----------
        mPaint.setTextSize(24 * mDensity);
        mPaint.setShader(null);
        
        // 绘制7个字符串
        canvas.drawText(getResources().getString(R.string.circle), 240 * mDensity, 50 * mDensity,
                mPaint);
        canvas.drawText(getResources().getString(R.string.square), 240 * mDensity, 120 * mDensity,
                mPaint);
        canvas.drawText(getResources().getString(R.string.rect), 240 * mDensity, 175 * mDensity,
                mPaint);
        canvas.drawText(getResources().getString(R.string.round_rect), 230 * mDensity,
                220 * mDensity, mPaint);
        canvas.drawText(getResources().getString(R.string.oval), 240 * mDensity, 260 * mDensity,
                mPaint);
        canvas.drawText(getResources().getString(R.string.triangle), 240 * mDensity, 325 * mDensity,
                mPaint);
        canvas.drawText(getResources().getString(R.string.pentagon), 240 * mDensity, 390 * mDensity,
                mPaint);
        
    }

}
