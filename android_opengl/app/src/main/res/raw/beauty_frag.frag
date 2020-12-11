precision mediump float;

uniform sampler2D vTexture;
varying  vec2 aCoord;

uniform vec2 singleStepOffset;
uniform  float beautyLevel;

const  vec3 W = vec3(0.299, 0.587, 0.114);
vec2 blurCoordinates[16];

const highp mat3 saturateMatrix = mat3(
1.1102, -0.0598, -0.061,
-0.0774, 1.0826, -0.1186,
-0.0228, -0.0228, 1.1772);

float hardLight(float color)
{
    if (color <= 0.5)
    color = color * color * 2.0;
    else
    color = 1.0 - ((1.0 - color)*(1.0 - color) * 2.0);
    return color;
}

void main(){
    vec3 centralColor = texture2D(vTexture, aCoord).rgb;
    blurCoordinates[0] = aCoord + singleStepOffset * vec2(0.0, -8.0);
    blurCoordinates[1] = aCoord + singleStepOffset * vec2(0.0, 8.0);
    blurCoordinates[2] = aCoord + singleStepOffset * vec2(-8.0, 0.0);
    blurCoordinates[3] = aCoord + singleStepOffset * vec2(8.0, 0.0);
    blurCoordinates[4] = aCoord + singleStepOffset * vec2(3.0, -6.0);
    blurCoordinates[5] = aCoord + singleStepOffset * vec2(3.0, 6.0);
    blurCoordinates[6] = aCoord + singleStepOffset * vec2(-3.0, 6.0);
    blurCoordinates[7] = aCoord + singleStepOffset * vec2(-3.0, -6.0);
    blurCoordinates[8] = aCoord + singleStepOffset * vec2(6.0, -3.0);
    blurCoordinates[9] = aCoord + singleStepOffset * vec2(6.0, 3.0);
    blurCoordinates[10] = aCoord + singleStepOffset * vec2(-6.0, 3.0);
    blurCoordinates[11] = aCoord + singleStepOffset * vec2(-6.0, -3.0);
    blurCoordinates[12] = aCoord + singleStepOffset * vec2(0.0, -4.0);
    blurCoordinates[13] = aCoord + singleStepOffset * vec2(0.0, 4.0);
    blurCoordinates[14] = aCoord + singleStepOffset * vec2(4.0, 0.0);
    blurCoordinates[15] = aCoord + singleStepOffset * vec2(-4.0, 0.0);

    float sampleColor = centralColor.g * 16.0;
    sampleColor += texture2D(vTexture, blurCoordinates[0]).g;
    sampleColor += texture2D(vTexture, blurCoordinates[1]).g;
    sampleColor += texture2D(vTexture, blurCoordinates[2]).g;
    sampleColor += texture2D(vTexture, blurCoordinates[3]).g;
    sampleColor += texture2D(vTexture, blurCoordinates[4]).g;
    sampleColor += texture2D(vTexture, blurCoordinates[5]).g;
    sampleColor += texture2D(vTexture, blurCoordinates[6]).g;
    sampleColor += texture2D(vTexture, blurCoordinates[7]).g;
    sampleColor += texture2D(vTexture, blurCoordinates[8]).g * 2.0;
    sampleColor += texture2D(vTexture, blurCoordinates[9]).g * 2.0;
    sampleColor += texture2D(vTexture, blurCoordinates[10]).g * 2.0;
    sampleColor += texture2D(vTexture, blurCoordinates[11]).g * 2.0;
    sampleColor += texture2D(vTexture, blurCoordinates[12]).g * 2.0;
    sampleColor += texture2D(vTexture, blurCoordinates[13]).g * 2.0;
    sampleColor += texture2D(vTexture, blurCoordinates[14]).g * 2.0;
    sampleColor += texture2D(vTexture, blurCoordinates[15]).g * 2.0;


    sampleColor = sampleColor / 40.0;

    highp float highPass = centralColor.g - sampleColor + 0.5;

    for (int i = 0; i < 3; i++) {
        highPass = hardLight(highPass);
    }
    highp float lumance = dot(centralColor, W);
    highp float alpha = pow(lumance, 1.0 - 0.6 * beautyLevel);

    highp vec3 smoothColor = centralColor + (centralColor-vec3(highPass))*alpha*0.1;
    float p = 1.0 - 0.3 * beautyLevel;
    smoothColor.r = clamp(pow(smoothColor.r,p), 0.0, 1.0);
    smoothColor.g = clamp(pow(smoothColor.g, p), 0.0, 1.0);
    smoothColor.b = clamp(pow(smoothColor.b, p), 0.0, 1.0);

    highp vec3 lvse = vec3(1.0)-(vec3(1.0)-smoothColor)*(vec3(1.0)-centralColor);
    highp vec3 bianliang = max(smoothColor, centralColor);
    highp vec3 rouguang = 2.0*centralColor*smoothColor + centralColor*centralColor - 2.0*centralColor*centralColor*smoothColor;

    gl_FragColor = vec4(mix(centralColor, lvse, alpha), 1.0);
}