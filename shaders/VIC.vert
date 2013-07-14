#version 330

// Incoming per vertex... position and normal 
in vec3 vVertex; 
out vec3 vColour;

uniform mat4 projection_matrix;
uniform mat4 model_matrix;
uniform mat4 view_matrix;

uniform vec3 color;


void main(void){

	vColour = color;
    // Don’t forget to transform the geometry! 
    gl_Position = projection_matrix * model_matrix * view_matrix * vec4(vVertex, 1.0);
}