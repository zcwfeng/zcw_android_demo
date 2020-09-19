package com.zero.myscrollerdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.zero.baseutils.bitmap.BitmapUtils;
import com.zero.myscrollerdemo.R;

public class OnePointerDragView extends View {


    private Paint mPaint;
    private Bitmap mBitmap;

    public OnePointerDragView(Context context) {
        this(context, null);
    }

    public OnePointerDragView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OnePointerDragView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mBitmap = BitmapUtils.getBitmap(getContext(), R.drawable.ic_launcher_background,300,300);
    }


    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
    }
}
