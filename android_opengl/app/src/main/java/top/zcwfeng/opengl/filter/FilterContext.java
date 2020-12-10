package top.zcwfeng.opengl.filter;

import top.zcwfeng.opengl.face.Face;

public class FilterContext {

    public Face face; // 人脸

    public float[] cameraMtx; //摄像头转换矩阵

    public int width;
    public int height;
    public float beautyLevel;

    public void setBeautyLevel(float beautyLevel) {
        this.beautyLevel = beautyLevel;
    }


    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setTransformMatrix(float[] mtx) {
        this.cameraMtx = mtx;
    }

    public void setFace(Face face) {
        this.face = face;
    }
}
