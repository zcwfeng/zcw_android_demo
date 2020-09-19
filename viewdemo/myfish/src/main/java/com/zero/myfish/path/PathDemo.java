package com.zero.myfish.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class PathDemo extends View {

    private Paint mPaint;
    private Path mPath;

    public PathDemo(final Context context) {
        super(context);
        init(context);
    }

    public PathDemo(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
    }

    public PathDemo(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context){
        mPaint = new Paint();
        mPath = new Path();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        //反面教材  mPaint = new Paint();
       mPaint.setStyle(Paint.Style.STROKE);
       mPaint.setColor(Color.RED);
       mPaint.setStrokeWidth(20);

       mPath.moveTo(100,300);
//       mPath.quadTo(200,200,300,300);
//       mPath.quadTo(400,400,500,300);
        mPath.rQuadTo(100,-100,200,0);
        mPath.rQuadTo(200,200,200,0);


       canvas.drawPath(mPath,mPaint);


    }
}
