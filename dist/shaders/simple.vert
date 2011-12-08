#version 150
 
in vec4 in_vertices;
in vec2 in_tex_coords;
smooth out vec2 var_tex_coords;
uniform mat4 u_mvp_mat;
 
void main(void) 
{
    gl_Position = u_mvp_mat * in_vertices;
    var_tex_coords = in_tex_coords;
}