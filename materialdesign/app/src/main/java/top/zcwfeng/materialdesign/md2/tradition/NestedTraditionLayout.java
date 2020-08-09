package top.zcwfeng.materialdesign.md2.tradition;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class NestedTraditionLayout extends LinearLayout {


    private float mLastY;

    private int mTouchSlop;

    int mTopViewHeight = 0;


    public NestedTraditionLayout(Context context) {
        super(context);
    }

    public NestedTraditionLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledPagingTouchSlop();

    }

    public NestedTraditionLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NestedTraditionLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        //外部拦截法
        int action = event.getAction();
        float y = event.getY();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = mLastY -y;
                if(Math.abs(dy) > mTouchSlop){
                    if(isHiddenTop(dy) || isShowTop(dy)){
                        //滚动头部
                        return true;
                    }
                }
                mLastY = y;
               break;
        }
        return super.onInterceptTouchEvent(event);
    }
    private boolean isHiddenTop(float dy){
        return dy > 0 && getScrollY() < mTopViewHeight;
    }

    private boolean isShowTop(float dy){
        return dy <0 && getScrollY() > 0 && !canScrollVertically(-1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = mLastY -y;
                if(Math.abs(dy) > mTouchSlop){
                    scrollBy(0,(int)dy);
                }
                mLastY = y;
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void scrollTo(int x, int y) {
        if(y < 0){
            y = 0;
        }
        if(y > mTopViewHeight){
            y = mTopViewHeight;
        }
        super.scrollTo(x, y);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = getChildAt(0).getMeasuredHeight();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        ViewGroup.LayoutParams layoutParams = getChildAt(2).getLayoutParams();
        layoutParams.height = getMeasuredHeight()- getChildAt(1).getMeasuredHeight();
        getChildAt(2).setLayoutParams(layoutParams);
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

    }
}
