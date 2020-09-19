package com.zero.matrixdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MyDrawableView extends View {

    TaskClearDrawable mTaskClearDrawable;
    Context mContext;

    public MyDrawableView(final Context context) {
        super(context);
        mContext = context;
    }

    public MyDrawableView(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public MyDrawableView(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public MyDrawableView(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTaskClearDrawable = new TaskClearDrawable(mContext,w,h);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        if(mTaskClearDrawable !=null){
            mTaskClearDrawable.setBounds(0,0,getWidth(),getHeight());
            mTaskClearDrawable.draw(canvas);
        }
    }
}
