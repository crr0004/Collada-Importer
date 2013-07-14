package me.tempus.gameobjects;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import me.tempus.shader.PVM;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;

public class Geometry {

	//Information used for rendering
	private int vboID;
	private int indicesID;
	
	//Geometry infomation
	private FloatBuffer vertices;
	private IntBuffer indices;
	private FloatBuffer modelMatrix;
	private int triangleSize;
	protected Vector3f scale = new Vector3f(1,1,1);
	protected Vector3f rotation = new Vector3f(0,0,0);
	protected Vector3f pos = new Vector3f(0,0,0);
	protected Vector3f colour;
	private String name;
	
	public void setName(String id) {
		// TODO Auto-generated method stub
		this.name = id;
	}
	
	public String getName(){
		return name;
	}
	
	public void setVertices(float[] vertices){
		this.vertices = BufferUtils.createFloatBuffer(vertices.length);
		this.vertices.put(vertices);
		this.vertices.flip();
	}
	
	public void setIndices(int[] indices){
		this.indices = BufferUtils.createIntBuffer(indices.length);
		this.indices.put(indices);
		this.indices.flip();
	}
	
	public void setModelMatrix(float[] modelMatrix){
		if(modelMatrix == null)
			return;
		this.modelMatrix = BufferUtils.createFloatBuffer(modelMatrix.length);
		this.modelMatrix.put(modelMatrix);
		this.modelMatrix.flip();
	}
	
	public void setTriangleSize(int triangleSize) {
		this.triangleSize = triangleSize;
	}

	public void setScale(float[] scale){
		if(scale == null)
			return;
		this.scale.x = scale[0];
		this.scale.y = scale[1];
		this.scale.z = scale[2];
	}
	
	public void setPos(float[] vector){
		if(vector == null)
			return;
		this.pos.x = vector[0];
		this.pos.y = vector[1];
		this.pos.z = vector[2];
	}
	
	public Vector3f getPos() {
		return pos;
	}

	public void setRotation(float[] rotation){
		this.rotation.x = rotation[0];
		this.rotation.y = rotation[1];
		this.rotation.z = rotation[2];
	}
	
	public FloatBuffer getVertices() {
		return vertices;
	}

	public IntBuffer getIndices() {
		return indices;
	}
	
	public int getVboID() {
		return vboID;
	}

	public int getIndicesID() {
		return indicesID;
	}

	/**
	 * 
	 * @param ids Assumes vbo is 0 and indices is 1
	 */
	public void setIDs(int[] ids){
		this.vboID = ids[0];
		this.indicesID = ids[1];
	}

	public int getTriangleSize() {
		// TODO Auto-generated method stub
		return triangleSize;
	}

	/**
	 * Use this for doing logic calculations, like calculations
	 */
	public void transform(){
		PVM.scale(scale.x, scale.y, scale.z);
		PVM.rotateDegrees(rotation.x, rotation.y, rotation.z);
		PVM.translate(pos.x, pos.y, pos.z);
	}
	
	/**
	 * Use this for rendering transformations
	 */
	public void doTransformation() {
		PVM.pushMatrix();
		transform();
		
		PVM.bufferMatricies();
		PVM.popMatrix();
	}

	public Vector3f getColour() {
		// TODO Auto-generated method stub
		return colour;
	}

	public void setColour(Vector3f colour) {
		// TODO Auto-generated method stub
		this.colour = colour;
	}
}
