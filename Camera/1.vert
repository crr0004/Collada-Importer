#version 330

// Incoming per vertex... position and normal 
in vec3 vVertex; 

uniform mat4 projection_matrix;
uniform mat4 model_matrix;
uniform mat4 view_matrix;

out vec3 vColour;

void main(void){

    // Don’t forget to transform the geometry! 
    gl_Position = projection_matrix * model_matrix * view_matrix * vec4(vVertex, 1.0);
    vColour = vec3(0.1,0.1,1);
}