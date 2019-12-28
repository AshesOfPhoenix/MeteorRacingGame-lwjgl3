#version 460 core


in vec3 position;
in vec2 textureCoordinates;

uniform mat4 transformationMatrix;

out vec3 color;
out vec2 pass_textureCoordinates;
void main() {
	gl_Position = vec4(position, 1.0);
    pass_textureCoordinates=textureCoordinates;
	color = vec3(position.x, position.y - position.x, position.y);
}