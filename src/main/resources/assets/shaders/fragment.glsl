#version 330 core

uniform float uTime;
uniform sampler2D TEX_SAMPLER;

in vec4 fColor;
in vec2 fTexCoords;

out vec4 color;

void main() {
    color = texture(TEX_SAMPLER, fTexCoords);

    //color = (sin(uTime * 5) + 1) / 2 * fColor;

    // float avg = (fColor.r + fColor.g + fColor.b) / 3;
    // color = vec4 (avg, avg, avg, 1);
}
