package beta4.Environment;

import java.util.LinkedList;

public class EnvironmentRelatedLightSource {	
		//would hold int values for how much light is at each square... could be refrenced directly from environment while drawing image, to lighten/darken things.
	private static int[][][] currentWorldLightMap;
	private static LinkedList<EnvironmentRelatedLightSource> currentLights;
	private static EnvironmentTactical thisEnvironment;
	
		//set the environment
	public static void setEnvironment(EnvironmentTactical newOne){
		thisEnvironment = newOne;
		//reset everything
		reset();
	}
		//clear everything, or instantiate it
	private static void reset(){
		if(thisEnvironment != null){
			int[] dimensions = thisEnvironment.XYZBoundsGet();
			currentWorldLightMap = new int[dimensions[0]][dimensions[1]][dimensions[2]];
			currentLights = new LinkedList<EnvironmentRelatedLightSource>();
		}else{
				//cleaar out all the static stuff.
			currentWorldLightMap = null;
			currentLights = null;
			thisEnvironment = null;
		}
	}
	public static boolean lightSourceAdd(EnvironmentRelatedLightSource toAdd){
		if(toAdd != null && currentLights != null){
			
			currentLights.add(toAdd);
			
			return true;
		}
		return false;
	}
	/*
	 * maybe have links to the outdoor light source available to buildings? so that they can have internal ambiant light in various rooms based on the outdoor lighting.
	 */
	
	private int xLoc;
	private int yLoc;
	private int zLoc;
		//
	private int intensity;	
		//lights can aim wherever, could even pan
	private int degreeFacing;
		//use same/similar raycasting as agent sight
	private int degreeFOV;
		//I suppose they could be turned on/off
	private boolean isOn;
	
		//would have a position, but would not cast rays via LOSRay, would just be hazy light... would not cast shadows.
	private boolean isAmbiant;
		
		//if set to false then have some other way to regulate how far the beams go?
	private boolean hasInverseSquareFallout;
		//TODO a type of light source that is rectangular, or is done via flood-fill (perhaps in some buildings?)
		//these could also be stored/connected to objects/environment tiles and turned on/off
	public EnvironmentRelatedLightSource(){
		xLoc = -1;
		yLoc = -1;
		zLoc = -1;
		intensity = -1;	
		degreeFacing = -1;
		degreeFOV = -1;
		isOn = false;
		isAmbiant = false;
		hasInverseSquareFallout = false;
	}
	public EnvironmentRelatedLightSource(int xLoc, int yLoc, int zLoc, int intensity, int degreeFacing, int degreeFOV, boolean isOn, boolean isAmbiant, boolean hasInverseSquareDropoff){
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		this.zLoc = zLoc;
		this.intensity = intensity;	
		this.degreeFacing = degreeFacing;
		this.degreeFOV = degreeFOV;
		this.isOn = isOn;
		this.isAmbiant = isAmbiant;
		this.hasInverseSquareFallout = hasInverseSquareFallout;
	}
	/*
	 * should be used before any changes are made to the light, 
	 */
	private boolean undoLightLevel(){
		boolean inReverse = true;
		drawLights(inReverse);
		return true;
	}
	/*
	 * use this method to actually render a light, input will always be false, unless we were deleting a light/changing its position
	 */
	private boolean drawLights(boolean areWeSubtractingTheseLights){
		//some case/switch stuff for the different kinds of lights... 
		
		
		return true;
	}
	public static boolean updateThisLocation(int[] xyzLoc, boolean subtract){
		//use this when moving things
		/*
		 * subtract = true means that this should undraw the lightsource aimed at this location, and also make sure it gets to the borders
		 * false, means to draw it normall, to the borders.
		 */
		 //TODO make sure that this is ALWAYS called by the TactEnvironment class and at the proper times.
		return false;
	}

}
