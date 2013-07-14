package me.tempus.collada;

import org.lwjgl.util.vector.Vector3f;

public class Geometry {

	/**
	 * ID
	 * Position Array
	 * Index Array
	 * Triangle Count
	 * 
	 */
	
	private String id;
	private float[] posistions;
	private int[] indices;
	private float modelMatrix[];
	private int triangleCount;
	private float xRotation;
	private float yRotation;
	private float zRotation;
	private float[] translation;
	private float[] scale;
	
	public Geometry(String id){
		this.id = id;
	}

	public float[] getModelMatrix() {
		return modelMatrix;
	}

	public void setModelMatrix(float[] matrix) {
		this.modelMatrix = matrix;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float[] getPosistions() {
		return posistions;
	}

	public void setPosistions(float[] posistions) {
		this.posistions = posistions;
	}

	public int[] getIndices() {
		return indices;
	}

	public void setIndices(int[] indices) {
		this.indices = indices;
	}

	public int getTriangleCount() {
		return triangleCount;
	}
	
	public void setTriangleCount(int triangleCount) {
		this.triangleCount = triangleCount;
	}

	public void setRotationX(float rotationValue) {
		this.xRotation = rotationValue;
	}
	
	public void setRotationY(float rotationValue) {
		this.yRotation = rotationValue;
	}
	
	public void setRotationZ(float rotationValue) {
		this.zRotation = rotationValue;
	}

	public void setTranslation(float[] vector) {
		translation = new float[3];
		this.translation[0] = vector[0];
		this.translation[1] = vector[1];
		this.translation[2] = vector[2];
	}

	public void setScale(float[] vector) {
		this.scale = vector;
	}
	
	public float getxRotation() {
		return xRotation;
	}

	public float getyRotation() {
		return yRotation;
	}

	public float getzRotation() {
		return zRotation;
	}

	public float[] getTranslation() {
		return translation;
	}

	public float[] getScale() {
		return scale;
	}

	/**
	 * @deprecated Needs to be implemented. Use direct buffer of the model matrix
	 */
	public Vector3f getPosistionInSpace(){
		return null;
	}
	

}
