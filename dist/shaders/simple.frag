#version 150
 
precision highp float; // Video card drivers require this next line to function properly

in vec2 var_tex_coords;
out vec4 frag_color;
uniform sampler2D s_texture;
 
void main(void) 
{
    frag_color = texture(s_texture, var_tex_coords.st);

}