package me.tempus.collada;

public class Node {

	private String id;
	private float[] matrix;
	
	public Node(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public float[] getMatrix() {
		return matrix;
	}
	public void setMatrix(float[] matrix) {
		this.matrix = matrix;
	}
	
	

}
