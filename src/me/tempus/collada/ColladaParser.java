/**
 * 
 */
package me.tempus.collada;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Chris
 * 
 */
public class ColladaParser extends DefaultHandler {

	private String tempValue;
	private boolean startStoring = false;
	private Geometry geometry;
	private Node currentNode;
	
	private Attributes tempAttributes;

	private final HashMap<String, Geometry> geometryMap = new HashMap<String, Geometry>();
	private char[] tempChars;

	public HashMap<String, Geometry> parse(String filePath) {
		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			saxParser.parse(new File(filePath), this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return geometryMap;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */

	public void startElement(String uri, String localName, String elementName,
			Attributes attributes) throws SAXException {

		if (elementName == "geometry") {
			//final String[] idValues = attributes.getValue("id").split("-");
			geometry = new Geometry(attributes.getValue("name"));
		}else if(elementName == "node"){
			currentNode = new Node(attributes.getValue("name")); // Name of each node, used for referencing
		}else if (elementName == "float_array") {
			final String id = tempAttributes.getValue("id");
			final String[] idElements = id.split("-");
			//System.out.println(idElements[1]);
			if (idElements[1].equalsIgnoreCase("POSITIONS")) {
				//startStoring = true;
			}
		}else if(elementName == "triangles"){
			geometry.setTriangleCount(Integer.parseInt(attributes.getValue("count")));
		}
		tempAttributes = attributes;
	}

	public void endElement(String uri, String localName, String elementName)
			throws SAXException {
		// Code for all cases of the elements you'll find and use temp value for
		// there contents

		if (elementName == "geometry") { // Finished the geometry so time to store it			
			geometryMap.put(geometry.getId(), geometry); // Name and the structure
			/**
			 * Pulls the data for the vertices of the geometry
			 */
		} else if (elementName == "float_array") {
			final String id = tempAttributes.getValue("id");
			final String[] idElements = id.split("-");
			if (idElements[2].equalsIgnoreCase("POSITIONS")) { // Turns the position array into a float array
				final int count = Integer.parseInt(tempAttributes.getValue("count"));
				/**
				 * Used for pulling out new line characters of values
				String[] posistionString = tempValue.split(" ");
				StringBuilder newPosistions = new StringBuilder();
				for(int i = 0; i < posistionString.length; i++){
					String c = posistionString[i];
					if(c.equals("\n")){
						c = c.replace('\n', '\0');
					}else if(c.contains("\n")){
						c = c.replaceAll("[\n\r]", " ");
						newPosistions.append(" " + c);
					}else if(!c.contains(" ")){
						newPosistions.append(" " + c);
					}
					
				}
				*/
				/**
				 * Separates the positions by a space
				 * Then goes through each value and puts it into an array
				 */
				final String[] posistionString = tempValue.split(" ");
				final float[] posistions = new float[count];
				for (int i = 0; i < count/3; i++) {
					String c = posistionString[(i*3)+0]; // X Axis
					posistions[(i*3) + 0] = Float.parseFloat(c);
					c = posistionString[(i*3)+2]; // Y Axis Swaps with Z
					posistions[(i*3) + 1] = Float.parseFloat(c);
					c = posistionString[(i*3)+1]; // Z Axis Swaps with Y
					posistions[(i*3) + 2] = Float.parseFloat(c) * -1;
				}
				geometry.setPosistions(posistions);
				//startStoring = false;
			}
			/**
			 * Pulls the data for the indices of the geometry
			 * Should use the offset data from the semantics
			 * Hard coded for first value of vertex is position
			 */
		} else if (elementName == "p") {
			final String[] values = tempValue.split(" ");
			final int[] indices = new int[geometry.getTriangleCount()*3];
			for (int i = 0; i < geometry.getTriangleCount()*3; i++) {
				indices[i] = Integer.parseInt(values[(i*3)]);
			}
			geometry.setIndices(indices);
			/**
			 * Pulls the data for the model matrix (pos, rotation, scale) of the geometry
			 * If there is no matrix then there must be standalone vectors (translate, rotation and scale)
			 */
		}else if(elementName == "matrix" && currentNode != null){
			final String[] splitTempValues = tempValue.split(" ");
			final float[] matrix = new float[splitTempValues.length];
			for(int i = 0; i < matrix.length; i++){
				matrix[i] = Float.parseFloat(splitTempValues[i]);
			}
			geometryMap.get(currentNode.getId()).setModelMatrix(matrix);
		}else if(elementName == "rotate" && currentNode != null){
			final String[] splitTempValues = tempValue.split(" ");
			final float rotationValue = Float.parseFloat(splitTempValues[3]);
			final int x = Math.abs(Integer.parseInt(splitTempValues[0]));
			final int y = Math.abs(Integer.parseInt(splitTempValues[1]));
			final int z = Math.abs(Integer.parseInt(splitTempValues[2]));
			if(x == 1){
				geometryMap.get(currentNode.getId()).setRotationX(rotationValue);
			}
			if(y == 1){
				geometryMap.get(currentNode.getId()).setRotationY(rotationValue);
			}
			if(z == 1){
				geometryMap.get(currentNode.getId()).setRotationZ(rotationValue);	
			}
		}else if(elementName == "translate" && currentNode != null){
			final String[] splitTempValues = tempValue.split(" ");
			final float[] vector = new float[3];
			vector[0] = Float.parseFloat(splitTempValues[0]);
			vector[1] = Float.parseFloat(splitTempValues[2]);
			vector[2] = Float.parseFloat(splitTempValues[1]);
			geometryMap.get(currentNode.getId()).setTranslation(vector);
		}else if(elementName == "scale" && currentNode != null){
			final String[] splitTempValues = tempValue.split(" ");
			final float[] vector = new float[3];
			for(int i =0; i < vector.length; i++){
				vector[i] = Float.parseFloat(splitTempValues[i]);
			}
			geometryMap.get(currentNode.getId()).setScale(vector);
		}
	}

	public void characters(char ch[], int start, int length)
			throws SAXException {
		/**
		 * Value storing is used because it only stores each line so
		 * to store multiline data when you need to store each line
		 */
		
		if(startStoring){
			//tempValue += new String(ch, start, length-1); // Contents of the element
		}else{
			tempValue = new String(ch, start, length);
		}
		tempChars = ch;
	}

	/**
	 * @param args
	*/ 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ColladaParser().parse("boxScene.DAE");
	}
	
}
