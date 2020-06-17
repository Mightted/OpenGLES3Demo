//#version 120
precision mediump float;

uniform sampler2D uTextureUnit;
varying vec4 outColor;
varying vec2 vTextureCoord;
void main() {

    gl_FragColor = texture2D(uTextureUnit, vTextureCoord) * outColor;
}
