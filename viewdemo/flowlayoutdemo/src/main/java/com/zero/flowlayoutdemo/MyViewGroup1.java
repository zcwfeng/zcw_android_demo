package com.zero.flowlayoutdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyViewGroup1 extends ViewGroup {
private static final String TAG = "Zero";
    private List<View> lineViews;//每一行的子View
    private List<List<View>> views;//所有的行 一行一行的存储
    private List<Integer> heights;//每一行的高度


    public MyViewGroup1(Context context) {
        super(context);
    }

    public MyViewGroup1(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public MyViewGroup1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        views = new ArrayList<>();
        lineViews = new ArrayList<>();
        heights = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 测量自己的尺寸
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 为每个子view计算测量的限制信息 Mode/size
        int widthMod = MeasureSpec.getMode(widthMeasureSpec);
        int heightMod = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int lineWidth = 0;// 当前行宽度是当前行子view的宽度之和
        int lineHeight = 0; // 当前行高度是当前行所有子View中高度的最大值

        int layoutWidth = 0; // 所有行中宽度的最大值
        int layoutHeight = getPaddingTop() + getPaddingBottom(); // 所有行中高度的累加
        init();

        int childCount = getChildCount();
        int count=0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 测量子View的宽高
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            // 获取到当前子View的测量的宽度/高度
            int childWight = childView.getMeasuredWidth();
            int childheight = childView.getMeasuredHeight();
            // 当前的行剩余宽度是否可以容纳下一个子View，
            // 如果放的下不换行，如果放下下换行，保存当前的所有子view，累加行高，
            // 当前行的宽度和高度置零
            Log.i(TAG, "childView = " + ((TextView)childView).getText());
            if ((lineWidth + childWight) > widthSize - getPaddingRight() - getPaddingLeft()) { // 换行处理
                count++;
                views.add(lineViews);
                lineViews = new ArrayList<>(); // 创建新的一行
                layoutWidth = Math.max(layoutWidth, lineWidth);
                Log.i(TAG, "换行 lineHeight = " + lineHeight + " , layoutHeight = " + layoutHeight);
                layoutHeight +=lineHeight; // 累加高度
                Log.i(TAG, "换行 flowlayoutHeight = " + layoutHeight+" 第： " + count + "行");
                heights.add(lineHeight);
                lineHeight = 0;
                lineWidth = 0;
            }
            // 不换行
            lineViews.add(childView);
            lineWidth += childWight;
            lineHeight = Math.max(lineHeight, childheight);
            Log.i(TAG, "lineHeight = " + lineHeight);
            if (i == childCount - 1) {
                layoutWidth = Math.max(layoutWidth, lineWidth);
                layoutHeight += lineHeight;
                Log.i(TAG, "最后一个 flowlayoutHeight = " + layoutHeight);
                heights.add(lineHeight);
                views.add(lineViews);
            }
            Log.i(TAG, "i =========================== " + i);
        }
        // 最终宽高
        Log.i(TAG, "最后结果 flowlayoutHeight = " + layoutHeight);
        setMeasuredDimension(widthMod == MeasureSpec.EXACTLY ? widthSize : layoutWidth,
                heightMod == MeasureSpec.EXACTLY ? heightSize + getPaddingTop() + getPaddingBottom() : layoutHeight);

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int lineCount = views.size(); // 获取行数
        int currx = 0;
        int curry = 0;

        for (int i = 0; i < lineCount; i++) {
            List<View> lineViews = views.get(i); // 取出一行
            int lineHeight = heights.get(i); // 取出这一行的高度值
            // 遍历当前行的子View
            int size = lineViews.size();
            for (int j = 0; j < size; j++) {
                View child = lineViews.get(j);
                int left = currx;
                int top = curry;
                int right = left + child.getMeasuredWidth();
                int bottom = top + child.getMeasuredHeight();
                // 确定子view的位置
                child.layout(left, top, right, bottom);
                // 确定下一个View的left
                currx += child.getMeasuredWidth();
            }
            // 重置另外一行的高度和left
            curry += lineHeight;
            currx = 0;
        }
    }

    /**
     * 检查layoutParams参数是否合法
     *
     * @param p
     * @return
     */
    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MyParams;
    }

    // 对传入的LayoutParams进行转化
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MyParams(getContext(), attrs);
    }

    // 对传入的LayoutParams进行转化
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MyParams(p);
    }

    // 生成默认的LayoutParams
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MyParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    public static class MyParams extends MarginLayoutParams {
        private int gravity = -1;

        public MyParams(Context c, AttributeSet attrs) {
            super(c, attrs);

        }

        public MyParams(int width, int height) {
            super(width, height);
        }

        public MyParams(MarginLayoutParams source) {
            super(source);
        }

        public MyParams(LayoutParams source) {
            super(source);
        }


    }
}
