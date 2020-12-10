precision mediump float;
uniform sampler2D vTexture;
varying vec2 aCoord;

//纹理宽、高
uniform float texelWidthOffset;
uniform float texelHeightOffset;

vec4 blurCoord[5];


void main(){
    //1、 进行模糊处理
    // 偏移步距  (0，0.1)
    vec2 singleStepOffset = vec2(texelWidthOffset, texelHeightOffset);

    blurCoord[0] = vec4(aCoord -  singleStepOffset, aCoord +  singleStepOffset);
    blurCoord[1] = vec4(aCoord -  2.0 * singleStepOffset, aCoord + 2.0*singleStepOffset);
    blurCoord[2] = vec4(aCoord -  3.0 * singleStepOffset, aCoord + 3.0*singleStepOffset);
    blurCoord[3] = vec4(aCoord -  4.0 * singleStepOffset, aCoord + 4.0*singleStepOffset);
    blurCoord[4] = vec4(aCoord -  5.0 * singleStepOffset, aCoord + 5.0*singleStepOffset);

    // 计算当前坐标的颜色值
    vec4 currentColor = texture2D(vTexture, aCoord);
    vec3 sum = currentColor.rgb;
    // 计算偏移坐标的颜色值总和
    for (int i = 0; i < 5; i++) {
        sum += texture2D(vTexture, blurCoord[i].xy).rgb;
        sum += texture2D(vTexture, blurCoord[i].zw).rgb;
    }
    //平均值 模糊效果
    vec4 blur = vec4(sum  / 11.0, currentColor.a);
    gl_FragColor = blur;

}
