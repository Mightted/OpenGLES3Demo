layout (location = 0) attribute vec4 aPos;
layout (location = 1) attribute vec3 aNormal;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

varying vec3 vNormal;
varying vec3 fragPos;


//varying vec4 outColor;
//varying vec2 vTextureCoord;



void main() {
//    vTextureCoord = aTextureCoord;
    //    outColor = vec4(aColor, 1.0);
    vNormal = aNormal;
    fragPos = vec3(modelMatrix * aPos);
    gl_Position = projectionMatrix  * viewMatrix * modelMatrix * aPos;
}
