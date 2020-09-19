package com.zero.toutiaodemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class Clip1View extends View {
    private static final String TAG = "Zero";
    private Paint mPaint;
    private Path mPath;

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3);
        mPath = new Path();
    }

    public Clip1View(final Context context) {
        super(context);
        init();
    }

    public Clip1View(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Clip1View(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public Clip1View(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(Color.BLUE);

        //1. intersect
//        Rect rect = new Rect(0,0,500,500);
//        Log.i(TAG, "rect = " + rect);
//        //取两个区域的相交区域作为最终区域
//        rect.intersect(250,250,750,750);
//        Log.i(TAG, "rect1 = " + rect);
//        canvas.clipRect(rect);
//        canvas.drawColor(Color.GREEN);
        //2. 取的是相交区域最远的左上端点作为新区域的左上端点，而取最远的右下端点作为新区域的右下端点
//        Rect rect = new Rect(0, 0, 500, 500);
//        Log.i(TAG, "rect = " + rect);
//        rect.union(250, 250, 750, 750);
//        Log.i(TAG, "rect1 = " + rect);
//        canvas.clipRect(rect);
//        canvas.drawColor(Color.RED);

        //3. clipPath
//        mPaint.setColor(Color.RED);
//        mPaint.setStyle(Paint.Style.STROKE);
//        // 移动起点至[50,50]
//        mPath.moveTo(50, 50);
//        mPath.lineTo(750, 230);
//        mPath.lineTo(1500, 1000);
//        mPath.lineTo(800, 1100);
//        // 闭合路径
//        mPath.close();
//        // 在原始画布上绘制蓝色
//        canvas.drawColor(Color.BLUE);
//        // 按照路径进行裁剪
//        canvas.clipPath(mPath);
//        // 在裁剪后剩余的画布上绘制红色
//        canvas.drawColor(Color.RED);

        //4. clipOutRect 挖掉的部分
//        canvas.clipOutRect(250,250,750,750);
//        Rect rect = canvas.getClipBounds();
//        Log.i(TAG, "rect = " + rect);
//        canvas.drawColor(Color.GREEN);
        //5. save和restore
//        //这里你看不到任何有关于层的特性，只看到了先画的图形会被放在最下面，
//        // 后画出的图像会叠放在上层
//        //画个蓝色的矩形
//        mPaint.setColor(Color.RED);
//        canvas.drawRect(50, 50, 250, 250, mPaint);
//        // 保存画布
//        canvas.save();
//        //旋转画布30度
//        canvas.rotate(30);
//        //画个蓝色的矩形
//        mPaint.setColor(Color.BLUE);
//        canvas.drawRect(100, 100, 200, 200, mPaint);
//        // 还原画布
//        canvas.restore();
////        如果把Canvas理解成画板，那么我们的“层”就像张张夹在画板上的透明的纸，
////        而这些纸对应到Android则是一个个封装在Canvas中的Bitmap
//        //画个绿色的圆
//        mPaint.setColor(Color.GREEN);
//        canvas.drawCircle(150, 150, 40, mPaint);
        //6. saveLayerXXX
//        mPaint.setColor(Color.RED);
//        canvas.drawRect(50, 50, 250, 250, mPaint);
//
//        canvas.drawLine(0,250,250,250,mPaint);
//        canvas.drawLine(250,0,250,250000000,mPaint);

        // 保存画布
//        canvas.save();
//        canvas.saveLayer(0, 0, 250, 250, null, Canvas.ALL_SAVE_FLAG);
//        canvas.saveLayerAlpha(0, 0, 250, 250, 100, Canvas.ALL_SAVE_FLAG);
//        // 旋转画布
//        canvas.rotate(30);
//        mPaint.setColor(Color.BLUE);
//        canvas.drawRect(100, 100, 200, 200, mPaint);
//        // 还原画布
//        canvas.restore();
//        mPaint.setColor(Color.GREEN);
//        canvas.drawCircle(150, 150, 40, mPaint);

        //7. restoreToCount
//        Log.i(TAG, "id:canvas.getSaveCount() = " + canvas.getSaveCount());
//        // 保存画布，并且得到id01
//        int saveID1 = canvas.save();
//        Log.i(TAG, "id01:canvas.getSaveCount() = " + canvas.getSaveCount());
//        // 旋转画布
//        canvas.rotate(30);
//        // 画红色的矩形
//        mPaint.setColor(Color.RED);
//        canvas.drawRect(50, 50, 250, 250, mPaint);
//
//        // 建立新的层，得到id02
//        int saveID2 = canvas.save();
//        Log.i(TAG, "id02:canvas.getSaveCount() = " + canvas.getSaveCount());
//        // 画蓝色的矩形
//        mPaint.setColor(Color.BLUE);
//        canvas.drawRect(100, 100, 200, 200, mPaint);
//
//        // 还原画布01
//        canvas.restoreToCount(saveID1);
//        Log.i(TAG, "id:canvas.getSaveCount() = " + canvas.getSaveCount());
//
//        // 画绿色圆形
//        mPaint.setColor(Color.GREEN);
//        canvas.drawCircle(150, 150, 40, mPaint);
////        虽然我们在绘制蓝色矩形前新建了一个图层，但蓝色矩形还是随着红色矩形旋转了。
////        而我们调用canvas.restoreToCount(saveID1)后绘制的绿色圆形却出现了正常的位置上，没有进行旋转

        canvas.save();
        canvas.translate(100, 100);
        mPaint.setTextSize(50);
        mPaint.setColor(Color.BLACK);
        String text = "享学课堂体验课";
        Rect textBounds = new Rect();
        mPaint.getTextBounds(text,0,text.length(),textBounds);
        float offsetx = (textBounds.left + textBounds.right)/2;
        float offsety = (textBounds.top + textBounds.bottom)/2;
        canvas.drawText(text,200 -offsetx,200 -offsety,mPaint);
        canvas.restore();
        //剪裁一个圆
        mPaint.setColor(Color.RED);
        canvas.save();
        canvas.translate(100, 100);
        mPath.reset();
//        canvas.clipPath(mPath); // makes the clip empty
        mPath.addCircle(200, 200, 100, Path.Direction.CCW);
        canvas.clipPath(mPath, Region.Op.INTERSECT);
        Rect clipRect = canvas.getClipBounds();
        Log.i(TAG, "clipRect = " + clipRect);

        canvas.drawColor(Color.GREEN);
        canvas.drawText(text,200 -offsetx,200 -offsety,mPaint);
        canvas.restore();

    }
}
