package beta4.Agent;

import beta4.HUD;
import beta4.HasAgency;

public class AgentStats {
	
	private HasAgency actor;
	
	
	private int currentTimeUnits;
	private int maxTimeUnits;
	private int currentEnergy;
	private int currentHealth;
	private int currentStrength;
	private int maxEnergy;
	private int maxHealth;
	private int maxStrength;
	private int thisAgentSex;
	private int experience;
	private int rank;
	private String[] rankArray;
	private String[] stanceNameStringArray;
	private double[] stanceHeightModArray;//TODO instantiate this!!! will hold the values use to modify the height based on the unit's stance
	private int stanceMinForMobility;
	private boolean[] stanceAvailable;
	private int stance;


	private boolean stealthOn;
	
	
	


	public AgentStats(HasAgency actor){
		this.actor = actor;
	}
	
	
	public String currentTimeUnitsGet() {
		// TODO Auto-generated method stub
		return null;
	}
	public String thisAgentSexGet() {
		// TODO Auto-generated method stub
		return null;
	}
	public void HUDRefresh() {
		// TODO Auto-generated method stub
		HUD.currentTimeUnitsSet(maxTimeUnits);
		
	}
	private String stanceToString() {
		// TODO Auto-generated method stub
		return stanceNameStringArray[stance];
	}
	
	//is there enough energy for this cost?
	public boolean ENHas(int toCheck){
		return toCheck <= currentEnergy;
	}
	public void ENUse(int toCheck){
		if(ENHas(toCheck))
			currentEnergy -= toCheck;
	}
	//would check against unitDefs/encumbrance to see if it can use a stance
 	private boolean stanceHasThisAvailable(int toCheck){
 		//TODO make this actually check something. (see if certain stances are off limits)
 		return stanceAvailable[toCheck];
 	}

	public boolean stanceUp(){
 		//not dead or unconscious
 		boolean retVal=false;
 		int origStance=stance;
 		if(stance >= stanceMinForMobility && stance < stanceNameStringArray.length-2){
 				//make sure that unit can have this stance
 			boolean hasntUpped = true;
 			while(hasntUpped)
 			{
 				//this should be able to look ahead more than just one
 				if(stanceHasThisAvailable(stance+1)){
 					stance += 1;
 					hasntUpped = false;
 				}
 			}
 			if(stance != origStance){
 	 			int tempTU = currentTimeUnits;
 	 			int tempEnergy = currentEnergy;
 	 			boolean error = false;
 	 			int ENCost = actor.theseCosts.getStanceENCostSwitchUpArray(stance);
 	 			int TUCost = actor.theseCosts.getStanceTUCostSwitchUpArray(stance);
 	 			
 	 			if(TUHas(TUCost)&& ENHas(ENCost)){
	 					tempTU -= TUCost;
		 				tempEnergy -= ENCost;
 					}
				else{
					actor.theseMessages.errorMessageAdd("Not enough TU/enegry to increase stance");
 					error=true;
 				}
 	 			
 	 			if(!error){
 	 				retVal=true;
 	 	 			currentTimeUnits = tempTU;
 	 	 			currentEnergy = tempEnergy;
 	 	 			
 	 	 		}
 	 		}
 			
 				
 			
 		}
 		else if(stance < stanceMinForMobility)
 			actor.theseMessages.errorMessageAdd("You're dead or unable to move");
 		else 
 			actor.theseMessages.errorMessageAdd("You can't get any faster");
 		
 		//updateOutput();
 		return retVal;
 		
 	}

 	//decreases the stance of a unit after checking to make sure that it has the TUs Energy to do such a thing
 	public boolean stanceDown(){
 		//not dead or unconscious
 		boolean retVal = false;
 		//TODO make sure stance up/down changes TU/EN properly
 		int origStance = stance;
 		if(stance > stanceMinForMobility){
 			//if unit has this stance
 			boolean hasntDowned = true;
 			while(hasntDowned)
 			{
 				//this should be able to look ahead more than just one
 				if(stanceHasThisAvailable(stance-1)){
 					stance-=1;
 					hasntDowned=false;
 				}
 			}
 		}
 		else if(stance < stanceMinForMobility)
 			actor.theseMessages.errorMessageAdd("You're dead or unconscious");
 		else 
 			actor.theseMessages.errorMessageAdd("You can't get any lower.");
 		//if you changed, charge for some energies
 		if(stance!=origStance){
 			int tempTU=currentTimeUnits;
 			int tempEnergy=currentEnergy;
 			boolean error=false;
 			
 			int ENCost = actor.theseCosts.getStanceENCostSwitchDownArray(stance);
	 		int TUCost = actor.theseCosts.getStanceTUCostSwitchDownArray(stance);
	 		
	 		if(TUHas(TUCost)&& ENHas(ENCost)){
					tempTU -= TUCost;
					tempEnergy -= ENCost;
				}
				else{
					actor.theseMessages.errorMessageAdd("Not enough TU/enegry to lower stance");
					error=true;
				}
 			if(!error){
 	 			currentTimeUnits = tempTU;
 	 			currentEnergy = tempEnergy;
 	 			retVal=true;
 	 		}
 		}
 		//updateOutput();
 		return retVal;
 	}
	private String rankToString() {
			
			return rankArray[rank];
			
		}
	public int heightGet() 
	{
		
			int retVal = (int)(stanceHeightModArray[stance]*actor.heightCMGet());
			
			return retVal;
		
	}
	public void toggleStealthOn(){
		stealthOn = !stealthOn;
		actor.refreshHUD();
	}
	public void newTurn() {
		currentTimeUnits = maxTimeUnits;
		currentEnergy += maxTimeUnits/3;
			//making sure not to have more than max energy
		if(currentEnergy > maxEnergy)
			currentEnergy = maxEnergy;
		
	}
	public boolean rankIncrease(){
		/*
		 *  5=Commander	  Only one allowed over all X-COM Bases, requires at least 30 soldiers in all bases.
			4=Colonel	Most experienced Captain is promoted to Colonel if opening present. 1 Colonel for 23 soldiers, limit of 10.
			3=Captain	Most experienced Sergeant is promoted to Captain if opening present. 1 Captain for 11 soldiers, limit of 22.
			2=Sergeant	Most experienced Squaddie is promoted to Sergeant if opening present. 1 Sergeant for 5 soldiers, limit of 50.
			1=Squaddie	Promotion is gained upon attacking any alien, may not be given with high troop counts.
			0=Rookie	Starting rank for all new recruits.
		 */
		if(!(rank == -1) && (rank < rankArray.length-1)){
			rank++;
			return true;
		}
		else 
			return false;
	}


	public boolean TUHas(int i) {
		
		return (i <= currentTimeUnits);
	}


	public boolean TUUse(int i) {
		
		if (TUHas(i)){
			currentTimeUnits -= i;
			return true;
		}
		return false;
		
	}


	public void setActor(HasAgency actor) {
		this.actor = actor;
	}


	public void setCurrentTimeUnits(int currentTimeUnits) {
		this.currentTimeUnits = currentTimeUnits;
	}


	public void setMaxTimeUnits(int maxTimeUnits) {
		this.maxTimeUnits = this.currentTimeUnits = maxTimeUnits;
	}


	public void setCurrentEnergy(int currentEnergy) {
		this.currentEnergy = currentEnergy;
	}


	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}


	public void setCurrentStrength(int currentStrength) {
		this.currentStrength = currentStrength;
	}


	public void setMaxEnergy(int maxEnergy) {
		this.maxEnergy = this.currentEnergy = maxEnergy;
	}


	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}


	public void setMaxStrength(int maxStrength) {
		this.maxStrength = maxStrength;
	}


	public void setThisAgentSex(int thisAgentSex) {
		this.thisAgentSex = thisAgentSex;
	}


	public void setExperience(int experience) {
		this.experience = experience;
	}


	public void setRank(int rank) {
		this.rank = rank;
	}


	public void setRankArray(String[] rankArray) {
		this.rankArray = rankArray;
	}


	public void setStanceNameStringArray(String[] stanceNameStringArray) {
		this.stanceNameStringArray = stanceNameStringArray;
	}


	public void setStanceHeightModArray(double[] stanceHeightModArray) {
		this.stanceHeightModArray = stanceHeightModArray;
	}


	public void setStanceMinForMobility(int stanceMinForMobility) {
		this.stanceMinForMobility = stanceMinForMobility;
	}


	public void setStanceAvailable(boolean[] stanceAvailable) {
		this.stanceAvailable = stanceAvailable;
	}
	public void setStance(int stance) {
		this.stance = stance;
	}


	public void setStealthOn(boolean stealthOn) {
		this.stealthOn = stealthOn;
	}


	public HasAgency getActor() {
		return actor;
	}


	public int getCurrentTimeUnits() {
		return currentTimeUnits;
	}


	public int getMaxTimeUnits() {
		return maxTimeUnits;
	}


	public int getCurrentEnergy() {
		return currentEnergy;
	}


	public int getCurrentHealth() {
		return currentHealth;
	}


	public int getCurrentStrength() {
		return currentStrength;
	}


	public int getMaxEnergy() {
		return maxEnergy;
	}


	public int getMaxHealth() {
		return maxHealth;
	}


	public int getMaxStrength() {
		return maxStrength;
	}


	public int getThisAgentSex() {
		return thisAgentSex;
	}


	public int getExperience() {
		return experience;
	}


	public int getRank() {
		return rank;
	}


	public String[] getRankArray() {
		return rankArray;
	}


	public String[] getStanceNameStringArray() {
		return stanceNameStringArray;
	}


	public double[] getStanceHeightModArray() {
		return stanceHeightModArray;
	}


	public int getStanceMinForMobility() {
		return stanceMinForMobility;
	}


	public boolean[] getStanceAvailable() {
		return stanceAvailable;
	}


	public int getStance() {
		return stance;
	}


	public boolean isStealthOn() {
		return stealthOn;
	}
	

}
