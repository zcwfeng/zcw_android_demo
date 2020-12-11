precision mediump float;
varying  vec2 aCoord;
uniform sampler2D vTexture;

void main() {
    float y = aCoord.y;
    float x = aCoord.x;
    float a = 1.0/4.0;
    if (y< a){
        y += a;
    } else if (y > 3.0*a){
        y -= 1.0/4.0;
    }

    if (x< a){
        x += a;
    } else if (x > 3.0*a){
        x -= 1.0/4.0;
    }
    gl_FragColor = texture2D(vTexture, vec2(x, y));
}
