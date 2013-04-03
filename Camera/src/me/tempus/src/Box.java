package me.tempus.src;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

public class Box {
	
	//private static int vertexCount = 6;
	
	private int vaoID;

	private Vector3f pos;

	private int vboiID;
	
	public Box(Vector3f pos){
		this.pos = pos;
	}
	
	public void draw(){
		
		PVM.pushMatrix();
		PVM.translate(pos.x, pos.y, pos.z);
		PVM.rotate(0, 45, 0);
		PVM.done();
		PVM.popMatrix();
		
		
		GL30.glBindVertexArray(vaoID);
		GL20.glEnableVertexAttribArray(0);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiID);
		
		//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 8);
		GL11.glDrawElements(GL11.GL_TRIANGLES, 36, GL11.GL_UNSIGNED_INT, 0);
		GL30.glBindVertexArray(0);
		
	}
	
	@SuppressWarnings("unused")
	public void create(){
		
		
		
		float[] verticesData = {
				-0.5f, 0.5f, 0f,	// Left top			ID: 0
				-0.5f, -0.5f, 0f,	// Left bottom		ID: 1
				0.5f, -0.5f, 0f,	// Right bottom		ID: 2
				0.5f, 0.5f, 0f	// Right left		ID: 3
		};		
		int[] indicesData = {
				// Left bottom triangle
				0, 1, 2,
				// Right top triangle
				2, 3, 0
		};
		
		float[] boxData = {
				-5.0f, -5.0f, 5.0f, -5.0f, -5.0f, -5.0f, 5.0f, -5.0f, -5.0f, 5.0f, -5.0f, 5.0f, -5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, -5.0f, -5.0f, 5.0f, -5.0f, -5.0f, -5.0f, 5.0f, 5.0f, -5.0f, 5.0f, 5.0f, 5.0f, 5.0f, -5.0f, 5.0f, 5.0f, 5.0f, -5.0f, 5.0f, 5.0f, -5.0f, -5.0f, 5.0f, 5.0f, -5.0f, 5.0f, 5.0f, 5.0f, 5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, 5.0f, -5.0f, 5.0f, 5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, 5.0f, -5.0f, 5.0f, 5.0f, -5.0f, 5.0f, -5.0f
		};
		
		int[] boxiData = {
				0, 1, 2, 2, 3, 0, 4, 5, 6, 6, 7, 4, 8, 9, 10, 10, 11, 8, 12, 13, 14, 14, 15, 12, 16, 17, 18, 18, 19, 16, 20, 21, 22, 22, 23, 20
		};
		
		
		final FloatBuffer vertices = BufferUtils.createFloatBuffer(boxData.length);
		//vertices.put( //The vertices for the box
			//	new float[]{-5.0f, -5.0f, 5.0f, -5.0f, -5.0f, -5.0f, 5.0f, -5.0f, -5.0f, 5.0f, -5.0f, 5.0f, -5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, -5.0f, -5.0f, 5.0f, -5.0f, -5.0f, -5.0f, 5.0f, 5.0f, -5.0f, 5.0f, 5.0f, 5.0f, 5.0f, -5.0f, 5.0f, 5.0f, 5.0f, -5.0f, 5.0f, 5.0f, -5.0f, -5.0f, 5.0f, 5.0f, -5.0f, 5.0f, 5.0f, 5.0f, 5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, 5.0f, -5.0f, 5.0f, 5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, 5.0f, -5.0f, 5.0f, 5.0f, -5.0f, 5.0f, -5.0f});
		
		vertices.put(boxData);
		vertices.flip();
		//indices.put(
				//new int[]{0, 1, 2, 2, 3, 0, 4, 5, 6, 6, 7, 4, 8, 9, 10, 10, 11, 8, 12, 13, 14, 14, 15, 12, 16, 17, 18, 18, 19, 16, 20, 21, 22, 22, 23, 20});
		
		final IntBuffer indices = BufferUtils.createIntBuffer(boxiData.length);
		indices.put(boxiData);
		indices.flip();
		
		
		vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		
		final int vboId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
		
		vboiID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		
		
		
	}
}
