package com.zero.flowlayoutdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

/**
 * @createTime: 2019-10-09
 * @author: lady_zhou
 * @Description: 流式布局
 * <p>
 * 流程：
 * 1、自定义属性：声明，设置，解析获取自定义值
 * 2、测量：在onMeasure MeasureSpec.AT_MOST/EXACTLY,自身的宽高/child的宽高
 * 3、布局：在onLayout方法里面根据自己规划来确定children的位置
 * 4、绘制：onDraw
 * 5、处理LayoutParams
 * 6、触摸反馈：滑动事件
 */
public class FlowLayout2 extends ViewGroup {
    private static final String TAG = "Zero";
    private List<View> lineViews;//每一行的子View
    private List<List<View>> views;//所有的行  一行一行的存储
    private List<Integer> heights;//每一行的高度

    public FlowLayout2(Context context) {
        this(context, null);
    }

    public FlowLayout2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public FlowLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heighMode = MeasureSpec.getMode(heightMeasureSpec);
        int heighSize = MeasureSpec.getSize(heightMeasureSpec);

        //记录当前行的宽度和高度
        int lineWidth = 0;//宽度是当前行子View的宽度之和
        int lineHeight = 0;//高度是当前行所有子View中高度的最大值

        //整个流式布局的
        int flowlayoutWidth = 0;//所有行中宽度的最大值
        int flowlayoutHeight = getPaddingTop() + getPaddingBottom();//所有行的高度的累加

        //初始化参数列表
        init();

        int marginTop = 0;
        int marginLeft = 0;
        int marginRight = 0;
        int marginBottom = 0;

        //遍历所有子View，对子View进行测量，分配到具体行
        int childCount = getChildCount();
        int count = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            //测量子View 获取到当前子View的测量的宽度/高度
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //测量Margin间距
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            marginLeft = Math.max(0, layoutParams.leftMargin);
            marginRight = Math.max(0, layoutParams.rightMargin);
            marginTop = Math.max(0, layoutParams.topMargin);
            marginBottom = Math.max(0, layoutParams.bottomMargin);

            //获取到当前子View的测量的宽度/高度
            int childWidth = child.getMeasuredWidth() + marginLeft + marginRight;
            int childHeight = child.getMeasuredHeight() + marginTop + marginBottom;

            //看下当前的行的剩余的宽度是否可以容纳下一个子View,
            // 如果放不下，换行 保存当前行的所有子View,累加行高，当前的宽度，高度 置零
            Log.i(TAG, "childView = " + ((TextView)child).getText());
            if (lineWidth + childWidth > widthSize - getPaddingRight() - getPaddingLeft()) {//就得换行
                count++;
                views.add(lineViews);
                lineViews = new ArrayList<>();//创建新的一行
                flowlayoutWidth = Math.max(flowlayoutWidth, lineWidth);
                flowlayoutHeight += lineHeight;
                Log.i(TAG, "换行 flowlayoutHeight = " + flowlayoutHeight +" 第： " + count + "行");
                heights.add(lineHeight);
                lineWidth = 0;
                lineHeight = 0;

            }
            lineViews.add(child);
            lineWidth += childWidth;
            lineHeight = Math.max(lineHeight, childHeight);

            //最后一行
            if (i == childCount - 1) {
                flowlayoutWidth = Math.max(flowlayoutWidth, lineWidth);
                flowlayoutHeight += lineHeight;
                Log.i(TAG, "最后一个 flowlayoutHeight = " + flowlayoutHeight);
                heights.add(lineHeight);
                views.add(lineViews);
            }
            Log.i(TAG, "i =========================== " + i);
        }
        //FlowLayout最终宽高
        Log.i(TAG, "最后结果 flowlayoutHeight = " + flowlayoutHeight);
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize + getPaddingLeft() + getPaddingRight() : flowlayoutWidth
                , heighMode == MeasureSpec.EXACTLY ? heighSize + getPaddingTop() + getPaddingBottom() : flowlayoutHeight);

    }

    private void init() {
        views = new ArrayList<>();
        lineViews = new ArrayList<>();
        heights = new ArrayList<>();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int lineCount = views.size();
        int currX = 0 + getPaddingLeft();
        int currY = 0 + getPaddingTop();
        for (int i = 0; i < lineCount; i++) {//大循环，所有的子View 一行一行的布局
            List<View> lineViews = views.get(i);//取出一行
            int lineHeight = heights.get(i);//取出这一行的高度值
            //遍历当前行的子View
            int size = lineViews.size();//获取到当前行的子view个数
            for (int j = 0; j < size; j++) {
                View child = lineViews.get(j);
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();

                int left = currX + layoutParams.leftMargin;
                int top = currY + layoutParams.topMargin;
                int right = left + child.getMeasuredWidth();
                int bottom = top + child.getMeasuredHeight();

                child.layout(left, top, right, bottom);
                //确定下一个view的left
                currX += child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin + getPaddingLeft();
            }
            currX = 0 + getPaddingLeft();
            currY += lineHeight;
        }
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }


    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return super.checkLayoutParams(p) && p instanceof LayoutParams;
    }

    public static class LayoutParams extends MarginLayoutParams {
        public int gravity = -1;


        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);

            TypedArray typedArray = c.obtainStyledAttributes(attrs, R.styleable.FlowLayout_Layout);
            try {
                gravity = typedArray.getInt(R.styleable.FlowLayout_Layout_android_layout_gravity, -1);
            } finally {
                typedArray.recycle();
            }

        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
