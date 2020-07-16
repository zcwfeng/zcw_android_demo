package top.zcwfeng.customui.baseui.srl_vp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CustomSRL2 extends SwipeRefreshLayout {
    public CustomSRL2(Context context) {
        super(context);
    }

    public CustomSRL2(Context context, AttributeSet attrs) {
        super(context, attrs);

    }
    private int mLastX, mLastY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 辅助内部拦截
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            super.onInterceptTouchEvent(ev);
            return false;
        }
        return true;

        // 外部拦截
        // 外部拦截法
//        int x = (int) ev.getX();
//        int y = (int) ev.getY();
//
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN: {
//                mLastX = (int) ev.getX();
//                mLastY = (int) ev.getY();
//
//                break;
//            }
//            case MotionEvent.ACTION_MOVE: {
//                int deltaX = x - mLastX;
//                int deltaY = y - mLastY;
//                if (Math.abs(deltaX) > Math.abs(deltaY)) {
//                    return false;
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
//        return super.onInterceptTouchEvent(ev);


    }

    // 反射
//    @Override
//    public void requestDisallowInterceptTouchEvent(boolean b) {
//        Class clazz = ViewGroup.class;
//        try {
//            Field mGroupFlagsField = clazz.getDeclaredField("mGroupFlags");
//            int c = mGroupFlagsField.getInt(this);
//            Log.e("zcw:::" ,"mGroupFlagsField--->" + c);
//
//            mGroupFlagsField.setAccessible(true);
//            if(b){
//                mGroupFlagsField.set(this,2900051);
//            } else {
//                mGroupFlagsField.set(this,2245715);
//
//            }
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        super.requestDisallowInterceptTouchEvent(b);
//    }
}
