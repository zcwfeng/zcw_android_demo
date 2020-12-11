precision mediump float;
varying  vec2 aCoord;
uniform sampler2D vTexture;

void main() {
    float y = aCoord.y;
    float a = 1.0/3.0;
    if (y< a){
        y += a;
    } else if (y > 2.0*a){
        y -= 1.0/3.0;
    }
    gl_FragColor = texture2D(vTexture, vec2(aCoord.x, y));
}
