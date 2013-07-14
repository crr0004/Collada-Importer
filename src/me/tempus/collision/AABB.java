package me.tempus.collision;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.util.vector.Vector3f;

public class AABB {

	private Vector3f min;
	private Vector3f max;
		
	
	
	
	public AABB(Vector3f max, Vector3f min) {
		// TODO Auto-generated constructor stub
		
		this.max = max;
		this.min = min;
	}

		
	public Vector3f getMinPoint() {
		return min;
	}




	public void setMinLengths(Vector3f min) {
		this.min = min;
	}




	public Vector3f getMaxPoint() {
		return max;
	}




	public void setMaxPoint(Vector3f max) {
		this.max.x = max.x;
		this.max.y = max.y;
		this.max.z = max.z;
	}


	public void setMinPoint(Vector3f min) {
		// TODO Auto-generated method stub
		this.min.x = min.x;
		this.min.y = min.y;
		this.min.z = min.z;
	}

	public static boolean intersect(AABB a, AABB b){
		
		/**
			int TestAABBAABB(AABB a, AABB b)
			{
				// Exit with no intersection if separated along an axis
				if (a.max[0] < b.min[0] || a.min[0] > b.max[0]) return 0;
				if (a.max[1] < b.min[1] || a.min[1] > b.max[1]) return 0;
				if (a.max[2] < b.min[2] || a.min[2] > b.max[2]) return 0;
				// Overlapping on all axes means AABBs are intersecting
				return 1;
			}
		 */
		
		/**
		 * Center Point and half Lengths
		if(Math.abs(a.c.x - b.c.x) > (Math.abs(a.hl.x) + Math.abs(b.hl.x))) return false;
		if(Math.abs(a.c.y - b.c.y) > (Math.abs(a.hl.y) + Math.abs(b.hl.y))) return false;
		if(Math.abs(a.c.z - b.c.z) > (Math.abs(a.hl.z) + Math.abs(b.hl.z))) return false;
		*/
		
		if (a.max.x < b.min.x || a.min.x > b.max.x) return false;
		if (a.max.y < b.min.y || a.min.y > b.max.y) return false;
		if (a.max.z < b.min.z || a.min.z > b.max.z) return false;
		// Overlapping on all axes means AABBs are intersecting		
		return true;
	}

	public static AABB findAABB(FloatBuffer vertices, IntBuffer indices){
		/**
		 * Searches for the extreame points on each axis
		 * Uses a linear search
		 */
		final Vector3f max = new Vector3f(0,0,0);
		final Vector3f min = new Vector3f(0,0,0);
		
		final int size = indices.capacity();
		for(int i = 0; i < size; i++){
			final int index = indices.get(i);
			
			final float x = vertices.get((index*3) + 0);
			final float y = vertices.get((index*3) + 1);
			final float z = vertices.get((index*3) + 2);
			if(x <= min.x){
				if(y <= min.y){
					if(z <= min.z){
						min.x = x;
						min.y = y;
						min.z = z;
					}
				}
			}else if(x >= max.x){
				if(y >= max.y){
					if(z >= max.z){
						max.x = x;
						max.y = y;
						max.z = z;
					}
				}
			}
		}
		System.out.println("Max: " + max + " Min: " + min);
		//final Vector3f c = new Vector3f((min.x + max.x) /2, (min.y + max.y) /2, (min.z + max.z) /2);
		//final Vector3f halfExtents = new Vector3f((max.x - min.x) /2, (max.y - min.y) /2, (max.z - min.z) /2);
		
		return new AABB(max, min);
	}
}
