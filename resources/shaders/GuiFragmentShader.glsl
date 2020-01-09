#version 330

in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D guiTexture;

void main(void){
    vec4 texColor = texture(guiTexture, textureCoords);
    if (texColor.a < 0.1)
    discard;
    out_Color = texColor;

}