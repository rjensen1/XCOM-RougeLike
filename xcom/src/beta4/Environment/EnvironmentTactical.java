package beta4.Environment;


import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;

import beta4.Display;
import beta4.DisplayColorChars;
import beta4.HasAgency;
import beta4.LightSource;
import beta4.PackageShip;
import beta4.Agent.AgentVisionSystem;
import beta4.LightSource.FallOffType;
import beta4.LightSource.LightSourceType;





public class EnvironmentTactical extends EnvironmentDisplayable {
	
	
	
	
	
	public EnvironmentTacticalUnits thisEnvironmentTacticalUnits;
	
	EnvironmentEquipment thisEquipment;
 	
	
	private boolean godVision;
	public enum TileWeightCheck{SAFE, DANGEROUS, WILLBREAK}
	
	/*
	 * agentsIncoming get randomly placed in viable places, shipIncoming gets randomly placed in a viable spot, and then
	 * it's agents and equip is added to the tactical environment and given locations
	 */
	public EnvironmentTactical(int xDimNew, int yDimNew, int zDimNew, int viewPortX, int viewPortY, LinkedList<HasAgency> agentsIncoming, Display toUse, PackageShip shipIncoming){
		super(xDimNew, yDimNew, zDimNew, viewPortX, viewPortY, toUse);
		
		
		godVision = false;
		
		
		LightSource.lightingMatrixInitialize(this);
		

		thisEquipment = new EnvironmentEquipment(xDim,yDim,zDim, this);

			//generate random landscape, should change this to a general, "generate landscape"
		generateLandscape();
			//using the environmentarray
		

		//letting each agent know which tactical environment it is connected to
		//tacticalCharRepresents = new char[zDim][yDim][xDim];
		tacticalColorCharsViewportRepresentsYX = new DisplayColorChars[viewPortXDim][viewPortYDim];
		//tacticalAgentArray = new HasAgency[xDim][yDim][zDim];
		//stationaryObjectsArray = new LinkedList[xDim][yDim][zDim];
		
			//place the ship first
		thisEnvironmentTacticalUnits = new EnvironmentTacticalUnits(this);

			//randomly placed dudes..
		if(agentsIncoming != null)
		{
			thisEnvironmentTacticalUnits.pcAgentList = new LinkedList<HasAgency>();
				//must give each incoming agent a location! either random, or based off of ship data, etc, and add it to the pcagent linked list
				Iterator<HasAgency> iteratorAgentsIncoming = agentsIncoming.iterator();
				int counter = 0;
			while(iteratorAgentsIncoming.hasNext()){
				HasAgency tempAgent = iteratorAgentsIncoming.next();
				thisEnvironmentTacticalUnits.pcAgentList.add(tempAgent);
				tempAgent.tacticalEnvironmentSet(this);
				//starting position
				tempAgent.placeObjectHere(new int[]{5, 5+counter, 0}, true);
				counter++;
			}
		}
		if(shipIncoming != null)
			placeShip(shipIncoming);
		
		//sending only one z at a time, make sure to remember that y and x are swapped.
		
		
		thisEnvironmentTacticalUnits.resetPCAgentIterators();
		
		//should only need to be done once, as every other update should update it on the fly
		generateViewportCharRepresenting(godVision);
		environmentTileArraySetPositions();
		
		generateTestingLights();
		}

	/**
	 * 
	 */
	private void generateTestingLights() {
		/*{
			//rectangle
			int brightness = 100;
			int[] thisDimension = new int[]{8,8};
			int[] xyLocation = new int[]{10,10,0};
			boolean isOn = true;
			boolean canBeSwitcheOff = false;
			LightSource.FallOffType fot = LightSource.FallOffType.LINEAR_FAST;
			LightSource.LightSourceType st = LightSource.LightSourceType.RECTANGULAR;
			LightSource templight = new LightSource(brightness, thisDimension, xyLocation , isOn, canBeSwitcheOff, fot , st);
			templight.thisEnvironmentSet(this);
			LightSource.lightSourceAdd(templight);
		}*/
		{
			//point_source
			int brightness = 100;
			int[] thisDimension = new int[]{10,10,10,10};
			int[] xyLocation = new int[]{10,10,0};
			boolean isOn = true;
			boolean canBeSwitcheOff = false;
			LightSource.FallOffType fot = LightSource.FallOffType.LINEAR_FAST;
			LightSource.LightSourceType st = LightSource.LightSourceType.POINT_SOURCE;
			LightSource templight = new LightSource(brightness, thisDimension, xyLocation , isOn, canBeSwitcheOff, fot , st);
			templight.thisEnvironmentSet(this);
			LightSource.lightSourceAdd(templight);
		}
		{
			//point_source
			int brightness = 70;
			int[] thisDimension = new int[]{10,10,10,10};
			int[] xyLocation = new int[]{3,0,0};
			boolean isOn = true;
			boolean canBeSwitcheOff = false;
			LightSource.FallOffType fot = LightSource.FallOffType.LINEAR_FAST;
			LightSource.LightSourceType st = LightSource.LightSourceType.POINT_SOURCE;
			LightSource templight = new LightSource(brightness, thisDimension, xyLocation , isOn, canBeSwitcheOff, fot , st);
			templight.thisEnvironmentSet(this);
			LightSource.lightSourceAdd(templight);
		}
	}
	
	/*
	 * deals with placeing a ship, the agents inside of it, and 
	 */
	
	private void placeShip(/*PackageAddToEnvironment*/PackageShip shipIncoming) {
			//find a good spot for the ship, top left corner of it's boundaries
		
		int[] shipDim = shipIncoming.shipDimenSionsColRowLevelGet();
		int xDim = shipDim[0]; 
		int yDim = shipDim[1];
		int zDim = shipDim[2];
		
			//this should actually search for a valid spot!
		
		int xLoc = 2;
		int yLoc = 2;
		int zLoc = 0;
		
		//for now, just a silly little method, starting at z = 0, top left, check that it is empty
		
		
		int[] shipPos = new int[]{xLoc,yLoc,zLoc};
			//add ship's environment tile to the main envirtile array, except ignore NULLs
		
		
		
		EnvironmentTile[][][] shipEnvir = shipIncoming.environmentTileArray;
		
		for(int z = 0; z < zDim; z++){
			for(int y = 0; y < yDim; y++){
				for(int x = 0; x < xDim; x++){
					//System.out.println(" "+x+" "+y+" "+z);
					if(shipEnvir[x][y][z] != null){
						//System.out.println("after");
						environmentTileArray[x + shipPos[0]][y + shipPos[1]][z + shipPos[2]] = shipEnvir[x][y][z];
					}
					
				}
			}
		}
		//add the equipment (this should maybe add instead of overwriting
		int[] equipPos = shipIncoming.equipLocationXYZTripletRelativeToTopLeftCornerOfShipEquipGet();
		LinkedList<EquipmentEtc> tempFromShip = shipIncoming.allShipEquipmentGet();
		
		
		
		thisEquipment.dropItemToGround(tempFromShip, (shipPos[0] + equipPos[0]), (shipPos[1] + equipPos[1]), (shipPos[2] + equipPos[2]));
		
		
		//add the agents, should also double check that nothing is there
		
		LinkedList<HasAgency> tempAgents = shipIncoming.allShipAgentGet();
		Iterator<HasAgency> tempAgentIterator = tempAgents.iterator();
		
		if(thisEnvironmentTacticalUnits.pcAgentList == null)
			thisEnvironmentTacticalUnits.pcAgentList = new LinkedList<HasAgency>();
		
		while(tempAgentIterator.hasNext()){
			
			HasAgency temp = tempAgentIterator.next();
			int[] relativeLoc = temp.locationXYZTripletGet();
			temp.placeObjectHere(new int[]{relativeLoc[0] + shipPos[0], relativeLoc[1] + shipPos[1], relativeLoc[2] + shipPos[2]}, true);
			temp.tacticalEnvironmentSet(this);
			thisEnvironmentTacticalUnits.pcAgentList.add(temp);
		}
		
	}
	/*
	 * should take care of ALL the landscape generation, including loading alien/xcom bases, etc.
	 * perhaps it's first step, should be to subdivide the place into sections, sort of like recursion, where each section is sent to a different thing
	 * perhaps "this section is a section of woods, this section is a corn field, this section gets a small house, this section cuts through the whole map and will be a road; etc
	 * 
	 */
	private void generateLandscape() {
			//just a simpe stub routine
		generateLandscapeRandom();
		thisEquipment.placeRandomItems(15);
		
		
	}
	
	/*
	 * will produce an entire character array of everything that should be visible. (change this to get the information from the AnAgent instead of directly from the Environment
	 * if omnipotence, will not get the information from a unit.
	 */
	private void generateViewportCharRepresenting(boolean omnipotence) {
		//calculate x adjust and y adjust
		
		
		
		int yMin = viewPortYFocus - viewPortYDim/2;
		int yMax =  1 + viewPortYFocus + viewPortYDim/2;
		int xMin = viewPortXFocus - viewPortXDim/2;
		int xMax = 1 + viewPortXFocus + viewPortXDim/2;
		
		HasAgency selectedUnit;
		if(thisEnvironmentTacticalUnits.currentUnit == null)
			selectedUnit = null;
		else
			selectedUnit = thisEnvironmentTacticalUnits.currentUnit;
		
		boolean[][][] tempWhatCanSee = null;
		if(selectedUnit != null)
			tempWhatCanSee = selectedUnit.thisVision.canSeeThisTile;
		
		
			for(int y = yMin; y < yMax ;y++ ){
				for(int x = xMin; x < xMax ;x++){
					//if then that sets the character, depending on what should be visible.
						//place the environ tile
					tacticalColorCharsViewportRepresentsYX[x - xMin][y - yMin] = whatTileShouldBeVisible(x, y, viewPortZFocus, omnipotence, tempWhatCanSee);
						//place any items here, but only if tile is visible
					boolean canSeeTile = (thisEnvironmentTacticalUnits.currentUnit.thisVision.canSeeThisTile(x,y,viewPortZFocus));
					if(canSeeTile){
						DisplayColorChars temp = thisEquipment.placeItemsOnViewScreenAtLocation(x,y,xMin,yMin, false, false);
						if(temp != null){
							//if there is an item(s) here, place it!
							tacticalColorCharsViewportRepresentsYX[x - xMin][y - yMin] = temp;
						}
					}
					
				}
			}
		
		//place items
		//place agents...
			placeAgentsWhenDisplayingScreen(xMin, xMax, yMin, yMax, godVision, thisEnvironmentTacticalUnits.currentUnit);	
	}
	/*
	 * actually decides what things will be put onto the screen
	 */
	private void placeAgentsWhenDisplayingScreen(int xMin, int xMax, int yMin, int yMax, boolean hasGodVision, HasAgency selectedUnit){
		Iterator<HasAgency> iterate = thisEnvironmentTacticalUnits.pcAgentList.iterator();
		if(selectedUnit != null){
			
			boolean[][][] selectedUnitLocationCanSee = selectedUnit.thisVision.whatThisAgentCanSeeGet(); 
			boolean thisUnitCanSeeAll = thisEnvironmentTacticalUnits.currentUnit.thisVision.canThisUnitSeeAllOtherUnitsAlwaysGet();
			
			while(iterate.hasNext()){
				
				HasAgency temp = iterate.next();
				
				boolean currentUnitCanSeeAllWithComsOnAndThisUnitHasComsOn = (thisUnitCanSeeAll && temp.thisVision.canThisUnitSeeAllOtherUnitsAlwaysGet());
				int triplet[][] = temp.locationXYZTripletGetForAllDimensions();
				//TODO check each square of unit to see if it can see?
				for(int i = 0; i < triplet.length; i++){
					if(!hasGodVision && (temp == selectedUnit || (currentUnitCanSeeAllWithComsOnAndThisUnitHasComsOn ||selectedUnitLocationCanSee[triplet[i][0]][triplet[i][1]][triplet[i][2]]))){
						//is this agent on the same zlevel?
						if(triplet[i][2] == viewPortZFocus){
							//adjust these x y coords for the display
							triplet[i][0] -= xMin;
							triplet[i][1] -= yMin;
								//making sure that the guy hasn't scrolled off the screen
							if(withinXYZBounds(triplet[0])){
								if(temp != thisEnvironmentTacticalUnits.currentUnit ){
									
										tacticalColorCharsViewportRepresentsYX[triplet[i][0]][triplet[i][1]] = new DisplayColorChars(temp.representingCharGet(EnvironmentObject.CharDisplayTypes.STANDARD, new int[]{triplet[i][0], triplet[i][1], triplet[i][2]})[0][0],  temp.representingColorTextGet(EnvironmentObject.CharDisplayTypes.STANDARD, new int[]{triplet[i][0], triplet[i][1], triplet[i][2],})[0][0], temp.representingColorBackGet(EnvironmentObject.CharDisplayTypes.STANDARD, new int[]{triplet[i][0], triplet[i][1], triplet[i][2],})[0][0] );
								}
								else{
									//display unit, but it's select, so differentiate it
									
										tacticalColorCharsViewportRepresentsYX[triplet[i][0]][triplet[i][1]] = new DisplayColorChars(temp.representingCharGet(EnvironmentObject.CharDisplayTypes.SELECTED, new int[]{triplet[i][0], triplet[i][1], triplet[i][2]})[0][0],  temp.representingColorTextGet(EnvironmentObject.CharDisplayTypes.SELECTED, new int[]{triplet[i][0], triplet[i][1], triplet[i][2],})[0][0], temp.representingColorBackGet(EnvironmentObject.CharDisplayTypes.SELECTED, new int[]{triplet[i][0], triplet[i][1], triplet[i][2],})[0][0] );
								}
							}
						}
					}
				}
			}
		}
	}
	
	/*
	 * only returns things that are stored in arrays.
	 */
	private DisplayColorChars whatTileShouldBeVisible(int x, int y, int z, boolean seeAll, boolean[][][] visibilityArray){
		
		/* this will be the arbitrary version, with vision/hearing/memory turned off
		 * check (for landscape) if it is either seen, or is remembered
		 * check (for objects) if is is either seen or is remembered
		 * check (for units) if it either seen, heard, referenced(
		 */
		//start with ground
		boolean[][][] canRememberArray = null;
		if(thisEnvironmentTacticalUnits.currentUnit.thisVision.canRememberThisTile != null){
			canRememberArray = thisEnvironmentTacticalUnits.currentUnit.thisVision.canRememberThisTile;
		}
		boolean[][][] canSeeSharedVision = null;
		if(thisEnvironmentTacticalUnits.currentUnit.thisVision.canSeeOtherUnitsVisionAlways){
			canSeeSharedVision = AgentVisionSystem.everyoneCanRememberThisTile;
		}
		
		char tempChar = ' ';
		Color tempFrontColor = Color.WHITE;
		Color tempBackColor = Color.BLACK;
		
		if(environmentTileArray[x][y][z] != null){
			
			if(seeAll || (visibilityArray != null && (visibilityArray[x][y][z] || (canRememberArray != null && canRememberArray[x][y][z]) || (canSeeSharedVision != null && canSeeSharedVision[x][y][z])))){
				float effectiveIntensity = LightSource.lightingMatrix[x][y][z]*environmentTileArray[x][y][z].albedoGet();
				//if(!( effectiveIntensity < this.currentUnit.getThisVision().getMinIntensityForVision())){
					tempChar = environmentTileArray[x][y][z].representingCharGet(EnvironmentObject.CharDisplayTypes.STANDARD, new int[]{x,y,z})[0][0];
				//}
				//else{
					//tempChar = ' ';
				//}
				tempFrontColor = environmentTileArray[x][y][z].representingColorTextGet(EnvironmentObject.CharDisplayTypes.STANDARD, new int[]{x,y,z})[0][0];
				tempBackColor = environmentTileArray[x][y][z].representingColorBackGet(EnvironmentObject.CharDisplayTypes.STANDARD, new int[]{x,y,z})[0][0];
				tempBackColor = DisplayColorChars.darkenThisColorByPercent(tempBackColor, (effectiveIntensity)/(LightSource.maxLightBrightness*1.0));
			}
			else{
				//can't see this
				tempChar = ' ';
				tempFrontColor = Color.black;
				tempBackColor = Color.black;
			}
			
				
		}

		return new DisplayColorChars(tempChar, tempFrontColor, tempBackColor);
	}
	
	
	
	/*
	 * called when a unit is JUST looking
	 */
	
	public void displayTacticalEnvironment(int HUDType){
		//send the char array to the display object
		if(thisEnvironmentTacticalUnits.currentUnit==null && HUDType==0)
			HUDType = -1;
		if(tacticalColorCharsViewportRepresentsYX != null && displayToUse != null){
			refreshHUD();
			
			if(thisEnvironmentTacticalUnits.currentUnit != null)
				thisEnvironmentTacticalUnits.currentUnit.thisVision.whatThisAgentCanSeeCalculate();
			generateViewportCharRepresenting(godVision);
			//TODO
			//generateViewportCharRepresenting(false);
			displayToUse.outPutMessageErrors(thisEnvironmentTacticalUnits.currentUnit.theseMessages.errorsGet());
			displayToUse.outputHUD(HUDType);
			displayToUse.outputTact(tacticalColorCharsViewportRepresentsYX);
					//would actually b (currentUnit.getHUDOutput(HUDTyp)) or something
			
			
		}
	}
	
	
	/*
	 * should display errors and messages
	 */
	public void displayErrorsAndMessages(){
		displayToUse.outPutMessageErrors(thisEnvironmentTacticalUnits.currentUnit.theseMessages.errorsGet());
	}

	/*
	 * should do everything that newturn/end turn does.... functionally "end turn"
	 */
	public void newTurn() {
		/*
		 * civ and alien movement and environment stuff would happen here
		 */
		//LightSource.drawAllLights();
		
		
		
		thisEnvironmentTacticalUnits.newTurn();
		
	}
	/*
	 * takes care of letting all the units know that it's a new turn!
	 */
	
	
	/*
	 * returns if a square can be passed into horizontally
	 */
	public boolean isThisLocationPassableHorizontal(int x, int y, int z){
		boolean passable = false;
		
		if(withinXYZBounds(x, y, z)) 
			passable = !environmentTileArray[x][y][z].blocksPassableHorizontalGet();
		
		//now check for any dudes standing there
		
		Iterator<HasAgency> iterate = thisEnvironmentTacticalUnits.pcAgentList.iterator();
		
		boolean isAtThisSpot = false;
		if(passable){
			while(iterate.hasNext()){
				HasAgency temp = iterate.next();
				
				if(thisEnvironmentTacticalUnits.currentUnit != temp){
					isAtThisSpot = temp.blocksPassableHorizontalGet() &&temp.isAtThisLocation(new int[]{x,y,z});
				}
				
				if(isAtThisSpot)
					break;
			}
		}
		return passable && !isAtThisSpot;
	}
	
	/*
	 * should end the mission
	 */
	public void endMission() {
		// TODO Auto-generated method stub
		
	}
	
	
	/*
	 * should go to the next unit, while deselecting the current (removing it from the active linked list.
	 */
	
	/*
	 * refreshes the HUD for the selected unit, or if no unit is selected, displays the HUD to nothing (though, is usually overridded
	 * by the other display method
	 */
	void refreshHUD(){
		if(thisEnvironmentTacticalUnits.currentUnit!=null)
			thisEnvironmentTacticalUnits.currentUnit.refreshHUD();
		else{
			displayToUse.outputHUD(-1);
		}
	}
	/*
	 * returns the current active unit
	 */
	
	

	public boolean downStairsAt(int x, int y, int z) {
		boolean retVal = false; 	
		
		if(withinXYZBounds(x, y, z)){
			EnvironmentTile temp = environmentTileArray[x][y][z];
			retVal = temp.canUnitMoveVertically(true);
			
		}
		return retVal;
	}
	/*
	 * check if there are upstairs at this location, true/false
	 */
	public boolean upStairsAt(int x, int y, int z) {
		boolean retVal = false; 	
	
		if(withinXYZBounds(x, y, z)){
			EnvironmentTile temp = environmentTileArray[x][y][z];
			retVal = temp.canUnitMoveVertically(true);
			
		}
		//in the future, this could return WHAT it is that the user can ascend.
		return retVal;
	}

	public boolean getIsPassableVertical(int x, int y, int z) {
		boolean retVal = false;
		
		if(withinXYZBounds(x, y, z)){
			retVal = environmentTileArray[x][y][z].blocksPassableVerticalGet();
		
		
			//now check for any dudes standing there
			
			Iterator<HasAgency> iterate = thisEnvironmentTacticalUnits.pcAgentList.iterator();
			while(iterate.hasNext()){
				HasAgency temp = iterate.next();
				int[] xyzTriplet = temp.locationXYZTripletGet();
				if((x == xyzTriplet[0] && y == xyzTriplet[1] && z == xyzTriplet[2]) && thisEnvironmentTacticalUnits.currentUnit != temp)
					retVal = !temp.blocksPassableVerticalGet();
			}
		}
		return retVal;
	}
	/*
	 * input a coordinate tuple and any optional 'weight to add' will return if this weigth (plus whatever might already be there) will be safe/break/dangerous
	 */
	public TileWeightCheck checkThisTileForWeightStrength(int x, int y, int z, int weightToAdd){
		int maxWeightAllowed = 0;
		if(withinXYZBounds(x, y, z)){
			maxWeightAllowed = environmentTileArray[x][y][z].supportsWeightGet();
			//just subtracting the weight of everything that's on this.
			maxWeightAllowed -= weightToAdd;
			
			maxWeightAllowed -= thisEquipment.weightCheck(x,y,z);
			
			if(thisEnvironmentTacticalUnits.pcAgentList != null){
				Iterator<HasAgency> tempAgent = thisEnvironmentTacticalUnits.pcAgentList.iterator();
				while(tempAgent.hasNext()){
					HasAgency temp = tempAgent.next();
					int[] location = temp.locationXYZTripletGet();
					if(location[0] == x && location[1] == y && location[2] == z){
						maxWeightAllowed -= temp.weightGet();
					}
				}
			}
			
		}
		if(maxWeightAllowed <= 0){
			return TileWeightCheck.WILLBREAK;
		}
		else if (maxWeightAllowed < weightToAdd){
			return TileWeightCheck.DANGEROUS;
		}
		else
			return TileWeightCheck.SAFE;
		
	}

	
	
	
	public EnvironmentEquipment getThisEquipment(){
		return thisEquipment;
	}
	public boolean blocksLight(int[] xyzLoc, EnvironmentObject[] toExcludeFromBlocksLightCheck){
		if(withinXYZBounds(xyzLoc) && environmentTileArray != null){
			return environmentTileArray[xyzLoc[0]][xyzLoc[1]][xyzLoc[2]].getBlocksLight(new int[]{xyzLoc[0], xyzLoc[1], xyzLoc[2]}) || thisEnvironmentTacticalUnits.doAgentsBlockLight(xyzLoc, toExcludeFromBlocksLightCheck);
		}
		else{
			return true;
		}
	}

	

	public void preMove(int[] xyzLocation){
		//Anything that needs to be turned off pre-move 
		LightSource.drawAllLights(true, xyzLocation);
	}
	public void postMove(int[] xyzLocation){
		//anything that needs to be done post-move
		LightSource.drawAllLights(false, xyzLocation);
	}

	
	
	
}
