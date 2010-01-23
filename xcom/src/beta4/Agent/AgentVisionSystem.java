package beta4.Agent;

import java.util.Iterator;
import java.util.LinkedList;

import beta4.HasAgency;
import beta4.LightSource;
import beta4.Tools;
import beta4.Environment.EnvironmentObject;
import beta4.Environment.EnvironmentTactical;

public class AgentVisionSystem {
	
	
	private HasAgency actor;
	
	/*
	 * things that are just set to null at creation
	 */
	public static boolean[][][] everyoneCanRememberThisTile;
	
	
		//these three hold things that would be targetable AND would show up on the display, but are available to only this agent (could overlap with above)
	private  LinkedList<HasAgency> iCanSeeThisNeutralNPC;
	private  LinkedList<HasAgency> iCanSeeThisFoe;
	private  LinkedList<HasAgency> iCanSeeThisFriendlyAgent;
	

	
	public boolean[][][] canSeeThisTile;
	public boolean[][][] canRememberThisTile; //holds boolean for places that it's seen
		//all units have read/write access to this unless they are radiosilent.
	private boolean canRemember;
	public boolean canSeeOtherUnitsVisionAlways;
	private boolean canShareVision;
	private int sightDistance;
	private int minIntensityForVision;
	private double[] stanceFOVAffectArray;
	private int agentFOVHoriz;
	
	
	public AgentVisionSystem(HasAgency actor){
		
		this.actor = actor;
		resetCanSeeAgency();
		
		//these three hold things that would be targetable AND would show up on the display, but are available to only this agent (could overlap with above)
		 
		 minIntensityForVision = 5;
		 sightDistance = 5;
		 canShareVision = true;
	}

	/*
	 * should be used when updating/changing stuff, like if the LOS is being recalculated
	 */
	private void resetCanSeeAgency() {
		iCanSeeThisNeutralNPC = new LinkedList<HasAgency>();
		iCanSeeThisFoe = new LinkedList<HasAgency>();
		iCanSeeThisFriendlyAgent = new LinkedList<HasAgency>();
		
	}


	public void HUDRefresh() {
		// TODO Auto-generated method stub
		
	}
	/*
	 * resets the memory array... should do this whenever changing environments.
	 */
	private void resetMemoryBoolArray() {
		int[] tempBounds = actor.currentEnvironment.XYZBoundsGet();
		canRememberThisTile = new boolean[tempBounds[0]][tempBounds[1]][tempBounds[2]];
		
	}
	private void resetCollectiveMemoryBoolArray(){
		int[] tempBounds = actor.currentEnvironment.XYZBoundsGet();
		everyoneCanRememberThisTile = new boolean[tempBounds[0]][tempBounds[1]][tempBounds[2]];
	}
	private void resetCanSeeBoolArray(){
		int[] tempXYZ = actor.currentEnvironment.XYZBoundsGet();
		this.canSeeThisTile = new boolean[tempXYZ[0]][tempXYZ[1]][tempXYZ[2]];
	}
	//should return the look degree for the z-level, with straight out, parallel to ground bin 90deg
	//[0] = down [1] = up
	/*
	public int[] degreeFOVRangeVertGet(){
		return new int[]{90 + agentFOVVertGet()/2, 90 - agentFOVVertGet()/2};
	}
	*/
	
	
	/*
	 * returns sight and memory combined into a byte array... with 0 being comlpletely dark, 1 being explored and 2 being 'currently seen. Can also be adjusted in the future so that brightness is taken into affect.
	 */
	/*
	private byte[][][] whatThisAgentCanSeeByteVersionGet() {
		
		byte[][][] tempArr = new byte[canSeeThisTile.length][canSeeThisTile[0].length][canSeeThisTile[0][0].length];
		int[] dim = actor.currentEnvironment.XYZBoundsGet();
		for(int x = 0; x < dim[0]; x ++){
			for(int y = 0; y < dim[1]; y++){
				for(int z = 0; z < dim[2]; z++){
					if(canSeeThisTile[x][y][z]){
						tempArr[x][y][z] = 2;
					}
					else if(canRememberThisTile[x][y][z]){
						tempArr[x][y][z] = 1;
					}
					else
						tempArr[x][y][z] = 0;
				}
			}
		}
		return tempArr;
	}*/

	
	
	//[0] = left [1] = right
	/*
	public int[] degreeFOVRangeHorizGet(){
		int[] retVal = new int[2];
		
		int lookDeg = actor.lookDirectionDegreesGet();
		retVal[0] = lookDeg - agentFOVHorizGet()/2;
		retVal[1] = lookDeg + agentFOVHorizGet()/2;
		/*
		for(int i = 0; i < 2; i ++){
			while(retVal[i] < 0){
				retVal[i] += 360;
			}
			while(retVal[i] >= 360){
				retVal[i] -= 360;
			}
		}
		
		return retVal;
	}*/
	/*
	 * gets the effective field of view, which changes based on stance and perhaps a skill. 
	 */
	
	private int agentFOVHorizGet() {
		return (int)Math.ceil(agentFOVHoriz * stanceFOVAffectArray[actor.theseStats.getStance()]);
	}
	


	public int agentSightDistanceGet() {
		
		return sightDistance;
	}
	public boolean canThisUnitSeeAllOtherUnitsAlwaysGet(){
		return canSeeOtherUnitsVisionAlways;
	}
	/*
	 * Set the personalHistory
	 */
	
	public boolean[][][] whatThisAgentCanSeeGet(){
		return canSeeThisTile;
	}
	public void whatThisAgentCanSeeCalculate(){
		
		this.resetCanSeeBoolArray();
		 
		int minLight = minIntensityForVision;
		int maxDist = sightDistance;
		int[] locAgent = actor.locationXYZTripletGet();;
		
		int[][][] lightMatrix  = LightSource.lightingMatrix; ;
		int[] environmentBounds = actor.currentEnvironment.XYZBoundsGet();;
		
		
		boolean contin = ((LightSource.maxLightBrightness > minLight) && (minLight > -1 ) &&(maxDist > -1) && (locAgent != null) && actor.currentEnvironment.withinXYZBounds(locAgent));
		if(contin){
			int xMin = locAgent[0] - maxDist;
			int xMax = locAgent[0] + maxDist;
			int yMin = locAgent[1] - maxDist;
			int yMax = locAgent[1] + maxDist;
			if(xMin < 0)
				xMin = 0;
			if(yMin < 0)
				yMin = 0;
			if(xMax > environmentBounds[0])
				xMax = environmentBounds[0];
			if(yMax > environmentBounds[1])
				yMax = environmentBounds[1];
			
			for(int x = xMin; x < xMax; x++){
				for(int y = yMin; y < yMax; y++){
					
					boolean continRay = true;
			
					if(/*actor.currentEnvironment.withinXYZBounds(x, y, 0) && */!canSeeThisTile[x][y][0] && (actor.locationXYZTripletGet() != new int[]{x,y,0}) && continRay){
						//if we can already see this spot, ignore!
						float currentDistance = Tools.getDistance(locAgent, new int[]{x,y,0});
						if( currentDistance <= maxDist){
							//if we're out of range, don't even bother!
							
							double[] riseRun = Tools.getSlopeAndDistance(new double[]{locAgent[0]+.5, locAgent[1]+.5}, new double[]{x+.5,y+.5});
							double[] rayLoc = new double[]{locAgent[0]+.5, locAgent[1]+.5};
							
							float distance = 0;
							boolean blocked = false;
							while(continRay){
								rayLoc[0] += riseRun[0];
								rayLoc[1] += riseRun[1];
								if(!this.actor.currentEnvironment.withinXYZBounds((int)rayLoc[0], (int)rayLoc[1], 0)){
									continRay = false;
								}else{
									distance += riseRun[2];
									blocked = ((EnvironmentTactical)actor.currentEnvironment).blocksLight(new int[]{(int)rayLoc[0],(int)rayLoc[1],0}, new EnvironmentObject[] {this.actor});
									if(blocked)
										System.out.println();
									int currentLightIntensity = lightMatrix[(int)rayLoc[0]][(int)rayLoc[1]][0];
									
									boolean brightEnough = currentLightIntensity >= minIntensityForVision;
									canSeeThisTile[(int)rayLoc[0]][(int)rayLoc[1]][0] = brightEnough;
									if(brightEnough)
										updateMemorySharedEtc((int)rayLoc[0],(int)rayLoc[1],0);
									
										//how does it know if a ray should stop
									if(blocked || ((int)distance >= maxDist ) || ((int)rayLoc[0] == x && (int)rayLoc[1] == y)){
										continRay = false;
									}
									else{
										System.out.println("distance "+distance);
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
		
		//now that we know what we can see, do other things!
		this.resetCanSeeAgency();
		this.calculateCanSeeAgency();
		
	}
	/*
	 * should populate the cansees with whatever agents are within this agent's field of view.
	 */
	private void calculateCanSeeAgency() {
		
		
		 LinkedList<HasAgency> tempNeutral = ((EnvironmentTactical)actor.currentEnvironment).thisEnvironmentTacticalUnits.getNeutralNPC();
		 LinkedList<HasAgency> tempFoe = ((EnvironmentTactical)actor.currentEnvironment).thisEnvironmentTacticalUnits.getFoePC();
		 LinkedList<HasAgency> tempFriendly = ((EnvironmentTactical)actor.currentEnvironment).thisEnvironmentTacticalUnits.getFriendlyPC();
		
		 if(tempNeutral !=null){
			 Iterator<HasAgency> it = tempNeutral.iterator();
			 while(it.hasNext()){
				 HasAgency externalAgent = it.next();
				 int[] externalAgentLoc = externalAgent.locationXYZTripletGet();
				 if(this.canSeeThisTile(externalAgentLoc[0], externalAgentLoc[1], externalAgentLoc[2]) && (externalAgent!=this.actor))
					 iCanSeeThisNeutralNPC.add(externalAgent);
			 }
		 }
		 if(tempFoe !=null){
			 Iterator<HasAgency> it = tempFoe.iterator();
			 while(it.hasNext()){
				 HasAgency externalAgent = it.next();
				 int[] externalAgentLoc = externalAgent.locationXYZTripletGet();
				 if(this.canSeeThisTile(externalAgentLoc[0], externalAgentLoc[1], externalAgentLoc[2]) && (externalAgent!=this.actor))
					 iCanSeeThisFoe.add(externalAgent);
			 }
		 }
		 if(tempFriendly !=null){
			 Iterator<HasAgency> it = tempFriendly.iterator();
			 while(it.hasNext()){
				 HasAgency externalAgent = it.next();
				 int[] externalAgentLoc = externalAgent.locationXYZTripletGet();
				 	//add if can see and is not yourself!
				 if(this.canSeeThisTile(externalAgentLoc[0], externalAgentLoc[1], externalAgentLoc[2]) && (externalAgent!=this.actor))
					 iCanSeeThisFriendlyAgent.add(externalAgent);
			 }
		 }
		
	}

	/*
	 * update the shared vision and memory
	 */
	private void updateMemorySharedEtc(int x, int y, int z) {
		if(canRemember && canSeeThisTile[x][x][z]){
			canRememberThisTile[x][y][z] = true;
		}
		if(canShareVision && canSeeThisTile[x][x][z]){
			everyoneCanRememberThisTile[x][y][z] = true;
		}
		
	}


	public boolean[][][] canRememberThisTileGet(){
		return canRememberThisTile;
	}


	public void newTurn() {
		// TODO Auto-generated method stub
		
	}


	public void resetWhenChangingEnvironment() {
		resetCanSeeBoolArray();
		resetMemoryBoolArray();
		resetCollectiveMemoryBoolArray(); 
		
	}


	public void setActor(HasAgency actor) {
		this.actor = actor;
	}


	public static void setEveryoneCanRememberThisTile(boolean[][][] everyoneCanRememberThisTile) {
		AgentVisionSystem.everyoneCanRememberThisTile = everyoneCanRememberThisTile;
	}


	

	public void setiCanSeeThisNeutralNPC(LinkedList<HasAgency> iCanSeeThisNeutralNPC) {
		this.iCanSeeThisNeutralNPC = iCanSeeThisNeutralNPC;
	}


	public void setiCanSeeThisFoe(LinkedList<HasAgency> iCanSeeThisFoe) {
		this.iCanSeeThisFoe = iCanSeeThisFoe;
	}


	public void setiCanSeeThisFriendlyAgent(
			LinkedList<HasAgency> iCanSeeThisFriendlyAgent) {
		this.iCanSeeThisFriendlyAgent = iCanSeeThisFriendlyAgent;
	}


	

	public void setCanSeeThisTile(boolean[][][] canSeeThisTile) {
		this.canSeeThisTile = canSeeThisTile;
	}


	public void setCanRememberThisTile(boolean[][][] canRememberThisTile) {
		this.canRememberThisTile = canRememberThisTile;
	}


	public void setCanRemember(boolean canRemember) {
		this.canRemember = canRemember;
	}


	public void setCanSeeOtherUnitsVisionAlways(boolean canSeeOtherUnitsVisionAlways) {
		this.canSeeOtherUnitsVisionAlways = canSeeOtherUnitsVisionAlways;
	}


	public void setSightDistance(int sightDistance) {
		this.sightDistance = sightDistance;
	}


	public void setStanceFOVAffectArray(double[] stanceFOVAffectArray) {
		this.stanceFOVAffectArray = stanceFOVAffectArray;
	}


	public void setAgentFOVHoriz(int setAgentFOVHoriz) {
		this.agentFOVHoriz = setAgentFOVHoriz;
	}
	public int getMinIntensityForVision(){
		return minIntensityForVision;
	}

	/*
	 * should use logic, perhaps have options to only check 'i can see' as opposed to memory 
	 */
	public boolean canSeeThisTile(int x, int y, int z) {
		// TODO Auto-generated method stub
		return this.canSeeThisTile[x][y][z] || ((canRemember && canRememberThisTile[x][y][z]) || (canSeeOtherUnitsVisionAlways && everyoneCanRememberThisTile[x][y][z]));
	
	}
	

}
