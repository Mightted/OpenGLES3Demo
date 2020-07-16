#version 320 es
layout (location = 0) in vec4 aPos;
layout (location = 1) in vec3 aNormal;
layout (location = 2) in vec2 aTextureCoord;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

out vec3 vNormal;
out vec3 fragPos;


//varying vec4 outColor;
out vec2 vTextureCoord;



void main() {
    vTextureCoord = aTextureCoord;
    //    outColor = vec4(aColor, 1.0);
    vNormal = mat3(transpose(inverse(modelMatrix))) * aNormal;
    fragPos = vec3(modelMatrix * aPos);
    gl_Position = projectionMatrix  * viewMatrix * modelMatrix * aPos;
}
