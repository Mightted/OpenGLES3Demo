#version 320 es
precision mediump float;

struct Material {

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

uniform vec3 viewPos;

out vec4 FragColor;
in vec3 vNormal;
in vec3 fragPos;
in vec2 vTextureCoord;
void main() {
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
}
