package com.zero.scrollerdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by randy on 16-3-25.
 */
public class DrawView extends View{
    private Paint mPaint;
    public DrawView(Context context) {
        super(context);
        init();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        setBackgroundResource(R.drawable.katong);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.save();
//        canvas.translate(getScrollX(),getScrollY());
        Rect rect = new Rect(0,0,getWidth()/2,getHeight()/2);
        canvas.drawRect(rect,mPaint);
//        canvas.restore();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mBool) {
            setScrollX(getScrollX() + 10);
            invalidate();
        }
    }
    private boolean mBool = false;
    public void setBool(boolean is) {
        mBool = is;
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }
}
