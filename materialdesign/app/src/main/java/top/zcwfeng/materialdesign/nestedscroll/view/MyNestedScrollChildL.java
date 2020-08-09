package top.zcwfeng.materialdesign.nestedscroll.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;

public class MyNestedScrollChildL extends LinearLayout implements NestedScrollingChild {

    private static String Tag = "Zero";

    private NestedScrollingChildHelper helper;

    public MyNestedScrollChildL(Context context) {
        super(context);
        init(context);
    }

    public MyNestedScrollChildL(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyNestedScrollChildL(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyNestedScrollChildL(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        helper = new NestedScrollingChildHelper(this);
        helper.setNestedScrollingEnabled(true);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        helper.setNestedScrollingEnabled(enabled);
        Log.i(Tag, "setNestedScrollingEnabled:" + enabled);
    }

    int realHeight = 0;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        realHeight = 0;
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        for(int i = 0; i < getChildCount(); i++){
            View view = getChildAt(i);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec),MeasureSpec.UNSPECIFIED);
            measureChild(view,widthMeasureSpec,heightMeasureSpec);
            Log.i("child: onMeasure","getMeasuredHeight: " + view.getMeasuredHeight());
            realHeight += view.getMeasuredHeight();
        }
        Log.i("child: onMeasure","realHeight: " + realHeight);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.getSize(heightMeasureSpec));
    }

    /**
     * 是否可以嵌套滑动
     *
     * @return
     */
    @Override
    public boolean isNestedScrollingEnabled() {
        Log.i(Tag, "isNestedScrollingEnabled");
        return helper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return helper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        Log.i(Tag, "stopNestedScroll");
        helper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        Log.i(Tag, "hasNestedScrollingParent");
        return helper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, int[] offsetInWindow) {
        Log.i(Tag, "dispatchNestedScroll:dxConsumed:" + dxConsumed + "," +
                "dyConsumed:" + dyConsumed + ",dxUnconsumed:" + dxUnconsumed + ",dyUnconsumed:" +
                dyUnconsumed + ",offsetInWindow:" + offsetInWindow);
        return helper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return helper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return helper.dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        Log.i(Tag, "dispatchNestedPreScroll:dx" + dx + ",dy:" + dy + ",consumed:" + consumed[1] + ",offsetInWindow:" + offsetInWindow);
        return helper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    private int mLastTouchX;
    private int mLastTouchY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int[] consumed = new int[2];//[0]:x,[1]:y
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                mLastTouchY = (int)(event.getRawY() + .5f);
                int nestedScrollAxis = ViewCompat.SCROLL_AXIS_NONE;

                nestedScrollAxis |= ViewCompat.SCROLL_AXIS_HORIZONTAL;
                startNestedScroll(nestedScrollAxis);
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                int x = (int)(event.getRawX() + .5f);
                int y = (int)(event.getRawY() + .5f);
                int dx = mLastTouchX -x;
                int dy = mLastTouchY -y;
                mLastTouchX = x;
                mLastTouchY = y;

                if(dispatchNestedPreScroll(dx,dy,consumed,null)){
                    Log.i("onMeasure","dy: " + dy + ", cosumed: " + consumed[1]);
                    dy -= consumed[1];
                    if(dy == 0){
                        Log.i("onMeasure","dy: " + dy);
                        return true;
                    }
                }else{
                    Log.i("onMeasure","scrollBy: " + dy);
                    scrollBy(0,dy);
                }
                break;
            }

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                stopNestedScroll();
        }
        return true;
    }

    @Override
    public void scrollTo(int x, int y) {
        Log.i("onMeasure","y: " + y + ", getScrollY: " + getScrollY() + ", height: " + getHeight() + ", realHeight: " + realHeight+ ", -- " + (realHeight -getHeight()));
        if (y < 0)
        {
            y = 0;
        }
        if (y > realHeight)
        {
            y = realHeight;
        }
        if ( y != getScrollY())
        {
            Log.e("onMeasure","scrollTo: " + y);
            super.scrollTo(x, y);
        }
    }
}
