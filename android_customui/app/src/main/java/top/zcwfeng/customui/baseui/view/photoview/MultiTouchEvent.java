package top.zcwfeng.customui.baseui.view.photoview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import top.zcwfeng.customui.utils.Utils;

/**
 * 最后一个按下的手指处理事件
 */
public class MultiTouchEvent extends View {

    private static final float IMAGE_WIDTH = Utils.dpToPixel(300);
    private Bitmap bitmap;
    private Paint paint;

    // 手指滑动偏移值
    private float offsetX;
    private float offsetY;

    // 按下时的x,y坐标
    private float downX;
    private float downY;

    // 上一次的偏移值
    private float lastOffsetX;
    private float lastOffsetY;

    // 当前按下的pointId
    private int currentPointId;

    public MultiTouchEvent(Context context) {
        this(context, null);
    }

    public MultiTouchEvent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiTouchEvent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bitmap = Utils.getPhoto(getResources(), (int) IMAGE_WIDTH);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()) {
            // 只触发一次，UP也是只触发一次
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                lastOffsetX = offsetX;
                lastOffsetY = offsetY;

                // id
                currentPointId = 0;
                break;

            case MotionEvent.ACTION_MOVE:
                // 通过id 拿index
                int index = event.findPointerIndex(currentPointId);
                // event.getX()默认 index = 0的坐标 --- move操作的是后按下的手指
                offsetX = lastOffsetX + event.getX(index) - downX;
                offsetY = lastOffsetY + event.getY(index) - downY;
                invalidate();
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                // 获取当前按下的index
                int actionIndex = event.getActionIndex();
                // 通过index 拿 id
                currentPointId = event.getPointerId(actionIndex);

                downX = event.getX(actionIndex);
                downY = event.getY(actionIndex);
                lastOffsetX = offsetX;
                lastOffsetY = offsetY;
                break;

            case MotionEvent.ACTION_POINTER_UP:
                int upIndex = event.getActionIndex();
                int pointerId = event.getPointerId(upIndex);
                // 非活跃手指的抬起不用处理
                if (pointerId == currentPointId) {
                    if (upIndex == event.getPointerCount() - 1) {
                        upIndex = event.getPointerCount() - 2;
                    } else {
                        upIndex++;
//                        upIndex = event.getPointerCount() - 1;
                    }
                    currentPointId = event.getPointerId(upIndex);

                    downX = event.getX(upIndex);
                    downY = event.getY(upIndex);
                    lastOffsetX = offsetX;
                    lastOffsetY = offsetY;
                }
                break;
        }

        return true;
    }
}
