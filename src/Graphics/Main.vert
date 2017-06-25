#version 330

uniform mat4 projection_matrix;
uniform mat4 model_view_matrix;
uniform mat4 zoom_matrix;
uniform int mainCharacter;

in vec2 position;
in vec2 texcoord;
out vec2 st;

void main(){
    gl_Position = projection_matrix*model_view_matrix*zoom_matrix*vec4(position, 0.0, 1.0);
    st = texcoord;
}