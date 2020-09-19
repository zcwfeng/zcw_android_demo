package com.zero.scrollerdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.core.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by randy on 16-3-29.
 */
public class TwoPointerDrawView extends View {
    private final static String TAG = "TEST";
    private final static int INVALID_ID = -1;
    private int mActivePointerId = INVALID_ID;
    private float mLastX=0;
    private float mLastY=0;
    private Paint mPaint;
    private int mTouchSlop;
    private int mSecondaryPointerId = INVALID_ID;
    private float mSecondaryLastX=0;
    private float mSecondaryLastY =0;
    private boolean mIsBeingDragged = false;
    public TwoPointerDrawView(Context context) {
        super(context);
        init(context);
    }

    public TwoPointerDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TwoPointerDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint(Color.BLUE);
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
        // left rect
        canvas.translate(-width,0);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(rectMiddle,mPaint);
        canvas.restore();

        //right rect
        canvas.translate(width,0);
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectMiddle,mPaint);
        canvas.restore();

        // top rect
        canvas.translate(0,-height);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(rectMiddle,mPaint);
        canvas.restore();

        // bottom rect
        canvas.translate(0,height);
        mPaint.setColor(Color.RED);
        canvas.drawRect(rectMiddle,mPaint);
        canvas.restore();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionId = MotionEventCompat.getActionMasked(event);
        switch (actionId) {
            case MotionEvent.ACTION_DOWN:
                int activePointerIndex = MotionEventCompat.getActionIndex(event);
                mActivePointerId = MotionEventCompat.findPointerIndex(event,activePointerIndex);
                mLastX = MotionEventCompat.getX(event,activePointerIndex);
                mLastY = MotionEventCompat.getY(event,activePointerIndex);
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                mIsBeingDragged = true;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                activePointerIndex = MotionEventCompat.getActionIndex(event);
                mSecondaryPointerId = MotionEventCompat.findPointerIndex(event,activePointerIndex);
                mSecondaryLastX = MotionEventCompat.getX(event,activePointerIndex);
                mSecondaryLastY = MotionEventCompat.getY(event,mActivePointerId);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mActivePointerId == INVALID_ID) {
                    break;
                }
                activePointerIndex = MotionEventCompat.findPointerIndex(event,mActivePointerId);
                if (activePointerIndex == -1) {
                    break;
                }
                float curX = MotionEventCompat.getX(event,activePointerIndex);
                float curY = MotionEventCompat.getY(event,activePointerIndex);
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
                // handle secondary pointer move
                if (mSecondaryPointerId != INVALID_ID) {
                    int mSecondaryPointerIndex = MotionEventCompat.findPointerIndex(event, mSecondaryPointerId);
                    mSecondaryLastX = MotionEventCompat.getX(event, mSecondaryPointerIndex);
                    mSecondaryLastY = MotionEventCompat.getY(event, mSecondaryPointerIndex);
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                //判断是否是activePointer up了
                activePointerIndex = MotionEventCompat.getActionIndex(event);
                int curPointerId  = MotionEventCompat.getPointerId(event,activePointerIndex);
                Log.e(TAG, "onTouchEvent: "+activePointerIndex +" "+curPointerId +" activeId"+mActivePointerId+
                                        "secondaryId"+mSecondaryPointerId);
                if (curPointerId == mActivePointerId) { // active pointer up
                    mActivePointerId = mSecondaryPointerId;
                    mLastX = mSecondaryLastX;
                    mLastY = mSecondaryLastY;
                    mSecondaryPointerId = INVALID_ID;
                    mSecondaryLastY = 0;
                    mSecondaryLastX = 0;
                    //重复代码，为了让逻辑看起来更加清晰
                } else{ //如果是secondary pointer up
                    mSecondaryPointerId = INVALID_ID;
                    mSecondaryLastY = 0;
                    mSecondaryLastX = 0;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mIsBeingDragged = false;
                mActivePointerId = INVALID_ID;
                mLastY = 0;
                mLastX = 0;
                break;
            default:
        }
        return true;
    }
}
