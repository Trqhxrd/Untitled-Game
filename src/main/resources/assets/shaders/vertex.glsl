#version 330

layout (location = 0) in vec2 aPosition;
layout (location = 1) in vec4 aColor;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 fColor;

void main() {
    fColor = aColor;
    gl_Position =  uProjection * uView * vec4(aPosition, 0.0, 1.0);
}
