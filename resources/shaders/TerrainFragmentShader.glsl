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

    float nDotProduct = dot(unitNormal, unitLightVector);
    float brightness = max(nDotProduct, 0.2);
    vec3 finalDiffuse = brightness * lightColor;

    vec3 unitVectorToCamera = normalize(toCameraVector);
    vec3 lightDirection = -unitLightVector;
    vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

    //Calculate how much light is going into the camera, how bright the pixel should be
    float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
    specularFactor = max(specularFactor, 0.0);
    //Aplly damper
    float dampedFactor = pow(specularFactor, shineDamper);
    //Final specular light = color
    vec3 finalSpecular = dampedFactor * reflectivity * lightColor;


    //!Takes in the texture and coordinates we want to sample
    //!Returns the color of the pixel at those coordinates
    outColor = (vec4(finalSpecular, 1.0) + vec4(finalDiffuse, 1.0)) * texture(textureSampler, pass_textureCoordinates);
    //outColor = texture(textureSampler, pass_textureCoordinates);   //<-- ONLY TEXTURE WITHOUT LIGHTNING
    //outColor = vec4(finalDiffuse, 1.0) + texture(textureSampler, pass_textureCoordinates); //<-- DIFFUSE LIGHTNING
    //outColor = texture(textureSampler, pass_textureCoordinates) + vec4(finalSpecular, 1.0); //<-- SPECULAR LIGHTNING
}