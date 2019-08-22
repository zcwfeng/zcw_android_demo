package top.zcwfeng.flowlayout.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Flowlayout extends ViewGroup {

    List<List<View>> mAllViews = new ArrayList<>();
    List<Integer> mLineHeight = new ArrayList<>();


    private int maxLines;

    // 因为系统同样有maxLines属性，我们不想在定义新的，可以直接用系统的android:maxLines
    private static final int[] ll = new int[]{android.R.attr.maxLines};

    public Flowlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 自定义普通写法,xml,对应自定义app:maxLines
//        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.FlowLayout);
//        maxLines = a.getInt(R.styleable.FlowLayout_maxLines,Integer.MAX_VALUE);
//        a.recycle();

        // 和系统公用一个属性名字
        TypedArray a = context.obtainStyledAttributes(attrs,ll);
        maxLines = a.getInt(0, Integer.MAX_VALUE);
        a.recycle();

        Log.d("zcw","maxLines==" + maxLines);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 摆放
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int lineNums = mLineHeight.size();
        for(int i=0;i<lineNums;i++) {
            int lineHeight = mLineHeight.get(i);
            List<View> lineViews = mAllViews.get(i);
            for(int j=0; j<lineViews.size();j++) {
                View child = lineViews.get(j);

                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                //l,t,r,b
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth() + lp.rightMargin                                               ;
                int bc = tc + child.getMeasuredHeight() + lp.bottomMargin;

                child.layout(lc,tc,rc,bc);

                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;

            }

            left = getPaddingLeft();
            top += lineHeight;
        }
    }

    // 保证如果以后没有传入LayoutParams的情况
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    }


    // 手动inflate或者setContentView
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    // addView
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    // add View
    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }

    /**
     * 1. 思考容器的场景
     * 2. flowlayout
     * 宽度：一定是确定的，这样子view才有参考
     * 高度：wrapcontent,exactly,unspec
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mAllViews.clear();
        mLineHeight.clear();
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        //widthMeasureSpec
        //建议宽度 + mode
        //1. 如300dp + mode （确切的值 + mode）
        //2. parent_width + at_most（只要不超过我父容器的宽度）
        //3. 0,parent_width + unspecified(ScrollView,ListView 等滚动控件，子控件随便造，父控件时可以滑动的，根据自己需要计算宽度)
        //4. 获取宽度时间上传入 widthMeasureSpec 根据int按位取获得的size和mode
        // heightMeasureSpec 同理

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
//        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);

        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int lineWidth = 0;
        int lineHeight = 0;
        int height = 0;

        List<View> lineView = new ArrayList<>();
        int cCount = getChildCount();
        for(int i=0;i<cCount; i++) {
           View child =  getChildAt(i);
           // child 也要确定高度
           // child mode + size
           // 1. xml 里面写的 300dp,wrap_content,mathch_parent
           // 2. 父控件的mode

            //判断Gone的情况
            if(child.getVisibility() == View.GONE) {
                continue;
            }


           measureChild(child,widthMeasureSpec,heightMeasureSpec);

           MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();

           int cWidth = child.getMeasuredWidth() +lp.leftMargin + lp.rightMargin;
           int cHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

           // 换行逻辑 宽度是否大于最大宽度
            if(lineWidth + cWidth > sizeWidth -(getPaddingLeft() + getPaddingRight())) {
                // 换行处理
                height += lineHeight;

                mLineHeight.add(lineHeight);
                mAllViews.add(lineView);
                lineView = new ArrayList<>();
                lineView.add(child);

                //重置
                lineWidth = cWidth;
                lineHeight = cHeight;


            } else {
                // 未换行
                lineWidth += cWidth;
                lineHeight = Math.max(lineHeight,cHeight);
                lineView.add(child);

            }

            if(i == cCount - 1) {
                height += lineHeight;
                mLineHeight.add(lineHeight);
                mAllViews.add(lineView);

            }

        }

        if(maxLines < mLineHeight.size())
        {
            height = getMaxLinesHeight();
        }

        // 判断一下mode height 可以前移优化
        if(modeHeight == MeasureSpec.EXACTLY) {
            height = sizeHeight;
        } else if(modeHeight == MeasureSpec.AT_MOST) {
            height = Math.min(sizeHeight,height);
            height = height + getPaddingTop() + getPaddingBottom();

        } else if(modeHeight == MeasureSpec.UNSPECIFIED) {
            height = height + getPaddingTop() + getPaddingBottom();
        }

        setMeasuredDimension(sizeWidth,height);
    }

    private int getMaxLinesHeight() {
        int height = 0;
        for(int i=0;i<maxLines;i++){
            height += mLineHeight.get(i);
        }
        return height;
    }


}
