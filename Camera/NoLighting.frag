#version 330

out vec4 vFragColor;
in vec3 vColour;


void main(void){ 

   vFragColor = vec4(vColour, 1);
}