package beta4.Environment;

import java.awt.Color;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

import beta4.DisplayColorChars;




public class EquipmentEtc extends EnvironmentObject{
	
		//can be switch to change how things are sorted.
	
	
	private boolean unlimitedAmmo;
	
	
	 
	//private int[] rangeAccuracyFalloff;
	//private int[] rangeDamageFalloff;
	private int[] acurracy;
	private int[] TUCostPerShot;
	private int objType;
		//0=weapon
	private int objID;
		//each different kind of item would be unique
	private int[] takesAmmoID;
	private EquipmentEtc ammoSlot;
	private boolean doesAmmoStack;
	private int ammoShotsLeft;
		//a=armor piercing
	private char[] tags;
		//how many inventory slots it takes up
	private int sizeInventory;
	
	private int damage;
	
	
	
	public EquipmentEtc(){
		super();
		//throw accuracy will always be calculated using weight and size?
	}
	
	//probably should have subclasses for different major equipment types.
	public EquipmentEtc(int thing, EnvironmentTactical thisOne){
		
		super();
		
		int weightKG = -1;
		String name = "unnamed";
	
		
		
			//if a default compare hasn't been set yet
		
		
		//TODO put this in each spot
		//blocksMovement = false;
		char representing = '?';
		switch(thing){
	
		
		case 0:
			//pistol
			weightKG=2;
			//throw/snap/auto/aimed, if one is not do-able, just put in a 0
			/*
			 *     * Snap: 18% (Accuracy 60%)
    * 				Aimed: 30% (Accuracy 78%) 
			 */
			acurracy=new int[]{18,0,78};
			//a percent, to be multiplied with the units total TUs
			TUCostPerShot=new int[]{18,0,30};
			objType=0; //weapon
			name="Pistol";
			representing='P';
			objID=0;
			//means that only objID of 1 will be accepted as ammo... this should probably be an array, so that different kinds of ammo can be used.
			takesAmmoID= new int[]{1};
			unlimitedAmmo=false;
			
			sizeInventory=2;
			break;
		case 1:
			//pistol ammo
			weightKG=1;
			//throw/snap/auto/aimed, if one is not do-able, just put in a 0
			acurracy=null;
			objType=1; //ammo
			name="Pistol ammo";
			representing='p';
			objID=1;
			//means that only objID of 1 will be accepted as ammo... this should probably be an array, so that different kinds of ammo can be used.
			takesAmmoID=new int[]{-1};
			unlimitedAmmo=false;
			doesAmmoStack=false;
			ammoShotsLeft=12;
			tags=new char[]{'a'};
			damage=26;
			sizeInventory=1;
			break;
		case 2:
			//semi-Automatic Rifle
			weightKG=3;
			//throw/snap/auto/aimed, if one is not do-able, just put in a 0
			/*
			 *     * Auto: 35% (Accuracy 35%)
			 * 	Snap: 25% (Accuracy 60%)
			 * 	Aimed: 80% (Accuracy 110%) 
			 */
			acurracy=new int[]{60,35,110};
			//a percent, to be multiplied with the units total TUs
			TUCostPerShot=new int[]{25,35,80};
			objType=0; //weapon
			name="Semi-Auto Rifle";
			representing='R';
			
			objID=2;
			//means that only objID of 1 will be accepted as ammo... this should probably be an array, so that different kinds of ammo can be used.
			takesAmmoID=new int[]{3};
			unlimitedAmmo=false;
			sizeInventory=3;
			break;
		case 3:
			// semi-auto ammo
			weightKG=1;
			//throw/snap/auto/aimed, if one is not do-able, just put in a 0
			acurracy=null;
			objType=1; //ammo
			name="Semi-Auto ammo";
			representing='r';
			objID=3;
			//means that only objID of 1 will be accepted as ammo... this should probably be an array, so that different kinds of ammo can be used.
			takesAmmoID=new int[]{-1};
			unlimitedAmmo=false;
			doesAmmoStack=false;
			ammoShotsLeft=35;
			
			tags=new char[]{'a'};
			damage=30;
			sizeInventory=1;
			break;
		case 4:
			//sniper rifle
			weightKG=4;
			//throw/snap/auto/aimed, if one is not do-able, just put in a 0
			acurracy=new int[]{65,0,125};
			//a percent, to be multiplied with the units total TUs
			TUCostPerShot=new int[]{45,0,85};
			objType=0; //weapon
			name="Sniper Rifle";
			representing='S';
			objID=4;
			sizeInventory=4;
			//means that only objID of 1 will be accepted as ammo... this should probably be an array, so that different kinds of ammo can be used.
			takesAmmoID=new int[]{5};
			unlimitedAmmo=false;
			break;
		case 5:
			// sniper rifle ammo
			weightKG=1;
			//throw/snap/auto/aimed, if one is not do-able, just put in a 0
			acurracy=null;
			objType=1; //ammo
			name="Sniper Rifle ammo";
			representing='s';
			objID=3;
			//means that only objID of 1 will be accepted as ammo... this should probably be an array, so that different kinds of ammo can be used.
			takesAmmoID=new int[]{-1};
			unlimitedAmmo=false;
			doesAmmoStack=false;
			ammoShotsLeft=10;
			sizeInventory = 1;
			damage=38;
			tags=new char[]{'a'};
			break;
		case 6:
		 //heavy cannon
			
			
			weightKG=8;
			//throw/snap/auto/aimed, if one is not do-able, just put in a 0
			acurracy=new int[]{60,0,90};
			//a percent, to be multiplied with the units total TUs
			TUCostPerShot=new int[]{33,0,80};
			objType=0; //weapon
			name="Heavy cannon";
			representing='H';
			objID=6;
			sizeInventory=6;
			//means that only objID of 1 will be accepted as ammo... this should probably be an array, so that different kinds of ammo can be used.
			takesAmmoID = new int[]{7,8,9};
			unlimitedAmmo=false;
			break;
		case 7:
			// HC AP ammo
			weightKG=3;
			//throw/snap/auto/aimed, if one is not do-able, just put in a 0
			acurracy=null;
			objType=1; //ammo
			name="HC AP ammo";
			representing='h';
			objID=7;
			//means that only objID of 1 will be accepted as ammo... this should probably be an array, so that different kinds of ammo can be used.
			takesAmmoID=new int[]{-1};
			unlimitedAmmo=false;
			sizeInventory = 2;
			doesAmmoStack=false;
			ammoShotsLeft=6;
			damage=56;
			tags=new char[]{'a'};
			break;
		case 8:
			// HC HE ammo
			weightKG=3;
			//throw/snap/auto/aimed, if one is not do-able, just put in a 0
			acurracy=null;
			objType=1; //ammo
			name="HC HE ammo";
			representing='h';
			objID=8;
			//means that only objID of 1 will be accepted as ammo... this should probably be an array, so that different kinds of ammo can be used.
			takesAmmoID=new int[]{-1};
			unlimitedAmmo=false;
			doesAmmoStack=false;
			ammoShotsLeft=6;
			sizeInventory = 2;
			damage=52;
			tags=new char[]{'e'};
			break;
		case 9:
			// HC AP ammo
			weightKG=3;
			//throw/snap/auto/aimed, if one is not do-able, just put in a 0
			acurracy=null;
			objType=1; //ammo
			name="HC IN ammo";
			representing='h';
			objID=9;
			//means that only objID of 1 will be accepted as ammo... this should probably be an array, so that different kinds of ammo can be used.
			takesAmmoID=new int[]{-1};
			unlimitedAmmo=false;
			doesAmmoStack=false;
			sizeInventory = 2;
			ammoShotsLeft=6;
			damage=60;
			tags=new char[]{'i'};
			break;
		case 10:
			//gradade
			weightKG=1;
			//throw/snap/auto/aimed, if one is not do-able, just put in a 0
			acurracy=null;
			this.TUCostPerShot=new int[]{50}; //cost to prime
			objType=2; //grenade
			name="Grenade";
			representing='g';
			objID=10;
			sizeInventory = 1;
			//means that only objID of 1 will be accepted as ammo... this should probably be an array, so that different kinds of ammo can be used.
			takesAmmoID=new int[]{-1};
			unlimitedAmmo=false;
			doesAmmoStack=false;
			ammoShotsLeft=-1;//used as a timer... -1 means not primed, 0 means to blow up when checked by end-of-turn
			damage=50;
			tags=new char[]{'e'};
			break;
			
		default:
				
		}
		super.weightSet(weightKG);
		super.thisNameSet(name);
		/*super.representingCharSet(representing);
		super.representingColorBackSet(Color.BLACK);
		super.representingColorFrontSet(Color.GRAY);*/
		//TODO custom item colors
		setTheseRepresentingChars(new DisplayColorChars[][]{{new DisplayColorChars(representing,Color.GRAY, Color.BLACK)}});
	}
	public int fireGun(int shotsFired){
		//not enough bullets
		int retVal=0;
		if(unlimitedAmmo){
			retVal=shotsFired;
		}
		else if(ammoSlot==null){
			retVal=0;
			
		}
		//there is ammo, but not quite enough, or it's exactly the right amount, fire all you can and then set ammo to null
		else if((ammoSlot!= null) && (shotsFired >= getAmmoLeft())){
			retVal = ammoSlot.getAmmoLeft();
			ammoSlot = null;
		}
		else{
			ammoSlot.ammoShotsLeft-=shotsFired;
			retVal= shotsFired;
		}
		return retVal;
		
	}
	public int[] getTUCost(){
		return TUCostPerShot;
	}
	public int getThrowCostPercent(){
		int retVal;
		int startVal = weightGet() * sizeInventory;
		if(startVal<10)
			retVal=15;
		else if(startVal<25)
			retVal=25;
		else if(startVal<50)
			retVal=40;
		else
			retVal=55;
		return retVal;
	}
	public boolean grenadePrime(int turns){
		//would add this grenade to the primed grenades list once it is thrown or dropped
		//returns false only when turns is out of bounds, or item is not a grenade
		if(turns<0 || turns>30 || objType!=2 || (objType==2 && ammoShotsLeft!=-1))
			return false;
		else{
			ammoShotsLeft=turns;
			return true;
		}
	}
	public boolean grenadeIsPrimed(){
		//returns true if the grenade is primed, false if it is not a grenade or not primed
		if(this.objType!=2 || (objType==2 && this.ammoShotsLeft!=-1))
				return false;
		else return true;
	}
	public int[] getAcurracy(){
		return acurracy;
	}
	public int getObjType(){
		return objType;
	}
	public int getObjId(){
		return objID;
	}
	/*
	 * checks this ammo id against what it accepts
	 */
	public boolean doesItTakeThisAmmo(int toCheck){
		boolean accepts = false;
		for(int i = 0; i < takesAmmoID.length; i++){
			if(toCheck == takesAmmoID[i])
				accepts = true;
		}
		return accepts;
	}
	public boolean takesAmmoID(int toCheck){
		boolean takesAmmo=false;
		for(int i=0;i<takesAmmoID.length;i++){
			if(toCheck==takesAmmoID[i])
				takesAmmo=true;
		}
		return takesAmmo;
	}
	//for checking the ammo
	public EquipmentEtc getAmmoLoaded(){
		return ammoSlot;
	}
	//gets the ammo while removing it.
	public EquipmentEtc getAmmoLoadedAndRemove(){
		EquipmentEtc temp = ammoSlot;
		ammoSlot = null;
		return temp;
	}
	public int getAmmoLeft(){
		int retVal = ammoShotsLeft;
		if(ammoSlot != null){
			retVal=ammoSlot.ammoShotsLeft;
		}
		
		return retVal;
		//return 7;
	}
	
	public int weightGet(){
		int retVal = super.weightGet();
		if(ammoSlot != null){
			retVal += ammoSlot.weightGet();
		}
		return retVal;
	}
	//should return general infor about this object, size, ammo (if weapon)/weight (weight will include weight of ammo, if any
	public String toString(){
		String retVal = ""+weightGet();
		
		if(takesAmmoID[0]!= -1 || objType == 1){
			retVal+="Ammo: "+getAmmoLeft()+" ";
		}
		else if(objType==0)
			retVal+="No Ammo ";
		retVal+=("Weight: "+weightGet()+" Size: "+sizeInventory);
		
		return retVal;
	}
	//used to load/unload ammo. can load a weapon with 'null' or real ammo, but either way, if there is already ammo in it, this gets unloaded and returned.
	public EquipmentEtc loadAmmo(EquipmentEtc newAmmo){
		boolean takesAmmo=false;
		for(int i=0;i<takesAmmoID.length;i++){
			if(newAmmo.objID==takesAmmoID[i])
				takesAmmo=true;
		}
		if(takesAmmo)
		{
			EquipmentEtc retVal=newAmmo;
			//is there already ammo in it?
			if(ammoSlot!=null){
				//can this ammo stack?
				if(newAmmo.doesAmmoStack && ammoSlot.doesAmmoStack){
					increaseAmmo(newAmmo.ammoShotsLeft);
				}
				else
				{
					retVal=ammoSlot;
					ammoSlot=newAmmo;
					
				}
				
			}
			else{
				ammoSlot=retVal;
				retVal=null;
			}
			return retVal;
		}
		else{
			//error!
			return newAmmo;
		}
	}
	private void increaseAmmo(int toAdd){
		ammoSlot.ammoShotsLeft+=toAdd;
	}
	/*
	 * returns the size of an object
	 */
	public int getSize(){
		return sizeInventory;
	}
	
	
	//public static enum whatToRepresent {STANDARD }
	//private boolean blocksMovement;
	
	/*
	 * should check howComparing to waysToCompare and use that method
	 */
	/*public int compare(Object o1, Object o2) {
		
		
		EquipmentEtc ob1 = (EquipmentEtc)o1;
		EquipmentEtc ob2 = (EquipmentEtc)o2;
			//largest goes on top
		if((waysToCompare)howComparing == waysToCompare.SIZELARGESTFIRST){
			if(ob1.size < ob2.size)
				return -1;
			if(ob1.size == ob2.size)
				//TODO put in secondary ways to sort if size is equal? perhaps have this inside of a while loop, like, while(not sorted) and it knows which thing it goes through, has an array of sort precedence objects that can be adjusted
				return 0;
			else
				return 1;
		}
		return -10;
	}*/
	/*
	 * inputs a chunk of items, returns an int, relative to how many stories below they should be placed.
	 */
	public static int itemsAreInFreeFall(EnvironmentEquipment currentEnvironment, int x, int y, int z, LinkedList<EquipmentEtc> equipList){
		int distanceFell = 0;
		int totalObjectsWeight = weightObjectsTotal(equipList);
		boolean falling = true;
		while(falling){
			if(z != 0 /*&& currentEnvironment.getIsPassableHorizontal(x, y, z-1)*/){
				distanceFell += 1;
				z -= 1;
					//if the lower sector allows for free vertical passage, IE air, fall automatically.
				/*if(!currentEnvironment.getIsPassableVertical(x, y, z)){
					//TODO, actually use a formula for how much extra force there would be because of weight.
				
					if(currentEnvironment.checkThisTileForWeightStrength(x, y, z, distanceFell*totalObjectsWeight) == EnvironmentTactical.TileWeightCheck.WILLBREAK){
						currentEnvironment.breakThisTile(x, y, z);
					}
					else{ //the falling body is stopped
						falling = false;
					}
				}*/
				
				
			}
			else{
				falling = false;
			}
			
			
		//TODO make it so that if you hit something (IE, passible horizontal = false) that you do damage, whatever.
		}
		return distanceFell;
		
		
	}
	/*
	 * inputs a linked list of equipment, returns the total weight of all the objects in it.
	 */
	private static int weightObjectsTotal(LinkedList<EquipmentEtc> equipList) {
		int weight = 0;
		if(equipList != null){
			Iterator<EquipmentEtc> it = equipList.iterator();
			while(it.hasNext()){
				EquipmentEtc temp = it.next();
				weight += temp.weightGet();
			}
		}
		return weight;
	}

	

	
	

	
	

}
