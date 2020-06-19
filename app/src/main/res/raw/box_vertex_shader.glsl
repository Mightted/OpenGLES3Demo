layout (location = 0) attribute vec4 aPos;
//layout (location = 1) attribute vec3 aColor;
layout (location = 2) attribute vec2 aTextureCoord;
uniform mat4 uMatrix;

uniform mat4 uRotateMatrix;


//varying vec4 outColor;
varying vec2 vTextureCoord;



void main() {
    vTextureCoord = aTextureCoord;
//    outColor = vec4(aColor, 1.0);
    gl_Position = uMatrix * uRotateMatrix * aPos;
}
