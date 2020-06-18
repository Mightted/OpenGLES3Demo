//#version 120
precision mediump float;

uniform sampler2D uTextureUnit1;
uniform sampler2D uTextureUnit2;
varying vec4 outColor;
varying vec2 vTextureCoord;
void main() {

    gl_FragColor = mix(texture2D(uTextureUnit1, vTextureCoord), texture2D(uTextureUnit2, vTextureCoord), 0.2);
}
