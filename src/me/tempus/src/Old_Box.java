package me.tempus.src;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import me.tempus.shader.PVM;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

public class Old_Box {
	
	//private static int vertexCount = 6;
	
	private int vaoID;

	private Vector3f pos;
	private Vector3f rotate = new Vector3f(0,0,0);
	private Vector3f colour;
	
	private Texture texture;

	private int vboiID;
	
	public Old_Box(Vector3f pos, Vector3f colour){
		this.pos = pos;
		this.colour = colour;
	}
	
	public void draw(){
		
		PVM.pushMatrix();
		PVM.rotate(rotate.x, rotate.y, rotate.z);
		PVM.translate(pos.x, pos.y, pos.z);
		PVM.scale(1, 1, 1);
		PVM.bufferMatricies();
		PVM.popMatrix();
		
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		
		GL30.glBindVertexArray(vaoID);
		GL20.glEnableVertexAttribArray(0);
		//GL20.glEnableVertexAttribArray(1);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiID);
		
		//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 8);
		GL11.glDrawElements(GL11.GL_TRIANGLES, 36, GL11.GL_UNSIGNED_INT, 0);
		//GL30.glBindVertexArray(1);
		GL30.glBindVertexArray(0);
		
	}
	
	@SuppressWarnings("unused")
	public void create(){
		
		
		float[] boxData = {
				1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f
		};
		
		float[] textureData = {
				0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f
		};
		
		int[] boxiData = {
				0, 1, 2, 0, 2, 3, 4, 5, 6, 4, 6, 7, 8, 9, 10, 8, 10, 11, 12, 13, 14, 12, 14, 15, 16, 17, 18, 16, 18, 19, 20, 21, 22, 20, 22, 23
		};
		
		
		final FloatBuffer vertices = BufferUtils.createFloatBuffer(boxData.length);
		vertices.put(boxData);
		vertices.flip();

		//final FloatBuffer textureCoords = BufferUtils.createFloatBuffer(textureData.length);
		//textureCoords.put(textureData);
		//textureCoords.flip();		
		
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
		
		//final int vbotId = GL15.glGenBuffers();
		//GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbotId);
		//GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureCoords, GL15.GL_STATIC_DRAW);
		//GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
		//GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		//GL30.glBindVertexArray(0);
		
		vboiID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		
		//try {
			//texture = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("Wood_Box_Texture.jpg"));
		//} catch (IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		
	}

	public Vector3f getPos() {
		return pos;
	}

	public void setPos(Vector3f pos) {
		this.pos.x = pos.x;
		this.pos.y = pos.y;
		this.pos.z = pos.z-10;
	}

	public void setRotation(Vector3f rotation) {
		this.rotate.x = rotation.x;
		this.rotate.y = rotation.y;
		this.rotate.z = rotation.z;
	}
	
	
}
