package top.zcwfeng.customui.baseui.view.photoview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.Nullable;

import top.zcwfeng.customui.utils.Utils;

public class PhotoView extends View {
    private static final float IMAGE_WIDTH = Utils.dpToPixel(300);
    private Bitmap bitmap;
    private Paint paint;

    float originalOffsetX;
    float originalOffsetY;

    private float smallScale;
    private float bigScale;

    private float currentScale;

    private float OVER_SCALE_FACTOR = 1.5f;

    private GestureDetector gestureDetector;

    private boolean isEnlarge;

    private ObjectAnimator scaleAnimator;

    private float offsetX;
    private float offsetY;

    private OverScroller overScroller;

    private ScaleGestureDetector scaleGestureDetector;

    public PhotoView(Context context) {
        this(context, null);
    }

    public PhotoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        bitmap = Utils.getPhoto(getResources(), (int) IMAGE_WIDTH);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gestureDetector = new GestureDetector(context, new PhotoGestureDetector());
        // 关闭长按响应
//        gestureDetector.setIsLongpressEnabled(false);

        overScroller = new OverScroller(context);

        scaleGestureDetector = new ScaleGestureDetector(context, new PhotoScaleGestureListener());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float scaleFaction = (currentScale - smallScale) / (bigScale - smallScale);
        // 当前放大比例为small时，scaleFaction = 0，不偏移
        canvas.translate(offsetX * scaleFaction, offsetY * scaleFaction);

        canvas.scale(currentScale, currentScale, getWidth() / 2f, getHeight() / 2f);

        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        originalOffsetX = (getWidth() - bitmap.getWidth()) / 2f;
        originalOffsetY = (getHeight() - bitmap.getHeight()) / 2f;

        if ((float) bitmap.getWidth() / bitmap.getHeight() > (float) getWidth() / getHeight()) {
            // smallScale 是宽全屏
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight() * OVER_SCALE_FACTOR;
        } else {
            smallScale = (float) getHeight() / bitmap.getHeight();
            bigScale = (float) getWidth() / bitmap.getWidth() * OVER_SCALE_FACTOR;
        }
        currentScale = smallScale;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 响应事件以双指缩放优先
        boolean result = scaleGestureDetector.onTouchEvent(event);
        if (!scaleGestureDetector.isInProgress()) {
            result = gestureDetector.onTouchEvent(event);
        }

        return result;
    }

    class PhotoGestureDetector extends GestureDetector.SimpleOnGestureListener {

        // up 触发，单击或者双击的第一次会触发 --- up时，如果不是双击的第二次点击，不是长按，则触发
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return super.onSingleTapUp(e);
        }

        // 长按 -- 默认300ms触发
        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        /**
         * 滚动 -- move
         *
         * @param e1        手指按下
         * @param e2        当前的
         * @param distanceX 旧位置 - 新位置
         * @param distanceY
         * @return
         */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (isEnlarge) {
                offsetX -= distanceX;
                offsetY -= distanceY;
                fixOffsets();
                invalidate();
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        /**
         * up 惯性滑动 -- 大于50dp/s
         *
         * @param velocityX x轴方向运动速度（像素/s）
         * @param velocityY
         * @return
         */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (isEnlarge) {
                overScroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                        -(int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                        (int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                        -(int) (bitmap.getHeight() * bigScale - getHeight()) / 2,
                        (int) (bitmap.getHeight() * bigScale - getHeight()) / 2,
                        300, 300);
                postOnAnimation(new FlingRunner());
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }

        // 延时100ms触发 -- 处理点击效果
        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
        }

        // 只需要关注 onDown 的返回值即可
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        // 双击的第二次点击down时触发。双击的触发时间 -- 40ms -- 300ms
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            isEnlarge = !isEnlarge;
            if (isEnlarge) {
                offsetX = (e.getX() - getWidth() / 2f) -
                        (e.getX() - getWidth() / 2f) * bigScale / smallScale;
                offsetY = (e.getY() - getHeight() / 2f) -
                        (e.getY() - getHeight() / 2f) * bigScale / smallScale;
                fixOffsets();
                getScaleAnimation().start();
            } else {
                getScaleAnimation().reverse();
            }
            return super.onDoubleTap(e);
        }

        // 双击的第二次down、move、up 都触发
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return super.onDoubleTapEvent(e);
        }

        // 单击按下时触发，双击时不触发，down，up时都可能触发
        // 延时300ms触发TAP事件
        // 300ms以内抬手 -- 才会触发TAP -- onSingleTapConfirmed
        // 300ms 以后抬手 --- 不是双击不是长按，则触发
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }
    }

    class FlingRunner implements Runnable {

        @Override
        public void run() {
            if (overScroller.computeScrollOffset()) {
                offsetX = overScroller.getCurrX();
                offsetY = overScroller.getCurrY();
                invalidate();
                // 下一帧动画的时候执行
                postOnAnimation(this);
            }
        }
    }

    private void fixOffsets() {
        offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetX = Math.max(offsetX, -(bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) / 2);
        offsetY = Math.max(offsetY, -(bitmap.getHeight() * bigScale - getHeight()) / 2);
    }

    private ObjectAnimator getScaleAnimation() {
        if (scaleAnimator == null) {
            scaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", 0);
        }
        scaleAnimator.setFloatValues(smallScale, bigScale);
        return scaleAnimator;
    }

    public float getCurrentScale() {
        return currentScale;
    }

    public void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
        invalidate();
    }

    class PhotoScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {

        float initScale;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if ((currentScale > smallScale && !isEnlarge)
                    || (currentScale == smallScale && !isEnlarge)) {
                isEnlarge = !isEnlarge;
            }
            // 缩放因子
            currentScale = initScale * detector.getScaleFactor();
            invalidate();
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            initScale = currentScale;
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }
}
