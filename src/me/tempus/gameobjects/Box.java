package me.tempus.gameobjects;

import org.lwjgl.util.vector.Vector3f;

import me.tempus.collision.*;
import me.tempus.shader.PVM;

public class Box extends Geometry implements IAABB {

	public AABB boundingVolume;
	private Vector3f iMax = new Vector3f();
	private Vector3f iMin = new Vector3f();
	
	private Vector3f initalColour = new Vector3f();
	
	public void create(){
		boundingVolume = AABB.findAABB(this.getVertices(), this.getIndices());
		iMin.x = boundingVolume.getMinPoint().x;
		iMin.y = boundingVolume.getMinPoint().y;
		iMin.z = boundingVolume.getMinPoint().z;
		
		iMax.x = boundingVolume.getMaxPoint().x;
		iMax.y = boundingVolume.getMaxPoint().y;
		iMax.z = boundingVolume.getMaxPoint().z;
		
	}
	
	public void setPos(Vector3f pos){
		this.pos.x = pos.x;
		this.pos.y = pos.y;
		this.pos.z = pos.z;
	}
	
	@Override
	public AABB getAABB() {
		
		
		PVM.pushMatrix();
		PVM.loadIdentity();
		//PVM.scale(scale.x, scale.y, scale.z);
		PVM.rotateDegrees(rotation.x, rotation.y, rotation.z);
		PVM.translate(pos.x, pos.y, pos.z);
		boundingVolume.setMaxPoint(PVM.transform(iMax));
		boundingVolume.setMinPoint(PVM.transform(iMin));
		//System.out.println(PVM.transform(boundingVolume.getMaxPoint()));
		PVM.popMatrix();
		
		return boundingVolume;
	}
	
	public void setInitialColour(Vector3f colour){
		this.initalColour.x = colour.x;
		this.initalColour.y = colour.y;
		this.initalColour.z = colour.z;
	}
	
	public Vector3f getInitialColour() {
		// TODO Auto-generated method stub
		return initalColour;
	}
}
