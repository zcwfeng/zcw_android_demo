package com.zero.matrixdemo;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

public class MatrixsView extends View {

    private Context mContext;
    private Paint paint;

    private Bitmap mBgBitmap;
    private int mWidth, mHeight;
    private float mPaintWidth;

    public MatrixsView(Context context) {
        super(context);
        init(context);
    }

    public MatrixsView(Context context, AttributeSet set) {
        super(context, set);
        init(context);

    }

    private void init(Context context) {
        mContext = context;
        paint = new Paint();

        mBgBitmap = ((BitmapDrawable) mContext.getResources().getDrawable(
                R.drawable.girl)).getBitmap();
        mWidth = mBgBitmap.getWidth();
        mHeight = mBgBitmap.getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        Matrix matrix = new Matrix();

        /**/
        // 定义矩阵对象
        float[] values = { 0.707f, -0.707f, 300f,
                           0.707f, 0.707f , 300f,
                           0.0f  , 0.0f   , 1.0f };
        matrix.setValues(values);
//
        matrix.reset();
//
//        matrix.setTranslate(200f, 200f);
//
//        matrix.setRotate(45);//顺时针为正，逆时针为负,
//
//        matrix.setRotate(45, mWidth / 3.0f, mHeight / 2.0f);
//
//        matrix.setScale(2.0f, 2.0f);
//
//        matrix.setScale(2.0f, 2.0f, mWidth / 2.0f, mHeight / 2.0f);
//
//        matrix.setSkew(-0.707f, 0.707f);
//
//        matrix.setSkew(0.5f, -0.5f, mWidth / 2.0f, mHeight / 2.0f);

//        canvas.drawBitmap(mBgBitmap, matrix, paint);
//
//
//
//        /**/
//        // 先旋转后平移
//        matrix.reset();
//        matrix.postRotate(45);// center(0,0)
//        matrix.postTranslate(300, 200);
//        matrix.reset();
//        matrix.preTranslate(300,200);
//        matrix.preRotate(45);
//        canvas.drawBitmap(mBgBitmap, matrix, paint);
//
//        /**/
//        // 先平移后旋转
//        matrix.reset();
//        matrix.postTranslate(300, 200);
//        matrix.postRotate(45);// center(100,100)
//        canvas.drawBitmap(mBgBitmap, matrix, paint);
//
//
//        /* */
//        // 平移-旋转-缩放-仿射
//        matrix.reset();
//        matrix.postTranslate(200, 200);
//        matrix.postRotate(45,300,300);// center(300,300)
//        // M' = S(sx, sy, px,py) * M
//        matrix.postScale(2.0f, 2.0f, 0.0f, 0.0f);
//        matrix.postSkew(0.2f,0.2f,0.0f, 0.0f);
//        canvas.drawBitmap(mBgBitmap, matrix, paint);
//
//
//        /*  */
//        // 先旋转后平移
//        matrix.reset();
//        matrix.postRotate(45);// center(0,0)
//        matrix.postTranslate(300, 300);
//        canvas.drawBitmap(mBgBitmap, matrix, paint);
//
//
//
//        matrix.reset();
//        // M' = M * R(degrees, px, py)
//        matrix.preTranslate(300, 300);
//        matrix.preRotate(45f, 0.0f, 0.0f);
//        canvas.drawBitmap(mBgBitmap, matrix, paint);
//
//
        //postRotate(30, -300, -600)方法分解
        paint.setColor(Color.RED);
        canvas.drawCircle(600, 600, 20, paint);
//
        matrix.reset();
        matrix.postTranslate(600, 600);
        matrix.postRotate(30, 300, 600);
        canvas.drawBitmap(mBgBitmap, matrix, paint);


        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                1,0,0,0,0,//R
                0,1,0,0,100,//G
                0,0,1,0,0,//B
                0,0,0,1,0,//A
        });
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        matrix.reset();
        matrix.postTranslate(600, 600);
        matrix.postTranslate(-300, -600);
        matrix.postRotate(30);
        matrix.postTranslate(300, 600);
        canvas.drawBitmap(mBgBitmap, matrix, paint);


    }
}
