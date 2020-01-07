#version 330

in vec2 position;

out vec2 textureCoords;

uniform mat4 transformationMatrixGui;

void main(void){

    gl_Position = transformationMatrixGui * vec4(position, 0.0, 1.0);
    textureCoords = vec2((position.x+1.0)/2.0, 1 - (position.y+1.0)/2.0);
}