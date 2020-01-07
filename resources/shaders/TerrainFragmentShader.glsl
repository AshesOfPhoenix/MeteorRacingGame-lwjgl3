#version 330 core

in vec2 pass_textureCoordinates;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;
in float visibility;

out vec4 outColor;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;

uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;

void main() {
    //Multitexturing
    vec4 blendMapColor = texture(blendMap, pass_textureCoordinates);

    float backTextureAmount = 1 - (blendMapColor.r + blendMapColor.g + blendMapColor.b);
    vec2 tiledCoords = pass_textureCoordinates * 500.0;
    vec4 backgroundTextureColor = texture(backgroundTexture, tiledCoords) * backTextureAmount;
    vec4 rTextureColor = texture(rTexture, tiledCoords) * blendMapColor.r;
    vec4 gTextureColor = texture(gTexture, tiledCoords) * blendMapColor.g;
    vec4 bTextureColor = texture(bTexture, tiledCoords) * blendMapColor.b;

    vec4 totalColor = backgroundTextureColor + rTextureColor + gTextureColor + bTextureColor;


    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    //AMBIENT LIGHTNING
    float ambientStrength = 0.5;
    vec3 ambient = ambientStrength * lightColor;

    //DIFFUSE LIGHTNING
    float nDotProduct = dot(unitNormal, unitLightVector);
    float brightness = max(nDotProduct, 0.4);
    vec3 finalDiffuse = brightness * lightColor;

    //SPECULAR LIGHTNING
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
    outColor = (vec4(finalSpecular, 1.0) + vec4(finalDiffuse, 1.0) + vec4(ambient, 1.0)) * totalColor;
    //outColor = texture(textureSampler, pass_textureCoordinates);   //<-- ONLY TEXTURE WITHOUT LIGHTNING
    //outColor = vec4(finalDiffuse, 1.0) + texture(textureSampler, pass_textureCoordinates); //<-- DIFFUSE LIGHTNING
    //outColor = texture(textureSampler, pass_textureCoordinates) + vec4(finalSpecular, 1.0); //<-- SPECULAR LIGHTNING

    outColor = mix(vec4(skyColor, 1.0), outColor, visibility);//!Mix the sky color with the fragment color for the fog effect
}