/**
 * 
 */
package me.tempus.src;

import static org.lwjgl.opengl.GL11.GL_FALSE;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 * @author Chris
 *
 */
public class NoLightingShader {

	private String vertPath;
	private String fragPath;
	
	protected int shaderID;
	protected int vertID;
	protected int fragID;
	
	/*
	private int vLightPosition;
	private int ambientColorID;
	private int diffuseColorID;
	private int specularColorID;
	private Vector3f lightPosistion;
	private int shineID;
	private int lightAmbientID;
	private int lightDiffuseID;
	private int lightSpecularID;
	
	private Vector4f ambient;
	private Vector4f diffuse;
	private Vector4f specular;
	
	private int samplerLoc;
	*/
	private int vVertex = 0;
	//private int vNormal;
	//private int vTexture;
	
	// TODO Fix attribute binding locations
	
	//public int 
	
	/**
	 * <h1>Shader Constuctor </h1>
	 * 
	 * @param vertPath The path to the vertex shader, (Full path, File.separatorChar for \, and extension)
	 * @param fragPath The path to the fragment shader, (Full path, File.separatorChar for \, and extension)
	 */
	
	public NoLightingShader(String vertPath, String fragPath){
		this.vertPath = vertPath;
		this.fragPath = fragPath;
	}
	
	/**
	 * <h1>Pass the uniform variable data to the shader</h1>
	 * Use GL20.glUniform<br>
	 * <b>Must be overriden to have code</b>
	 */
	
	public void passUniforms(float[] ambientColor, float[] diffuseColor, float[] specularColor, float shine){
		/*
		GL20.glUniform4f(ambientColorID, ambientColor[0], ambientColor[1], ambientColor[2], 1);
		GL20.glUniform4f(diffuseColorID, diffuseColor[0], diffuseColor[1], diffuseColor[2], 1);
		GL20.glUniform4f(specularColorID, specularColor[0], specularColor[1], specularColor[2], 1);
		GL20.glUniform3f(vLightPosition, lightPosistion.x, lightPosistion.y, lightPosistion.z);
		GL20.glUniform1f(shineID, shine);
		GL20.glUniform1i(samplerLoc, 0);
		*/
	}
	
	/**
	 * <h1>Set the uniform locations to variables</h1>
	 * Use GL20.glGetUniformLocation(program, name)<br>
	 * <b>Must be overriden to have code</b>
	 */
	
	public void getUniformLocations(){
		/*
		vLightPosition = GL20.glGetUniformLocation(shaderID, "vLightPosition");
		ambientColorID = GL20.glGetUniformLocation(shaderID, "ambientColor");
		diffuseColorID = GL20.glGetUniformLocation(shaderID, "diffuseColor");
		specularColorID = GL20.glGetUniformLocation(shaderID, "specularColor");
		shineID = GL20.glGetUniformLocation(shaderID, "shine");
		lightAmbientID = GL20.glGetUniformLocation(shaderID, "lightAmbient");
		lightDiffuseID = GL20.glGetUniformLocation(shaderID, "lightDiffuse");
		lightSpecularID = GL20.glGetUniformLocation(shaderID, "lightSpecular");
		samplerLoc = GL20.glGetUniformLocation(shaderID, "colorMap");
		*/
	}
	
	/**
	 * <h1> Bind attribute locations (in variables)</h1>
	 * Use GLint glGetAttribLocation(int program, char *name);<br>
	 * Called from within loadShaders<br>
	 * <b>Must be overriden to have code</b>
	 */
	
	public void bindAttributeLocations(){
		//vTexture = GL20.glGetAttribLocation(shaderID, "vTexture");
		//vNormal = GL20.glGetAttribLocation(shaderID, "vNormal");
		GL20.glBindAttribLocation(shaderID, vVertex, "vVertex");
		
	}
	
	/**
	 * <h1>Load the Shaders</h1>
	 * Loads, complies, sets up attribute locations and links shader<br>
	 * <b>Must be called after the setup of OpenGL</b>
	 */
	
	public void loadShaders(){
		
		shaderID = GL20.glCreateProgram();
		vertID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		fragID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

		StringBuilder[] shaderSources = Utilities.loadShaders(vertPath, fragPath);

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

		bindAttributeLocations();
		
		GL20.glAttachShader(shaderID, vertID);
		GL20.glAttachShader(shaderID, fragID);

				
		
		GL20.glLinkProgram(shaderID);
		GL20.glValidateProgram(shaderID);
		
		System.out.println("Bound vVertex to: " + GL20.glGetAttribLocation(shaderID, "vVertex"));
		
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
	
	public int getShaderID(){
		return shaderID;
	}
	
	public int getFragID(){
		return fragID;
	}
	
	public int getVertID(){
		return vertID;
	}
	
}
