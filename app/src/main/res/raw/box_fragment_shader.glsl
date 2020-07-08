#version 320 es
precision mediump float;

//uniform sampler2D uTextureUnit1;
//uniform sampler2D uTextureUnit2;
struct Material {
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    float shininess;
};

struct Light {
    vec3 position;
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

uniform Material material;
uniform Light light;

//uniform vec3 lightColor;
//uniform vec3 objectColor;
//uniform vec3 lightPos;
uniform vec3 viewPos;

out vec4 FragColor;
in vec3 vNormal;
in vec3 fragPos;
//varying vec4 outColor;
//varying vec2 vTextureCoord;
void main() {

//    float ambientStrength = 0.1;
//    float specularStrength = 0.5;
    vec3 ambient = light.ambient * material.ambient;



    vec3 norm = normalize(vNormal);
    vec3 lightDir = normalize(light.position - fragPos);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff * light.diffuse * material.diffuse;

    vec3 viewDir = normalize(viewPos - fragPos);  // pass
    vec3 reflectDir = reflect(-lightDir, norm);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    vec3 specular =  light.specular * (spec * material.specular);

    vec3 result = ambient + diffuse + specular;
    FragColor = vec4(result, 1.0);

//    gl_FragColor = mix(texture2D(uTextureUnit1, vTextureCoord), texture2D(uTextureUnit2, vTextureCoord), 0.2);
}
