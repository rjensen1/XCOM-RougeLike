package beta4;


import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;





public class EnvironmentTactical extends Environment {
	
	private Display displayToUse;
	private int xDim;
	private int yDim;
	private int zDim;
	//TODO put in unidirection light source (sun/moon)
	//TODO put in optional objects/tiles that also create stationary light sources.
	
	private int viewPortXDim;
	private int viewPortYDim;
	private int viewPortXFocus;
	private int viewPortYFocus;
	private int viewPortZFocus;
	
	private EnvironmentTile[][][] environmentTileArray;
	
	private EnvironmentEquipment thisEquipment;
	/*
	 * with sounds, all 'current' movement gets added to 'current' sounds, except for reaction fire, which gets added to last turn's and current turn's
	 */
	private LinkedList<EnvironmentRelatedSenseSounds> lastTurnsSounds;
	private LinkedList<EnvironmentRelatedSenseSounds> currentTurnsSounds;

	
	private LinkedList<HasAgency> pcAgentList;
	private LinkedList<HasAgency> pcAgentActive;
	private Iterator<HasAgency> currentUnitIterator;
	private HasAgency currentUnit;
 	//private char[][][] tacticalCharRepresents;
 	private DisplayColorChars[][] tacticalColorCharsViewportRepresentsYX;
	//private HasAgency[][][] tacticalAgentArray;
	private boolean godVision;
	public enum TileWeightCheck{SAFE, DANGEROUS, WILLBREAK}
	//private LinkedList[][][] stationaryObjectsArray;
	/*
	 * agentsIncoming get randomly placed in viable places, shipIncoming gets randomly placed in a viable spot, and then
	 * it's agents and equip is added to the tactical environment and given locations
	 */
	public EnvironmentTactical(int xDimNew, int yDimNew, int zDimNew, int viewPortX, int viewPortY, LinkedList<HasAgency> agentsIncoming, Display toUse, PackageShip shipIncoming){
		
		
		
		godVision = false;
		setDisplay(toUse);
		xDim=xDimNew;
		yDim=yDimNew;
		zDim=zDimNew;
		LightSource.lightingMatrixInitialize(this);
		{
			//rectangle
			int brightness = 100;
			int[] thisDimension = new int[]{8,8};
			int[] xyLocation = new int[]{7,7};
			boolean isOn = true;
			boolean canBeSwitcheOff = false;
			LightSource.FallOffType fot = LightSource.FallOffType.INVERSE_SQUARE;
			LightSource.LightSourceType st = LightSource.LightSourceType.RECTANGULAR;
			LightSource templight = new LightSource(brightness, thisDimension, xyLocation , isOn, canBeSwitcheOff, fot , st);
			templight.thisEnvironmentSet(this);
			LightSource.lightSourceAdd(templight);
		}
		
		
		if(viewPortX > xDim || viewPortY > yDim )
			System.out.println("Error, viewport is larger than the environment");
		else{
			viewPortYDim = viewPortY;
			viewPortXDim = viewPortX;
				//viewport must be odd number
			if(viewPortYDim % 2 ==0)
				viewPortYDim -= 1;
			if(viewPortXDim % 2 ==0)
				viewPortXDim -= 1;
		}
		/*
		 * initialize sounds
		 */
		/*
		currentTurnsSounds = new LinkedList<EnvironmentRelatedSenseSounds>();
		lastTurnsSounds = new LinkedList<EnvironmentRelatedSenseSounds>();
		*/
		environmentTileArray = new EnvironmentTile[xDim][yDim][zDim];
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
			if(shipIncoming != null)
				placeShip(shipIncoming);
		
		
		
			//randomly placed dudes..
		if(agentsIncoming != null)
		{
			pcAgentList = new LinkedList<HasAgency>();
				//must give each incoming agent a location! either random, or based off of ship data, etc, and add it to the pcagent linked list
				Iterator<HasAgency> iteratorAgentsIncoming = agentsIncoming.iterator();
				int counter = 0;
			while(iteratorAgentsIncoming.hasNext()){
				HasAgency tempAgent = iteratorAgentsIncoming.next();
				pcAgentList.add(tempAgent);
				tempAgent.tacticalEnvironmentSet(this);
				//starting position
				tempAgent.locationXYZTripletSet(5, 5+counter, 0);
				counter++;
			}
		}
		
		
		//sending only one z at a time, make sure to remember that y and x are swapped.
		
		
		resetPCAgentIterators();
		
		//should only need to be done once, as every other update should update it on the fly
		generateViewportCharRepresenting(godVision);
		environmentTileArraySetPositions();
		}
	
	private void environmentTileArraySetPositions() {
		for(int x = 0; x < environmentTileArray.length; x++){
			for(int y = 0; y < environmentTileArray[1].length; y++){
				for(int z = 0; z < environmentTileArray[1][1].length; z++){
					environmentTileArray[x][y][z].locationXYZTripletSet(x, y, z);
					environmentTileArray[x][y][z].tacticalEnvironmentSet(this);
				}
			}
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
		
		int xLoc = 1;
		int yLoc = 1;
		int zLoc = 0;
		
		//for now, just a silly little method, starting at z = 0, top left, check that it is empty
		
		
		int[] shipPos = new int[]{xLoc,yLoc,zLoc};
			//add ship's environment tile to the main envirtile array, except ignore NULLs
		
		
		
		EnvironmentTile[][][] shipEnvir = shipIncoming.shipLayoutGet();
		
		for(int z = 0; z < zDim; z++){
			for(int y = 0; y < yDim; y++){
				for(int x = 0; x < xDim; x++){
					System.out.println(" "+x+" "+y+" "+z);
					if(shipEnvir[x][y][z] != null){
						System.out.println("after");
						environmentTileArray[x + shipPos[0]][y + shipPos[1]][z + shipPos[2]] = shipEnvir[x][y][z];
					}
					
				}
			}
		}
		//add the equipment (this should maybe add instead of overwriting
		int[] equipPos = shipIncoming.equipLocationXYZTripletRelativeToTopLeftCornerOfShipEquipGet();
		LinkedList<EquipmentEtc> tempFromShip = shipIncoming.allShipEquipmentGet();
		
		
		
		thisEquipment.dropItemToGround(tempFromShip, (shipPos[0] + equipPos[0]), (shipPos[1] + equipPos[1]), (shipPos[2] + equipPos[2]));
		//equipListArray[][][] = tempFromShip;
		
		//add the agents, should also double check that nothing is there
		
		LinkedList<HasAgency> tempAgents = shipIncoming.allShipAgentGet();
		Iterator<HasAgency> tempAgentIterator = tempAgents.iterator();
		
		if(pcAgentList == null)
			pcAgentList = new LinkedList<HasAgency>();
		
		while(tempAgentIterator.hasNext()){
			
			HasAgency temp = tempAgentIterator.next();
			int[] relativeLoc = temp.locationXYZTripletGet();
			temp.locationXYZTripletSet(relativeLoc[0] + shipPos[0], relativeLoc[1] + shipPos[1], relativeLoc[2] + shipPos[2]);
			temp.tacticalEnvironmentSet(this);
			pcAgentList.add(temp);
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
		
		
		/*
		 * int minXPos = viewPortXDim/2;
		int maxXPos = xDim - (viewPortXDim/2 + 1);
		int minYPos = viewPortYDim/2;
		int maxYPos = yDim - (viewPortYDim/2 + 1);
		 */
		int yMin = viewPortYFocus - viewPortYDim/2;
		int yMax =  1 + viewPortYFocus + viewPortYDim/2;
		int xMin = viewPortXFocus - viewPortXDim/2;
		int xMax = 1 + viewPortXFocus + viewPortXDim/2;
		
		HasAgency selectedUnit;
		if(currentUnit == null)
			selectedUnit = null;
		else
			selectedUnit = currentUnit;
		
		boolean[][][] tempWhatCanSee = null;
		if(selectedUnit != null)
			tempWhatCanSee = selectedUnit.getThisVision().canSeeThisTile;
		
		
			for(int y = yMin; y < yMax ;y++ ){
				for(int x = xMin; x < xMax ;x++){
					//if then that sets the character, depending on what should be visible.
						//place the environ tile
					tacticalColorCharsViewportRepresentsYX[x - xMin][y - yMin] = whatTileShouldBeVisible(x, y, viewPortZFocus, omnipotence, tempWhatCanSee);
						//place any items here, but only if tile is visible
					boolean canSeeTile = (this.currentUnit.getThisVision().canSeeThisTile(x,y,viewPortZFocus));
					if(canSeeTile){
						DisplayColorChars temp = thisEquipment.placeItemsOnViewScreenAtLocation(x,y,xMin,yMin, false, false);
						if(temp != null){
							//if there is an item(s) here, place it!
							tacticalColorCharsViewportRepresentsYX[x - xMin][y - yMin] = temp;
						}
					}
					//check to see if air, if it's air, display a symbol if there is something that blocks movement on the lower level.
					/*if(tacticalColorCharsViewportRepresentsYX[x-xMin][y-yMin] != null && tacticalColorCharsViewportRepresentsYX[x-xMin][y-yMin].getThisChar()[0] == ' '){
						if(viewPortZFocus-1 > -1){
							//TODO
							boolean somethingIsBlockingLowerTile = false;//getIsPassableVertical(x,y,viewPortZFocus-1) && ;
							if(somethingIsBlockingLowerTile){
								//tacticalCharViewportRepresentsYX[y-yMin][x-xMin] = '*';
							}
						}
					}*/
				}
			}
		
		//place items
		//place agents...
			placeAgentsWhenDisplayingScreen(xMin, xMax, yMin, yMax, false, godVision, currentUnit);	
	}
	/*
	 * actually decides what things will be put onto the screen
	 */
	private void placeAgentsWhenDisplayingScreen(int xMin, int xMax, int yMin, int yMax, boolean subLevel, boolean hasGodVision, HasAgency selectedUnit){
		Iterator<HasAgency> iterate = pcAgentList.iterator();
		if(selectedUnit != null){
			
			boolean[][][] selectedUnitLocationCanSee = selectedUnit.getThisVision().whatThisAgentCanSeeGet(); 
			boolean thisUnitCanSeeAll = currentUnit.getThisVision().canThisUnitSeeAllOtherUnitsAlwaysGet();
			
			while(iterate.hasNext()){
				
				HasAgency temp = iterate.next();
				
				boolean currentUnitCanSeeAllWithComsOnAndThisUnitHasComsOn = (thisUnitCanSeeAll && temp.getThisVision().canThisUnitSeeAllOtherUnitsAlwaysGet());
				int triplet[] = temp.locationXYZTripletGet();
				if(!hasGodVision && (temp == selectedUnit || (selectedUnitLocationCanSee[triplet[0]][triplet[1]][triplet[2]] || (currentUnitCanSeeAllWithComsOnAndThisUnitHasComsOn)))){
					//is this agent on the same zlevel?
					if(triplet[2] == viewPortZFocus){
						//adjust these x y coords for the display
						triplet[0] -= xMin;
						triplet[1] -= yMin;
							//making sure that the guy hasn't scrolled off the screen
						if(!(triplet[0] < 0 || triplet[1] < 0 || triplet[0] >= xMax || triplet[1] >= yMax)){
							if(temp != currentUnit )
								tacticalColorCharsViewportRepresentsYX[triplet[0]][triplet[1]] = new DisplayColorChars(temp.representingCharGet(EnvironmentObject.CharDisplayTypes.STANDARD, null),  temp.representingColorFrontGet(EnvironmentObject.CharDisplayTypes.STANDARD, null), temp.representingColorBackGet(EnvironmentObject.CharDisplayTypes.STANDARD, null) );
							else
								//display unit, but it's select, so differentiate it
								tacticalColorCharsViewportRepresentsYX[triplet[0]][triplet[1]] = new DisplayColorChars(temp.representingCharGet(EnvironmentObject.CharDisplayTypes.SELECTED, null),  temp.representingColorFrontGet(EnvironmentObject.CharDisplayTypes.SELECTED, null), temp.representingColorBackGet(EnvironmentObject.CharDisplayTypes.SELECTED, null) );
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
		if(this.currentUnit.getThisVision().canRememberThisTile != null){
			canRememberArray = this.currentUnit.getThisVision().canRememberThisTile;
		}
		boolean[][][] canSeeSharedVision = null;
		if(this.currentUnit.getThisVision().canSeeOtherUnitsVisionAlways){
			canSeeSharedVision = AgentVisionSystem.everyoneCanRememberThisTile;
		}
		
		char tempChar = ' ';
		Color tempFrontColor = Color.WHITE;
		Color tempBackColor = Color.BLACK;
		
		if(environmentTileArray[x][y][z] != null){
			
			if(seeAll || (visibilityArray != null && (visibilityArray[x][y][z] || (canRememberArray != null && canRememberArray[x][y][z]) || (canSeeSharedVision != null && canSeeSharedVision[x][y][z])))){
				float effectiveIntensity = LightSource.lightingMatrix[x][y][z]*environmentTileArray[x][y][z].albedoGet();
				//if(!( effectiveIntensity < this.currentUnit.getThisVision().getMinIntensityForVision())){
					tempChar = environmentTileArray[x][y][z].representingCharGet(EnvironmentObject.CharDisplayTypes.STANDARD, null);
				//}
				//else{
					//tempChar = ' ';
				//}
				tempFrontColor = environmentTileArray[x][y][z].representingColorFrontGet(EnvironmentObject.CharDisplayTypes.STANDARD, null);
				tempBackColor = environmentTileArray[x][y][z].representingColorBackGet(EnvironmentObject.CharDisplayTypes.STANDARD, null);
				tempBackColor = DisplayColorChars.darkenThisColorByPercent(tempBackColor, (effectiveIntensity)/(LightSource.maxLightBrightness*1.0));
			}
			else{
				//can't see this
				tempChar = ' ';
				tempFrontColor = Color.black;
				tempBackColor = Color.black;
			}
			//2 is 'can see now 1 is /can remember/ 0 is /not seen
			/*else if(visibilityArray[x][y][z] == 1){
					//can remember, but not see
				tempBackColor = Color.black;//tempBackColor.darker();
				tempFrontColor = Color.white;tempFrontColor.darker();
			}
			else if(visibilityArray[x][y][z] == 2){
				//can see
				if(LightSource.lightingMatrix != null)
					tempBackColor = DisplayColorChars.darkenThisColorByPercent(tempBackColor, LightSource.lightingMatrix[x][y][z]/(LightSource.maxLightBrightness*1.0));
				else
					System.out.println("oops");
				//tempBackColor = tempBackColor.brighter();
				//tempFrontColor = tempFrontColor.darker();
			}*/
				
		}

		return new DisplayColorChars(tempChar, tempFrontColor, tempBackColor);
	}
	
	
	/*
	 * just a very simple randomgeneration
	 */
	private void generateLandscapeRandom() {
		int tile = 1;
		for(int z=0; z<zDim; z++){
			for(int x=0;x<xDim;x++){
				for(int y=0;y<yDim;y++){
					//temp=Tools.getRandInt(1, 6, 0);
					environmentTileArray[x][y][z] = new EnvironmentTile(tile, this);
				}
			}
			tile = 0;
		}
	}
	/*
	 * called when a unit is JUST looking
	 */
	
	public void displayTacticalEnvironment(int HUDType){
		//send the char array to the display object
		if(currentUnit==null && HUDType==0)
			HUDType = -1;
		if(tacticalColorCharsViewportRepresentsYX != null && displayToUse != null){
			refreshHUD();
			
			if(currentUnit != null)
				currentUnit.getThisVision().whatThisAgentCanSeeCalculate();
			generateViewportCharRepresenting(godVision);
			//TODO
			//generateViewportCharRepresenting(false);
			displayToUse.outPutMessageErrors(currentUnit.getTheseMessages().errorsGet());
			displayToUse.outputHUD(HUDType);
			displayToUse.outputTact(tacticalColorCharsViewportRepresentsYX);
					//would actually b (currentUnit.getHUDOutput(HUDTyp)) or something
			
			
		}
	}
	/*
	 * used (for now) to display something that overwrites the main screen
	 */
	/*
	public void displayTextToMainTactScreen(String message){
		if(message != null)
			displayToUse.outputTact(message);
	}*/
	
	/*
	 * should display errors and messages
	 */
	public void displayErrorsAndMessages(){
		displayToUse.outPutMessageErrors(currentUnit.getTheseMessages().errorsGet());
	}
	/*
	 * sets the display
	 */
	private void setDisplay(Display toUse){
		displayToUse=toUse;
	}
	/*
	 * should do everything that newturn/end turn does.... functionally "end turn"
	 */
	public void newTurn() {
		/*
		 * civ and alien movement and environment stuff would happen here
		 */
		//LightSource.drawAllLights();
		
		
		resetPCAgentIterators();
		turnThingsThatHappenToUnits();
		lastTurnsSounds = currentTurnsSounds;
		currentTurnsSounds = new LinkedList<EnvironmentRelatedSenseSounds>();
	}
	/*
	 * takes care of letting all the units know that it's a new turn!
	 */
	private void turnThingsThatHappenToUnits(){
		
		Iterator<HasAgency> tempPcUnitIterator = pcAgentList.iterator();
		while(tempPcUnitIterator.hasNext()){
			tempPcUnitIterator.next().newTurn();
		}
		
	}
	/*
	 * makes a new iterator that has all of the active agents in it, also, selects the first agent and refreshes its HUD
	 */
	@SuppressWarnings("unchecked")
	private void resetPCAgentIterators(){
		//copy pcagentlist into the active linked list
		pcAgentActive = (LinkedList<HasAgency>) pcAgentList.clone();
		//reset the current unit iterator
		currentUnitIterator = pcAgentActive.iterator();
		currentUnit = currentUnitIterator.next();
		centerScreen(currentUnit.locationXYZTripletGet());
		refreshHUD();
	}
	/*
	 * returns if a square can be passed into horizontally
	 */
	public boolean getIsPassableHorizontal(int x, int y, int z){
		boolean retVal = false;
		if(withinXYZBounds(x, y, z)) 
			retVal = !environmentTileArray[x][y][z].blocksPassableHorizontalGet();
		if(!retVal)
			return retVal;
		//now check for any dudes standing there
		
		Iterator<HasAgency> iterate = pcAgentList.iterator();
		while(iterate.hasNext()){
			HasAgency temp = iterate.next();
			int[] xyzTriplet = temp.locationXYZTripletGet();
			if((x == xyzTriplet[0] && y == xyzTriplet[1] && z == xyzTriplet[2]) && currentUnit != temp)
				retVal = !temp.blocksPassableHorizontalGet();
			if(!retVal)
				break;
		}
		return retVal;
	}
	/*
	 * checks the incoming xyz variables against the environment's dimensions, returns false if any one of these is out-of-bounds
	 */
	public boolean withinXYZBounds(int x, int y, int z) {
	
		return x>-1 && x < xDim && y>-1 && y < yDim && z>-1 && z < zDim;
	}
	/*
	 * same as above, but accepts xyz triplet
	 */
	public boolean withinXYZBounds(int[] locationXYZTriple) {
		
		return locationXYZTriple[0] > -1 && locationXYZTriple[0] < xDim && locationXYZTriple[1] > -1 && locationXYZTriple[1] < yDim && locationXYZTriple[2] > -1 && locationXYZTriple[2] < zDim;
	}
	/*
	 * should end the mission
	 */
	public void endMission() {
		// TODO Auto-generated method stub
		
	}
	/*
	 * cycles forward through the unit list, assuming that the active list isn't empty
	 */
	public void selectNextUnitPC() {
		if(currentUnitIterator == null)
			return;
		
		
		if(currentUnitIterator.hasNext()){
			currentUnit = currentUnitIterator.next();
			centerScreen(currentUnit.locationXYZTripletGet());
			refreshHUD();
		}
		else if(!pcAgentActive.isEmpty()){
			currentUnitIterator = pcAgentActive.iterator();
			currentUnit = currentUnitIterator.next();
			centerScreen(currentUnit.locationXYZTripletGet());
			refreshHUD();
		}
	}
	/*
	 * cycles to the previous unit in the active unit list
	 */
	public void selectPreviousUnitPC() {
		if(currentUnitIterator == null)
			return;
		
		if(pcAgentActive.size()==1)
			return;
		
		else if(currentUnit == pcAgentActive.element()){ //is this the first?
			currentUnitIterator = pcAgentActive.iterator();
				//fast forward to end of the list
			while(currentUnitIterator.hasNext())
				currentUnit = currentUnitIterator.next();
		}
		else{
			HasAgency tempUnit = currentUnit;
			currentUnitIterator = pcAgentActive.iterator();
			HasAgency lastUnit;
			HasAgency nextUnit = lastUnit = currentUnitIterator.next();
			
				//starting the iterator over
			
			
			
			while(nextUnit != tempUnit){
				lastUnit = nextUnit;
				 nextUnit = currentUnitIterator.next();
				
			}
			currentUnit = lastUnit;
		}
		centerScreen(currentUnit.locationXYZTripletGet());
		refreshHUD();
	}
	/*
	 * should go to the next unit, while deselecting the current (removing it from the active linked list.
	 */
	public void selectNextPCUnitDeselectCurrent() {
		
		if(pcAgentActive.size()==0)
			return;
				//case 2: unit is the last of the units in the list
		else if (pcAgentActive.size()==1){
			pcAgentActive.clear();
			currentUnit = null;
			currentUnitIterator = null;
			refreshHUD();
		}		//case 1: unit is at end of list
		else if(!currentUnitIterator.hasNext()){
			currentUnitIterator.remove();
			currentUnitIterator = pcAgentActive.iterator();
			currentUnit = currentUnitIterator.next();
			centerScreen(currentUnit.locationXYZTripletGet());
		}
		else{
			currentUnitIterator.remove();
			currentUnit = currentUnitIterator.next();
			centerScreen(currentUnit.locationXYZTripletGet());
		}
		refreshHUD();
		
	}
	/*
	 * refreshes the HUD for the selected unit, or if no unit is selected, displays the HUD to nothing (though, is usually overridded
	 * by the other display method
	 */
	private void refreshHUD(){
		if(currentUnit!=null)
			currentUnit.refreshHUD();
		else{
			displayToUse.outputHUD(-1);
		}
	}
	/*
	 * returns the current active unit
	 */
	public HasAgency getCurrentActivePCAgent(){
		return currentUnit;
	}
	

	/*
	 * retuns the name of the tile in questions
	 */
	public String getEnvironmentTileNameStandingOn(int x, int y, int z) {
		if(withinXYZBounds(x, y, z))
			return environmentTileArray[x][y][z].thisNameGet();
		return "None";
	}
	
	/*
	 * gets the ground tile at this location
	 */
	public EnvironmentTile getTileAtThisLocation( int x, int y, int z){
		EnvironmentTile retVal = null;
		
		if(withinXYZBounds(x, y, z)){
			retVal = environmentTileArray[x][y][z];
		}
		
		return retVal;
	}
	
	 
	/*
	 * this should be connected to the current unit.
	 */
	public void addToUnitMessages(String string) {
		
		currentUnit.getTheseMessages().messageAdd(string);
		
	}
	/*
	 * inputs a xy coordinate adjustment and shifts the viewport in this direction, if possible, followed by Z adjustment
	 */
	public void scroll(int[] locationAdjustment, int zAdjust) {
		
		System.out.println("scroll");
			//change the focus if it's valid
			centerScreen(new int[]{viewPortXFocus + locationAdjustment[0], viewPortYFocus + locationAdjustment[1], viewPortZFocus += zAdjust});
			
	
	}
	/*
	 * attempts to center the screen around xyz point
	 */
	public void centerScreen(int[] xyz){
			//if the z dim is out of bounds, do nothing!
		if(!(xyz[2] < 0 || xyz[2] >= zDim)){
		
		int minXPos = viewPortXDim/2;
		int maxXPos = xDim - (viewPortXDim/2 + 1);
		int minYPos = viewPortYDim/2;
		int maxYPos = yDim - (viewPortYDim/2 + 1);
		
		if(xyz[0] < minXPos)
			xyz[0] = minXPos;
		if(xyz[0] > maxXPos)
			xyz[0] = maxXPos;
		if(xyz[1] < minYPos)
			xyz[1] = minYPos;
		if(xyz[1] > maxYPos)
			xyz[1] = maxYPos;
		
		viewPortXFocus = xyz[0];
		viewPortYFocus = xyz[1];
		viewPortZFocus = xyz[2];
		
		}
	}

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
			
			Iterator<HasAgency> iterate = pcAgentList.iterator();
			while(iterate.hasNext()){
				HasAgency temp = iterate.next();
				int[] xyzTriplet = temp.locationXYZTripletGet();
				if((x == xyzTriplet[0] && y == xyzTriplet[1] && z == xyzTriplet[2]) && currentUnit != temp)
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
			
			if(pcAgentList != null){
				Iterator<HasAgency> tempAgent = pcAgentList.iterator();
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
	public boolean breakThisTile(int x, int y, int z){
		if(withinXYZBounds(x, y, z)){
			environmentTileArray[x][y][z].breakThisObject();
		}
			//if there is any equipment, make sure it falls too. (perhaps some equipment won't automatically fall?)
		
		thisEquipment.objectsInFreeFall(x,y,z);
		
		return true;
	}
	public void whatCanThisAgentSee(HasAgency theOneDoingTheLooking, boolean[][][] oldBoolean){
		
		//TODO check for blindness
		
		
		int agentHeight = theOneDoingTheLooking.heightCMGet();
		int[] agentFOVRangeHoriz = theOneDoingTheLooking.getThisVision().degreeFOVRangeHorizGet();
		//int[] agentFOVRangeVert = theOneDoingTheLooking.degreeFOVRangeVertGet();
		//int agentPerception = theOneDoingTheLooking.perceptionGet();
		//int sightDistance = theOneDoingTheLooking.agentSightDistanceGet();
		//generate visibility matrix
			//byte has max of 127
			//TODO should these and all other sight information be stored in the agent? (makes sens, would save some computation time)
		
		byte[][][] visibility = visibilityMatrixGenerate();
		
		//make rays from agentFOVRange for each degree in the FOV
		//LinkedList<LOSRay> arrayLOS = new LinkedList<LOSRay>();
		//System.out.println("min"+agentFOVRange[0]+ " max "+agentFOVRange[1]);
			
			// riseFallVert[0] = lower bounds of vertical vision [1] = upper bounds
		//double[] riseFallVert = new double[2];
		//TODO try/catch for if undefined.
		//riseFallVert[0] = Math.tan((90-agentFOVRangeVert[1])*0.0174532925);
		//riseFallVert[1] = Math.tan((90-agentFOVRangeVert[0])*0.0174532925);
		
		
		
		/*
			//for(double i = agentFOVRangeHoriz[0]; i < agentFOVRangeHoriz[1]; i += Tools.steppingForDegreeLOS){
			//	System.out.println("degree :"+i);
				//arrayLOS.add(new LOSRay(oldBoolean, visibility, agentHeight,/* agentPerception, sightDistance, theOneDoingTheLooking, theOneDoingTheLooking.locationXYZTripletGet(), Tools.getSlopeRiseRunFromDegree(i), /*riseFallVert, this));
				
			//}
		/*
		 * */
		
		//System.out.println("array"+arrayLOS.size());
			//making all the lines, setting up the visibility thingy.
		/*Iterator<LOSRay> it = arrayLOS.iterator();
		while(it.hasNext()){
			LOSRay temp = it.next();
			//System.out.println(temp.sightDistance);
			//System.out.println("x"+temp.agentLocation[0]+" y"+ temp.agentLocation[1]);
			//System.out.println(temp.riseRun[0]+" "+temp.riseRun[1]);
			temp.computeArray();
		}
		
		//return canSee;
		 * */
		 
	}
	/*
	 * generates a byte matrix, each cubby representing visibility as a percent, 0 = blocks all vision, 90 = blocks 90%
	 */
	private byte[][][] visibilityMatrixGenerate() {
		byte[][][] visMat = new byte[xDim][yDim][zDim];
			//setting it to 0 is this necessary?
		for(int z = 0; z < zDim; z++){
			for(int y = 0; y < yDim; y++){
				for(int x = 0; x < xDim; x++){
					visMat[x][y][z] = environmentTileArray[x][y][z].coverGet()/*divide this number by something representing 'darkness' factor */;
				}
			}
		}
		
		
		
		return visMat;
	}
	/*
	public void soundToEnvironmentAdd(EnvironmentRelatedSenseSounds thisSound, boolean isReactionary){
		if(isReactionary){
			//to last turn
			lastTurnsSounds.add(thisSound);
		}
		//add to this turn
		currentTurnsSounds.add(thisSound);
	}

	public void sightsToEnvironmentAdd(EnvironmentRelatedSenseSights thisOne, boolean isReactionary) {
	
		
	}

	public void smellsToEnvironmentAdd(EnvironmentRelatedSenseSmells thisOne, boolean isReactionary, boolean isFixedSmell) {
		
		
	}*/
	public int[] XYZBoundsGet(){
		return new int[]{xDim,yDim,zDim};
	}

	public EnvironmentTile environmentTileAtThisLocationGet(int x, int y, int z) {
		if(withinXYZBounds(x, y, z))
			return environmentTileArray[x][y][z];
		return null;
	}

	public EnvironmentRelatedLightSource getEnvironmentLightSource() {
	
		return null;
	}

	public int getViewPortZFocus() {
		
		return viewPortZFocus;
	}
	public EnvironmentEquipment getThisEquipment(){
		return thisEquipment;
	}
	
	
	
}
