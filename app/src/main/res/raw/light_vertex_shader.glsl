layout (location = 0) attribute vec4 aPos;
//layout (location = 1) attribute vec3 aColor;
layout (location = 2) attribute vec2 aTextureCoord;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;


//varying vec4 outColor;
varying vec2 vTextureCoord;



void main() {
    vTextureCoord = aTextureCoord;
    //    outColor = vec4(aColor, 1.0);
    gl_Position = projectionMatrix  * viewMatrix * modelMatrix * aPos;
}
