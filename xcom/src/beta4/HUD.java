package beta4;



public class HUD {
	
	

	private static String pointingAt;
	private static int encumberance; 
	private static int HUDType;
	private static String unitName;
	private static String errors;
	private static int maxTimeUnits;
	private static int currentTimeUnits;
	private static String rank;
	private static int maxEnergy;
	private static int currentEnergy;
	private static int maxHealth;
	private static int currentHealth;
	private static String lookDirection;
	private static boolean lookLock;
	private static String stance;
	private static String moveCost;
	private static boolean stealthOn;
	private static int weightUnitKG;
	private static int weightCarriedKG;
	private static int currentStrength;
	private static int maxStrength;
	private static int workingHeight;
	private static String holdingLeft;
	private static String holdingRight;
	private static String allInventory;
//    private static EquipmentEtc inLimbo;
	private static String standingOnString;
	private static int[] holdingLeftAccuracy;
	private static int[] holdingRightAccuracy;
	private static String standingOnEquip;
	private static int effectiveFOV;
	
	
	public static String displayHUD(int type){
		String retVal="";
		if(type==0 /*tactical normal*/){
		
		//tactical normal
		if(HUDType==0){
			retVal=unitName+" TUs: "+currentTimeUnits+"/"+maxTimeUnits+"  Health: "+currentHealth+"/"+maxHealth+"  Energy: "+currentEnergy+"/"+maxEnergy+"\n";
			retVal+=stance+" Move Cost:"+moveCost+" Facing:"+lookDirection+" FOV: "+effectiveFOV+" rank: "+rank+" Stealth: ";
			{
			if(stealthOn)
				retVal+="Yes ";
			else
				retVal+="No ";
			}
			retVal+=" Standing on: "+standingOnString+", ";
			if(standingOnEquip != null)
				retVal += standingOnEquip;
			else
				retVal += "no items. ";
			{
			if(lookLock)
				retVal+=" Just looking\n";
			else
				retVal+="\n";
			}
			retVal+="Units Weight: "+weightUnitKG+"KG"+" Equipment weight "+weightCarriedKG+"KG "+"Strength: "+currentStrength+"/"+maxStrength+" Encumberance: "+encumberance+" Height: "+workingHeight+"cm\n";
			/*retVal+="LeftHand: "+holdingLeft.toString();
			
			
			if(holdingLeftAccuracy!=null){
				retVal+=" Accuracy: ";
				for(int i=0;i<holdingLeftAccuracy.length;i++){
					retVal+=holdingLeftAccuracy[i]+"% ";
				}
			}
			retVal+="RightHand: "+holdingRight.toString();
			
			if(holdingRightAccuracy!=null){
				retVal+=" Accuracy: ";
				for(int i=0;i<holdingRightAccuracy.length;i++){
					retVal+=holdingRightAccuracy[i]+"% ";
				}
			}*/
			//retVal+="\n"+allInventory;
			if(pointingAt!=null){
				errors+=pointingAt;
			}
			retVal+=errors+"\n";
			
		}
		
		return retVal;
		}
		else if(type==1){
			retVal=unitName+" TUs: "+currentTimeUnits+"\n";
			
			
			
			
			retVal+=errors+"\n";
			
			return retVal;
			
		}
		//no unit selected
		else if(type==-1){
			return "No Unit Selected";
		}
		return "HUD";
	}
	
	//eventually, chanch this from an equpemnt etc array to some sort of object that does a linked list, with a max size equal to the number of squares that a piece of equipment takes up
	
		
	
	public static void holdingLeftStringSet(String newHoldingName){
		holdingLeft=newHoldingName;
	}
		
	
	public static void holdingLeftAccuracySet(int[] newAccuracy){
		holdingLeftAccuracy=newAccuracy;
		
	}

	
	
	
	
	public static void standingOnTileStringSet(String newStandingOn){
		standingOnString=newStandingOn;
	}
	public static void stealthOnSet(boolean newStealth){
		stealthOn=newStealth;
	}
	public static void rankSet(String newRankString){
		rank=newRankString;
	}
	public static void objectNameSet(String newName){
		unitName=newName;
	}
	public static void errorsSet(String newErrors){
		errors=newErrors;
	}
	public static void currentTimeUnitsSet(int currentTU){
		currentTimeUnits=currentTU;
	}
	public static void maxTimeUnitsSet(int maxTU){
		maxTimeUnits=maxTU;
	}
	
	public static void maxEnergySet(int newMaxEnergy){
		maxEnergy=newMaxEnergy;
	}
	public static void currentEnergySet(int newCurrentEnergy){
		currentEnergy=newCurrentEnergy;
	}
	public static void maxHealthSet(int newMaxHealth){
		maxHealth=newMaxHealth;
	}
	public static void currentHealthSet(int newCurrentHealth){
		currentHealth=newCurrentHealth;
	}
	public static void lookDirectionSet(String newLookDirection){
		lookDirection=newLookDirection;
	}
	public static void lookLockSet(boolean in){
		lookLock=in;
	}
	public static void stanceSet(String newStance){
		stance=newStance;
	}
	public static void moveCostSet(String newCost){
		moveCost=newCost;
	}
	public HUD(){
		HUDType=0;
		unitName="";
		errors="";
		maxTimeUnits=0;
		currentTimeUnits=0;
		rank="";
		maxEnergy=0;
		currentEnergy=0;
		maxHealth=0;
		currentHealth=0;
		lookDirection="";
		stance="";
		moveCost="";
		stealthOn=false;
		
		standingOnString="nothing";
		standingOnEquip="";
	}
	
	public static void weightCarriedSet(int newWeight){
		weightCarriedKG=newWeight;
	}
	public static void standingOnEquipStringSet(String newStandingOnEquip){
		standingOnEquip=newStandingOnEquip;
	}
	
	
	
	public static void weightUnitSet(int newWeight){
		weightUnitKG=newWeight;
	}
	public static void encumberanceSet(int newEncumberance){
		encumberance=newEncumberance;
	}
	public static void currentStrengthSet(int newStrength){
		currentStrength=newStrength;
	}
	public static void maxStrengthSet(int newStrength){
		maxStrength=newStrength;
	}
	public static void workingUnitHeightSet(int newHeight){
		workingHeight=newHeight;
	}
	public static void cursorPointingSet(String newPointingAt){
		if(newPointingAt.length()<1)
			pointingAt=null;
		else{
			pointingAt=newPointingAt;
		}
	}
	public static void inventorySet(String newAllInventory) {
		allInventory = newAllInventory;
		
	}

	public static void FOVSet(int effectiveFOVincoming) {
		effectiveFOV = effectiveFOVincoming;
		
	}

	
	
	
	
	

}