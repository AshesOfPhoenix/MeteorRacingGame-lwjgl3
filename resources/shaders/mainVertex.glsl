#version 460 core


in vec3 position;
in vec2 textureCoordinates;

uniform mat4 transformationMatrix;
uniform mat4 projectionMat;
uniform mat4 viewMat;
out vec3 color;
out vec2 pass_textureCoordinates;
void main() {
	gl_Position = projectionMat*viewMat*transformationMatrix*vec4(position, 1.0);
    pass_textureCoordinates=textureCoordinates;
	color = vec3(position.x, position.y - position.x, position.y);
}