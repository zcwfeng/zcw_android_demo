precision mediump float;
uniform sampler2D vTexture;  //原图
varying vec2 aCoord;


uniform sampler2D blurTexture; //原图模糊
uniform sampler2D highpassBlurTexture; //模糊后的高反差图


//磨皮程度 0-1.0
uniform float level;


void main(){
    //4、磨皮
    vec4 currentColor = texture2D(vTexture, aCoord);
    vec4 blurColor = texture2D(blurTexture, aCoord);
    vec4 highpassBlurColor = texture2D(highpassBlurTexture, aCoord);

    float value = clamp((min(currentColor.b, blurColor.b) - 0.2) * 5.0, 0.0, 1.0);
    float maxChannelColor = max(max(highpassBlurColor.r, highpassBlurColor.g), highpassBlurColor.b);
    float currentIntensity = (1.0 - maxChannelColor / (maxChannelColor + 0.2)) * value * level;
    // 混合
    vec3 resultColor = mix(currentColor.rgb, blurColor.rgb, currentIntensity);
    gl_FragColor = vec4(resultColor, 1.0);
}
