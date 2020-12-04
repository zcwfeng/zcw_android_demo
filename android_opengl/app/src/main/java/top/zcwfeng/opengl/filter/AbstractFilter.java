package top.zcwfeng.opengl.filter;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import top.zcwfeng.opengl.utils.OpenGLUtils;

public class AbstractFilter {
    FloatBuffer vertexBuffer; //顶点坐标缓存区
    FloatBuffer textureBuffer; // 纹理坐标
    int mWidth;
    int mHeight;
    int vPosition;
    int vCoord;
    int vTexture;
    int vMatrix;
    int program;

    public AbstractFilter(Context context,int vertexShaderId, int fragmentShaderId) {
        vertexBuffer = ByteBuffer.allocateDirect(4 * 4 * 2).order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        textureBuffer = ByteBuffer.allocateDirect(4 * 4 * 2).order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        initCoord();
        initGL(context,vertexShaderId,fragmentShaderId);
    }

    public void initGL(Context context,int vertexShaderId, int fragmentShaderId) {

        String vertexSharder = OpenGLUtils.readRawTextFile(context,vertexShaderId);
        String fragSharder = OpenGLUtils.readRawTextFile(context,fragmentShaderId);
        //着色器程序准备好
        program = OpenGLUtils.loadProgram(vertexSharder, fragSharder);
        //获取程序中的变量 索引
        vPosition = GLES20.glGetAttribLocation(program, "vPosition");
        vCoord = GLES20.glGetAttribLocation(program, "vCoord");
        vTexture = GLES20.glGetUniformLocation(program, "vTexture");
    }

    public void initCoord() {
        vertexBuffer.clear();
        vertexBuffer.put(OpenGLUtils.VERTEX);
        textureBuffer.clear();
        textureBuffer.put(OpenGLUtils.TEXURE);
    }
    public void setSize(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    public int onDraw(int texture) {
        //设置绘制区域
        GLES20.glViewport(0, 0, mWidth, mHeight);


        GLES20.glUseProgram(program);

        vertexBuffer.position(0);
        // 4、归一化 normalized  [-1,1] . 把[2,2]转换为[-1,1]
        GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        //CPU传数据到GPU，默认情况下着色器无法读取到这个数据。 需要我们启用一下才可以读取
        GLES20.glEnableVertexAttribArray(vPosition);


        textureBuffer.position(0);
        // 4、归一化 normalized  [-1,1] . 把[2,2]转换为[-1,1]
        GLES20.glVertexAttribPointer(vCoord, 2, GLES20.GL_FLOAT, false, 0, textureBuffer);
        //CPU传数据到GPU，默认情况下着色器无法读取到这个数据。 需要我们启用一下才可以读取
        GLES20.glEnableVertexAttribArray(vCoord);


        //相当于激活一个用来显示图片的画框
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,texture);
        // 0: 图层ID  GL_TEXTURE0
        // GL_TEXTURE1 ， 1
        GLES20.glUniform1i(vTexture,0);

        // TODO: 2020/12/4 befroeDraw
        beforeDraw();



        //通知画画，
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,4);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);

        return texture;

    }


    public void beforeDraw() {
    }

    public void release(){
        GLES20.glDeleteProgram(program);
    }

}
