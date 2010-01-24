package beta4;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class EnvironmentObject {
	
	
	
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
	
	protected EnvironmentTactical currentEnvironment; //holds the connected environment
	private int lookDirection;
	private int locationX;
	private int locationY;
	private int locationZ;

	private boolean onFire;
	
	
	
	/*
	 * stuff that needs to be explicitly set
	 */
	
	//visibility
	private char representingChar;
	private Color standardColorFront;
	private Color standardColorBack;
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
	
	
	
	
	public EnvironmentObject(){
			//default
		this('?', Color.white, Color.white, -1, null);
	}
	
	public EnvironmentObject(char representingIn, Color front, Color back, int objectType, EnvironmentTactical thisOne){
		representingChar = representingIn;
		standardColorFront = front;
		standardColorBack = back;
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

	public String toString(){
		return "REP: "+representingChar+" Name: "+thisName+" Dimensions: X"+dimensions[0]+" Y"+dimensions[1]+" Z"+dimensions[2]+" ";
	}
	/*
	 * set the environment (don't place unit, I guess?
	 */
	public void tacticalEnvironmentSet(EnvironmentTactical tacticalEnvironmentNew){
		currentEnvironment = tacticalEnvironmentNew;
	}
	/*
	 * changes the dimension unless input is null or has larger than 4 dimensions, or it cannot fit in the space it is currently
	 */
	protected boolean dimensionsSet(short[] newDimension){
		if(newDimension != null && newDimension.length < 4){
			
			//check
			return true;
		}
		return false;
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
			System.out.println("error in direction degrees conversion");
			retVal = -1;
			
			break;	
		}
		System.out.println(retVal+" degree facing");
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
	
	public void isInFreeFall(){
		System.out.println("isinfreefall undefined for "+this);
	}
	/*
	 * should handle display modes that ALL envirobjects can use 
	 */
	public Color representingColorFrontGet(CharDisplayTypes type, int[] parameters){
		Color retVal = standardColorFront;
		switch(type){
		case STANDARD:
				//nothing
			break;
		case SELECTED:
				//make sure that the it has some contrast
			retVal = DisplayColorChars.returnAColorForFirstColorThatIsMoreDifferentThanSecondColorIfTheyAreSimilar(standardColorFront, standardColorBack);
			break;
		case DIRECTION_FACING:
			//make sure that the it has some contrast
			retVal = DisplayColorChars.returnAColorForFirstColorThatIsMoreDifferentThanSecondColorIfTheyAreSimilar(standardColorFront, standardColorBack);	
			break;
		}
		
		return retVal;
	}
	/*
	 * should handle display modes that all envir objects can use
	 */
	public Color representingColorBackGet(CharDisplayTypes type, int[] parameters){
		
		Color retVal = standardColorBack;
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
	public char representingCharGet(CharDisplayTypes type, int[] paramaters){
		
		char retVal = representingChar;
		
		switch(type){
		case STANDARD:
				//nothing
			break;
		case SELECTED:
				//only change this if the the repChar is ' '
			retVal = '*';
			break;
		case DIRECTION_FACING:
				retVal = (char)(lookDirection + 48);
			break;
		}
		
		
		return retVal;
	}

	protected void representingCharSet(char newOne){
		representingChar = newOne;
		
	}
	protected boolean representingColorFrontSet(Color newOne){
		if(newOne != null){
			standardColorFront = newOne;
			return true;
		}else
			return false;
		
	}
	protected boolean representingColorBackSet(Color newOne){
		if(newOne != null){
			standardColorBack = newOne;
			return true;
		}else
			return false;
		
	}
	protected boolean weightSet(int weightKG2) {
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
	
	
	/*
	 * protected members
	 */
	
	protected void blocksPassableHorizontalSet(boolean b) {
		blocksPassableHorizontal = b;
		
	}
	public boolean blocksPassableVerticalGet() {
		return blocksPassableVertical;
		
	}
	protected void blocksPassableVerticalSet(boolean b) {
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
	
	
	

	
	
	public abstract void checkToSeeIfGroundWillSupport();
		

	
	public int[] locationXYZTripletGet(){
			return new int[]{locationX,locationY,locationZ};
	}
	
	/*
	 * used at the beginning of a mission, etc, to set an arbitrary location. For now, this does little more than assign values
	 * could also check for out-of-bounds and if it can legally occupy a space.
	 */
	public void locationXYZTripletSet(int x, int y, int z) {
		locationX = x;
		locationY = y;
		locationZ = z;
	}
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
		HUD.standingOnTileStringSet(currentEnvironment.getEnvironmentTileNameStandingOn(locationX, locationY, locationZ));
		HUD.standingOnEquipStringSet(currentEnvironment.getThisEquipment().getItemGroundNameList(locationX, locationY, locationZ, false, 0));
		HUD.lookDirectionSet(lookDirectionToString());
		HUD.weightUnitSet(weightKG);
		
	}
	
	
	/*
	 * protected
	 */
	
	protected void burnsToSet(int toSet){
		burnsTo = toSet;
	}
	protected void coverSet(byte newCover){
		if(cover > -1)
			cover = newCover;
	}
	protected void explodesToSet(int newExplodes){
		explodesTo = newExplodes;
	}
	protected void heightCMSet(int newHeightCM) {
		heightCM = newHeightCM;
	}
	protected void howFlammableSet(int newFlammable){
		howFlammable = newFlammable;
	}
	protected void  howLongBurnsSet(int newHowLong){
		howLongBurns = newHowLong;
	}

	protected void supportsWeightSet(int newSupports) {
		supportsWeight = newSupports;	
	}
	/*
	 * set the object type, this should double check that this number is valid
	 */
	protected void objectTypeSet(int objectType2) {
		objectType = objectType2;
		
	}
	protected int explodesToGet() {
		
		return explodesTo;
	}
	protected void lookDirectionSet(int newLook){
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

	protected void hitPointsSet(int hitPoints2) {
		hitPoints = hitPoints2;
		
	}
	
	
	/*
	 * PRIVATE MEMBERS
	 */
	

	

}
