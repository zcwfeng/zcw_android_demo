package top.zcwfeng.opengl.filter;

import android.content.Context;
import android.opengl.GLES20;

import top.zcwfeng.opengl.R;

public
class TestSoulFilter extends AbstractFrameFilter{
    private int mixturePercent;
    private int scalePercent;
    public TestSoulFilter(Context context) {
        super(context, R.raw.base_vert,R.raw.soul_frag);
    }

    @Override
    public void initGL(Context context, int vertexShaderId, int fragmentShaderId) {
        super.initGL(context, vertexShaderId, fragmentShaderId);
        mixturePercent = GLES20.glGetUniformLocation(program, "mixturePercent");
        scalePercent = GLES20.glGetUniformLocation(program, "scalePercent");

    }

    float mix = 0.0f;
    float scale = 0.0f;

    @Override
    public void beforeDraw(FilterContext filterContext) {
        super.beforeDraw(filterContext);

        mix += 0.08f;
        scale += 0.08f;

        GLES20.glUniform1f(mixturePercent,1.0f - mix);
        GLES20.glUniform1f(scalePercent,1.0f + scale);

    }
}
