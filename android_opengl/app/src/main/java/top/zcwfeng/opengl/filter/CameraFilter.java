package top.zcwfeng.opengl.filter;

import android.content.Context;
import android.opengl.GLES20;

import top.zcwfeng.opengl.R;

public
class CameraFilter extends AbstractFrameFilter {

    private float[] mtx;
    private int vMatrix;

    public CameraFilter(Context context) {
        super(context, R.raw.camera_vert, R.raw.camera_frag);
    }

    @Override
    public void beforeDraw(FilterContext filterContext) {
        super.beforeDraw(filterContext);
        GLES20.glUniformMatrix4fv(vMatrix, 1, false, mtx, 0);

    }

    @Override
    public int onDraw(int texture, FilterChain filterChain) {
        mtx = filterChain.filterContext.cameraMtx;
        return super.onDraw(texture, filterChain);
    }

    @Override
    public void initGL(Context context, int vertexShaderId, int fragmentShaderId) {
        super.initGL(context, vertexShaderId, fragmentShaderId);
        vMatrix = GLES20.glGetUniformLocation(program, "vMatrix");

    }

}
