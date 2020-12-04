package top.zcwfeng.opengl.widget;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.opengl.GLSurfaceView;

import androidx.camera.core.Preview;
import androidx.lifecycle.LifecycleOwner;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import top.zcwfeng.opengl.filter.CameraFilter;
import top.zcwfeng.opengl.filter.ScreenFilter;
import top.zcwfeng.opengl.record.MediaRecorder;
import top.zcwfeng.opengl.utils.CameraHelper;

// 回调方法在GLThread 里面，是个状态机，只能在重载方法写
// 大小端排序 ByteBuffer
public
class CameraRender implements GLSurfaceView.Renderer, Preview.OnPreviewOutputUpdateListener, SurfaceTexture.OnFrameAvailableListener {
    private CameraView cameraView;
    private CameraHelper cameraHelper;
    // 摄像头的图像  用OpenGL ES 画出来
    private SurfaceTexture mCameraTexure;
    private ScreenFilter screenFilter;
    private CameraFilter cameraFilter;
    private MediaRecorder mRecorder;
    private int[] textures;
    float[] mtx = new float[16];

    public CameraRender(CameraView cameraView) {
        this.cameraView = cameraView;
        LifecycleOwner lifecycleOwner = (LifecycleOwner) cameraView.getContext();
        cameraHelper = new CameraHelper(lifecycleOwner, this);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //创建OpenGL 纹理 ,把摄像头的数据与这个纹理关联
        textures = new int[1];  //当做能在opengl用的一个图片的ID
        mCameraTexure.attachToGLContext(textures[0]);
        // 当摄像头数据有更新回调 onFrameAvailable
        mCameraTexure.setOnFrameAvailableListener(this);
        Context context = cameraView.getContext();
        cameraFilter = new CameraFilter(context);
        screenFilter = new ScreenFilter(context);
        //录制视频的宽、高
        mRecorder = new MediaRecorder(cameraView.getContext(), "/sdcard/a.mp4",
                EGL14.eglGetCurrentContext(),
                480, 640);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        cameraFilter.setSize(width, height);

        screenFilter.setSize(width, height);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //todo 更新纹理
        mCameraTexure.updateTexImage();

        mCameraTexure.getTransformMatrix(mtx);

        cameraFilter.setTransformMatrix(mtx);

        int id = cameraFilter.onDraw(textures[0]);// 这个返回的id就是FBO
        screenFilter.onDraw(id);
        // TODO: 2020/12/4 录制
        mRecorder.fireFrame(id,mCameraTexure.getTimestamp());

    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        //  请求执行一次 onDrawFrame
        cameraView.requestRender();
    }

    @Override
    public void onUpdated(Preview.PreviewOutput output) {
        mCameraTexure = output.getSurfaceTexture();

    }

    public void onSurfaceDestroyed() {
        cameraFilter.release();
        screenFilter.release();
    }

    public void startRecord(float speed) {
        try {
            mRecorder.start(speed);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopRecord() {
        mRecorder.stop();
    }
}
