precision mediump float;
uniform sampler2D vTexture;//高反差保留纹理（高通滤波）
varying vec2 aCoord;

uniform int width;
uniform int height;

vec4 blurCoord[2];

void main(){
    //3、保边预处理 保留边沿的细节不被模糊掉
    vec4 currentColor = texture2D(vTexture, aCoord);

    vec2 singleStepOffset = vec2(width, height);
    blurCoord[0] = vec4(aCoord -  singleStepOffset, aCoord +  singleStepOffset);
    blurCoord[1] = vec4(aCoord -  2.0 *singleStepOffset, aCoord + 2.0*singleStepOffset);

    vec3 sum = currentColor.rgb;
    for (int i = 0; i < 2; i++) {
        sum += texture2D(vTexture, blurCoord[i].xy).rgb;
        sum += texture2D(vTexture, blurCoord[i].zw).rgb;
    }
    vec4 highPassBlur = vec4(sum*1.0/5.0, currentColor.a);
    gl_FragColor = highPassBlur;
}
