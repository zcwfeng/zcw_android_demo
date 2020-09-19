package com.zero.matrixdemo;

import android.R.color;
import android.R.drawable;
import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region.Op;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class BitmapDrawView extends View {
    
    private Path mClipPath;
    private Paint mPaint;
    private Context mContext;
    private Bitmap mBitmapEx;
    
    private Bitmap mBitmap;
    private int mWidth, mHeight;
    private float mDensity;
    public BitmapDrawView(Context context) {
        super(context);
        init(context);
    }

    public BitmapDrawView(Context context, AttributeSet set) {
        super(context, set);
        init(context);
    }
    private void init(Context context){
        mContext = context;
        mPaint = new Paint();
        mClipPath = new Path();
        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.scene);
        mWidth  = mBitmap.getWidth();
        mHeight = mBitmap.getHeight();
        
//        mBitmapEx = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);

        DisplayMetrics displayMetrics = new DisplayMetrics();   
        displayMetrics = context.getResources().getDisplayMetrics();   
        mDensity = displayMetrics.density;
        
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float x, y;

//        canvas.drawBitmap(mBitmap, 50 * mDensity, 20 * mDensity, mPaint);

        x = 50 * mDensity;
        y = 140 * mDensity;
        mBitmapEx = processRoundBitmap(mBitmap);
        canvas.drawBitmap(mBitmapEx, x, y, mPaint);

//        /* */
//        x = 50 * mDensity;
//        y = 260 * mDensity;
//        canvas.save();
//        mPaint.setAntiAlias(true);
//        canvas.clipRect(x, y, x + mWidth, y + mHeight);
//        canvas.drawBitmap(mBitmap, x, y, mPaint);
//        mClipPath.addOval(new RectF(x, y, x + mWidth, y + mHeight),
//                Path.Direction.CCW);
//
//        canvas.clipPath(mClipPath, Op.DIFFERENCE);
//        canvas.drawColor(Color.WHITE);
//        canvas.restore();

    }
    
    private Bitmap processRoundBitmap(Bitmap bitmap) {
        int x, y;
        int radius;
        Bitmap bitmapEx;
        Canvas canvas;
        Paint paint = new Paint();
        final PorterDuffXfermode mXfermodeSrcIn = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

        bitmapEx = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmapEx);
        radius = bitmapEx.getHeight() / 2;
        x = bitmapEx.getWidth() / 2 - radius;
        y = bitmapEx.getHeight() / 2 - radius;

        paint.setColor(0xFFFFFFFF);
        paint.setAntiAlias(true);
        canvas.drawOval(new RectF(x, y, x + 2 * radius, y + 2 * radius), paint);
        paint.setAntiAlias(false);
        paint.setXfermode(mXfermodeSrcIn);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return bitmapEx;
    }

}
