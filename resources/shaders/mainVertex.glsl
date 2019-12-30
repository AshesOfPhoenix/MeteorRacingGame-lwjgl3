#version 330 core


in vec3 position;
in vec2 textureCoordinates;

uniform mat4 transMatrix;
uniform mat4 projeMat;
uniform mat4 viewMat;

out vec3 fs_color;
out vec2 pass_textureCoordinates;

void main() {
	gl_Position = projeMat * viewMat * transMatrix * vec4(position, 1.0);
    pass_textureCoordinates=textureCoordinates;
	fs_color = vec3(gl_Position.x+0.5, 0.0, gl_Position.y+0.5);
}