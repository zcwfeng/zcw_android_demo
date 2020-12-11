package top.zcwfeng.opengl.filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import top.zcwfeng.opengl.R;
import top.zcwfeng.opengl.face.Face;
import top.zcwfeng.opengl.utils.OpenGLUtils;

public
class StickFilter extends AbstractFrameFilter {
    private int[] textures;
    private Bitmap bizi;
    public StickFilter(Context context) {
        super(context, R.raw.base_vert, R.raw.base_frag);

        // 把图片加载到创建的纹理中
        textures = new int[1];
        OpenGLUtils.glGenTextures(textures);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
        bizi = BitmapFactory.decodeResource(context.getResources(), R.drawable.bizi);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bizi, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }

    @Override
    public int onDraw(int texture, FilterChain filterChain) {
        textureBuffer.clear();
        textureBuffer.put(OpenGLUtils.TEXURE);
        textureBuffer.position(0);
        // 父类画摄像头图像
        return super.onDraw(texture, filterChain);

    }



    @Override
    public void afterDraw(FilterContext filterContext) {
        super.afterDraw(filterContext);
        //五个关键点 左眼，右眼，鼻子，左嘴角，右嘴角
        Face face = filterContext.face;
        if (face == null) {
            return;
        }
        Log.e("zcw_opengl", "---->StickFilter 是个人脸。。。");
        // 开启混合模式
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_CONSTANT_ALPHA);

        // 计算坐标
        // 基于画布的鼻子的中心点X
        // 取反就是左下角为起始点
        float x = face.nose_x / face.imgWidth * filterContext.width;
        float y = (1.0f - face.nose_y / face.imgHeight) * filterContext.height;

        // 两嘴角间距离-鼻子贴纸的宽高
        float mrx = face.mouseRight_x / face.imgWidth * filterContext.width;
        float mlx = face.mouseLeft_x / face.imgWidth * filterContext.width;
        int width = (int) ((mrx - mlx)*0.75f);

        // 嘴角的y到鼻子的y之间差，为贴纸高,左下角
        float mry = (1.0f - face.mouseRight_y / face.imgHeight) * filterContext.height;
        int height = (int) ((y - mry)*0.75f);// 左下角坐标，鼻子-嘴巴

        GLES20.glViewport((int) x - width / 2, (int) y - height / 2, width, height);

        // 画画鼻子---抄摄像头的
        GLES20.glUseProgram(program);

        vertexBuffer.position(0);
        GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glEnableVertexAttribArray(vPosition);



        textureBuffer.clear();
        textureBuffer.put(OpenGLUtils.TEXURE_180);
        textureBuffer.position(0);
        GLES20.glVertexAttribPointer(vCoord, 2, GLES20.GL_FLOAT, false, 0, textureBuffer);
        GLES20.glEnableVertexAttribArray(vCoord);


        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);//GL_TEXTURE0==第0层，需要区分
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
        GLES20.glUniform1i(vTexture, 0);

        //通知画画，
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

        // 关闭混合模式
        GLES20.glDisable(GLES20.GL_BLEND);

    }
}
