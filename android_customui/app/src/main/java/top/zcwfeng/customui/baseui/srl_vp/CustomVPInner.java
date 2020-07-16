package top.zcwfeng.customui.baseui.srl_vp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

public class CustomVPInner extends ViewPager {

    private float startX;
    private float startY;
    private float x;
    private float y;
    private float deltaX;
    private float deltaY;

    public CustomVPInner(Context context) {
        super(context);
    }

    public CustomVPInner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    // 内部拦截
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                Log.e("内部View-CustomVPInner", "dispatchTouchEvent: Down");
                ViewCompat.setNestedScrollingEnabled(this,true);
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("内部View-CustomVPInner", "dispatchTouchEvent: Move");
                x = ev.getX();
                y = ev.getY();
                deltaX = Math.abs(x - startX);
                deltaY = Math.abs(y - startY);
                if (deltaX < deltaY) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        boolean a = super.dispatchTouchEvent(ev);
        Log.e("内部View-CustomVPInner", "dispatchTouchEvent: a = "+a);
        return a;    }
}
