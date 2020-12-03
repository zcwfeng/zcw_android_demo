#extension GL_OES_EGL_image_external : require
precision mediump float;//数据精度
varying vec2 aCoord;
uniform samplerExternalOES vTexture;//samplerExternalOES 图片，采样器
void main() {
//    gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
    vec4 rgba = texture2D(vTexture,aCoord);  //rgba
//    gl_FragColor = rgba;//rgba
    gl_FragColor = vec4(1.-rgba.r,1.-rgba.g,1.-rgba.b,rgba.a); // 底片效果
//    gl_FragColor = vec4(1.-rgba.r,1.-rgba.g,1.-rgba.b,1.-rgba.a); // 底片效果 荧光

}
