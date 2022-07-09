#version 330

layout (location = 0) in vec2 aPosition;
layout (location = 1) in vec4 aColor;
layout (location = 2) in vec2 aTexture;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 fColor;
out vec2 fTexture;

void main() {
    fColor = aColor;
    fTexture = aTexture;
    gl_Position =
    // uProjection * uView *
    vec4(aPosition, 0.0, 1.0);
}
