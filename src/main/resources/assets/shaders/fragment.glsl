#version 330

in vec4 fColor;
in vec2 fTexture;

uniform sampler2D textureSampler;

out vec4 color;

void main() {
    color = texture(textureSampler, fTexture);
}
