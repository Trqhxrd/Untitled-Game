#version 330

layout (location = 0) in vec2 aPosition;
layout (location = 1) in vec4 aColor;

out vec4 fColor;

void main() {
    fColor = aColor;
    gl_Position = vec4(aPosition, 1.0, 1.0);
}
