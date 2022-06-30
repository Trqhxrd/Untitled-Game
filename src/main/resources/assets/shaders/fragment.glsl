#version 330 core

uniform float uTime;

in vec4 fColor;

out vec4 color;

void main() {
    color = (sin(uTime * 5) + 1) / 2 * fColor;

    // float avg = (fColor.r + fColor.g + fColor.b) / 3;
    // color = vec4 (avg, avg, avg, 1);
}
