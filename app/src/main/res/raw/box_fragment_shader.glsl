//#version 120
precision mediump float;

//uniform sampler2D uTextureUnit1;
//uniform sampler2D uTextureUnit2;

uniform vec3 lightColor;
uniform vec3 objectColor;
uniform vec3 lightPos;

varying vec3 vNormal;
varying vec3 fragPos;
//varying vec4 outColor;
//varying vec2 vTextureCoord;
void main() {

    float ambientStrength = 0.1;
    vec3 ambient = lightColor * ambientStrength;

    vec3 norm = normalize(vNormal);
    vec3 lightDir = normalize(lightPos - fragPos);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff * lightColor;

    vec3 result = (ambient + diffuse) * objectColor;
    gl_FragColor = vec4(result, 1.0);

//    gl_FragColor = mix(texture2D(uTextureUnit1, vTextureCoord), texture2D(uTextureUnit2, vTextureCoord), 0.2);
}
