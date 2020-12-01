#version 120
attribute vec4 vPosition;//变量 float[4] 一个顶点 java 传过来
attribute vec2 vCoord;// 纹理坐标
varying vec2 aCoord;
void main() {
//    gl_Position = vec4(vec3(0.0), 1.0);
    // 内置变量：把坐标点赋值给gl_position就ok
    gl_Position = vPosition;
    aCoord = vCoord;
}
