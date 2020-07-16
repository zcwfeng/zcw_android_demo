package top.zcwfeng.customui.baseui.dispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class BadViewPager extends ViewPager {

    private int mLastX, mLastY;


    public BadViewPager(@NonNull Context context) {
        super(context);
    }

    public BadViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 演示，结构 ViewPager 包裹》ListView
     * return super.onInterceptHoverEvent(event); google工程师帮我们处理好了冲突
     * return false; 可以上下滑动和左右滑动
     * return true; 只能 上下滑动listview
     *
     * 上面结论在v4管用
     *
     * 然而 androidX 帮我做了更多的兼容
     *
     *
     *
     * @param event
     * @return
     */
    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        // 外部拦截法：父容器处理冲突
        // 我想要把事件分发给谁就分发给谁
//        1.
//        return super.onInterceptHoverEvent(event);
//        2.
//        return false;
//        3.
//        return true;

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            super.onInterceptHoverEvent(event);
            return false;
        }
        return true;



//        int x = (int) event.getX();
//        int y = (int) event.getY();
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN: {
//                mLastX = (int) event.getX();
//                mLastY = (int) event.getY();
//                break;
//            }
//            case MotionEvent.ACTION_MOVE: {
//                int deltaX = x - mLastX;
//                int deltaY = y - mLastY;
//                if (Math.abs(deltaX) > Math.abs(deltaY)) {
//                    return true;
//                }
//                break;
//            }
//            case MotionEvent.ACTION_UP: {
//                break;
//            }
//            default:
//                break;
//        }
//
//        return super.onInterceptTouchEvent(event);
    }



}

