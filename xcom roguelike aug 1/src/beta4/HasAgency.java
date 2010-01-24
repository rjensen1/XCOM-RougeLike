package beta4;





import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;





/*
 * weighted sum of distances to find where optimal safe place should be
 */
public class HasAgency extends EnvironmentObject{
			
	
	/*
	 * various enums
	 */
	
	
	//TODO implement checking against Skills before allowing any of these movements. ALso, initiate the skills when creating a character
	
	
	
	
	
	private AgentStats theseStats;
	private AgentCosts theseCosts;
	private AgentVisionSystem thisVision;
	private AgentSkills theseSkills;
	private AgentInventory thisInventory;
		//wouldn't hold much, but will handle all movement issues, etc.
	private AgentMovement thisMovement;
	private AgentEnvironmentActions theseActions;
	private AgentMessages theseMessages;
	private LightSource thisLight;
	
	
	
	/*
	 * the following are variables that represent a rule-set, something that would affect ALL agents in the game, such as 
	 */
	
	public static LinkedList<Species> loadedSpecies;
	public static LinkedList<Class_Modifiers> loadedClassMods;
	public static LinkedList<Sub_Class_Modifiers> loadedSubClassMods;
	public static LinkedList<DifficultyLevels> loadedDifficultyLevels;
	//public static LinkedList<RuleSet> loadedRuleSet;
		//these get applied in this order
			//anything that's overlapping would be multiplied, so a .5 in one and a .5 in the other would yield .25
	
	
	
	/*
	 * the following are variables that are defined at character creation (like health, etc)
	 */
	
			
			
			
			/*private LinkedList<EquipmentEtc> [] unitCubbyLLArray; //holds all the linkedlists for equipment
			private String[] inventorySlotNames;
			
			private short[] equipMaxSizeArray; //holds the max size of an item that each array slot can fit
			private short[] equipMaxNumberArray; //holds max total inventory size available for each inventory slots
				//holds the grid array with the TU costs to move items from one spot to another
			int[][] unitMoveItemCostArray;*/
			
			
	
	
	
	public static void instantiateSpecies() {
		//TODO put these somewhere that makes sense.
		//loadedRuleSet = new LinkedList<RuleSet>() ;
		loadedSpecies = new LinkedList<Species>() ;
		loadedSpecies.add(new Species());
		loadedClassMods = new LinkedList<Class_Modifiers>() ;
		loadedSubClassMods = new LinkedList<Sub_Class_Modifiers>() ;
		loadedDifficultyLevels = new LinkedList<DifficultyLevels>() ;
	}
	public static Species speciesGet(String name){
		Iterator<Species> speciesIt =  loadedSpecies.iterator();
		Species retVal = null;
		boolean found = false;
		while(speciesIt.hasNext() && !found){
			retVal = speciesIt.next();
			if( retVal.speciesNameGet().equals(name))
				found = true;
			
		}
		if(!found)
			retVal = null;
		
		return retVal;
	}
	public HasAgency(){
		super();
		theseStats = new AgentStats(this);
		theseCosts = new AgentCosts(this);
		thisVision = new AgentVisionSystem(this);
		theseSkills = new AgentSkills(this);
		thisInventory = new AgentInventory(this);
			//wouldn't hold much, but will handle all movement issues, etc.
		thisMovement = new AgentMovement(this);
		theseActions = new AgentEnvironmentActions(this);
		theseMessages = new AgentMessages(this);
		
	}
	public HasAgency(char represents, Color frontColor, Color backColor, int objectType, EnvironmentTactical thisOne) {
		super(represents, frontColor, backColor, objectType, thisOne);
		theseStats = new AgentStats(this);
		theseCosts = new AgentCosts(this);
		thisVision = new AgentVisionSystem(this);
		theseSkills = new AgentSkills(this);
		thisInventory = new AgentInventory(this);
			//wouldn't hold much, but will handle all movement issues, etc.
		thisMovement = new AgentMovement(this);
		theseActions = new AgentEnvironmentActions(this);
		theseMessages = new AgentMessages(this);
		
	}
	
	/*
	 * good example of iterating through the equipment stuff. should check all equipment and return its total weight in KG
	 */
	public int weightGet() {			 
			//adding this to weight of individual
		//should other groupings be allowed to add to the weight?
		return thisInventory.weightGet() + super.weightGet();
	}
	/*
	 * toString method, should call super, and then any/all tostring methods 
	 */
	public String toString(){
	
		return super.toString()+" "+theseStats.toString()+" "+theseCosts.toString()+" "+theseSkills.toString()+" "+thisInventory.toString()+" "+theseMessages.toString()+" "+thisVision.toString()+" "+thisMovement.toString();
	}
	/*
	 * make sure to reset stuff that needs to be, when the environment (level) is changed
	 * @see beta4.EnvironmentObject#tacticalEnvironmentSet(beta4.EnvironmentTactical)
	 */
	public void tacticalEnvironmentSet(EnvironmentTactical thisOne){
		super.tacticalEnvironmentSet(thisOne);
		resetWhenChangingEnvironment();
		
		
	}
	/*
	 * this method should call all of the methods of the various modules, etc
	 */
	private void resetWhenChangingEnvironment() {
		thisVision.resetWhenChangingEnvironment();
		
		if(thisLight != null){
			//delete old light
			LightSource.removeLight(thisLight);
			thisLight = null;
		}
		{
			
			int brightness = 30;
			int[] thisDimension = new int[]{5,5};
			int[] xyLocation = this.locationXYZTripletGet();
			boolean isOn = true;
			boolean canBeSwitcheOff = true;
			LightSource.FallOffType fot = LightSource.FallOffType.LINEAR_FAST;
			LightSource.LightSourceType st = LightSource.LightSourceType.CIRCULAR;
			LightSource templight = new LightSource(brightness, thisDimension, xyLocation , isOn, canBeSwitcheOff, fot , st);
			templight.thisEnvironmentSet(this.currentEnvironment);
			LightSource.lightSourceAdd(templight);
			templight.drawLight(false);
			thisLight = templight;
		}
		
	}
	/*
	 * have each module refresh the HUD
	 * @see beta4.EnvironmentObject#refreshHUD()
	 */
	public void HUDRefresh(){
		super.refreshHUD();
		theseStats.HUDRefresh();
		theseCosts.HUDRefresh();
		theseSkills.HUDRefresh();
		thisInventory.HUDRefresh();
		theseMessages.HUDRefresh();
		thisVision.HUDRefresh();
		thisMovement.HUDRefresh();
	}


	
	//this would vary, depending on PC, alien, NPC.. just returns a string version of a unit's rank
	
	
	//where the eyes are. (for now)
	
	public char representingCharGet(CharDisplayTypes type, int[] parameters) {
		char retVal = '?';
		switch(type){
		
		case SELECTED:
				retVal = super.representingCharGet(CharDisplayTypes.STANDARD, parameters);
			break;
		case STANDARD:
		default:
				retVal =  super.representingCharGet(type, parameters);
		}
		return retVal;
	}
	public Color representingColorBackGet(CharDisplayTypes type, int[] parameters) {
		
		int[] tempXYZ = super.locationXYZTripletGet();
		
		Color retVal = Color.BLACK;
		switch(type){
		
		case STANDARD:
		case SELECTED:
			retVal =  currentEnvironment.getTileAtThisLocation(tempXYZ[0], tempXYZ[1], tempXYZ[2]).representingColorBackGet(CharDisplayTypes.STANDARD, parameters);
			break;
		default:
				retVal = super.representingColorBackGet(type, parameters);
		}
		return retVal;
	}

	public Color representingColorFrontGet(CharDisplayTypes type, int[] parameters) {
		Color retVal = Color.BLACK;
		int[] tempXYZ = super.locationXYZTripletGet();
		
		switch(type){
		
		/*case STANDARD:
			retVal = super.getRepresentingColorFront();
			break;*/
		case SELECTED:
			retVal =  DisplayColorChars.invertThisColor(currentEnvironment.getTileAtThisLocation(tempXYZ[0], tempXYZ[1], tempXYZ[2]).representingColorBackGet(CharDisplayTypes.STANDARD, parameters), false);
			break;
		default:
				retVal = super.representingColorFrontGet(type, parameters);
		}
		return retVal;
	}

	
	/*
	 * this lets this unit know that it should replenish its stats and apply and ailments,
	 * such as fatal wounds
	 */
	public void newTurn() {
		super.newTurn();
		
		theseStats.newTurn();
		theseCosts.newTurn();
		theseSkills.newTurn();
		thisInventory.newTurn();
		theseMessages.newTurn();
		thisVision.newTurn();
		thisMovement.newTurn();
		theseActions.newTurn();
	}
	@Override
	public void checkToSeeIfGroundWillSupport() {
		
		
	}
	public void refreshVisual() {
		currentEnvironment.displayTacticalEnvironment(0);
		
	}
	public AgentMessages getTheseMessages() {
		return theseMessages;
	}
	public void setTheseMessages(AgentMessages theseMessages) {
		this.theseMessages = theseMessages;
	}
	public AgentStats getTheseStats() {
		return theseStats;
	}
	public void setTheseStats(AgentStats theseStats) {
		this.theseStats = theseStats;
	}
	public AgentCosts getTheseCosts() {
		return theseCosts;
	}
	public void setTheseCosts(AgentCosts theseCosts) {
		this.theseCosts = theseCosts;
	}
	public AgentVisionSystem getThisVision() {
		return thisVision;
	}
	public void setThisVision(AgentVisionSystem thisVision) {
		this.thisVision = thisVision;
	}
	public AgentSkills getTheseSkills() {
		return theseSkills;
	}
	public void setTheseSkills(AgentSkills theseSkills) {
		this.theseSkills = theseSkills;
	}
	public AgentInventory getThisInventory() {
		return thisInventory;
	}
	public void setThisInventory(AgentInventory thisInventory) {
		this.thisInventory = thisInventory;
	}
	public AgentMovement getThisMovement() {
		return thisMovement;
	}
	public void setThisMovement(AgentMovement thisMovement) {
		this.thisMovement = thisMovement;
	}
	public AgentEnvironmentActions getTheseActions() {
		return theseActions;
	}
	public void setTheseActions(AgentEnvironmentActions theseActions) {
		this.theseActions = theseActions;
	}
	protected void  updatesLights(boolean undraw){
		if(!undraw){
			//if drawing, make sure to update location first
			thisLight.setLocationXYZ(this.locationXYZTripletGet());
		}
		/*
		 * this could be a linked list if necessary
		 */
		thisLight.drawLight(undraw);
		
	}
}
