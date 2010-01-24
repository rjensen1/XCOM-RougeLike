package beta4;

import java.awt.Color;
import java.util.LinkedList;

public class PackageAddToEnvironment {
	
	
	
	protected EnvironmentTactical currentEnvironment; //holds the connected environment
	private char representingChar;
	private Color standardColorFront;
	private Color standardColorBack;
	private String thisName; 
	private int directionFacing;
	private int locationX;
	private int locationY;
	private int locationZ;
	private int objectType;
	
	private short[] dimensions; 
	private LinkedList<LightSource> includedLights;//TODO
	private int[] lightingMatrix;//TODO

	
		//should be a superclass of ship, buildings, etc. Something to handle adding bits of a level to a level
}
