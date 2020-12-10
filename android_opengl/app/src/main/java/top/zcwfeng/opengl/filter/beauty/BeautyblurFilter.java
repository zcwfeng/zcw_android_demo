package top.zcwfeng.opengl.filter.beauty;

import android.content.Context;
import android.opengl.GLES20;

import top.zcwfeng.opengl.R;
import top.zcwfeng.opengl.filter.AbstractFrameFilter;
import top.zcwfeng.opengl.filter.FilterContext;


public class BeautyblurFilter extends AbstractFrameFilter {
    private int texelWidthOffset;
    private int texelHeightOffset;
    private float mTexelWidth;
    private float mTexelHeight;

    public BeautyblurFilter(Context context) {
        super(context, R.raw.base_vert, R.raw.beauty_blur);
    }

    @Override
    public void initGL(Context context, int vertexShaderId, int fragmentShaderId) {
        super.initGL(context, vertexShaderId, fragmentShaderId);
        texelWidthOffset = GLES20.glGetUniformLocation(program, "texelWidthOffset");
        texelHeightOffset = GLES20.glGetUniformLocation(program, "texelHeightOffset");
    }

    @Override
    public void beforeDraw(FilterContext filterContext) {
        super.beforeDraw(filterContext);
        GLES20.glUniform1f(texelWidthOffset, mTexelWidth);
        GLES20.glUniform1f(texelHeightOffset, mTexelHeight);
    }

    /**
     * 设置高斯模糊的宽高
     */
    public void setTexelOffsetSize(float width, float height) {
        mTexelWidth = width;
        mTexelHeight = height;
        if (mTexelWidth != 0) {
            mTexelWidth = 1.0f / mTexelWidth;
        } else {
            mTexelWidth = 0;
        }
        if (mTexelHeight != 0) {
            mTexelHeight = 1.0f / mTexelHeight;
        } else {
            mTexelHeight = 0;
        }
    }
}
