package top.zcwfeng.opengl.filter.beauty;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import top.zcwfeng.opengl.R;
import top.zcwfeng.opengl.filter.AbstractFrameFilter;
import top.zcwfeng.opengl.filter.FilterContext;

public class BeautyFilter2 extends AbstractFrameFilter {
    private FloatBuffer singleStepOffset;
    private float brightLevel;
    private int beautyLevelLocation;
    private int singleStepOffsetLocation;

    public BeautyFilter2(Context context) {
        super(context, R.raw.base_vert, R.raw.beauty_frag);

        singleStepOffset = ByteBuffer.allocateDirect(8).order(ByteOrder.nativeOrder()).asFloatBuffer();
    }

    @Override
    public void initGL(Context context, int vertexShaderId, int fragmentShaderId) {
        super.initGL(context, vertexShaderId, fragmentShaderId);
        beautyLevelLocation = GLES20.glGetUniformLocation(program, "beautyLevel");
        singleStepOffsetLocation = GLES20.glGetUniformLocation(program, "singleStepOffset");
    }

    @Override
    public void afterDraw(FilterContext filterContext) {
        super.afterDraw(filterContext);
        GLES20.glUniform1f(beautyLevelLocation,  filterContext.beautyLevel);


        singleStepOffset.clear();
        singleStepOffset.put(2.0f / filterContext.width);
        singleStepOffset.put(2.0f / filterContext.height);
        singleStepOffset.position(0);
        GLES20.glUniform2fv(singleStepOffsetLocation, 1, singleStepOffset);
    }
}
