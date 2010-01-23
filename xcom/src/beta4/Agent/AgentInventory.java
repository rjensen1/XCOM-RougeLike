package beta4.Agent;

import java.util.Iterator;
import java.util.LinkedList;

import beta4.HasAgency;
import beta4.Environment.EnvironmentTactical;
import beta4.Environment.EquipmentEtc;

public class AgentInventory {
	/*
	 * models the inventory of one agent
	 */
	
	
	
	private LinkedList<EquipmentEtc> [] unitCubbyLLArray; //holds all the linkedlists for equipment
	private String[] inventorySlotNames;
	
	private short[] equipMaxSizeArray; //holds the max size of an item that each array slot can fit
	private short[] equipMaxNumberArray; //holds max total inventory size available for each inventory slots
		//holds the grid array with the TU costs to move items from one spot to another
	int[][] unitMoveItemCostArray;
	private int weightKGEquip;
	private int numCompartmentsWieldable;
	
	private HasAgency actor;
	
	public AgentInventory(HasAgency hasAgency){
		this.actor = actor;
		
	}
	public AgentInventory(HasAgency actor, LinkedList<EquipmentEtc> [] unitCubbyLLArray, String[] inventorySlotNames, short[] equipMaxSizeArray, short[] equipMaxNumberArray, int[][] unitMoveItemCostArray){
		this.actor = actor;
		this.unitCubbyLLArray = unitCubbyLLArray;
		this.inventorySlotNames = inventorySlotNames;
		this.equipMaxSizeArray = equipMaxSizeArray;
		this.equipMaxNumberArray = equipMaxNumberArray;
		this.unitMoveItemCostArray = unitMoveItemCostArray;
	}
	/*
	 *	returns the collected weights of all things in inventory 
	 */
	public int weightGet() {
		int retVal = 0;
		if(unitCubbyLLArray != null){
			for(int i = 0; i < unitCubbyLLArray.length; i++){
				
				Iterator<EquipmentEtc> iterate = unitCubbyLLArray[i].iterator();
				while(iterate.hasNext())
					retVal += iterate.next().weightGet();
			}
		}
		return retVal;
	}
	public String allInventoryToStringForDroppingMoving(boolean showTUCostToGround, int pageNumber, boolean isUnloading) 
	{
		int alpha = 97;
		String retVal = "Inventory:\n";
		int compartmentsToCheck = unitCubbyLLArray.length;
			//if we're unloading a weapon, only have it check those that are held in 'hands'
		if(isUnloading)
			compartmentsToCheck = numCompartmentsWieldable;
		for(int compartment = 0; compartment < unitCubbyLLArray.length; compartment++)
		{		//if there is at least one item in this slot, print it out along with it's TU drop cost
			if( ! (unitCubbyLLArray[compartment].size() < 1)){
				
				int TUcost = TUCostInventoryMoveGet(compartment, groundCompartmentIntNumberGet());
				//if we're unloading, cost is 8
				if(isUnloading)
					TUcost = actor.theseCosts.getTUCostUnloading();
				
				String TUcostString = "";
				if(showTUCostToGround)
					TUcostString += " TU cost to drop: " + TUcost;
				else if(isUnloading)
					TUcostString += " TU cost to unload: " + TUcost;
					//if it's too expensive, mark it with asterix
				if( (!actor.theseStats.TUHas(TUcost)) && (showTUCostToGround || isUnloading) )
					TUcostString +="**";
				if(isUnloading)
					retVal += inventorySlotNames[compartment]+";\n"; 
				else
					retVal += inventorySlotNames[compartment]+"; max item size: "+equipMaxSizeArray[compartment]+ " free space: "+freeSpaceInventoryGet(compartment)+ TUcostString +"\n";
				Iterator<EquipmentEtc> tempIt = unitCubbyLLArray[compartment].iterator();
				while(tempIt.hasNext()){
					retVal += "   ";
						//only print a char and incrememnt alpha if enough TUs
					EquipmentEtc temp = tempIt.next();
					boolean showLoad = isUnloading;
					if(temp.getAmmoLeft() == 0 )
						showLoad  = false;
					if(! TUcostString.contains("**")){
						if(!isUnloading || (isUnloading && showLoad))
						retVal += (char)alpha+ ": ";
						
					}
					else{
						retVal += "   ";
					}
					alpha++;
					
					if(!isUnloading)
						retVal += temp.toString()+"\n";
					else if(isUnloading)
						retVal += temp.toString()+" Ammo: "+temp.getAmmoLeft()+"\n";
				}
			}
				
		}
		
	return retVal;
}
	public void HUDRefresh() {
		// TODO Auto-generated method stub
		
	}

	/*
	 * returns the compartment that's the ground (should be the last compartment)
	 */
	private int groundCompartmentIntNumberGet() {
		
		return (inventorySlotNames.length);
	}
	/*
	 * should calculate the TU it would cost to move from one spot to another (these values should be set in the unit creation area)
	 */
	private int TUCostInventoryMoveGet(int source, int destination){
		int retVal = 999;
		if(source > -1 && destination > -1 && source < unitMoveItemCostArray.length && destination < unitMoveItemCostArray[source].length)
			retVal=unitMoveItemCostArray[source][destination];

	return retVal;
	}
	/*
	 * returns all the inventory in a unit, preceeded by an alpha(numeric) character
	 * in the future, if there are more than 26 things, have it done as 'pages'
	 */
	
	
	/*
	 * iterates through all items, in the same order as appears in item viewer, and drops the item at this position
	 */
	public void dropThisItem(int itemToDrop){
		int alpha = 0;
		boolean dropped = false;
		for(int compartment = 0; compartment < unitCubbyLLArray.length; compartment ++){
			
			
			
			
			
			Iterator<EquipmentEtc> tempIt = unitCubbyLLArray[compartment].iterator();
			int[] tempxyz = actor.locationXYZTripletGet();
			
			while(tempIt.hasNext()){
				if(alpha == itemToDrop){
						//are there enough TU?
					EquipmentEtc tempObToDrop = tempIt.next();
					if(actor.theseStats.TUHas(  TUCostInventoryMoveGet(compartment, groundCompartmentIntNumberGet()))  ){
						actor.theseMessages.errorMessageAdd("Dropping "+ tempObToDrop.toString());
						tempIt.remove();
						((EnvironmentTactical)actor.currentEnvironment).getThisEquipment().dropItemToGround(tempObToDrop, tempxyz[0], tempxyz[1], tempxyz[2]);
						dropped = true;
						actor.theseStats.TUUse( TUCostInventoryMoveGet(compartment, groundCompartmentIntNumberGet()) );
						actor.refreshHUD();
					}
					else{
						actor.theseMessages.errorMessageAdd("Not enough TUs");
						dropped = true;
					}
				}
				else	//burn it up if it isn't right
					tempIt.next();
				alpha++;
				
			}
			
		}
		if(!dropped)
			actor.theseMessages.errorMessageAdd("Invalid item");
	}


	
	/*
	 * input a number matching the inventory array, returns the amount of free space of that array (as in, empty squares
	 */
	private int freeSpaceInventoryGet(int compartment) {
		
		int retVal = 0;
		if(compartment < unitCubbyLLArray.length){
			Iterator<EquipmentEtc> tempIt = unitCubbyLLArray[compartment].iterator();
		
			
		while(tempIt.hasNext()){
			retVal += tempIt.next().getSize();
		}
			//if it isn't left/right hand
		if(compartment != 0 && compartment != 1)
			return equipMaxNumberArray[compartment] - retVal;
		else
			if(retVal != 0)
				return 0;
			return equipMaxSizeArray[compartment];
		}
		return retVal;
	}
	/*
	 * tries to place this piece of equipment in x compartment space, returns null if it succeeded, or the equipment if it failes
	 * 
	 */
	public EquipmentEtc equipThisObject(EquipmentEtc incomingOb, int compartment){
		
		EquipmentEtc retVal = incomingOb;
			//check for free space
		if(freeSpaceInventoryGet(compartment) >= incomingOb.getSize() && unitCubbyLLArray[compartment].size() <= equipMaxNumberArray[compartment]){
			unitCubbyLLArray[compartment].add(retVal);
			//actor.getTheseMessages().errorMessageAdd("Equipped the "+((EnvironmentObject)retVal).thisNameGet());
			retVal=null;
		}
		weightKGEquip = weightGet();
		//actor.refreshHUD();
		return retVal;
	}
	/*
	 * 
	 */
	public EquipmentEtc equipObAtSlotLocationInCompartmentGet(int slot, int compartment){
		
		EquipmentEtc retVal = null;
		Iterator<EquipmentEtc> tempIt = unitCubbyLLArray[compartment].iterator();
			//check to see if size of list is greater than the slot 
		if(unitCubbyLLArray[compartment].size() > slot)
		{
		
			for( int i = 0; i < slot-1; i ++){
				tempIt.next();
			}
			retVal = tempIt.next();
			tempIt.remove();
		}
		weightKGEquip = weightGet();
		
		
		
		actor.refreshHUD();
		return retVal;
	}
	/*
	 * same as previous method, but gets and removes an object based solely by the order it appears as the compartments are iterated through
	 */
	public EquipmentEtc equipObAtSlotLocationInCompartmentGet(int numberOfObject, boolean isLoadedAmmo){
		
		EquipmentEtc retVal = null;
		int itemsFound = 0;
		int maxCompartment = numCompartmentsWieldable;
		if(!isLoadedAmmo)
			maxCompartment = unitCubbyLLArray.length;
		for(int compartment = 0; compartment < maxCompartment; compartment ++){
			Iterator<EquipmentEtc> tempIt = unitCubbyLLArray[compartment].iterator();
				//check to see if size of list is greater than the slot 
			while(tempIt.hasNext()){
				EquipmentEtc temp = tempIt.next();
					//have we found it?
				if(itemsFound == numberOfObject){
					if(isLoadedAmmo){
						if(temp.getObjType() == 0)
							retVal = temp.getAmmoLoadedAndRemove();
					}
					else{
						retVal = temp;
						tempIt.remove();
					}
					//break;
				}
				itemsFound++;
			}
			
		}
		actor.refreshHUD();
		return retVal;
	}
	public String inventoryToString() 
	{
		String retVal = "Inventory:\n";
		for(int compartment = 0; compartment < unitCubbyLLArray.length; compartment++)
		{
			retVal += inventorySlotNames[compartment]+"; max item size: "+equipMaxSizeArray[compartment]+ " free space: "+freeSpaceInventoryGet(compartment)+ "\n";
			Iterator<EquipmentEtc> tempIt = unitCubbyLLArray[compartment].iterator();
			while(tempIt.hasNext()){
				retVal += "   "+ tempIt.next().toString()+"\n";
			}
		}
		
		return retVal;
	}
	public void newTurn() {
		// TODO Auto-generated method stub
		
	}
	public String whereThisItemCanGoToString(boolean isOnGround, int itemNumber, int pageNumber, boolean isUnloading) 
	{
		
		
		int[] sizeID = new int[]{-1,-1,-1};
		int source = -1;
		int destination = -1;
		String item = "";
		boolean thisIsAmmo = false;
		//display the different compartments, showing how much TUs for each one and if they are viable as far as left over space goes. only display alpha characters next to ones that you can use
		int[] xyzTemp = actor.locationXYZTripletGet();
		if(isOnGround && ((EnvironmentTactical)actor.currentEnvironment).getThisEquipment().getNumberOfObjectsAtThisLocation(xyzTemp[0], xyzTemp[1], xyzTemp[2]) > itemNumber*(pageNumber+1))
		{
					//getting a size/id twin
			sizeID = ((EnvironmentTactical)actor.currentEnvironment).getThisEquipment().getSizeIDTypeItemFromGroundAtLocationAndIntPostionInList(xyzTemp[0], xyzTemp[1], xyzTemp[2], itemNumber*(pageNumber+1));
			source = groundCompartmentIntNumberGet();
			item = singleItemOnPersonNameGet(itemNumber*(pageNumber+1), false);
		}
		else if(!isOnGround && !isUnloading)
		{
				//getting an object from inventory
				//get compartment that object is in
			
				source = compartmentOfThisItemNumberGet(itemNumber*(pageNumber+1));
				boolean getLoadedAmmo = false;
				sizeID = sizeIDTypeItemFromPersonIntPostionInListGet(itemNumber*(pageNumber+1), getLoadedAmmo);
				item = singleItemOnPersonNameGet(itemNumber*(pageNumber+1), false);
					
		}
		else if(isUnloading){
			source = compartmentOfThisItemNumberGet(itemNumber*(pageNumber+1));
			boolean getLoadedAmmo = true;
			sizeID = sizeIDTypeItemFromPersonIntPostionInListGet(itemNumber*(pageNumber+1), getLoadedAmmo);
			item = singleItemOnPersonNameGet(itemNumber*(pageNumber+1), true);
		}
		else{ //out of bounds
			return "Out of bounds.";
		}
		
		int alpha = 97;
		String retVal = "Chose where to place "+item+":\n";
			//if this is ammo
		if(sizeID[2] == 1)
			thisIsAmmo = true;
		
		//find the item based on itemNumber and (to be implemented in future, pageNumber)
		
		for(int compartment = 0; compartment < unitCubbyLLArray.length; compartment++)
		{
			boolean showLoadStuff = false;
			String weaponString = "";
			int [] sizeIDofWielded = {-1,-1,-1};
			//if this is ammo and this is a wieldable compartment, check for a suitable weapon that can take this ammo
			if(thisIsAmmo && compartment < this.numCompartmentsWieldable){
					//is there an item here?
				if(unitCubbyLLArray[compartment].size() != 0){
					EquipmentEtc temp = unitCubbyLLArray[compartment].peekFirst();
					
					if(temp != null && temp.doesItTakeThisAmmo(sizeID[2]) && temp.getAmmoLeft() == 0){
						showLoadStuff = true;
						weaponString = temp.toString();
						
					}
				}
			}
			if(!showLoadStuff){
				boolean impossible = false;
				destination = compartment;
				int TUcost = actor.theseCosts.getTUCostUnloading();
				if(!isUnloading)
					TUcost = TUCostInventoryMoveGet(source, destination);
				String TUcostString = "";
				
					TUcostString += " TU cost to move: " + TUcost;
					//if it's too expensive, mark it with asterix
				if( !actor.theseStats.TUHas(TUcost))
				{
					TUcostString +="**";
					impossible = true;
				}
				String sizeStuff = "";
					//if compartment is full, or the item is too big
				if((sizeID[0] > freeSpaceInventoryGet(compartment)) || sizeID[0] > equipMaxSizeArray[compartment])
				{
					sizeStuff += "*: ";
					impossible = true;
				}
				
				
				if(!impossible)
				{
					retVal += (char)alpha+ ": ";
				}
				
				
				retVal += sizeStuff + inventorySlotNames[compartment]+"; max item size: "+equipMaxSizeArray[compartment]+ " free space: "+freeSpaceInventoryGet(compartment)+ TUcostString +"\n";
			}
			else{
				int TUcost = 15;
				
				String TUcostString = "";
				
				TUcostString += " TU cost to load: " + TUcost;
				
				boolean impossible = false;
				
				if( !actor.theseStats.TUHas(TUcost))
				{
					TUcostString +="**";
					impossible = true;
				}
				if(!impossible)
				{
					retVal += (char)alpha+ ": ";
				}
				retVal += inventorySlotNames[compartment]+";\n" + TUcostString +" "+ weaponString+"\n";
			}
				alpha++;
				
			}
		return retVal;
				
	}
	/*
	 * should look through a player's inventory and return the size/type of this item 
	 */
	private int[] sizeIDTypeItemFromPersonIntPostionInListGet(int itemNumberToLookFor, boolean getLoadedAmmo) {
		
		int[] sizeType = new int[]{-1,-1,-1};
		int itemsFound = 0;
		int maxCompartment = unitCubbyLLArray.length;
		if(getLoadedAmmo)
			maxCompartment = numCompartmentsWieldable;
		
		for(int compartment = 0; compartment < maxCompartment; compartment ++){
			//are there objects?
		if(unitCubbyLLArray[compartment].size() > 0){
			Iterator<EquipmentEtc> tempIt = unitCubbyLLArray[compartment].iterator();
			while(tempIt.hasNext()){
				EquipmentEtc temp = null;
				if(getLoadedAmmo)
					temp = tempIt.next().getAmmoLoaded();
				else
					temp = tempIt.next();
				if(itemNumberToLookFor == itemsFound && temp != null){
					
					sizeType[0] = temp.getSize(); 
					sizeType[1] = temp.getObjId();
					sizeType[2] = temp.getObjType();
					
				}
				itemsFound++;
				
			}
		}
	}
	
		return sizeType;
	}
	/*
	 * returns the name of the object at this number
	 */
	private String singleItemOnPersonNameGet(int itemNumberToLookFor, boolean isLoadedAmmo) {

		String retVal = "Invalid object";
		int itemsFound = 0;
	
		for(int compartment = 0; compartment < unitCubbyLLArray.length; compartment ++){
			//are there objects?
			if(unitCubbyLLArray[compartment].size() > 0){
				Iterator<EquipmentEtc> tempIt = unitCubbyLLArray[compartment].iterator();
				while(tempIt.hasNext()){
					EquipmentEtc temp = null;
					if(isLoadedAmmo)
						temp = tempIt.next().getAmmoLoaded();
					else
						temp = tempIt.next();
					if(itemNumberToLookFor == itemsFound){
						if(!isLoadedAmmo)
							retVal = temp.thisNameGet();
						else if(temp != null)
							retVal = temp.thisNameGet()+" "+temp.getAmmoLeft();
						//break;
					}
					itemsFound++;
					
				}
			}
		}
		return retVal;
	}
	/*
	 * returns the compartment that this item number is in, iterating from the compartments all the way to the end.
	 */
	private int compartmentOfThisItemNumberGet(int itemNumber) {
		
		
		for(int compartment = 0; compartment < unitCubbyLLArray.length; compartment ++){
				//are there objects?
			if(unitCubbyLLArray[compartment].size() > 0){
				Iterator<EquipmentEtc> tempIt = unitCubbyLLArray[compartment].iterator();
				while(tempIt.hasNext()){
					tempIt.next();
					itemNumber--;
					
				}
				if(itemNumber < 0)
					return compartment;
			}
		}
		//error, not found
		return -1;
	}
	/*
	 * lists the items that the character is stangind on, preceded by alpha characters; page skips ahead 26 items
	 */
	public String showItemsOnGroundWithAlpha(int page) {
		boolean prefixWithAlphaCharacters = true;
		
		int[] xyzTemp = actor.locationXYZTripletGet();
		return ((EnvironmentTactical)actor.currentEnvironment).getThisEquipment().getItemGroundNameList(xyzTemp[0], xyzTemp[1], xyzTemp[2], prefixWithAlphaCharacters, page);
		
	}
	/*
	 * inputs an item number that is on the person, puts it in the given compartment
	 */
	public void moveItemInInventory(int tempItemNumToPickupOrMove, int tempPlaceToPutObject, boolean sourceIsGround, boolean itemIsLoaded) {
		
		EquipmentEtc toMove = null;
		int compartment=0;
		boolean found = false;
		int TUcost = 0;
		boolean areWeLoadingThisIntoAWeapon = false;
		if(!sourceIsGround){
			//moving an item from one unitcubby to another/not ground!
			
			//int itemCounter = 0;
			TUcost = actor.theseCosts.getTUCostUnloading();
			if(!itemIsLoaded)
				TUcost = TUCostInventoryMoveGet(compartmentOfThisItemNumberGet(tempItemNumToPickupOrMove), tempPlaceToPutObject);
			boolean getLoadedAmmo = itemIsLoaded;
			int[] sizeID = sizeIDTypeItemFromPersonIntPostionInListGet(tempItemNumToPickupOrMove, getLoadedAmmo);
			boolean inRange = (tempPlaceToPutObject < equipMaxSizeArray.length);
				//is this ammo and are we putting it in a wieldable spot?
			if(sizeID[2] == 1 && tempPlaceToPutObject < this.numCompartmentsWieldable){
					//does this take this kind of ammo, and if it does, is it empty?
				if(unitCubbyLLArray[tempPlaceToPutObject].size()!=0 && unitCubbyLLArray[tempPlaceToPutObject].peekFirst().doesItTakeThisAmmo(sizeID[1])){
					if(unitCubbyLLArray[tempPlaceToPutObject].peekFirst().getAmmoLeft() == 0){
					areWeLoadingThisIntoAWeapon = true;
					TUcost = actor.theseCosts.getTUCostLoading();
					}
					else
						actor.theseMessages.errorMessageAdd("Weapon is already loaded.");
				}
				else
					actor.theseMessages.errorMessageAdd("Weapon doesn't take this ammo.");
			}
			boolean foundItemOnPerson = false;
			boolean enoughTU = false;
			boolean enoughSpace = false;
			boolean itemNotTooBig = false;
			if(inRange){
				foundItemOnPerson = (sizeID[0] !=-1 && sizeID[1] !=-1);
				enoughTU = actor.theseStats.TUHas(TUcost);
				enoughSpace = (sizeID[0] <= freeSpaceInventoryGet(tempPlaceToPutObject));
				itemNotTooBig = sizeID[0] <= equipMaxSizeArray[tempPlaceToPutObject];
			}
			
				//if we can put this object this place
			if(foundItemOnPerson && enoughTU && ((enoughSpace && itemNotTooBig) || areWeLoadingThisIntoAWeapon)){
					//should get and remove this in the folloowing called method.
				EquipmentEtc tempToMove = this.equipObAtSlotLocationInCompartmentGet(tempItemNumToPickupOrMove,itemIsLoaded);
				if(!areWeLoadingThisIntoAWeapon)
					unitCubbyLLArray[tempPlaceToPutObject].add(tempToMove);
				else{ //we are
					EquipmentEtc tempWeapon = unitCubbyLLArray[tempPlaceToPutObject].peekFirst();
					tempWeapon.loadAmmo(tempToMove);
					actor.theseMessages.errorMessageAdd("Ammo Loaded");
				}
				actor.theseStats.TUUse(TUcost);
			}
			else{
				//errors!
				if(!inRange)
					actor.theseMessages.errorMessageAdd("Cannot put it there.");
				else if(!foundItemOnPerson)
					actor.theseMessages.errorMessageAdd("Invalid object");
				else if(!enoughTU)
					actor.theseMessages.errorMessageAdd("Not enough TUs");
				else if(!enoughSpace)
					actor.theseMessages.errorMessageAdd("Not enough free space");
				else if(!itemNotTooBig)
					actor.theseMessages.errorMessageAdd("Item is too large");
			}
			
			
		}
		else{
			//getting the item from the ground
			
			
			TUcost = TUCostInventoryMoveGet(groundCompartmentIntNumberGet(), tempPlaceToPutObject);
			int[] xyzTemp = actor.locationXYZTripletGet();
			toMove = ((EnvironmentTactical)actor.currentEnvironment).getThisEquipment().getItemFromGroundAtLocationAndIntPostionInList(xyzTemp[0], xyzTemp[1], xyzTemp[2], tempItemNumToPickupOrMove);
			if(toMove !=null)
				found = true;
				
		
		if(found && actor.theseStats.TUHas(TUcost)){
			//if it found the item and there are enough time units, try to place it
			toMove = this.equipThisObject(toMove, tempPlaceToPutObject);
		}
		if(toMove != null){
			//it wasn't properly placed, better put it back.
			if(!sourceIsGround)
				unitCubbyLLArray[compartment].add(toMove);
			else{
				((EnvironmentTactical)actor.currentEnvironment).getThisEquipment().dropItemToGround(toMove, xyzTemp[0], xyzTemp[0], xyzTemp[0]);
			}
		}
		else
			actor.theseStats.TUUse(TUcost);
		}
	}
	public void setUnitCubbyLLArray(LinkedList<EquipmentEtc>[] unitCubbyLLArray) {
		this.unitCubbyLLArray = unitCubbyLLArray;
	}
	public void setInventorySlotNames(String[] inventorySlotNames) {
		this.inventorySlotNames = inventorySlotNames;
	}
	public void setEquipMaxSizeArray(short[] equipMaxSizeArray) {
		this.equipMaxSizeArray = equipMaxSizeArray;
	}
	public void setEquipMaxNumberArray(short[] equipMaxNumberArray) {
		this.equipMaxNumberArray = equipMaxNumberArray;
	}
	public void setUnitMoveItemCostArray(int[][] unitMoveItemCostArray) {
		this.unitMoveItemCostArray = unitMoveItemCostArray;
	}
	public void setWeightKGEquip(int weightKGEquip) {
		this.weightKGEquip = weightKGEquip;
	}
	public void setNumCompartmentsWieldable(int numCompartmentsWieldable) {
		this.numCompartmentsWieldable = numCompartmentsWieldable;
	}
	public void setActor(HasAgency actor) {
		this.actor = actor;
	}
	
}
