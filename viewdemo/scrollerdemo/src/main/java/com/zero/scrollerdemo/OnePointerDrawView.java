package com.zero.scrollerdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.core.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by randy on 16-3-29.
 */
public class OnePointerDrawView extends View {
    private final static int INVALID_ID = -1;

    private float mLastX;
    private float mLastY;
    private Paint mPaint;
    private int mTouchSlop;
    private boolean mIsBeingDragged = false;
    public OnePointerDrawView(Context context) {
        super(context);
        init(context);
    }
    public OnePointerDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public OnePointerDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context) {
        mPaint = new Paint(Color.RED);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画几个比较长的长方形
        int width = getWidth();
        int height = getHeight();
        Rect rectMiddle = new Rect(0,0,width,height);
        canvas.drawRect(rectMiddle,mPaint);
//        // left rect
//        canvas.translate(-width,0);
//        mPaint.setColor(Color.GREEN);
//        canvas.drawRect(rectMiddle,mPaint);
//        canvas.restore();

//        //right rect
//        canvas.translate(width,0);
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(rectMiddle,mPaint);
//        canvas.restore();
//
//        // top rect
//        canvas.translate(0,-height);
//        mPaint.setColor(Color.YELLOW);
//        canvas.drawRect(rectMiddle,mPaint);
//        canvas.restore();
//
//        // bottom rect
//        canvas.translate(0,height);
//        mPaint.setColor(Color.RED);
//        canvas.drawRect(rectMiddle,mPaint);
//        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionId = MotionEventCompat.getActionMasked(event);
        switch (actionId) {
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getX();
                mLastY = event.getY();
                mIsBeingDragged = true;
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float curX = event.getX();
                float curY = event.getY();
                int deltaX = (int) (mLastX - curX);
                int deltaY = (int) (mLastY - curY);
                if (!mIsBeingDragged && (Math.abs(deltaX)> mTouchSlop ||
                                                        Math.abs(deltaY)> mTouchSlop)) {
                    mIsBeingDragged = true;
                    // 让第一次滑动的距离和之后的距离不至于差距太大
                    // 因为第一次必须>TouchSlop,之后则是直接滑动
                    if (deltaX > 0) {
                        deltaX -= mTouchSlop;
                    } else {
                        deltaX += mTouchSlop;
                    }
                    if (deltaY > 0) {
                        deltaY -= mTouchSlop;
                    } else {
                        deltaY += mTouchSlop;
                    }
                }
                // 当mIsBeingDragged为true时，就不用判断> touchSlopg啦，不然会导致滚动是一段一段的
                // 不是很连续
                if (mIsBeingDragged) {
                        scrollBy(deltaX, deltaY);
                        mLastX = curX;
                        mLastY = curY;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mIsBeingDragged = false;
                mLastY = 0;
                mLastX = 0;
                break;
            default:
        }
        return mIsBeingDragged;
    }
}
