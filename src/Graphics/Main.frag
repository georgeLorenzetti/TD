#version 330

uniform sampler2D texMap;

in vec2 st;
out vec4 out_color;

void main(){
    out_color = texture(texMap, st);
}