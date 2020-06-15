//#version 120

layout (location = 0)in vec4 aPos;
//uniform mat4 uMatrix;

void main() {
    gl_Position = aPos;
}
