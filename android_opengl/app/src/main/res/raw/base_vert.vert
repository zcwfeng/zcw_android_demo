attribute vec4 vPosition;//变量 float[4] 一个顶点 java 传过来
attribute vec2 vCoord;
varying vec2 aCoord;

void main() {
    gl_Position = vPosition;// 因为CameraFilter 旋转需要用矩阵vMatrix，
    aCoord = vCoord;//ScreenFilter作为base，接受的数据已经由Camera处理好了，不需要
}
