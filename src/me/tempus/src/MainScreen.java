package me.tempus.src;

import java.util.HashMap;
import java.util.Random;

import me.tempus.collada.ColladaParser;
import me.tempus.collision.AABB;
import me.tempus.gameobjects.Box;
import me.tempus.gameobjects.Geometry;
import me.tempus.shader.PVM;
import me.tempus.shader.VCI_Shader;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;

public class MainScreen {

	private boolean done = false;
	
	private VCI_Shader shader;
	
	private final Camera camera = new Camera();
	private Geometry[] sceneGeometry;
	
	//private Box box = new Box(new Vector3f(5,0,0), new Vector3f(0,0,0));
	
	private Random random = new Random();

	private Box player;
	Vector3f playerPos = new Vector3f(0,0,0);
	private final float moveSpeed = 0.5f;
	
	//final AABB playerBox = new AABB(new Vector3f(0.001f, 0.001f, 0.001f), new Vector3f(-0.001f, -0.001f, -0.001f));
	private void createScreen(int width, int height){
		try {

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
		Keyboard.enableRepeatEvents(true);
		
	}
	
	public void loop(){
		
		createScreen(640, 480);
		initOPG();
		setUpShaders();
		loadContent();
		while(!done){
			update();
			draw();
			
			
		}
		return;
	}
	
	private void loadContent() {
		//player = new Box(playerPos, new Vector3f(0,1,0));
		//player.create();
		
		final HashMap<String, me.tempus.collada.Geometry> geometryMap = (new ColladaParser()).parse("boxScene.DAE");
		sceneGeometry = new Geometry[geometryMap.size()];
		int i = 0;
		for(me.tempus.collada.Geometry g : geometryMap.values()){
			final Box geometry = new Box();
			geometry.setName(g.getId());
			geometry.setIndices(g.getIndices());
			geometry.setVertices(g.getPosistions());
			geometry.setTriangleSize(g.getTriangleCount());
			geometry.setModelMatrix(g.getModelMatrix());
			
			geometry.setPos(g.getTranslation());
			geometry.setRotation(new float[]{g.getxRotation(), g.getyRotation(), g.getzRotation()});
			geometry.setScale(g.getScale());
			
			geometry.setColour(new Vector3f((float)random.nextDouble(), (float)random.nextDouble(), (float)random.nextDouble()));
			geometry.setInitialColour(geometry.getColour());
			
			geometry.create();
			
			if(geometry.getTriangleSize() == 0){
				System.err.println("Can't have 0 triangles");
				System.exit(1);
			}
			geometry.setIDs(shader.getVBOID(geometry.getVertices(), geometry.getIndices()));
			sceneGeometry[i] = geometry;
			i++;
		}
		player = (Box) sceneGeometry[sceneGeometry.length-1];
		playerPos.y += 2;
	}

	private void setUpShaders(){
		shader = new VCI_Shader("shaders/VIC.vert", "shaders/VIC.frag");
	}
	
	private void update(){
		camera.pollInput();
		
		final boolean state = Keyboard.getEventKeyState();
		if(Keyboard.getEventKey() == Keyboard.KEY_T){
			if(state){
				playerPos.z += -moveSpeed;
			}
		}else if(Keyboard.getEventKey() == Keyboard.KEY_G){
			if(state){
				playerPos.z += moveSpeed;
			}
		}
		if(Keyboard.getEventKey() == Keyboard.KEY_H){
			if(state){
				playerPos.x += moveSpeed;
			}
		}else if(Keyboard.getEventKey() == Keyboard.KEY_F){
			if(state){
				playerPos.x += -moveSpeed;
			}
		}
		player.setPos(playerPos);
		PVM.loadIdentity();
		camera.transform();
		//playerBox.setMaxPoint(camera.getPos());
		
		for(int i = 0; i < sceneGeometry.length-1; i++){
			final Box currentBox = (Box)sceneGeometry[i];
			if(AABB.intersect(player.getAABB(), currentBox.getAABB())){
				//System.out.println("Player intersects a box at: " + ((Box)sceneGeometry[i]).boundingVolume.getMaxPoint());
				currentBox.setColour(new Vector3f(1,0,0));
			}else{
				currentBox.setColour(currentBox.getInitialColour());
			}
		}
	}
	
	private void draw(){
		
		GL20.glUseProgram(shader.getShaderID());
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		
		//player.setPos(camera.getPos());
		//player.setRotation(camera.getRotation());
		//player.draw();
		
		for(int i = 0; i < sceneGeometry.length; i++){
			final Geometry g = sceneGeometry[i];
			g.doTransformation();
			shader.setColour(g.getColour());
			shader.render(g.getVboID(), g.getIndicesID(), g.getTriangleSize());
		}
		//box.draw();
		
		GL20.glUseProgram(0);
		Display.sync(60);
		Display.update();
		
	}
	
	
	
	public static void main(String[] args){
		(new MainScreen()).loop();
	}
}
