#version 120
precision mediump float;//数据精度
varying vec2 aCoord;
// uniform 片元着色器必须用这个
uniform samplerExternalOES vTexture;//samplerExternalOES 图片，采样器
void main() {
//    gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
    gl_FragColor = texure2D(vTexture,aCoord);//rgba
}
