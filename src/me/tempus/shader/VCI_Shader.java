package me.tempus.shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

public class VCI_Shader {

	protected int shaderID;
	protected int vertID;
	protected int fragID;
	
	private int vVertex = -1;	// Set to -1 so we can validate
	private int colourUniformLocation = -2;
	
	/**
	 * When creating this it will load the shader so create this in setup and from the GL Context thread
	 * @param vertPath Path to the vertex shader file
	 * @param fragPath Path to the Fragment shader file
	 */
	
	public VCI_Shader(String vertPath, String fragPath){
		load(vertPath, fragPath);		
	}
	
	/**
	 * Renders a vertex buffer object created with this shader
	 * @param vaoID ID of the vertex buffer object
	 * @param indicesBufferID ID of the index data
	 * @param numberOfTriangles Amount of triangles to be rendered
	 */
	public void render(int vaoID, int indicesBufferID, int numberOfTriangles){
		
		if(numberOfTriangles <= 0){
			try {
				throw new Exception("Can't have 0 triangles");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		GL30.glBindVertexArray(vaoID);
		
		GL20.glEnableVertexAttribArray(vVertex);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBufferID);
		GL11.glDrawElements(GL11.GL_TRIANGLES, numberOfTriangles*3, GL11.GL_UNSIGNED_INT, 0);
		
		//GL20.glDisableVertexAttribArray(vVertex);
		GL30.glBindVertexArray(0);
	}
	
	/**
	 * Creates and binds the vertex buffer object id for a given set of vertices and indices
	 * @param vertices
	 * @param indices
	 * @return The vertex buffer object's ID and the index data's ID, in said order
	 */
	public int[] getVBOID(FloatBuffer vertices, IntBuffer indices){
		
		
		final int vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		
		final int vboId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(vVertex, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		final int vboiID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		return new int[]{vaoID, vboiID};
		
	}
	
	
	/**
	 * <h1>Set the uniform locations to variables</h1>
	 * Use GL20.glGetUniformLocation(program, name)<br>
	 * <b>Must be overriden to have code</b>
	 */
	
	private void getUniformLocations(){
		colourUniformLocation = GL20.glGetUniformLocation(shaderID, "color");
		System.out.println(GL20.glGetActiveUniform(shaderID, 0, 100));
	}
	
	/**
	 * <h1> Bind attribute locations (in variables)</h1>
	 * Use GLint glGetAttribLocation(int program, char *name);<br>
	 * Called from within loadShaders<br>
	 * <b>Must be overriden to have code</b>
	 */
	
	private void getAttributeLocations(){
		vVertex = GL20.glGetAttribLocation(shaderID, "vVertex");
		if(vVertex == -1){
			try {
				throw new Exception("vVertex cannot be bound to -1");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	/**
	 * <h1>Load the Shaders</h1>
	 * Loads, complies, sets up attribute locations and links shader<br>
	 * <b>Must be called after the setup of OpenGL</b>
	 * @param vertPath 
	 * @param fragPath 
	 */	
	
	public void load(String vertPath, String fragPath){
		
		shaderID = GL20.glCreateProgram();
		vertID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		fragID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

		StringBuilder[] shaderSources = ShaderUtilities.loadShaders(vertPath, fragPath);

		GL20.glShaderSource(vertID, shaderSources[0]);

		GL20.glCompileShader(vertID);

		if((GL20.glGetShaderi(vertID, GL20.GL_COMPILE_STATUS)) == GL11.GL_FALSE){
			System.err.println("Compling of vertex shader failed");

		}


		GL20.glShaderSource(fragID, shaderSources[1]);
		GL20.glCompileShader(fragID);

		if((GL20.glGetShaderi(fragID, GL20.GL_COMPILE_STATUS)) == GL11.GL_FALSE){
			System.err.println("Compling of fragment shader failed");
		}

		
		
		GL20.glAttachShader(shaderID, vertID);
		GL20.glAttachShader(shaderID, fragID);
				
		
		GL20.glLinkProgram(shaderID);
		GL20.glValidateProgram(shaderID);
		
		getAttributeLocations();
		
		int projectionMatrixLocation = GL20.glGetUniformLocation(shaderID, "projection_matrix");
		int viewMatrixLocation = GL20.glGetUniformLocation(shaderID, "view_matrix");
		int modelMatrixLocation = GL20.glGetUniformLocation(shaderID, "model_matrix");

		PVM.setLocations(projectionMatrixLocation, viewMatrixLocation, modelMatrixLocation);
		getUniformLocations();
		
		if((GL20.glGetProgrami(shaderID, GL20.GL_LINK_STATUS) == GL_FALSE)){
				
			System.out.println("Link failed");
			System.out.println(GL20.glGetShaderInfoLog(fragID, 10000));
			System.out.println(GL20.glGetShaderInfoLog(vertID, 10000));
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 10000));
		}
	}

	public int getShaderID() {
		// TODO Auto-generated method stub
		return shaderID;
	}

	public void setColour(Vector3f colour) {
		// TODO Auto-generated method stub
		GL20.glUniform3f(colourUniformLocation, colour.x, colour.y, colour.z);
	}


}
