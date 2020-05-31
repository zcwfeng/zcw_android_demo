package top.zcwfeng.opengl.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

public class MyGLSurfaceView extends GLSurfaceView implements SurfaceHolder.Callback2 {

//    private final MyGLRenderer mRenderer;
//    private final TriangleRender mMyGLRender3;

    private final BaseRenderer mRenderer;
//    GLThread mGLThread;

    public MyGLSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);


        mRenderer = new TriangleRender();
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);

        // Set the RenderMode
//        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
//        getHolder().addCallback(this);

        this.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        super.surfaceCreated(holder);

//        mGLThread = new GLThread(holder.getSurface());
//        mGLThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        super.surfaceChanged(holder, format, w, h);
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);
//        mGLThread.stopDraw();
//        try {
//            mGLThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    class GLThread extends Thread {

        private Surface mSurface;
        private boolean mRunning = true;
        private Paint mPaint = new Paint();

        GLThread(Surface surface) {
            mSurface = surface;
            mPaint.setColor(Color.RED);
        }

        void stopDraw() {
            mRunning = false;
        }

        @Override
        public void run() {
            super.run();
            try {
                guardedRun();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void guardedRun() throws InterruptedException {
            while (mRunning) {
                Canvas canvas = mSurface.lockCanvas(null);
                canvas.drawColor(Color.WHITE);
                canvas.drawRect(0, 0, 100, 100, mPaint);
                mSurface.unlockCanvasAndPost(canvas);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("zcw", "test");

            }
        }
    }
}