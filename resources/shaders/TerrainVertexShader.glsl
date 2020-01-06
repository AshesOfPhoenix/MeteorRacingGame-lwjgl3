#version 330 core

in vec3 position;
in vec2 textureCoordinates;
in vec3 normal;

out vec2 pass_textureCoordinates;
out vec3 surfaceNormal;
out vec3 toLightVector;
out vec3 toCameraVector;
out float visibility;

uniform mat4 projeMat;// where the default location of everything is
uniform mat4 viewMat;// camera's perspective
uniform mat4 transMatrix;// the entity's position relative to the everything
uniform vec3 lightPosition;

const float density = 0.003;
const float gradient = 0.8;

void main() {
    vec4 worldPosition = transMatrix * vec4(position, 1.0);
    vec4 positionRelativeToCamera = viewMat * worldPosition;//Get the distance between vertex and the camera
    gl_Position = projeMat * positionRelativeToCamera;
    pass_textureCoordinates=textureCoordinates * 500;//Enable tile-ing, increses texture cooridnates so it doesnt look stretched

    surfaceNormal = (transMatrix * vec4(normal, 0.0)).xyz;//Swizzel vec4
    toLightVector = lightPosition - worldPosition.xyz;

    //Get camera vector = vector pointing to the camera
    toCameraVector = (inverse(viewMat) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz;

    //Calculate visibility
    float distance = length(positionRelativeToCamera.xyz);
    visibility = exp(-pow((distance*density), gradient));
    visibility = clamp(visibility, 0.0, 1.0);
}