#version 330 core

in vec2 pass_textureCoordinates;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;

out vec4 outColor;

uniform sampler2D textureSampler;
uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;

void main() {
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    //AMBIENT LIGHTNING
    float ambientStrength = 0.3;
    vec3 ambient = ambientStrength * lightColor;

    //DIFFUSE LIGHTNING
    float nDotProduct = dot(unitNormal, unitLightVector);
    float brightness = max(nDotProduct, 0.0);
    vec3 finalDiffuse = brightness * lightColor;

    //SPECULAR LIGHTNING
    vec3 unitVectorToCamera = normalize(toCameraVector);
    vec3 lightDirection = -unitLightVector;
    vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
    float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);//Calculate how much light is going into the camera, how bright the pixel should be
    specularFactor = max(specularFactor, 0.0);
    float dampedFactor = pow(specularFactor, shineDamper);//Apply damper
    vec3 finalSpecular = dampedFactor * reflectivity * lightColor;//Final specular light = color


    //!Takes in the texture and coordinates we want to sample
    //!Returns the color of the pixel at those coordinates
    outColor = (vec4(finalSpecular, 1.0) + vec4(finalDiffuse, 1.0) + vec4(ambient, 1.0)) * texture(textureSampler, pass_textureCoordinates);
    //outColor = texture(textureSampler, pass_textureCoordinates);   //<-- ONLY TEXTURE WITHOUT LIGHTNING
    //outColor = vec4(finalDiffuse, 1.0) + texture(textureSampler, pass_textureCoordinates); //<-- DIFFUSE LIGHTNING
    //outColor = texture(textureSampler, pass_textureCoordinates) + vec4(finalSpecular, 1.0); //<-- SPECULAR LIGHTNING
}