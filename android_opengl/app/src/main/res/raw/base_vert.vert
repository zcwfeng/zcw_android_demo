attribute vec4 vPosition;//变量 float[4] 一个顶点 java 传过来

attribute vec2 vCoord;//传给片元进行采样的纹理坐标

varying vec2 aCoord;//易变变量 和片元写的一模一样 会传给片元

void main() {
    // 内置变量：把坐标点赋值给gl_position就ok
    gl_Position = vPosition;// 因为CameraFilter 旋转需要用矩阵vMatrix，
    aCoord = vCoord;//ScreenFilter作为base，接受的数据已经由Camera处理好了，不需要
}
