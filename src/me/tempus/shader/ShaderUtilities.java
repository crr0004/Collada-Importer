package me.tempus.shader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ShaderUtilities {
	
	
	/**
	 * 
	 * @param vertPath Path to vertex (vert) shader source
	 * @param fragPath Path to fragment (frag) shader source
	 * @return Vertsouce and FragSouce in stringbuilder array. VertSource at posistion 0 in array
	 */
	
	public static StringBuilder[] loadShaders(String vertPath, String fragPath){
		
		StringBuilder vertSource = new StringBuilder();
		StringBuilder fragSource = new StringBuilder();
		
		try{
			File file = new File(vertPath);
			BufferedReader reader = new BufferedReader(new FileReader(vertPath));
			String line;
			while((line = reader.readLine()) != null){
				vertSource.append(line).append("\n");
			}
			reader.close();
			BufferedReader reader1 = new BufferedReader(new FileReader(fragPath));
			while((line = reader1.readLine()) != null){
				fragSource.append(line).append("\n");
			}
			reader1.close();
		}catch(Exception e){
			e.printStackTrace();
		}
				
		
		return new StringBuilder[]{vertSource, fragSource};
	}
}
