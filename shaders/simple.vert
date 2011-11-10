#version 150
 
in vec4 vert;
uniform mat4 mvpMat;
 
void main(void) 
{
    gl_Position = mvpMat * vert;
}