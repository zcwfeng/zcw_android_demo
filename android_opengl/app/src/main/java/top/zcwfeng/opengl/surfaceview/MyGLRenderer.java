package top.zcwfeng.opengl.surfaceview;

import android.opengl.EGLConfig;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {
 
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(1.0f, 0.2f, 0.2f, 1.0f);
    }
 
    public void onDrawFrame(GL10 unused) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config) {
        GLES20.glClearColor(1.0f, 0.2f, 0.2f, 1.0f);

    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        //利用glViewport()设置Screen space的大小，在onSurfaceChanged中回调
        GLES20.glViewport(0, 0, width, height);
    }
}