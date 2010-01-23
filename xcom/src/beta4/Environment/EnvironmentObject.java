package beta4.Environment;

import java.awt.Color;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

import beta4.DisplayColorChars;
import beta4.HUD;

public abstract class EnvironmentObject{
	
	/**
	 * 
	 */
	//private static final long serialVersionUID = -6588765722195707882L;

	
	
	
	
		//just a subclass that all objects that exist in the environment are part of.
		//the following things have to do with sounds, anything that would be perceived in addition to what's on the display
	public static enum ActionType{EXIST, WALK, CRAWL, RUN, SPRINT, FLOAT, BREAK_OBJECT, ADJUST_INVENTORY, FALL, TUMBLE, CLIMB, OPEN_DOOR, THROW, JUMP, SHOOT}
	public static enum SoundType{NONE, BREATHING, HEARTBEAT, FOOTSTEPS_SHARP, FOOTSTEPS_HARD, FOOTSTEPS_SOFT, THUD, CRUNCH, SHOUT, WHISPER, BARK, SHATTER, RUMBLE, WHOOSH, GUNSHOT, EXPLOSION, THUMP, ZAP, RICOCHET, CRACKLE, SQUEAK, CREEK, PITTER_PATTER, SQUISH, SLURP, SPLAT, SCREAM, BLOOD_CURDLING_SCREAM, WHIMPER, CHOKE, SNAP, RUSTLE}
	public static enum SmellType{NONE, SWEAT, URINE, FECES, HUMAN_SCENT, DIRT, WOOD, METAL, PLASTIC, ROTTING_FLESH, PINE, FLOWERS,}
	public static enum SightType{NONE, /*fear? horror? seeing movement?*/}
	public static enum whatToRepresent {STANDARD, SELECTED, DIRECTION_FACING }
	public static enum CharDisplayTypes{STANDARD, SELECTED, DIRECTION_FACING}
		//will this kind of movement be automatically granyted movement?
	
	//TODO movetypes instead of 'blocksmovement', like for vertical would be 'weight'
	
	/*
	 * stuff that will be set to defaults at creation
	 */
	
	public Environment currentEnvironment; //holds the connected environment
	private int lookDirection;
	
		//top left corner
	private int[] locationXYZTriplet;

	private boolean onFire;
	
	
	
	/*
	 * stuff that needs to be explicitly set
	 */
	
	private DisplayColorChars[][] theseRepresentingChars;
		//multiply the albedo by the amount of light that hits it to get 'true' light level
	private float albedo;
	private String thisName;
	
	
	//1 = one square, Default north facing direction will be the 'front' there could theoretically be a creature that is two z levels tall [x][y][z]
	private short[] dimensions;
	private int supportsWeight;
		//what percentage movement is multiplied when a unit moves through this square
	private int percentageMovement;
	//how much percentage each square of this material has to block a unit shorter than its height, 
	private byte cover;
	//flammability, how long a flame must be adjacent to this bit before it catches fire, -1 means immune
	private int howFlammable;
	//how many turns it burns before it decomposes
	private int howLongBurns;
	//this is what will replace it once it has burned... done via a complete swamp of stuff, so it's essentially a new tile, would equal the switch/case 
	private int burnsTo; 
	//what something is converted to on an explosion. -1 means it stays the same (switch case)
	private int explodesTo;
	private int hitPoints;
	private int objectType;
	private boolean blocksPassableHorizontal;
	private boolean blocksPassableVertical;
	private int weightKG;
	private int heightCM;
	private boolean blocksLight;
	
	
	
	
	public EnvironmentObject(){
			//default
		this(new DisplayColorChars[][]{{new DisplayColorChars('?',Color.black, Color.white)}},  -1, null);
	}
	
	public EnvironmentObject(DisplayColorChars[][] newRepresenting, int objectType, EnvironmentTactical thisOne){
		this.setTheseRepresentingChars(newRepresenting);
		dimensions = new short[]{1,1,1};
		currentEnvironment = thisOne;
		objectTypeSet(objectType);
		albedo = (float)1.0;
		//iterate through the obstructions, sort them into horiz and vert	
	}
	

	/*
	 * get the percentageMovement
	 */
	public double percentageMovementGet(){
		return percentageMovement/100.0;
	}
	public void percentageMovementSet(int newPercentMovement){
		percentageMovement = newPercentMovement;
	}
	public void blocksLightSet(boolean newBlocks){
		blocksLight = newBlocks;
	}

	public String toString(){
		return "REP: "+this.getTheseRepresentingChars()[0].toString()+" Name: "+thisName+" Dimensions: X"+dimensions[0]+" Y"+dimensions[1]+" Z"+dimensions[2]+" ";
	}
	/*
	 * set the environment (don't place unit, I guess?
	 */
	public void tacticalEnvironmentSet(Environment environment){
		currentEnvironment = environment;
	}
	
	/*
	 * returns if a unit blocks passable.. IE, not dead/unconscious... perhaps have two separate ones for friendly/foe
	 */
	public boolean blocksPassableHorizontalGet(){
		return blocksPassableHorizontal;
	}
	
	
	public int heightCMGet() {
		return this.heightCM;
	}
	
	
	public int lookDirectionGet() {
		
		return lookDirection;
	}
	/*
	 * returns the degree equivalent of the direction the unit is facing.
	 */
	public int lookDirectionDegreesGet(){
		int retVal;
		switch(lookDirection){
		case 7:
			retVal = 135;
			break;
		case 8:
			retVal = 90;
			break;
		case 9:
			retVal = 45;
			break;
		case 4:
			retVal = 180;
			break;
		
		case 6:
			retVal = 360;
			break;
		case 1:
			retVal = 225;
			break;
		case 2:
			retVal = 270;
			break;
		case 3:
			retVal = 315;
			break;
		default:
			//System.out.println("error in direction degrees conversion");
			retVal = -1;
			
			break;	
		}
		//System.out.println(retVal+" degree facing");
		return retVal;
	}
	public float albedoGet(){
		return albedo;
	}
	/*
	 * returns the direction this agent is looking
	 */
	public String lookDirectionToString(){
		String retVal="Facing: ";
		switch(lookDirection){
		case 7:
			retVal+="NW";
			break;
		case 8:
			retVal+="N";
			break;
		case 9:
			retVal+="NE";
			break;
		case 4:
			retVal+="W";
			break;
		case 5:
			retVal+="360";
			break;
		case 6:
			retVal+="E";
			break;
		case 1:
			retVal+="SW";
			break;
		case 2:
			retVal+="S";
			break;
		case 3:
			retVal+="SE";
			break;
		default:
			
			break;	
		}
		return retVal;
	}
	
	 
	/*
	
	 public EnvironmentRelatedSenseSounds soundTypeForActionGet(ActionType action){
		 System.out.println("getSoundTypeForAction undefined for "+this);
		 return null;
	 }
	 public EnvironmentRelatedSenseSmells smellTypeForActionGet(ActionType action){
		 System.out.println("getSmellTypeForAction undefined for "+this);
		 return null;
	 }
	public EnvironmentRelatedSenseSights sightsForActionGet(ActionType action){
		System.out.println("getSightsForAction undefined for "+this);
		return null;
	}
	void addThisSoundToEnvironment(EnvironmentRelatedSenseSounds thisOne, boolean isReactionary){
		System.out.println("addThisSoundToEnvironment undefined for "+this);
		
	}
	void addThisSmellToEnvironment(EnvironmentRelatedSenseSmells thisOne, boolean isReactionary, boolean isFixedSmell){
		System.out.println("addThisSmellToEnvironment undefined for "+this);
	}
	void addThisSightToEnvironment(EnvironmentRelatedSenseSights thisOne, boolean isReactionary){
		System.out.println("addThisSightToEnvironment undefined for "+this);
	}
	*/
	
	public void isInFreeFall(){
		System.out.println("isinfreefall undefined for "+this);
	}
	/*
	 * should handle display modes that ALL envirobjects can use 
	 */
	public Color[][] representingColorTextGet(CharDisplayTypes type, int[] parameters){
		Color retVal[][] = getAllStandardColorText();
		
		if(locationXYZTriplet != null){
			//just return one
			retVal = new Color[][] {{getDisplayColorChars(locationXYZTriplet).getColorText()}};
			
		}
		
		switch(type){
		case STANDARD:
				//nothing
			break;
		case SELECTED:
				//make sure that the it has some contrast
			//TODO
			//retVal = DisplayColorChars.returnAColorForFirstColorThatIsMoreDifferentThanSecondColorIfTheyAreSimilar(standardColorFront, standardColorBack);
			break;
		case DIRECTION_FACING:
			//make sure that the it has some contrast
			//TODO
			//retVal = DisplayColorChars.returnAColorForFirstColorThatIsMoreDifferentThanSecondColorIfTheyAreSimilar(standardColorFront, standardColorBack);	
			break;
		}
		
		return retVal;
	}
	private Color[][] getAllStandardColorText() {
		Color[][] retVal = new Color[getTheseRepresentingChars().length][getTheseRepresentingChars()[0].length];
		for(int y = 0; y < getTheseRepresentingChars().length; y++){
			for(int x = 0; x < getTheseRepresentingChars()[y].length; x++){
				retVal[x][y] = getTheseRepresentingChars()[x][y].getColorText();
			}
		}
		return retVal;
	}
	private Color[][] getAllStandardColorBackground() {
		Color[][] retVal = new Color[getTheseRepresentingChars().length][getTheseRepresentingChars()[0].length];
		for(int y = 0; y < getTheseRepresentingChars().length; y++){
			for(int x = 0; x < getTheseRepresentingChars()[y].length; x++){
				retVal[x][y] = getTheseRepresentingChars()[x][y].getColorBack();
			}
		}
		return retVal;
	}

	/*
	 * should handle display modes that all envir objects can use
	 */
	public Color[][] representingColorBackGet(CharDisplayTypes type, int[] parameters){
		
		Color retVal[][] = getAllStandardColorBackground();
		if(locationXYZTriplet != null){
			//just return one
			retVal = new Color[][] {{getDisplayColorChars(locationXYZTriplet).getColorBack()}};
			
		}
		switch(type){
		case STANDARD:
				//nothing
			break;
		case SELECTED:
				//contrast given by foreground
			break;
		case DIRECTION_FACING:
				//contrast given by foreground
			break;
		}
		
		
		return retVal;
	}
	/*
	 * should handle display modes that All envir objects can use
	 */
	public char[][] representingCharGet(CharDisplayTypes type, int[] locationXYZTriplet){
		
		char retVal[][] = getAllRepresentingChars();
		if(locationXYZTriplet != null){
			//just return one
			try{
			retVal = new char[][] {{getDisplayColorChars(locationXYZTriplet).getThisChar()}};
			}catch(Exception e){
				
			}
			
		}
		switch(type){
		case STANDARD:
				//nothing
			break;
		case SELECTED:
				//only change this if the the repChar is ' '
			retVal = setAllCharsToThisChar(retVal, '*');
			break;
		case DIRECTION_FACING:
				retVal = setAllCharsToThisChar(retVal, (char)(lookDirection + 48));
			break;
		}
		
		
		return retVal;
	}
	protected char[][] setAllCharsToThisChar(char[][] retVal, char c) {
		for(int y = 0; y < retVal[0].length; y++){
			for(int x = 0; x < retVal.length; x++){
				retVal[x][y] = c;
			}
		}
		return retVal;
	}
	protected DisplayColorChars[][] setAllDCCToThisChar(DisplayColorChars[][] retVal, char c) {
		for(int y = 0; y < retVal[0].length; y++){
			for(int x = 0; x < retVal.length; x++){
				retVal[x][y].setRepChar(c);
			}
		}
		return retVal;
	}
	public DisplayColorChars getDisplayColorChars(int[] xyzTriplet){
		return this.getDisplayColorChars(xyzTriplet[0], xyzTriplet[1], xyzTriplet[2]);
	}
	public DisplayColorChars getDisplayColorChars(int x, int y, int z) {
		// should return the proper DCC by checkin the location and plugging this into this objects thingy
		//object location is always top left corner of array
		
		//is x in bounds?
		//does z match
		DisplayColorChars retVal = null;
		if(locationXYZTriplet != null){
			if(z == locationXYZTriplet[2] ){
				if(!(x < locationXYZTriplet[0] || x > (locationXYZTriplet[0] + getTheseRepresentingChars().length-1))){
					//is y in bounds
					if(!(y < locationXYZTriplet[1] || y > (locationXYZTriplet[1] + getTheseRepresentingChars()[0].length-1))){
								//is z in bounds
								//if(!(z < locationXYZTriplet[2] || z > (locationXYZTriplet[2] + theseRepresentingChars[x][y].length-1))){
						
						retVal = getTheseRepresentingChars()[x - locationXYZTriplet[0]][y - locationXYZTriplet[1]];
					}
					
				}
			}
		}
		return retVal;
	}

	private char[][] getAllRepresentingChars() {
		char[][] retVal = new char[getTheseRepresentingChars().length][getTheseRepresentingChars()[0].length];
		for(int y = 0; y < getTheseRepresentingChars().length; y++){
			for(int x = 0; x < getTheseRepresentingChars()[y].length; x++){
				retVal[x][y] = getTheseRepresentingChars()[x][y].getThisChar();
			}
		}
		return retVal;
	}

	protected void theseRepresentingCharsSet(DisplayColorChars[][] newOne){
		setTheseRepresentingChars(newOne);
		
	}
	public boolean weightSet(int weightKG2) {
		if(weightKG2 > -1){
			weightKG = weightKG2;
			return true;
		}
		return false;
		
		
	}
	public boolean thisNameSet(String name) {
		if(name != null){
			thisName= name;
			return true;
		}
		return false;
		
	}
	public int weightGet(){
		return weightKG;
	}
	public int supportsWeightGet(){
		return supportsWeight;
	}
	
	

	
	public void blocksPassableHorizontalSet(boolean b) {
		blocksPassableHorizontal = b;
		
	}
	public boolean blocksPassableVerticalGet() {
		return blocksPassableVertical;
		
	}
	public void blocksPassableVerticalSet(boolean b) {
		blocksPassableVertical = b;
		
	}
	protected boolean breakThisObject(){
		if(explodesTo != -1 ){
			thisName = "Broken "+thisName;
			
			return true;
		}
			//TODO perhaps could create 'item' debris below
		return false;
	}
	public byte coverGet(){
		return cover;
	}
	public String thisNameGet(){
		return thisName;
	}
	public Boolean getBlocksLight(int[] locationXYZTripletToCheck){
		boolean retVal = false;
		if(this.locationXYZTriplet != null && locationXYZTripletToCheck != null){
				//check z level
			if(locationXYZTripletToCheck[2] == this.locationXYZTriplet[2] )
			{
					//check x bounds
				if((locationXYZTripletToCheck[0] >= this.locationXYZTriplet[0] && locationXYZTripletToCheck[0] < (this.locationXYZTriplet[0] + getTheseRepresentingChars().length)))
				{
					//is y in bounds
					if((locationXYZTripletToCheck[1] >= this.locationXYZTriplet[1] && locationXYZTripletToCheck[1] < (this.locationXYZTriplet[1] + getTheseRepresentingChars()[0].length)))
					{
								
						retVal = this.blocksLight;
					}
					
				}
			}
		}
		//return blocksLight;
		return retVal;
	}

	
	
	public int[] locationXYZTripletGet(){
	
			return new int[]{locationXYZTriplet[0],locationXYZTriplet[1],locationXYZTriplet[2]};
	
	}
	
	/*
	 * used at the beginning of a mission, etc, to set an arbitrary location. For now, this does little more than assign values
	 * could also check for out-of-bounds and if it can legally occupy a space.
	 */
	
	public void newTurn() {
		//would put burning and general falling check stuff here?
		//checkIfGroundWillSupportAgent();
	}
	public short[] dimensionsGet(){
		return dimensions;
	}
	public int howFlammableGet(){
		return howFlammable;
	}
	public int howLongBurnsGet(){
		return howLongBurns;
	}
	public void refreshHUD(){
		HUD.objectNameSet(thisName);
		HUD.standingOnTileStringSet(currentEnvironment.getEnvironmentTileNameAtLocation(locationXYZTriplet[0],locationXYZTriplet[1],locationXYZTriplet[2]));
		HUD.standingOnEquipStringSet(((EnvironmentTactical)currentEnvironment).getThisEquipment().getItemGroundNameList(locationXYZTriplet[0],locationXYZTriplet[1],locationXYZTriplet[2], false, 0));
		HUD.lookDirectionSet(lookDirectionToString());
		HUD.weightUnitSet(weightKG);
		
	}
	public boolean placeObjectHere(int[] newLocationXYZ, boolean falseIsCheckTrueIsPlace){
		boolean retVal = false;
		
			//is this object in bounds?
		if(currentEnvironment != null){
			if(currentEnvironment.withinXYZBounds(newLocationXYZ)){
				
				
				
				boolean allInBounds = willThisObjectBeInBounds(newLocationXYZ, true);
				if(allInBounds){
					
					boolean allUnobstructed = true;
					//TODO
					if(allUnobstructed){
						boolean noPreviousLocation = false;
						
						if(locationXYZTriplet == null)
							noPreviousLocation = true;
						
						if(noPreviousLocation){
							
						}
						else{
							
						}
						retVal = true;
						if(falseIsCheckTrueIsPlace)
							locationXYZTriplet = newLocationXYZ;
					}
					
				}
				
			}
		}
		else{
			//TODO is likely inside of a ship
			if(falseIsCheckTrueIsPlace)
				locationXYZTriplet = newLocationXYZ;
			
		}
		
		
		
		
		//does this object already have a location?
		//if yes:
			//undraw lights
			//remove from substrate
		
		//change location
		//place object
		//redraw lights
		return retVal;
	}
	

	/**
	 * @param newLocationXYZ
	 * @param allInBounds
	 */
	private boolean willThisObjectBeInBounds(int[] newLocationXYZ, boolean allInBounds) {
		//will this object be entirely in bounds?
		boolean retVal = false;
		if(getTheseRepresentingChars() != null){
			//is it all in bounds?
			
			boolean done = false;
			while(allInBounds && !done){
				
				for(int y = 0; y < getTheseRepresentingChars()[0].length; y++){
					for(int x = 0; x < getTheseRepresentingChars().length; x++){
						int[] locationToCheck = new int[]{newLocationXYZ[0]+x, newLocationXYZ[1]+y, newLocationXYZ[2]};
						allInBounds = allInBounds && (currentEnvironment.withinXYZBounds(locationToCheck));
					}
				}
				done = true;
				
			}
			
		}
		return retVal || allInBounds;
	}
	public static boolean thisObjectIsIncludedInThisArray(EnvironmentObject toExcludeFromBlocksLightCheck, EnvironmentObject[] listToExcludeFrom) {
		boolean retVal = false;
		if(listToExcludeFrom != null){
			for(EnvironmentObject t : listToExcludeFrom){
				if(t == toExcludeFromBlocksLightCheck)
					retVal = true;
			}
		}
		return retVal;
	}
	
	
	/*
	 * protected
	 */
	
	public void burnsToSet(int toSet){
		burnsTo = toSet;
	}
	public void coverSet(byte newCover){
		if(cover > -1)
			cover = newCover;
	}
	public void explodesToSet(int newExplodes){
		explodesTo = newExplodes;
	}
	public void heightCMSet(int newHeightCM) {
		heightCM = newHeightCM;
	}
	public void howFlammableSet(int newFlammable){
		howFlammable = newFlammable;
	}
	public void  howLongBurnsSet(int newHowLong){
		howLongBurns = newHowLong;
	}

	public void supportsWeightSet(int newSupports) {
		supportsWeight = newSupports;	
	}
	/*
	 * set the object type, this should double check that this number is valid
	 */
	public void objectTypeSet(int objectType2) {
		objectType = objectType2;
		
	}
	protected int explodesToGet() {
		
		return explodesTo;
	}
	public void lookDirectionSet(int newLook){
		if(newLook > -1 && newLook < 10)
			lookDirection = newLook;
	}
	
	public int objectTypeGet(){
		return objectType;
	}
	protected void onFireSet(boolean onFire){
		this.onFire = onFire;
	}
	public boolean onFireGet(){
		return onFire;
	}

	public void hitPointsSet(int hitPoints2) {
		hitPoints = hitPoints2;
		
	}
	public boolean isAtThisLocation(int[] locationXYZTripletToCheck) {
		
			boolean retVal = false;
			if(this.locationXYZTriplet != null && locationXYZTripletToCheck != null){
					//check z level
				if(locationXYZTripletToCheck[2] == this.locationXYZTriplet[2] )
				{
						//check x bounds
					if((locationXYZTripletToCheck[0] >= this.locationXYZTriplet[0] && locationXYZTripletToCheck[0] < (this.locationXYZTriplet[0] + getTheseRepresentingChars().length)))
					{
						//is y in bounds
						if((locationXYZTripletToCheck[1] >= this.locationXYZTriplet[1] && locationXYZTripletToCheck[1] < (this.locationXYZTriplet[1] + getTheseRepresentingChars()[0].length)))
						{
									
							retVal = true;
						}
						
					}
				}
			}
			return retVal;
	}
	public int[][] locationXYZTripletGetForAllDimensions(){
		if(getTheseRepresentingChars() == null || locationXYZTriplet == null)
			return null;
		
		int[][] retVal = new int[getTheseRepresentingChars().length + getTheseRepresentingChars()[0].length][3];
		int i = 0;
		for(int y = 0; y < getTheseRepresentingChars()[0].length; y++){
			for(int x = 0; x < getTheseRepresentingChars().length; x++){
				retVal[i] = new int[]{locationXYZTriplet[0]+x,locationXYZTriplet[1]+y,locationXYZTriplet[2]};
				i++;
			}
		}
		return retVal;
		
	}
	/*
	 * should return a size based on how many tiles the object takes up
	 */
	public int getSize() {
		int size = 0;
		//for(int z = 0; z < theseRepresentingChars[0][0].length;z++)
		for(int x = 0; x < getTheseRepresentingChars().length; x++){
			for(int y = 0; y < getTheseRepresentingChars()[x].length; y++){
				size++;
			}
		}
		return size;
	}

	public void setTheseRepresentingChars(DisplayColorChars[][] theseRepresentingChars) {
		this.theseRepresentingChars = theseRepresentingChars;
	}

	public DisplayColorChars[][] getTheseRepresentingChars() {
		return theseRepresentingChars;
	}
}
			

