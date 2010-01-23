package beta4;

import java.util.Iterator;
import java.util.LinkedList;

import beta4.Environment.EnvironmentTactical;

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
	public enum LightSourceType {DISTANT, CIRCULAR, RECTANGULAR, POINT_SOURCE}
	
	
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
			addThis.drawLight(undrawLight, null);
			
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
		//for pointsource
		int maxDist = (int)Math.ceil(getMaxDistFromIntensityAndFallOff(intensity, thisFallOff));
		
		if(thisLightSourceType == LightSourceType.POINT_SOURCE){
			for(int i = 0; i < 4; i++){
				//check the dimensions, to make sure that none are larger than maxDist.
				if(thisDimension[i] > maxDist)
					thisDimension[i] = maxDist;
			}
		}
		
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
	public  void drawLight(boolean undrawLight, int[] xyzLocationTriplet){
		//System.out.println(LightSource.allLights.size()+ "num lights");
		
		boolean withinDistance = false;
		if(xyzLocationTriplet != null && withinThisFieldOfAffect(xyzLocationTriplet)){
			//if(/*distanceCalculate(xyzLocationTriplet, thisXYZLocation) <= (int)Math.ceil(getMaxDistFromIntensityAndFallOff(intensity, thisFallOff)) && */)
				withinDistance = true;
		}
		
		
		if(xyzLocationTriplet == null || withinDistance || (isOn && !hasBeenDrawn))
		{
			int negPos = 1;
			
			if(undrawLight){
				negPos = -1;
			}
			if((undrawLight && hasBeenDrawn) || (!undrawLight && !hasBeenDrawn))
			{
				
				
				hasBeenDrawn = !hasBeenDrawn;
				
				
				switch(thisLightSourceType){
				case RECTANGULAR:
					//the rectangle has its coord at the top left
					//only one dimension, any with fall off will draw to the boundaries
					for(int x = thisXYZLocation[0]; x < thisXYZLocation[0]+thisDimension[0]; x++){
						for(int y = thisXYZLocation[1]; y < thisXYZLocation[1]+thisDimension[1]; y++){
							
							int currentIntensity = getItensity(distanceCalculate(new int[]{x,y}, thisXYZLocation), intensity, thisFallOff);
							
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
							
							int currentIntensity = getItensity(distanceCalculate(new int[]{x,y}, thisXYZLocation), intensity, thisFallOff);
						
						if(currentIntensity >0)
							addLightToMatrix(new int[]{x,y}, currentIntensity*negPos);
						}
					}
					break;
				case POINT_SOURCE:
					/*
					 * 4 dimensions for distance, left, right, up, down.
					 */
	
					
					int[] environmentBounds = thisStaticEnvironment.XYZBoundsGet();;
					
					
					int maxDist = (int)Math.ceil(getMaxDistFromIntensityAndFallOff(intensity, thisFallOff));
					
				
					
					
					
					boolean contin = (thisXYZLocation != null) && thisStaticEnvironment.withinXYZBounds(thisXYZLocation);
					if(contin){
						int xMin = thisXYZLocation[0] - thisDimension[0];
						int xMax = thisXYZLocation[0] + thisDimension[1];
						int yMin = thisXYZLocation[1] - thisDimension[2];
						int yMax = thisXYZLocation[1] + thisDimension[3];
						if(xMin < 0)
							xMin = 0;
						if(yMin < 0)
							yMin = 0;
						if(xMax > environmentBounds[0])
							xMax = environmentBounds[0];
						if(yMax > environmentBounds[1])
							yMax = environmentBounds[1];
						
						//keep track of cells so that we don't draw to the same cell twice.
						boolean tempDrawMatrixAlreadyLit[][][] = new boolean[environmentBounds[0]][environmentBounds[1]][environmentBounds[2]];
						
						for(int x = xMin; x < xMax; x++){
							for(int y = yMin; y < yMax; y++){
								
								boolean continRay = true;
						
								if(!tempDrawMatrixAlreadyLit[x][y][0] /*&& (actor.locationXYZTripletGet() != new int[]{x,y,0}) && continRay*/){
									//if we can already see this spot, ignore!
									float currentDistance = Tools.getDistance(thisXYZLocation, new int[]{x,y,0});
									if( currentDistance <= maxDist){
										//if we're out of range, don't even bother!
										
										double[] riseRun = Tools.getSlopeAndDistance(new double[]{thisXYZLocation[0]+.5, thisXYZLocation[1]+.5}, new double[]{x+.5,y+.5});
										double[] rayLoc = new double[]{thisXYZLocation[0]+.5, thisXYZLocation[1]+.5};
										
										float distance = 0;
										boolean blocked = false;
										while(continRay){
											rayLoc[0] += riseRun[0];
											rayLoc[1] += riseRun[1];
											if(!thisStaticEnvironment.withinXYZBounds((int)rayLoc[0], (int)rayLoc[1], 0)){
												continRay = false;
											}else{
												distance += riseRun[2];
												blocked = thisStaticEnvironment.blocksLight(new int[]{(int)rayLoc[0],(int)rayLoc[1],0}, null);
												
												int currentIntensity = getItensity(distanceCalculate(new int[]{(int)rayLoc[0],(int)rayLoc[1]}, thisXYZLocation), intensity, thisFallOff);
												
												
												//don't add/subtract more light if we've already added to this spot.
												if(currentIntensity > 0 && !tempDrawMatrixAlreadyLit[(int)rayLoc[0]][(int)rayLoc[1]][0]){
													addLightToMatrix(new int[]{(int)rayLoc[0],(int)rayLoc[1]}, currentIntensity*negPos);
													tempDrawMatrixAlreadyLit[(int)rayLoc[0]][(int)rayLoc[1]][0] = true;
												}
												
												
													//how does it know if a ray should stop
												if(blocked || ((int)distance >= maxDist ) || ((int)rayLoc[0] == x && (int)rayLoc[1] == y)){
													continRay = false;
												}
												else{
													//System.out.println("distance "+distance);
													if(riseRun[0] == 0 && riseRun[1] == 0)
														continRay=false;
												}
											}
										}
									}
									
								}
							}
						}
					}
				default:
					break;
				
				}
			}
		}
		
		
		
	}
	/*
	 * should return true if this location is within the range of this light, false otherwise
	 */
	private boolean withinThisFieldOfAffect(int[] xyzLocationTriplet) {
		boolean retVal = true;
		
		if(thisStaticEnvironment.withinXYZBounds(xyzLocationTriplet)){
			switch(thisLightSourceType){
				case CIRCULAR:
					
					for(int i = 0; i < thisDimension.length; i++){
					
							if(!(xyzLocationTriplet[i] >= this.thisXYZLocation[i]- thisDimension[i] && xyzLocationTriplet[i] <= this.thisXYZLocation[i]+ thisDimension[i]))
							{
								retVal = false;
							}
					}
					
					break;
				case DISTANT:
					retVal = false;
					break;
				case POINT_SOURCE:
					
					int xMin = thisXYZLocation[0] - thisDimension[0];
					int xMax = thisXYZLocation[0] + thisDimension[1];
					int yMin = thisXYZLocation[1] - thisDimension[2];
					int yMax = thisXYZLocation[1] + thisDimension[3];
					
					
					 if(!((xyzLocationTriplet[0] >= xMin && xyzLocationTriplet[0] < xMax) && (xyzLocationTriplet[1] >= yMin && xyzLocationTriplet[1] < yMax))){
						 retVal = false;
					 }
							
					
					
					break;
				case RECTANGULAR:
					
					for(int i = 0; i < thisDimension.length; i++){
						if(!(xyzLocationTriplet[i] >= this.thisXYZLocation[i] && xyzLocationTriplet[i] < this.thisXYZLocation[i]+ thisDimension[i]))
						{
							retVal = false;
						}
					}
					
					break;
					default:
						break;
			}
		}
		else
			retVal = false;
		return retVal;
	}
	/*
	 * should return the maximum distance that light will travel based on the intensity and the falloff type
	 * no falloff means -1
	 */
	private double getMaxDistFromIntensityAndFallOff(int intensity, FallOffType thisFallOff) {
		double distance = 0;
		switch(thisFallOff){
		case INVERSE_SQUARE:
			//need some sort of threshhold, since won't actually reach 0. Perhaps .5?
			double threshHold = .5;
			int originalIntensity = intensity;
			
			while(intensity > threshHold){
				distance +=1; //origin intensity is at 1 distance
		
				intensity = (int) (originalIntensity * (1.0/(distance*distance*1.0)));
				
			}
			break;
		case LINEAR_FAST:
			distance = (double)intensity/(double)linearFast;
			break;
		case LINEAR_SLOW:
			distance = (double)intensity/(double)linearSlow;
			break;
		case NONE:
			distance = -1;
			break;
		default:
			
		
		}
		return distance;
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
			
		}
		
	}
		
	
	public static void drawAllLights(boolean undrawLights, int[] xyzLocationTriplet/* to draw/undraw all lights to a specific location*/){
		if(allLights != null){
			Iterator<LightSource> tempIt = allLights.iterator();
			while(tempIt.hasNext()){
				tempIt.next().drawLight(undrawLights, xyzLocationTriplet);
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
	private static int getItensity(float distance, int originalIntensity, FallOffType thisFallOff){
		int retVal = originalIntensity;
		
		switch(thisFallOff){
		case INVERSE_SQUARE:
			if(distance > 1){
				
				retVal = (int) (originalIntensity * (1.0/(distance*distance*1.0)));
			}
			break;
		case LINEAR_FAST:
			retVal -= (int)(distance*linearFast);
			break;
		case LINEAR_SLOW:
			retVal -= (int)(distance*linearSlow);
			break;
		case NONE:
			//no change
			break;
			default:
				
			
		}
		return retVal;
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
						tempLight.drawLight(true, null);
						tempIt.remove();
						done = true;
					}
				}
				
			}
		}
		
	}
}
