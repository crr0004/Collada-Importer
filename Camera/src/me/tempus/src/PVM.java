package me.tempus.src;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class PVM {
	
	private static Matrix4f projectionMatrix = new Matrix4f();
	private static Matrix4f viewMatrix = new Matrix4f();
	private static Matrix4f modelMatrix = new Matrix4f();
	private static FloatBuffer matrix44Buffer = BufferUtils.createFloatBuffer(16);
	
	private static List<Matrix4f> modelMatrixStack = new ArrayList<Matrix4f>();
	
	private final static double PI = 3.14159265358979323846;
	private static int projectionMatrixLocation;
	private static int viewMatrixLocation;
	private static int modelMatrixLocation;
	
	public static void setLocations(int projectionMatrixLoc, int viewMatrixLoc, int modelMatrixLoc){
		PVM.projectionMatrixLocation = projectionMatrixLoc;
		PVM.viewMatrixLocation = viewMatrixLoc;
		PVM.modelMatrixLocation = modelMatrixLoc;
	}
	public static void setUpProjection(float fov, float width, float height, float znear, float zfar){
		float aspectRatio = width / height;
		
		float y_scale = coTangent(degreesToRadians(fov / 2f));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = zfar - znear;
		
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((zfar + znear) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * znear * zfar) / frustum_length);
	}
	
	public static void setUpOrg(float left, float right, float bottom, float top, float zNear, float zFar){
		
		projectionMatrix.m00 = 2 / (right - left);
		projectionMatrix.m11 = 2 / (top - bottom);
		projectionMatrix.m22 = -2 / (zFar - zNear);
		projectionMatrix.m30 = -((right + left) / (right - left));
		projectionMatrix.m31 = -((top + bottom) / (top - bottom));
		projectionMatrix.m32 = -((zFar + zNear) / (zFar - zNear));
		projectionMatrix.m33 = 1;
	}
	
	public static void translateCamera(float x, float y, float z){
		viewMatrix.translate(new Vector3f(x, y, z));
	}
	public static void translate(float x, float y, float z){
		
		modelMatrix.translate(new Vector3f(x, y, z));
	}
	
	public static void scale(float x, float y, float z){
		modelMatrix.scale(new Vector3f(x, y, z));
	}
	
	public static void rotate(float x, float y, float z){
		modelMatrix.rotate(degreesToRadians(z), new Vector3f(0, 0, 1));
		modelMatrix.rotate(degreesToRadians(y), new Vector3f(0, 1, 0));
		modelMatrix.rotate(degreesToRadians(x), new Vector3f(1, 0, 0));
	}
	
	public static void done(){
		projectionMatrix.store(matrix44Buffer);

		matrix44Buffer.flip();


		GL20.glUniformMatrix4(projectionMatrixLocation, false, matrix44Buffer);

		viewMatrix.store(matrix44Buffer);
		
		matrix44Buffer.flip();

		GL20.glUniformMatrix4(viewMatrixLocation, false, matrix44Buffer);
		
		modelMatrix.store(matrix44Buffer);
		
		matrix44Buffer.flip();

		GL20.glUniformMatrix4(modelMatrixLocation, false, matrix44Buffer);
	}
	
	private static float coTangent(float angle){
		return (float)(1/(Math.tan(angle)));
	}
	
	private static float degreesToRadians(float degrees){
		return (float)(degrees * (PI / 180d));
	}
	
	public static void loadIdentity(){
		modelMatrix = new Matrix4f();
		viewMatrix = new Matrix4f();
	}
	
	public static void pushMatrix(){	
		Matrix4f m = new Matrix4f();
		Matrix4f.load(modelMatrix, m);
		modelMatrixStack.add(m);
	}
	
	public static void popMatrix(){
		int size = modelMatrixStack.size();
		modelMatrix = modelMatrixStack.get(size -1);
		modelMatrixStack.remove(size -1);
	}
}
