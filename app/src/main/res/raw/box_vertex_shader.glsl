layout (location = 0) attribute vec4 aPos;
layout (location = 1) attribute vec3 aColor;
uniform mat4 uMatrix;
varying vec4 outColor;



void main() {
    outColor = vec4(aColor, 1.0);
    gl_Position = uMatrix * aPos;
}
