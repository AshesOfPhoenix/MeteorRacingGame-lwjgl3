#version 460 core

in vec3 color;
in vec2 pass_textureCoordinates;

out vec4 outColor;

uniform sampler2D textureSampler;

void main() {
    //!Takes in the texture and coordinates we want to sample
    //!Returns the color of the pixel at those coordinates
    outColor = texture(textureSampler, pass_textureCoordinates);
}