precision mediump float;//数据精度

varying vec2 aCoord;

uniform sampler2D vTexture;// samplerExternalOES 图片，采样器

void main(){
    //  texture2D: vTexture采样器，采样  aCoord 这个像素点的RGBA值
    vec4 rgba = texture2D(vTexture,aCoord);  //rgba
    //    gl_FragColor = vec4(1.-rgba.r,1.-rgba.g,1.-rgba.b,rgba.a);
    gl_FragColor = rgba;
}
