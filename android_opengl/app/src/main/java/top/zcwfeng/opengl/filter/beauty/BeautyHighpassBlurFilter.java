package top.zcwfeng.opengl.filter.beauty;

import android.content.Context;
import android.opengl.GLES20;

import top.zcwfeng.opengl.R;
import top.zcwfeng.opengl.filter.AbstractFrameFilter;
import top.zcwfeng.opengl.filter.FilterContext;

public class BeautyHighpassBlurFilter extends AbstractFrameFilter {
    private int widthIndex;
    private int heightIndex;

    public BeautyHighpassBlurFilter(Context context) {
        super(context, R.raw.base_vert, R.raw.beauty_highpass_blur);
    }

    @Override
    public void initGL(Context context, int vertexShaderId, int fragmentShaderId) {
        super.initGL(context, vertexShaderId, fragmentShaderId);
        widthIndex = GLES20.glGetUniformLocation(program, "width");
        heightIndex = GLES20.glGetUniformLocation(program, "height");
    }


    @Override
    public void beforeDraw(FilterContext filterContext) {
        super.beforeDraw(filterContext);
        GLES20.glUniform1i(widthIndex, filterContext.width);
        GLES20.glUniform1i(heightIndex, filterContext.height);
    }

}
