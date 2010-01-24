package beta4;


	//TODO, what to do with this, considering that some of its code could be reused
	//maybe have an interface that light sources and things that see, anything that can cast a ray must use? or can I just use generic arrays like those below and rename it to 'visited' and 'with what intensity, etc.
public class LOSRay {
	/*
	private byte[][][] visibility;
	boolean[][][] canSeeThis;
	public int agentHeight;
	public int agentPerception;
	public int sightDistance;
	public AnAgent theOneDoingTheLooking;
	public int[] agentLocation;
	public double[] riseRunHoriz;
	public double[] lastCoordsXYZ;
	public byte visibilityLeft; // between 0 and 100
	TacticalEnvironment thisOne;
	
	//TODO have simply incline/decline instead of flat 2d? (taking into account objects' height, etc. should not need agent heith/perception, etc... just equivalent but non-agent specific things, like 'light level' etc, optional light falloff
	public LOSRay(boolean[][][] canSeeThisIn, byte[][][] visibilityIn, int agentHeightIn, int agentPerceptionIn, int sightDistanceIn, AnAgent theOneDoingTheLookingIn, int[] agentLocationIn, double[] riseRunIn, TacticalEnvironment thisOneIn) {
		canSeeThis = canSeeThisIn;
		visibility = visibilityIn;
		agentHeight = agentHeightIn;
		agentPerception = agentPerceptionIn;
		sightDistance = sightDistanceIn;
		theOneDoingTheLooking = theOneDoingTheLookingIn;
		agentLocation = agentLocationIn;
		riseRunHoriz = riseRunIn; //y,x
		lastCoordsXYZ = new double[3];
		//if(agentLocation != null){
			lastCoordsXYZ[0] = agentLocation[0]; 
			lastCoordsXYZ[1] = agentLocation[1]; 
			lastCoordsXYZ[2] = agentLocation[2];
		//}
		visibilityLeft = 100;
		thisOne = thisOneIn;
		//System.out.println("rise"+riseRun[0]+" run"+riseRun[1]);
	}
	/*
	 * SHOULD RAW THE ARRAY FROM the agent's start until it is either blocked fully by something, or it's reaches the sight distance.
	 *//*
	public void computeArray() {
		boolean blocked = false;
		while((distanceFromAgent() < sightDistance && !blocked) && thisOne.withinXYZBounds(new int[]{(int) Math.round(lastCoordsXYZ[0]),(int) Math.round(lastCoordsXYZ[1]),(int) Math.round(lastCoordsXYZ[2])}))
		{
			//progress line
			
			visibilityLeft -= visibility[(int) Math.round(lastCoordsXYZ[0])][(int) Math.round(lastCoordsXYZ[1])][agentLocation[2]];
			canSeeThis[(int) Math.round(lastCoordsXYZ[0])][(int) Math.round(lastCoordsXYZ[1])][agentLocation[2]] = !blocked;
			if(visibilityLeft <= 0)
				blocked = true;
			
			lastCoordsXYZ[0]/*x*/ //+=riseRunHoriz[1];
			//lastCoordsXYZ[1]/*y*/ +=riseRunHoriz[0];
		//}
	/*
	}
	private int distanceFromAgent() {
		
		return Tools.getDistance(agentLocation, new int[]{(int)lastCoordsXYZ[0],(int)lastCoordsXYZ[1],(int)lastCoordsXYZ[2]});
	}*/






private byte[][][] visibility;
private boolean[][][] canSeeThis;
private int agentHeight;
private int agentPerception;
private int sightDistance;
private HasAgency theOneDoingTheLooking;
private int[] agentLocation;
private double[] riseRunHoriz;
private double[] lastCoordsXYZ;
private byte visibilityLeft; // between 0 and 100
private static EnvironmentTactical thisOne;
private double[] riseFallVertAgentFOV;
private double[] upperLowerZFOVBounds;
private static int[] environmentBounds;
private boolean[] riseFallMaxMin;



//TODO have simply incline/decline instead of flat 2d? (taking into account objects' height, etc. should not need agent heith/perception, etc... just equivalent but non-agent specific things, like 'light level' etc, optional light falloff
public LOSRay(boolean[][][] canSeeThisIn, byte[][][] visibilityIn, int agentHeightIn/*, int agentPerceptionIn, int sightDistanceIn,*/, HasAgency theOneDoingTheLookingIn, int[] agentLocationIn, double[] riseRunInHoriz /*double[] riseFallVert  riseFallVert[0] = upper bounds of vertical vision [1] = lower bounds*/, EnvironmentTactical thisOneIn) {
	setNewEnvBounds();
	canSeeThis = canSeeThisIn;
	//riseFallVertAgentFOV = riseFallVert;
	visibility = visibilityIn;
	agentHeight = agentHeightIn;
	//agentPerception = agentPerceptionIn;
	//sightDistance = sightDistanceIn;
	theOneDoingTheLooking = theOneDoingTheLookingIn;
	agentLocation = agentLocationIn;
	riseRunHoriz = riseRunInHoriz; //y,x
	//upperLowerZFOVBounds = new double[2];
		//simulating moving .5 squares (from center to edge)
	//upperLowerZFOVBounds[0] = riseFallVert[0]/2.0 + ((double)agentHeight)/Tools.heightOfSquareInCentimeters;
	//upperLowerZFOVBounds[1] = riseFallVert[1]/2.0 + ((double)agentHeight)/Tools.heightOfSquareInCentimeters;
	lastCoordsXYZ = new double[2];
	//if(agentLocation != null){
		lastCoordsXYZ[0] = agentLocation[0]; 
		lastCoordsXYZ[1] = agentLocation[1]; 
		//lastCoordsXYZ[2] = agentLocation[2];
	//}
	visibilityLeft = 100;
	thisOne = thisOneIn;	
		//when either of these hits true, this means that vertical vision min/max has reached the bounds of the level
	riseFallMaxMin = new boolean[2];
	//System.out.println("rise"+riseRun[0]+" run"+riseRun[1]);
}
/*
 * SHOULD RAW THE ARRAY FROM the agent's start until it is either blocked fully by something, or it's reaches the sight distance.
 */
public void computeArray() {
	boolean blocked = false;
	while((distanceFromAgent() < sightDistance && !blocked) && thisOne.withinXYZBounds(new int[]{(int) Math.round(lastCoordsXYZ[0]),(int) Math.round(lastCoordsXYZ[1]),0}))
	{
		//progress line
		
		visibilityLeft -= visibility[(int) Math.round(lastCoordsXYZ[0])][(int) Math.round(lastCoordsXYZ[1])][agentLocation[2]];
		canSeeThis[(int) Math.round(lastCoordsXYZ[0])][(int) Math.round(lastCoordsXYZ[1])][agentLocation[2]] = !blocked;
		if(visibilityLeft <= 0)
			blocked = true;
		
		lastCoordsXYZ[0]/*x*/ +=riseRunHoriz[1];
		lastCoordsXYZ[1]/*y*/ +=riseRunHoriz[0];
		
			//only increase the upper bounds if it isn't already at max
		/*if(!riseFallMaxMin[0] ){
			upperLowerZFOVBounds[0] += riseFallVertAgentFOV[0];
			if(upperLowerZFOVBounds[0] > environmentBounds[2]){
				upperLowerZFOVBounds[0] = environmentBounds[2]*Tools.heightOfSquareInCentimeters;
				riseFallMaxMin[0] = true;
			}	
		}
			//only decrease lower bounds if it isn't already at max.
		if(!riseFallMaxMin[1] ){
			upperLowerZFOVBounds[1] += riseFallVertAgentFOV[1];
			if(upperLowerZFOVBounds[1] < 0){
				upperLowerZFOVBounds[1] = 0;
				riseFallMaxMin[1] = true;
			}
		}*/
		
		
	}
}
private float distanceFromAgent() {
	
	return Tools.getDistance(agentLocation, new int[]{(int)lastCoordsXYZ[0],(int)lastCoordsXYZ[1],0});
}
private static void setNewEnvBounds(){
	if(thisOne != null)
		environmentBounds = thisOne.XYZBoundsGet();
}

}
