#version 330

in vec4 fColor;
in vec2 fTexture;

uniform sampler3D textureSampler;

out vec4 color;

void main() {
    color = texture3D(textureSampler, vec3(fTexture, 0));
// color = fColor;
}
