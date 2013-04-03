package me.tempus.src;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private boolean done = false;
	
	public NoLightingShader shader;
	
	final Box box = new Box(new Vector3f(0,0,-15f));
	
	private void createScreen(int width, int height){
		try {

			//Display.setFullscreen(fullscreen);

			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle("Camera");
				Display.create();
			
			GL11.glViewport(0, 0, width, height);

			GL11.glEnable(GL11.GL_TEXTURE_2D);                          // Enable Texture Mapping
			
			GL11.glShadeModel(GL11.GL_SMOOTH);                          // Enables Smooth Color Shading
			GL11.glClearColor(0.5f, 0.5f, 0.5f, 0.0f);                // This Will Clear The Background Color To Black
			GL11.glClearDepth(1.0);                                   // Enables Clearing Of The Depth Buffer
			GL11.glEnable(GL11.GL_DEPTH_TEST);                          // Enables Depth Testing
			GL11.glDepthFunc(GL11.GL_LEQUAL);                           // The Type Of Depth Test To Do
			PVM.setUpProjection(45f, width, height, 0.1f, 100f);
			System.out.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initOPG(){
		
	}
	
	public void loop(){
		
		createScreen(800, 600);
		initOPG();
		setUpShaders();
		
		box.create();
		while(!done){
			update();
			draw();
			
		}
		return;
	}
	
	private void setUpShaders(){
		shader = new NoLightingShader("1.vert", "NoLighting.frag");
		shader.loadShaders();
	}
	
	private void update(){
		
		
		
	}
	
	private void draw(){
		
		GL20.glUseProgram(shader.getShaderID());
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		box.draw();
		
		GL20.glUseProgram(0);
		Display.sync(60);
		Display.update();
		
	}
	
	
	
	public static void main(String[] args){
		(new Camera()).loop();
	}
}
