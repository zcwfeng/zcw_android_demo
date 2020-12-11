
varying highp vec2 aCoord;

uniform sampler2D vTexture;
uniform lowp float mixturePercent;
uniform highp float scalePercent;

void main() {
    lowp vec4 textureColor = texture2D(vTexture, aCoord);
    //gl_FragColor  =  textureColor;
    highp vec2 textureCoordinateToUse = aCoord;
    // 纹理中心点
    highp vec2 center = vec2(0.5, 0.5);
    // 当前要上颜色的点 与中心点的偏移
    textureCoordinateToUse -= center;
    //scalePercent： 放大参数
    // 如果大于1，
    textureCoordinateToUse = textureCoordinateToUse / scalePercent;
    textureCoordinateToUse += center;


    lowp vec4 textureColor2 = texture2D(vTexture, textureCoordinateToUse);

    gl_FragColor = mix(textureColor, textureColor2, mixturePercent);
}
