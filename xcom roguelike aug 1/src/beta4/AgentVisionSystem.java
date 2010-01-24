package beta4;

import java.util.LinkedList;

public class AgentVisionSystem {
	
	
	private HasAgency actor;
	
	/*
	 * things that are just set to null at creation
	 */
	public static boolean[][][] everyoneCanRememberThisTile;
	
	private static LinkedList<HasAgency> everyoneCanSeeThisNeutralNPC;
	private static LinkedList<HasAgency> everyoneCanSeeThisFoe;
	private static LinkedList<HasAgency> everyoneCanSeeThisFriendlyAgent;
		//these three hold things that would be targetable AND would show up on the display, but are available to only this agent (could overlap with above)
	private  LinkedList<HasAgency> iCanSeeThisNeutralNPC;
	private  LinkedList<HasAgency> iCanSeeThisFoe;
	private  LinkedList<HasAgency> iCanSeeThisFriendlyAgent;
	
	private LinkedList<HasAgency> friendliesThisAgentCanSee;
	private LinkedList<HasAgency> enemiesThisAgentCanSee;
	
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
		everyoneCanSeeThisNeutralNPC = new LinkedList<HasAgency>();
		everyoneCanSeeThisFoe = new LinkedList<HasAgency>();
		everyoneCanSeeThisFriendlyAgent = new LinkedList<HasAgency>();
		//these three hold things that would be targetable AND would show up on the display, but are available to only this agent (could overlap with above)
		 iCanSeeThisNeutralNPC = new LinkedList<HasAgency>();
		 iCanSeeThisFoe = new LinkedList<HasAgency>();
		 iCanSeeThisFriendlyAgent = new LinkedList<HasAgency>();
		 minIntensityForVision = 5;
		 sightDistance = 5;
		 canShareVision = true;
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
		}*/
		
		return retVal;
	}
	/*
	 * gets the effective field of view, which changes based on stance and perhaps a skill. 
	 */
	
	public int agentFOVHorizGet() {
		return (int)Math.ceil(agentFOVHoriz * stanceFOVAffectArray[actor.getTheseStats().getStance()]);
	}
	


	public int agentSightDistanceGet() {
		// TODO Auto-generated method stub
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
		//TODO
		//this.resetCanSeeBoolArray();
		
		/*
		 * using actor location, sight distance, and light matrix/min intensity, calculate thigs
		 */
		int zDim = canSeeThisTile[0][0].length;
		int yDim = canSeeThisTile[0].length;
		int xDim = canSeeThisTile.length;
		
		int[][][] lightMatrix = LightSource.lightingMatrix;
		for(int z = 0; z < zDim; z++){
			for(int x = 0; x < xDim; x++){
				for(int y = 0; y < yDim; y++){
					//find distance
					double distance = Tools.getDistance(actor.locationXYZTripletGet(), new int[]{x,y,z});
					boolean bright = lightMatrix[x][y][z] >= minIntensityForVision;
					boolean close = distance <= sightDistance;
					boolean canSeeThis = bright && close;
					if(canSeeThis){
						canSeeThisTile[x][y][z] = true;
						if(canRemember){
							canRememberThisTile[x][y][z] = true;
						}
						if(canShareVision){
							everyoneCanRememberThisTile[x][y][z] = true;
						}
					}
					else{
						canSeeThisTile[x][y][z] = false;
					}
				}
			}
		}
		
		//actor.currentEnvironment.whatCanThisAgentSee(actor, canSeeThisTile);
		//memoryFromSightUpdate();
	}
	/*
	 * should go through the 'cansee' bool array and copy any trues into the memory array
	 */
	/*
	private void memoryFromSightUpdate() {
		/*
		 * private boolean[][][] canSeeThisTile;
	private boolean[][][] canRememberThisTile;
		 */
	/*
	if(canRemember){
			for(int z = 0; z < canRememberThisTile[0][0].length; z++){
				for(int y = 0; y < canRememberThisTile[0].length; y++){
					for(int x = 0; x < canRememberThisTile.length; x++){
						if(canSeeThisTile[x][y][z] || (canSeeOtherUnitsVisionAlways && everyoneCanRememberThisTile[x][y][z])){
							canRememberThisTile[x][y][z] = true;
							if(canSeeOtherUnitsVisionAlways){
								everyoneCanRememberThisTile[x][y][z] = true;
							}
						}
					}
				}
				
			}	
		}
	}*/
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


	public static void setEveryoneCanRememberThisTile(
			boolean[][][] everyoneCanRememberThisTile) {
		AgentVisionSystem.everyoneCanRememberThisTile = everyoneCanRememberThisTile;
	}


	public static void setEveryoneCanSeeThisNeutralNPC(
			LinkedList<HasAgency> everyoneCanSeeThisNeutralNPC) {
		AgentVisionSystem.everyoneCanSeeThisNeutralNPC = everyoneCanSeeThisNeutralNPC;
	}


	public static void setEveryoneCanSeeThisFoe(
			LinkedList<HasAgency> everyoneCanSeeThisFoe) {
		AgentVisionSystem.everyoneCanSeeThisFoe = everyoneCanSeeThisFoe;
	}


	public static void setEveryoneCanSeeThisFriendlyAgent(
			LinkedList<HasAgency> everyoneCanSeeThisFriendlyAgent) {
		AgentVisionSystem.everyoneCanSeeThisFriendlyAgent = everyoneCanSeeThisFriendlyAgent;
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


	public void setFriendliesThisAgentCanSee(
			LinkedList<HasAgency> friendliesThisAgentCanSee) {
		this.friendliesThisAgentCanSee = friendliesThisAgentCanSee;
	}


	public void setEnemiesThisAgentCanSee(
			LinkedList<HasAgency> enemiesThisAgentCanSee) {
		this.enemiesThisAgentCanSee = enemiesThisAgentCanSee;
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
	 * should use logic 
	 */
	public boolean canSeeThisTile(int x, int y, int z) {
		// TODO Auto-generated method stub
		return this.canSeeThisTile[x][y][z] || ((canRemember && canRememberThisTile[x][y][z]) || (canSeeOtherUnitsVisionAlways && everyoneCanRememberThisTile[x][y][z]));
	}

}
