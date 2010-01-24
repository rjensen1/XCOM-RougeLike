package beta4;

import java.util.Iterator;
import java.util.LinkedList;

public class LightSource {
	
		//the maximum that a lightmatrix square can have
	public static final int maxLightBrightness = 100;
	public static int[][][] lightingMatrix;
	private static EnvironmentTactical thisStaticEnvironment;
	private static LinkedList<LightSource> allLights;
	private boolean hasBeenDrawn;
	
	public enum FallOffType {NONE, LINEAR_FAST, LINEAR_SLOW, INVERSE_SQUARE}
		//5 units per change in square
	public static final int linearFast = 10;
	public static final int linearSlow = 5;
		//a fallOffYpe of none will only illuminate its dimensions, with no falling lightsource outside of this
	public enum LightSourceType {DISTANT, CIRCULAR, RECTANGULAR, FIELD}
	
	
	private int intensity;
	
	private EnvironmentTactical thisEnvironment;
	private boolean isOn;
	private boolean canBeTurnedOff;
	private FallOffType thisFallOff;
	private LightSourceType thisLightSourceType;
	private int[] thisXYZLocation;
	private int[] thisDimension;
	
	
	public static void lightingMatrixInitialize(EnvironmentTactical thisStaticEnvironmentNew){
		thisStaticEnvironment = thisStaticEnvironmentNew;
		if(thisStaticEnvironment != null){
			int[] tempBounds =  thisStaticEnvironment.XYZBoundsGet();
			lightingMatrix = new int[tempBounds[0]][tempBounds[1]][tempBounds[2]];
			//initializeLightingMatrixArbitrary();
			allLights = new LinkedList<LightSource>();
			
		}
		else{
			lightingMatrix = null;
		}
	}
	/*
	 * just giving it a value
	 */
	private static void initializeLightingMatrixArbitrary() {
		for(int z = 0; z < lightingMatrix[0][0].length; z++){
			for(int y = 0; y < lightingMatrix[0].length; y++){
				for(int x = 0; x < lightingMatrix.length; x++){
					lightingMatrix[x][y][z] = 10;
				}
			}
		}
		
	}
	public static void lightSourceAdd(LightSource addThis){
		
		if(allLights != null){
			allLights.add(addThis);
			boolean undrawLight = false;
			addThis.drawLight(undrawLight);
			
		}
		else
			System.out.println("error adding light source, must be first initialized");
	}
	
	/*
	 * use this to get the lighting matrix, which will be applied to the display array
	 */
	public static int[][][] lightingMatrixGet(){
		return lightingMatrix;
	}
	
	public LightSource(int intensity, int[] thisDimension, int[] xyzLocation, boolean isOn, boolean canBeTurnedOff, FallOffType thisFallOff, LightSourceType thisLightSourceType)
	{
		this.thisDimension = thisDimension;
		this.thisXYZLocation = xyzLocation;
		intensitySet(intensity);
		this.isOn = isOn;
		this.canBeTurnedOff = canBeTurnedOff;
		this.thisFallOfSet(thisFallOff);
		this.thisLightSourceType = thisLightSourceType;
	}
	public int intensityGet() {
		return intensity;
	}
	public void intensitySet(int intensity) {
		this.intensity = intensity;
	}
	public boolean isOnGet() {
		return isOn;
	}
	public void setOnSet(boolean isOn) {
		this.isOn = isOn;
	}
	public EnvironmentTactical thisEnvironmentGet() {
		return thisEnvironment;
	}
	public void thisEnvironmentSet(EnvironmentTactical thisEnvironment) {
		this.thisEnvironment = thisEnvironment;
	}
	public void canBeTurnedOffSet(boolean canBeTurnedOff) {
		this.canBeTurnedOff = canBeTurnedOff;
	}
	
	public boolean CanBeTurnedOffGet() {
		return canBeTurnedOff;
	}
	/*
	 * this will draw a light, or undraw one if 'undrawlight' is true
	 */
	//public  void drawLight(boolean undrawLight,int[] xyzLocationTriplet){
		
	//}
		//else, do nothing, the light is off
	public  void drawLight(boolean undrawLight){
		System.out.println(LightSource.allLights.size()+ "num lights");
		int negPos = 1;
		
		if(undrawLight){
			negPos = -1;
		}
		if((undrawLight && hasBeenDrawn) || (!undrawLight && !hasBeenDrawn))
		{
			boolean noFallOff = false;
			boolean linearFallOffFast = false;
			boolean linearFallOffSlow = false;
			boolean IQFallOff = false;
			
			hasBeenDrawn = !hasBeenDrawn;
			
			switch(thisFallOff){
			case NONE:
				noFallOff = true;
				break;
			case LINEAR_FAST:
				linearFallOffFast = true;
				break;
			case LINEAR_SLOW:
				linearFallOffSlow = true;
				break;
			case INVERSE_SQUARE:
				IQFallOff = true;
				break;
			default:
				return;
			}
			switch(thisLightSourceType){
			case RECTANGULAR:
				//the rectangle has its coord at the top left
				//only one dimension, any with fall off will draw to the boundaries
				for(int x = thisXYZLocation[0]; x < thisXYZLocation[0]+thisDimension[0]; x++){
					for(int y = thisXYZLocation[1]; y < thisXYZLocation[1]+thisDimension[1]; y++){
						
						int currentIntensity = intensity;
							//only check for falloff modifiers if there is one 
						if(!noFallOff){
							if(linearFallOffFast){
								currentIntensity = LightSource.falloffLinearFast(distanceCalculate(new int[]{x,y}, thisXYZLocation), intensity);
							}
							else if(linearFallOffSlow){
								currentIntensity = LightSource.falloffLinearSlow(distanceCalculate(new int[]{x,y}, thisXYZLocation), intensity);
							}
							else if(IQFallOff){
								currentIntensity = LightSource.falloffISQ(distanceCalculate(new int[]{x,y}, thisXYZLocation), intensity);
							}
							
						}
						
						if(currentIntensity >0)
							addLightToMatrix(new int[]{x,y}, currentIntensity*negPos);
					}
				}
				break;
			case CIRCULAR:
				//the circle starts with the coords at its center
				//only one dimension
				for(int x = thisXYZLocation[0]-thisDimension[0]; x <= thisXYZLocation[0]+thisDimension[0]; x++){
					for(int y = thisXYZLocation[1]-thisDimension[1]; y <= thisXYZLocation[1]+thisDimension[1]; y++){
						
						int currentIntensity = intensity;
						//only check for falloff modifiers if there is one 
					if(!noFallOff){
						if(linearFallOffFast){
							currentIntensity = LightSource.falloffLinearFast(distanceCalculate(new int[]{x,y}, thisXYZLocation), intensity);
						}
						else if(linearFallOffSlow){
							currentIntensity = LightSource.falloffLinearSlow(distanceCalculate(new int[]{x,y}, thisXYZLocation), intensity);
						}
						else if(IQFallOff){
							currentIntensity = LightSource.falloffISQ(distanceCalculate(new int[]{x,y}, thisXYZLocation), intensity);
						}
						
					}
					if(currentIntensity >0)
						addLightToMatrix(new int[]{x,y}, currentIntensity*negPos);
					}
				}
				break;
			default:
				break;
			
			}
		}
		
		
		
	}
	private void addLightToMatrix(int[] xyzLoc, int intensity){
		boolean safeToDraw = true;
		for(int numDim = 0; numDim < xyzLoc.length; numDim++){
			if(( xyzLoc[numDim] < 0) || (xyzLoc[numDim] >= lightingMatrix[numDim].length))
				safeToDraw = false;
		}
		if(safeToDraw){
			//draw! should we allow negative total light?
			lightingMatrix[xyzLoc[0]][xyzLoc[1]][/*xyzLoc[2]*/0] += intensity;
			
			
				//for some reason, the following code causes problems with artifacting the light
			//now make sure we aren't negative or beyond max light brightness
			//if(lightingMatrix[xyzLoc[0]][xyzLoc[1]][/*xyzLoc[2]*/0] < 0)
				//lightingMatrix[xyzLoc[0]][xyzLoc[1]][/*xyzLoc[2]*/0] = 0;
			//else if (lightingMatrix[xyzLoc[0]][xyzLoc[1]][/*xyzLoc[2]*/0] > maxLightBrightness)
				//lightingMatrix[xyzLoc[0]][xyzLoc[1]][/*xyzLoc[2]*/0] = maxLightBrightness;
		}
		
	}
		
	
	public static void drawAllLights(boolean undrawLights/*, int[] xyzLocationTriplet to draw/undraw all lights to a specific location*/){
		if(allLights != null){
			Iterator<LightSource> tempIt = allLights.iterator();
			while(tempIt.hasNext()){
				tempIt.next().drawLight(undrawLights/*, xyzLocationTriplet*/);
			}
		}
	}
	private void thisFallOfSet(FallOffType thisFallOff) {
		this.thisFallOff = thisFallOff;
	}
	private FallOffType thisFallOffGet() {
		return thisFallOff;
	}
	/*
	 * used for finding the distance of two xy coordinates
	 */
	private static float distanceCalculate(int[] firstCoord, int[] secondCoord){
		return Tools.getDistance(firstCoord, secondCoord);
	}
	
	private static int falloffLinearFast(float distance, int originalIntensity){
		return originalIntensity -= distance*linearFast;
		
	}
	private static int falloffLinearSlow(float distance, int originalIntensity){
		return originalIntensity -= distance*linearSlow;
	}
	private static int falloffISQ(float distance, int originalIntensity){
		float retVal = originalIntensity;
			//do nothing if 1 or closer to source
		if(distance > 1){
			
			retVal *= 1.0/(distance*distance*1.0);
		}
		
		return (int) (retVal);
	}
	public void setLocationXYZ(int [] thisNewXYZLocation){
		//maybe have it automatically redraw if certain conditions are right?
		thisXYZLocation = thisNewXYZLocation;
		
	}
	public static void removeLight(LightSource thisLight) {
		if(allLights != null){
			Iterator<LightSource> tempIt = allLights.iterator();
			
			boolean done = false;
			
			while(tempIt.hasNext() || done){
				LightSource tempLight = tempIt.next();
				
				if(thisLight == tempLight){
					//undraw and then delete
					if(tempLight.isOn){
						tempLight.drawLight(true);
						tempIt.remove();
						done = true;
					}
				}
				
			}
		}
		
	}
}
