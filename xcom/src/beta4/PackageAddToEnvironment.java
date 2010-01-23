package beta4;

import java.util.LinkedList;

import beta4.Environment.Environment;
import beta4.Environment.EnvironmentEquipment;
import beta4.Environment.EnvironmentObject;



public class PackageAddToEnvironment extends Environment{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EnvironmentObject representsWhenZoomedOut;
	
	public EnvironmentEquipment thisEquipment;
	
	//to deal with later
	public static LinkedList<LightSource> allLights;
	

	
		//should be a superclass of ship, buildings, etc. Something to handle adding bits of a level to a level
	
	
	public PackageAddToEnvironment(int xDimNew, int yDimNew, int zDimNew, EnvironmentObject toBeRepresented) {
		super(xDimNew, yDimNew, zDimNew);
		
		this.representsWhenZoomedOut = toBeRepresented;
	}
}
