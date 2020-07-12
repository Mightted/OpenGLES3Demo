#version 320 es
precision mediump float;

//uniform sampler2D uTextureUnit1;
//uniform sampler2D uTextureUnit2;
struct Material {
//    vec3 ambient;
    sampler2D diffuse;
    sampler2D specular;
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
in vec2 vTextureCoord;
void main() {

    //    float ambientStrength = 0.1;
    //    float specularStrength = 0.5;
    vec3 ambient = light.ambient * vec3(texture(material.diffuse, vTextureCoord));



    vec3 norm = normalize(vNormal);
    vec3 lightDir = normalize(light.position - fragPos);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff * light.diffuse * vec3(texture(material.diffuse, vTextureCoord));

    vec3 viewDir = normalize(viewPos - fragPos);// pass
    vec3 reflectDir = reflect(-lightDir, norm);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    vec3 specular =  light.specular * spec * vec3(texture(material.specular, vTextureCoord));

    vec3 result = ambient + diffuse + specular;
    FragColor = vec4(result, 1.0);

    //    gl_FragColor = mix(texture2D(uTextureUnit1, vTextureCoord), texture2D(uTextureUnit2, vTextureCoord), 0.2);
}
