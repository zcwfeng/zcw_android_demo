package top.zcwfeng.opengl.filter;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import top.zcwfeng.opengl.R;
import top.zcwfeng.opengl.face.Face;

public class BigEyeFilter extends AbstractFrameFilter {
    private FloatBuffer left;
    private FloatBuffer right;
    int left_eye;
    int right_eye;

    public BigEyeFilter(Context context) {
        super(context, R.raw.base_vert, R.raw.bigeye_frag);

        left = ByteBuffer.allocateDirect(8).order(ByteOrder.nativeOrder()).asFloatBuffer();
        right = ByteBuffer.allocateDirect(8).order(ByteOrder.nativeOrder()).asFloatBuffer();
    }

    @Override
    public void initGL(Context context, int vertexShaderId, int fragmentShaderId) {
        super.initGL(context, vertexShaderId, fragmentShaderId);
        left_eye = GLES20.glGetUniformLocation(program, "left_eye");
        right_eye = GLES20.glGetUniformLocation(program, "right_eye");
    }




    @Override
    public void beforeDraw(FilterContext filterContext) {
        super.beforeDraw(filterContext);
        Face face = filterContext.face;

        if (face == null) {
//            Log.e("zcw_opengl","未识别到人脸");
            left.clear();
            left.put(0).put(0).position(0);
            GLES20.glUniform2fv(left_eye, 1, left);

            right.clear();
            right.put(0).put(0).position(0);
            GLES20.glUniform2fv(right_eye, 1, right);
            return;
        }
        Log.e("zcw_opengl","BigEyeFilter 是一个-------人脸");

        float x = face.left_x / face.imgWidth;
        float y = 1.0f - face.left_y / face.imgHeight;

        left.clear();
        left.put(x).put(y).position(0);

        GLES20.glUniform2fv(left_eye, 1, left);


        x = face.right_x / face.imgWidth;
        y = 1.0f - face.right_y / face.imgHeight;

        right.clear();
        right.put(x).put(y).position(0);

        GLES20.glUniform2fv(right_eye, 1, right);


    }
}
