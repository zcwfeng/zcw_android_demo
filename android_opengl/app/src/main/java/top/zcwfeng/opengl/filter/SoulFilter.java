package top.zcwfeng.opengl.filter;

import android.content.Context;
import android.opengl.GLES20;

import top.zcwfeng.opengl.R;

public
class SoulFilter extends AbstractFrameFilter{
    private int mixturePercent;
    private int scalePercent;
    public SoulFilter(Context context) {
        super(context, R.raw.base_vert,R.raw.soul_frag);
    }

    @Override
    public void initGL(Context context, int vertexShaderId, int fragmentShaderId) {
        super.initGL(context, vertexShaderId, fragmentShaderId);
        mixturePercent = GLES20.glGetUniformLocation(program, "mixturePercent");
        scalePercent = GLES20.glGetUniformLocation(program, "scalePercent");

    }

    float mix = 0.0f;// 透明度，越大越透明，但是android 里面alpha越小透明
    float scale = 0.0f;//缩放，越来越大

    @Override
    public void beforeDraw(FilterContext filterContext) {
        super.beforeDraw(filterContext);



        GLES20.glUniform1f(mixturePercent,1.0f - mix);
        GLES20.glUniform1f(scalePercent,1.0f + scale);

        mix += 0.05f;
        scale += 0.05f;
        if(mix >= 1.0f) {
            mix = 0.0f;
        }
        if(scale >= 1.0f){
            scale = 0.0f;
        }

    }
}
