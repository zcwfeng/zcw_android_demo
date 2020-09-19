package com.zero.matrixdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

public class CustomerDrawCanvas extends View {
    private int size;
    private int totalW,totalH;
    private Bitmap bitmap;
    private Paint mPaint;
    private int width;
    private int height;

    public CustomerDrawCanvas(Context context) {
        super(context);
        mPaint = new Paint();
        //解析原图
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.girl);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        size=Math.min(width,height);
    }

    public CustomerDrawCanvas(Context context,  AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        //解析原图
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.girl);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        size=Math.min(width,height);
    }

    public CustomerDrawCanvas(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (heightMode == MeasureSpec.UNSPECIFIED){
            setMeasuredDimension(widthSize, 1000);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        test16(canvas);
//        test15(canvas);
//        test14(canvas);
//        test13(canvas);
//        test12(canvas);
//        test11(canvas);
//        test10(canvas);
//        test9(canvas);
//        test8(canvas);
//        test7(canvas);
//        test6(canvas);
//        test5(canvas);
//        test4(canvas);
//        test3(canvas);
//        test2(canvas);
//        test1(canvas);
    }

    /**
     * 制作圆形图片
     * 方法1：创建一个圆形bitmap作为 dst 真实图片作为 source 使用Xfermode将其重叠部分显示成 dst
     * 方法2：创建一个圆形bitmap作为 source 真实图片作为 dst 使用Xfermode将其重叠部分显示成 source
     * @param canvas
     */
    int x = 0;
    int y = 0;
    private void test16(Canvas canvas) {
        int sc=canvas.saveLayer(0,0,800,1000,mPaint,Canvas.ALL_SAVE_FLAG);
        Bitmap dst = makeCircle();
        canvas.drawBitmap(dst, x, y, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, x, y, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(sc);

    }
    private Bitmap makeCircle(){
        Bitmap bitmap=Bitmap.createBitmap(height,height, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x+height/2, y+height/2, height/2, paint);
        return bitmap;
    }
    private Bitmap mkBitmap(){
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawOval(new RectF(200, 500, 200+width, 500+height), paint);
        return bitmap;

    }
//    private Bitmap mkBitmap(){
//        int radius = 0;
//        if (width <= height) {
//            radius = width / 2;
//        } else {
//            radius = height / 2;
//        }
//
//        Paint paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setDither(true);
//        paint.setColor(Color.WHITE);
//
//        Rect rect = new Rect(0, 0, 2 * radius, 2 * radius);
//        RectF rectF = new RectF(rect);
//        Bitmap desBitmap = Bitmap.createBitmap(2 * radius, 2 * radius, Bitmap.Config.ARGB_4444);
//        Canvas canvas = new Canvas(desBitmap);
//        canvas.drawARGB(0, 0, 0, 0);
//        //绘制下层图
//        canvas.drawRoundRect(rectF, radius, radius, paint);
//        // 设置PorterDuffXfermode模式，取绘制的图的交集部分，显示上层
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        //绘制上层图
//        canvas.drawBitmap(bitmap, rect, rect, paint);
//
//        return desBitmap;
//    }
//


    private void test15(Canvas canvas) {
        int startX = 200;
        int startY = 200;
        int endX = 400;
        int endY = 400;
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setTextSize(30);

        /**
         * 设置View的离屏缓冲。在绘图的时候新建一个“层”，所有的操作都在该层而不会影响该层以外的图像
         * 必须设置，否则设置的PorterDuffXfermode会无效，具体原因不明
         */
        int sc = canvas.saveLayer(0, 0, totalW, totalH, paint, Canvas.ALL_SAVE_FLAG);
        canvas.drawText("Mode： PorterDuff.Mode.CLEAR", 100, 50, paint);
        mPaint.setColor(Color.YELLOW);//DST是黄色圆，SRC是蓝色矩形
        canvas.drawCircle(startX, startY, 100, mPaint);//先画目标

        mPaint.setColor(Color.BLUE);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawRect(startX, startY, endX, endY, mPaint);//再画源
        mPaint.setXfermode(null);
        canvas.restoreToCount(sc);
//--------------------------------------------------------------------------------------------------
        for (int i = 1; i < 16; i++) {
            int sc1 = canvas.saveLayer(0, 0, totalW, totalH, paint, Canvas.ALL_SAVE_FLAG);
            Paint paint1 = getPaint();
            canvas.drawText("Mode： PorterDuff.Mode." + sLabels[i], 100, endY + 100, paint);
            startY = endY + 230;
            endY = endY + 430;
            paint1.setColor(Color.YELLOW);
            canvas.drawCircle(startX, startY, 100, paint1);//目标像素dst

            paint1.setColor(Color.BLUE);
            paint1.setXfermode(sModes[i]);
            canvas.drawRect(startX, startY, endX, endY, paint1);//源像素src
            paint1.setXfermode(null);
            canvas.restoreToCount(sc1);
        }
    }

    private Paint getPaint(){
        Paint paint = new Paint();
        paint.setTextSize(30);
        return paint;
    }
    private void test14(Canvas canvas) {
        mPaint.setAntiAlias(true);
        canvas.drawColor(Color.GRAY);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
//        Bitmap bitmap = Bitmap.createBitmap(this.bitmap);
//        canvas.drawBitmap(bitmap, 200, 200, mPaint);
//        canvas.clipRect(new RectF(200, 200, 200 + width, 200 + height));
//        Path path = new Path();
//        path.addOval(new RectF(200, 200, 200 + width, 200+ height),Path.Direction.CCW);
//        canvas.clipPath(path);
//        canvas.drawBitmap(bitmap, 200, 200, mPaint);
//        canvas.save();
//
//        path.reset();
//        path.addCircle(200+width/2, 200+height/2, Math.min(width/2, height/2) - 50,Path.Direction.CCW );
//        canvas.clipPath(path);
//        canvas.drawColor(Color.GRAY);
//        canvas.save();
//
//        path.reset();
//        path.addCircle(200+width/2, 200+height/2, Math.min(width/2, height/2) - 60,Path.Direction.CCW );
//        canvas.clipPath(path, Region.Op.REPLACE);
//        canvas.drawBitmap(bitmap, 200, 200, mPaint);
//        canvas.save();

//        canvas.drawBitmap(bitmap, 200, 500, mPaint);
//        canvas.save();
//        Bitmap bitmap = processRoundBitmap(width, height);
//        canvas.drawBitmap(bitmap, 200, 500, mPaint);
        Bitmap bitmapEmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        RectF rect = new RectF(200, 500, 200 + width, 500 + height);
        canvas.drawRoundRect(rect, 200, 200, mPaint);
        mPaint.setXfermode(xfermode);
        canvas.drawBitmap(bitmap, 200, 500, mPaint);
    }

    private Bitmap processRoundBitmap(int width, int height) {
        Paint p = new Paint();
        Bitmap bitmapEmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        int radius = bitmapEmp.getHeight()/2;
        Canvas canvas2 = new Canvas(bitmapEmp);
        canvas2.drawOval(new RectF(200, 500, 200+bitmapEmp.getWidth()/2, 500+bitmapEmp.getHeight()/2), p);
//        canvas2.drawOval(new RectF(200, 500, 200+width/2, 500+height/2), p);
        p.setAntiAlias(true);
        p.setXfermode(xfermode);
        canvas2.drawBitmap(bitmap, 0, 0, p);
        return bitmapEmp;
    }

    private void test13(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        canvas.drawCircle(200, 200, 100, mPaint);
        canvas.drawCircle(500, 200, 100, mPaint);
        canvas.drawCircle(200, 500, 100, mPaint);
        canvas.drawCircle(500, 500, 100, mPaint);
        mPaint.setStrokeWidth(1);
        canvas.drawLine(100, 200, 600, 200, mPaint);
        canvas.drawLine(100, 500, 600, 500, mPaint);
        canvas.drawLine(200, 100, 200, 600, mPaint);
        canvas.drawLine(500, 100, 500, 600, mPaint);

//        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(50);
        Rect rect = new Rect();
        mPaint.getTextBounds("aajaa", 0, "aajaa".length(), rect);
        float offset = (rect.top + rect.bottom) / 2;
        System.out.println(offset);
//        canvas.drawText("abjtg", 200, 200, mPaint);
        canvas.drawText("aajaa", 200, 200 - offset, mPaint);

        Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
        mPaint.getFontMetrics(fontMetrics);
        float v = fontMetrics.ascent + fontMetrics.descent;
        canvas.drawText("aajaa", 500, 200+fontMetrics.descent, mPaint);

        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("aajaa", 200, 500-v/2, mPaint);
        canvas.drawText("aajaa", 500, 500 - mPaint.getFontSpacing(), mPaint);

    }

    private void test12(Canvas canvas) {
        canvas.drawArc(new RectF(100, 100, 300, 300), 0, 120, true, mPaint);
    }

    private void test11(Canvas canvas) {
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        Path path = new Path();
        path.moveTo(50, 300);
        path.cubicTo(100, 50, 500, 400, 700, 150);
        canvas.drawPath(path, mPaint);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextScaleX(1.5f);
        mPaint.setTextSize(50);
        canvas.drawTextOnPath("我爱你塞北的雪！", path, 0, 15, mPaint);
    }

    private void test10(Canvas canvas) {
//        canvas.drawText("sdfsfd", 100, 100, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        Path path = new Path();
        path.moveTo(100, 100);
        path.lineTo(300, 300);
        path.lineTo(100, 500);
        mPaint.setPathEffect(new CornerPathEffect(30));
        canvas.drawPath(path, mPaint);
        mPaint.setTextSize(30);
        canvas.drawTextOnPath("we are family! 是的123!! afdads", path, 0, 0, mPaint);
        path = new Path();
        path.moveTo(100, 600);
        path.lineTo(500, 600);
        canvas.drawPath(path, mPaint);
        canvas.drawTextOnPath("32sfdsf!", path, 0, 15, mPaint);
        canvas.drawText("abc11", 0,4, 0, 30, mPaint );
        canvas.save();

        mPaint.setShadowLayer(5, 10, 10, Color.BLUE);
        mPaint.setTextScaleX(1.5f);
        canvas.translate(100, 0);
        canvas.rotate(90);
        canvas.drawText("abc22", 0,4, 0, 0, mPaint );
        mPaint.setColor(Color.RED);
        canvas.save();
        canvas.save();
        canvas.save();
        canvas.save();
        canvas.restore();
        canvas.restore();
        canvas.drawRect(new Rect(0, 50, 100, 100), mPaint);
        canvas.restore();
        canvas.restore();
        canvas.drawText("abc33", 0,4, 0, 100, mPaint );
    }

    private void test9(Canvas canvas) {
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawColor(Color.GRAY);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(350, 350, 50, mPaint);
        canvas.save();

        canvas.clipRect(new Rect(100, 100, 600, 600));
        canvas.drawColor(Color.parseColor("#99ff0000"));
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(new Rect(110, 110, 200, 200), mPaint);
        canvas.save();

        canvas.clipRect(new Rect(150, 150, 350, 350));
        canvas.drawColor(Color.parseColor("#aa000000"));
        canvas.save();

        canvas.clipRect(new Rect(200, 200, 300, 300));
        canvas.drawColor(Color.YELLOW);
        canvas.save();

        canvas.restore();
        canvas.drawColor(Color.parseColor("#aa0000ff"));
        canvas.restore();
        canvas.drawColor(Color.parseColor("#aa0000ff"));
        canvas.restore();
        canvas.drawColor(Color.parseColor("#aa0000ff")); canvas.restore();
        canvas.drawColor(Color.parseColor("#aa0000ff"));
    }

    private void test8(Canvas canvas) {
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawColor(Color.GRAY);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(350, 350, 50, mPaint);
        canvas.save();
        canvas.clipRect(new Rect(100, 100, 600, 600));
        canvas.drawColor(Color.parseColor("#99ff0000"));
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(new Rect(110, 110, 200, 200), mPaint);
        canvas.save();
        canvas.clipRect(new Rect(150, 150, 350, 350));
        canvas.drawColor(Color.parseColor("#aa000000"));
    }

    private void test7(Canvas canvas) {
        mPaint.setAntiAlias(true);
        canvas.drawColor(Color.GRAY);
        canvas.translate(100, 100);
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(new Rect(0, 0, 100, 100), mPaint);

//        canvas.translate(0, 100);
        canvas.scale(0.5f, 0.5f);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(new Rect(0, 0, 100, 100), mPaint);

        canvas.rotate(45);
        canvas.translate(100, 200);
        canvas.drawRect(new Rect(0, 0, 100, 100), mPaint);

        canvas.rotate(-45);
        canvas.scale(2f, 2f);
        canvas.translate(50, 0);
        canvas.skew(-0.707f, 0.707f);
        canvas.drawRect(new Rect(0, 0, 100, 100), mPaint);
        mPaint.setColor(Color.parseColor("#8800ffff"));
        canvas.drawRect(new Rect(0, 0, 50, 50), mPaint);
    }

    private void test6(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#99ff0000"));
        mPaint.setShadowLayer(5, 5, 5, Color.BLUE);
        canvas.drawCircle(150, 100, 50, mPaint);
        canvas.drawRect(new Rect(100, 200, 200, 300), mPaint);
        canvas.drawRect(new Rect(100, 350, 200, 400), mPaint);
        canvas.drawOval(new RectF(100, 450, 200, 500),mPaint);
        mPaint.setPathEffect(new CornerPathEffect(50));
        canvas.drawRect(new Rect(100, 550, 200, 650), mPaint);
        mPaint.setPathEffect(null);
        canvas.drawRoundRect(new RectF(100, 700, 200, 800), 20, 5, mPaint);
        Path path = new Path();
        path.moveTo(150, 850);
        path.lineTo(200, 935);
        path.lineTo(100, 935);
        path.close();
        canvas.drawPath(path, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setShader(new LinearGradient(250, 200, 350, 300, Color.GRAY, Color.RED, Shader.TileMode.MIRROR));
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(300, 100, 50, mPaint);
        mPaint.setShader(null);

        canvas.drawRect(250, 200, 350, 300, mPaint);
//        mPaint.setPathEffect(new CornerPathEffect(25));
//        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(new Rect(250, 350, 350, 400), mPaint);
//        mPaint.setPathEffect(null);
        canvas.drawRoundRect(new RectF(250, 450, 350, 500), 50, 50, mPaint);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawOval(250, 550, 350, 600, mPaint);
        }
        path = new Path();
        path.moveTo(300, 650);
        path.lineTo(250, 735);
        path.lineTo(350, 735);
        canvas.drawPath(path, mPaint);
//        mPaint.setShader(null);
    }

    private void test5(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setPathEffect(new CornerPathEffect(10));
//        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setShadowLayer(10, 20, 20, Color.RED);
        Path path = getPath();
        canvas.translate(10, 200);
        canvas.drawPath(path, mPaint);
    }
    private Path getPath() {
        Path path = new Path();
        path.moveTo(0,0);
        for( int i = 1; i <= 50; i++ ) {
            path.lineTo( 20 * i, (float) (( Math.random() * 50 * i) % 200));
        }
        return path;
    }

    private void test4(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(20);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setShadowLayer(5, 5, 50, Color.BLUE);
        Path path = new Path();
        path.moveTo(200, 200);
        path.lineTo(500, 200);
        path.lineTo(200, 400);
//        path.close();
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawPath(path, mPaint);

        path.moveTo(200, 500);
        path.lineTo(500, 500);
        path.lineTo(200, 700);
        mPaint.setStrokeJoin(Paint.Join.BEVEL);
        canvas.drawPath(path, mPaint);

        path = new Path();
        path.moveTo(200, 800);
        path.lineTo(500, 800);
        path.lineTo(200, 1000);
        mPaint.setStrokeJoin(Paint.Join.MITER);
        canvas.drawPath(path, mPaint);
    }

    private void test3(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(100);
        mPaint.setColor(Color.parseColor("#00ffff"));
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(100, 100, 400, 100, mPaint);

        mPaint.setStrokeCap(Paint.Cap.BUTT);
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(100, 300, 400, 300, mPaint);

        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setColor(Color.RED);
        canvas.drawLine(100, 500, 400, 500, mPaint);
    }

    private void test2(Canvas canvas) {
        mPaint.setAntiAlias(true);
//        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(5);
        mPaint.setARGB(200,0,100,200);
//        mPaint.setPathEffect(new DashPathEffect(new float[] {2f, 2f}, 0f));
//        mPaint.setShader(new LinearGradient(50, 50, 150, 150, Color.GRAY, Color.BLUE, Shader.TileMode.REPEAT));
        mPaint.setShadowLayer(10, 20, 20, Color.RED);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(100, 100, 50, mPaint);
        mPaint.setShadowLayer(10, 20, 20, Color.BLACK);
        canvas.drawLine(100, 300, 500, 300, mPaint);

    }

    private void test1(Canvas canvas) {
        canvas.drawColor(Color.GRAY);
        mPaint.setColor(Color.YELLOW);
        canvas.translate(100, 100);
        canvas.drawRect(new Rect(0, 0, 200, 200), mPaint);

        mPaint.setColor(Color.BLUE);
        canvas.translate(0, 300);
        canvas.scale(0.5f, 0.5f);
        canvas.drawRect(new Rect(0, 0, 200, 200), mPaint);

        canvas.translate(0, 0);
        mPaint.setColor(Color.RED);
        canvas.drawRect(new Rect(0, 0, 100, 100), mPaint);
        canvas.restore();
//        canvas.translate(0, 300);
//        canvas.rotate(30);
//        canvas.drawRect(new Rect(0, 0, 200, 200), mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        totalW=w;
        totalH=h;
    }
    private static final Xfermode[] sModes = {
            new PorterDuffXfermode(PorterDuff.Mode.CLEAR),
            new PorterDuffXfermode(PorterDuff.Mode.SRC),
            new PorterDuffXfermode(PorterDuff.Mode.DST),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
            new PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
            new PorterDuffXfermode(PorterDuff.Mode.DST_IN),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
            new PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
            new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
            new PorterDuffXfermode(PorterDuff.Mode.XOR),
            new PorterDuffXfermode(PorterDuff.Mode.DARKEN),
            new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
            new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
            new PorterDuffXfermode(PorterDuff.Mode.SCREEN)
    };

    private static final String[] sLabels = {
            "Clear", "Src", "Dst", "SrcOver",
            "DstOver", "SrcIn", "DstIn", "SrcOut",
            "DstOut", "SrcATop", "DstATop", "Xor",
            "Darken", "Lighten", "Multiply", "Screen"
    };

}
