package beta4;

public class InputManagerTactical{
	//objects that this class needs access to
	private static EnvironmentTactical currentEnvironment;
	
		//used to keep track of what part of the game the command is in
	//private static boolean tacticalMissionStandard;
	
		//used to keep track of what part of the key commands the input should go to (tactical mission)
	private static boolean tacticalMissionLookToggleOn;
	private static boolean tacticalMissionTargetingWeaponOrThrow;
	private static boolean tacticalMissionUsingAnItem;
	private static boolean inHelpScreen;
	private static boolean tacticalMissionInInventoryScreen;
	private static boolean tacticalMissionRegularInput;
	
			//for mapping the keys, TACTICAL
	private static char tacticalMissionUnitDownKey;
	private static char tacticalMissionUnitUpKey;
	private static char tacticalMissionEndTurnKey;
	private static char tacticalMissionEndMissionKey;
	private static char tacticalMissionLookToggleKey;
	private static char tacticalMissionUseItemInHandKey;
	private static char tacticalMissionStanceUpKey;
	private static char tacticalMissionStanceDownKey;
	private static char tacticalMissionStealthToggleKey;
	private static char tacticalMissionInventoryScreenKey;
	private static char tacticalMissionCycleNextUnitKey;
	private static char tacticalMissionCyclePreviousUnitKey;
	private static char tacticalMissionDeselectUnitKey;
	private static char tacticalMissionTargetSomethingKey;
	private static char escapeKey;
	private static char toggleGodVisionKey; 
	//TODO make god vision toggle

	private static boolean tacticalMissionOpenDoor;

	

	private static char tacticalMissionCloseDoorKey;

	private static char tacticalMissionOpenDoorKey;

	private static boolean tacticalMissionCloseDoor;
	private static boolean tacticalMissionScrollPanLock;

	private static char tacticalMissionScrollPanLockKey;

	private static char tacticalMissionGetKey;

	private static char tacticalMissionDropKey;

	private static char tacticalMissionMoveKey;

	private static char tacticalMissionUnloadKey;

	private static boolean tacticalMissionGettingAnItem;

	private static boolean tacticalMissionDroppingAnItem;

	private static boolean tacticalMissionMovingAnItem;

	private static boolean tacticalMissionUnloadingAWeapon;

	private static boolean getMoveItemListed;

	private static int tempItemNumToPickupOrMove;

	private static int tempPlaceToPutObject;

	
	
	/*
	 * would only need this when starting it at first... something to get the ball rolling
	 */
	public static void setInputType(char in){
		switch(in){
		case 't':
				//All tactical commands go through here
			//tacticalMissionStandard=true;
			tacticalMissionRegularInput=true;
			setUpTheKeys();
			break;
			default:
				break;
		}
		
	}
	/*
	 * should all the user to remap the keys 
	 */
	public static void remapTheKeys(){
		// TODO Auto-generated method stub
	}	
	/*
	 * should set the keys to default when loading the game, or perhaps load them from an external settings file.
	 */
	public static void setUpTheKeys(){
				//TACTICAL MISSION KEYS
		tacticalMissionUnitDownKey = '>';
		tacticalMissionUnitUpKey = '<';
		tacticalMissionEndTurnKey = 'E';
		tacticalMissionEndMissionKey = 'M';
		tacticalMissionLookToggleKey = 'L';
		tacticalMissionUseItemInHandKey = 'F';
		tacticalMissionStanceUpKey = 'U';
		tacticalMissionStanceDownKey = 'K';
		tacticalMissionStealthToggleKey = 'S';
		tacticalMissionInventoryScreenKey = 'i';
		tacticalMissionCycleNextUnitKey = 'N';
		tacticalMissionCyclePreviousUnitKey = 'P';
		tacticalMissionDeselectUnitKey = 'X';
		tacticalMissionTargetSomethingKey = 'T';
		tacticalMissionOpenDoorKey = 'o';
		tacticalMissionCloseDoorKey = 'c';
		tacticalMissionScrollPanLockKey = 'y';
		tacticalMissionGetKey = 'g';
		tacticalMissionDropKey = 'd';
		tacticalMissionMoveKey = 'm';
		tacticalMissionUnloadKey = 'u';
		tacticalMissionRegularInput=true;
		
		//always the same
		escapeKey=(char)27;
			 
	}
	/*
	 * all commands originally come through this method. use booleans, set by other methods in this class
	 * to keep track of what's going on.
	 */
	public static void incomingCommand(char fromKeyBoard){
		/*
		 * Use booleans to know which part of the input the user is in, tacticalMissionStandard handles all tactical mission commands
		 */
		
			tacticalMissionStandard(fromKeyBoard);
		
	}
	/*
	 * all input for the tactical mission will come through this method. Things should be grouped, based on what you can do in each situation, to make things more simple
	 */
	private static void tacticalMissionStandard(char input){
		if(currentEnvironment != null)
		currentEnvironment.addToUnitMessages((int)input+"");
		
		/*
		 * all commands that can always be used go here
		 * 
		 */
		{
			/*
			 * enter the help menu, should work no matter what you're doing
			 */
			if(input=='?'){
				inHelpScreen(' ');
			}
		}
				//if you're in the help screen, which should keep track of what part of the game you're in
		if(inHelpScreen){
			inHelpScreen(input);
		}
		else if(tacticalMissionOpenDoor){
			tacticalMissionOpenDoor(input);
		}
		else if(tacticalMissionCloseDoor){
			tacticalMissionCloseDoor(input);
		}
		/*
		 * if you're looking around the screen
		 */
		else if(tacticalMissionScrollPanLock){
			tacticalMissionScrollPan(input);
		}
		
				//all commands that can be used while looking around get put here
		else if(tacticalMissionLookToggleOn){
			tacticalMissionLookToggle(input);
			
		}
				//all commands that can be used while targeting can go here
		else if(tacticalMissionTargetingWeaponOrThrow){
			tacticalMissionTargetingWeapongOrThrow(input);
		}
		else if(tacticalMissionUsingAnItem){
			tacticalMissionUseItem(input);
			
				
		}
		else if(tacticalMissionGettingAnItem){
			tacticalMissionGetItem(input);
			//drop back to inventory! if no longer getting
			if(tacticalMissionInInventoryScreen && !tacticalMissionGettingAnItem)
				tacticalMissionViewInventoryScreen(' ');
		}
		else if(tacticalMissionDroppingAnItem){
			tacticalMissionDropItem(input);
			//drop back to inventory! if no longer dropping
			if(tacticalMissionInInventoryScreen && !tacticalMissionDroppingAnItem)
				tacticalMissionViewInventoryScreen(' ');
		}
		else if(tacticalMissionMovingAnItem){
			tacticalMissionMoveItem(input);
			//drop back to inventory! if no longer moving
			if(tacticalMissionInInventoryScreen && !tacticalMissionMovingAnItem)
				tacticalMissionViewInventoryScreen(' ');
		
		}
		else if(tacticalMissionUnloadingAWeapon){ //could move be used to unload weapons?
			tacticalMissionUnloadWeapon(input);
				//drop back to inventory if no longer unloading
			if(tacticalMissionInInventoryScreen && !tacticalMissionUnloadingAWeapon)
				tacticalMissionViewInventoryScreen(' ');
		}
				//if you're in the inventory screen, should come after get, drop, move, and unload (so that those will drop back to this when they are done
		else if(tacticalMissionInInventoryScreen){
			tacticalMissionViewInventoryScreen(input);
		}
		//All commands the can be used from the normal screen get put here
		else if(tacticalMissionRegularInput){
			tacticalMissionTacticalMissionRegularInput(input);
		}
		

	}
	/*
	 * should handle scrolling around the screen, default lock key is 'y'
	 */
	private static void tacticalMissionScrollPan(char input) {
		
		if(input == ' '){
			currentEnvironment.addToUnitMessages("Scroll lock is on");
			tacticalMissionScrollPanLock = true;
		}
		else if(input==escapeKey){
			tacticalMissionOpenDoor = false;
			currentEnvironment.addToUnitMessages("Scroll lock is off");
			tacticalMissionScrollPanLock = false;
		}
		else{
			int tempInput = (int)input -48;
			if((tempInput >-1) && (tempInput <10) && (tempInput != 5)){
					currentEnvironment.scroll(Tools.getLocationAdjustment(tempInput), 0);
					currentEnvironment.displayTacticalEnvironment(0);
			}
		}
		
	}
	/*
	 * should deal with opening doors
	 */
	private static void tacticalMissionOpenDoor(char input) {
		
		if(input==' '){
			tacticalMissionOpenDoor = true;
			currentEnvironment.addToUnitMessages("Open door in which direction?");
		}
		else if(input==escapeKey){
			tacticalMissionOpenDoor = false;
			currentEnvironment.addToUnitMessages("Cancelled open door");
		}
		else if((int)input>48 && (int)input<58){
				//false closes a door, true opens it
			currentEnvironment.getCurrentActivePCAgent().getTheseActions().openDoor(true, (input - 48));
			tacticalMissionOpenDoor = false;
		}
		
	}
	/*
	 * deals with closing of doors
	 */
	private static void tacticalMissionCloseDoor(char input) {
		
		if(input==' '){
			tacticalMissionCloseDoor = true;
			currentEnvironment.addToUnitMessages("Close door in which direction?");
		}
		else if(input==escapeKey){
			tacticalMissionCloseDoor = false;
			currentEnvironment.addToUnitMessages("Door closing cancelled");
		}
		else if((int)input>48 && (int)input<58){
				//false closes a door, true opens it
			currentEnvironment.getCurrentActivePCAgent().getTheseActions().openDoor(false, (input - 48));
			tacticalMissionCloseDoor = false;
		}
		
	}
	/*
	 * all help screens would go through here, but the method itself would not alter other booleans, but instead, 
	 * would use them to decide what kind of help it was going to give you SPACE MEANS IT"S THE INITIATION OF THIS METHOID
	 */
	private static void inHelpScreen(char input) {
		//if it isn't alreadyin the helpscreen, do this!
		
		//show the intial help stuff!
		if(input==' '){
			inHelpScreen=true;
			currentEnvironment.addToUnitMessages("entering help");
		}
		else if(input==escapeKey){
			inHelpScreen=false;
			currentEnvironment.addToUnitMessages("leaving help");
		}
		String commands = "Down stairs: "+tacticalMissionUnitDownKey+"\n";
		commands += "Up stairs: "+tacticalMissionUnitUpKey+"\n";
		commands += "End Turn: "+tacticalMissionEndTurnKey+ "\n";
		commands += "Look toggle key: "+tacticalMissionLookToggleKey+ "\n";
		commands += "Stance up key: "+tacticalMissionStanceUpKey+ "\n";
		commands += "Stance down key: "+tacticalMissionStanceDownKey+ "\n";
		commands += "End Mission: "+tacticalMissionEndMissionKey+ "\n";
		commands += "Next unit: "+tacticalMissionCycleNextUnitKey+ "\n";
		commands += "Previous unit: "+tacticalMissionCyclePreviousUnitKey+ "\n";
		commands += "Toggle Stealth: "+tacticalMissionStealthToggleKey+ "\n";
		commands += "Previous unit: "+tacticalMissionCyclePreviousUnitKey+ "\n";
		commands += "De-select unit: "+tacticalMissionDeselectUnitKey+ "\n";
		commands += "Open door: "+tacticalMissionOpenDoorKey+ "\n";
		commands += "Close door: "+tacticalMissionCloseDoorKey+ "\n";
		commands += "Scroll/pan lock: "+tacticalMissionScrollPanLockKey+"\n";
		commands += "Get item from ground: "+tacticalMissionGetKey+"\n";
		commands += "Drop item to ground: "+tacticalMissionDropKey+"\n";
		commands += "Move item in inventory: "+tacticalMissionMoveKey+"\n";
		commands += "Unload a weapon: "+tacticalMissionUnloadKey+"\n";	
		commands += "Hit escape to continue";
		
		/*
		 *
		tacticalMissionUseItemInHandKey = 'F';
		tacticalMissionStanceUpKey = 'U';
		tacticalMissionStanceDownKey = 'K';
		tacticalMissionStealthToggleKey = 'S';
		tacticalMissionInventoryScreenKey = 'i';
		tacticalMissionCycleNextUnitKey = 'N';
		tacticalMissionCyclePreviousUnitKey = 'P';
		tacticalMissionDeselectUnitKey = 'X';
		tacticalMissionTargetSomethingKey = 'T';
		tacticalMissionOpenDoorKey = 'o';
		tacticalMissionCloseDoorKey = 'c';
		tacticalMissionScrollPanLockKey = 'y';
		 */
		currentEnvironment.addToUnitMessages(commands);
		
		
		
		
		/*
		 * have separate instances for which kind of help to show, based on which booleans are turned.
		 */
		
	}
	
	/*
	 * all targeting/throwing commands go through here? (maybe split these up or have them branch from here?) SPACE MEANS IT"S THE INITIATION OF THIS METHOID
	 */
	private static void tacticalMissionTargetingWeapongOrThrow(char input) {
		// TODO Auto-generated method stub
		
		//show the intial help stuff!
		if(input==' '){
			tacticalMissionTargetingWeaponOrThrow=true;
			currentEnvironment.addToUnitMessages("entering targeting something");	
		}
		
		//move the targeting cursor
		else if(((int)input>48 && (int)input<58)){
			moveTargetCursor(input-48);
		}
		//escape key/ cancel
		else if(input==escapeKey){
			tacticalMissionTargetingWeaponOrThrow=false;
			currentEnvironment.addToUnitMessages("leaving targeting something");
		}
	}
	/*
	 * all look commands go through here SPACE MEANS IT"S THE INITIATION OF THIS METHOID
	 */
	private static void tacticalMissionLookToggle(char input) {
		// TODO Auto-generated method stub
		/*
		 * could this maybe make a pretend / temp agent that is stored somewhere in the environ that is moved around? (so it can use the already existing code)
		 */
		
		//show the intial help stuff!
		if(input==' '){
			tacticalMissionLookToggleOn = true;
			currentEnvironment.addToUnitMessages("entering look toggle");
		}
		
		//move the look cursor
		else if(((int)input>48 && (int)input<58)){
			tacticalMissionMoveLookCursor(input-48);
		}
		else if (input==escapeKey){
			tacticalMissionLookToggleOn = false;
			currentEnvironment.addToUnitMessages("leaving look toggle");
		}
		
	}
	/*
	 * everything you can do from the main screen in a tactical mission goes through here. 
	 */
	private static void tacticalMissionTacticalMissionRegularInput(char input) {
		
	
			
			//movement, 1-9, if moving, but no 5
		if(((int)input>48 && (int)input<58 && (int)input!=53)){
			tacticalMissionMoveUnit(input-48);
		}
			//move unit up/down stairs
		else if((input==tacticalMissionUnitUpKey || input==tacticalMissionUnitDownKey)){
			tacticalMissionMoveUnitUpDownStairs(input);
		}
		/*
		 * end the turn! should also reset all input booleans, if they aren't done so already
		 */
		else if(input==tacticalMissionEndTurnKey){
			
			newTurn();
		}
		/*
		 * open a door
		 */
		else if(input == tacticalMissionOpenDoorKey){
			tacticalMissionOpenDoor(' ');
		}
		/*
		 * close a door
		 */
		else if(input == tacticalMissionCloseDoorKey){
			tacticalMissionCloseDoor(' ');
		}
		else if(input == tacticalMissionScrollPanLockKey){
			tacticalMissionScrollPan(' ');
		}
		/*
		 * end the mission!
		 */
		else if(input==tacticalMissionEndMissionKey){
				currentEnvironment.addToUnitMessages("End mission");
			//currentEnvironment.endMission();
		}
		/*
		 * toggles look on, sends a space when it is first entered
		 */
		else if(input==tacticalMissionLookToggleKey){
			
			tacticalMissionLookToggle(' ');
		}
		/*
		 * get an object!
		 */
		else if(input == tacticalMissionGetKey){
			tacticalMissionGetItem(' ');
			
				
		}
		/*
		 * drop an object
		 */
		else if(input == tacticalMissionDropKey){
			tacticalMissionDropItem(' ');
		}
		/*
		 * move an object
		 */
		else if(input == tacticalMissionMoveKey){
			tacticalMissionMoveItem(' ');
		}
		/*
		 * unload a weapon
		 */
		else if(input == tacticalMissionUnloadKey){
			tacticalMissionUnloadWeapon(' ');
		}
		/*
		 * use an item in your hand, sends a space when it is first entered
		 */
		else if(input==tacticalMissionUseItemInHandKey){
			//tacticalMissionUsingAnItem = true;
			tacticalMissionUseItem(' ');
		}
		/*
		 * stance is up, you can do this, I suppose, if you are looking around or targeting
		 */
		else if(input==tacticalMissionStanceUpKey){
			tacticalMissionStanceUp();
		}
		/*
		 * stance is down, you can do this, I suppose, if you are looking around or targeting
		 */
		else if(input==tacticalMissionStanceDownKey){
			tacticalMissionStanceDown();
		}
		/*
		 * toggles stealth on/off
		 */
		else if(input==tacticalMissionStealthToggleKey){
			tacticalMissionStealthToggle();
		}
		/*
		 * enters the inventory screen
		 */
		else if(input==tacticalMissionInventoryScreenKey){
			//tacticalMissionInInventoryScreen = true; //, sends a space when it is first entered
			tacticalMissionViewInventoryScreen(' ');
		}
		/*
		 * goes forward in the unit queue
		 */
		else if(input==tacticalMissionCycleNextUnitKey){
			tacticalMissionCycleUnitForward();
		}
		/*
		 * goes backward in the unit queue
		 */
		else if(input==tacticalMissionCyclePreviousUnitKey){
			tacticalMissionCycleUnitBackward();
		}
		/*
		 * goes forward in the unit queue, but deselects the current unit... if all of they are deselected and you hit it again, it reselects them
		 */
		else if(input==tacticalMissionDeselectUnitKey){
			tacticalMissionDeselectUnit();
		}
		/*
		 * target something
		 */
		else if(input==tacticalMissionTargetSomethingKey){
			tacticalMissionTargetingWeaponOrThrow = true;
			tacticalMissionTargetingWeapongOrThrow(' ');
		}
			
		
	}
	/*
	 * should deal with dropping an item
	 */
	private static void tacticalMissionDropItem(char input) {
		
		
		//list all equipment in inventory
			//if no equipment, give an error an exit drop item
		
		
		if(input==' '){
			tacticalMissionDroppingAnItem=true;
			currentEnvironment.addToUnitMessages("Drop an item, or press escape to cancel.");	
			boolean showTUCostToGround = true;
			boolean isUnloading = false;
			currentEnvironment.addToUnitMessages(currentEnvironment.getCurrentActivePCAgent().getThisInventory().allInventoryToStringForDroppingMoving(showTUCostToGround, 0, isUnloading));
		}
		
		else if(input==escapeKey){
			tacticalMissionDroppingAnItem=false;
			currentEnvironment.addToUnitMessages("leaving drop item");	
		}
			//if it's between a and z
		else if(((int)input - 97 > -1) && ((int)input - 122 < 1)){
			currentEnvironment.getCurrentActivePCAgent().getThisInventory().dropThisItem((int)input - 97);
			tacticalMissionDroppingAnItem=false;
			
			currentEnvironment.displayTacticalEnvironment(0);
			currentEnvironment.addToUnitMessages("leaving drop item");	
		}
	}
	/*
	 * should deal with unloading an item
	 */
	private static void tacticalMissionUnloadWeapon(char input) {
		
		
		
		if(input==' '){
			tacticalMissionUnloadingAWeapon=true;
			currentEnvironment.addToUnitMessages("entering weapon unloading");	
			boolean showTUCostToGround = false;
			boolean isUnloading = true;
			currentEnvironment.addToUnitMessages(currentEnvironment.getCurrentActivePCAgent().getThisInventory().allInventoryToStringForDroppingMoving(showTUCostToGround, 0, isUnloading));
		}
		
		else if(input==escapeKey){
			tacticalMissionUnloadingAWeapon = false;
			getMoveItemListed = false;
			tacticalMissionUnloadingAWeapon = false;
			currentEnvironment.addToUnitMessages("leaving weapon unloading");	
		}
		//if it's between a and z
		else if(((int)input - 97 > -1) && ((int)input - 122 < 1)){
			
			
			
			
			
			boolean onGround = false;
			int page = 0;
			if(!getMoveItemListed){
					//list the objects
				boolean isUnloading = true;
			currentEnvironment.addToUnitMessages(currentEnvironment.getCurrentActivePCAgent().getThisInventory().whereThisItemCanGoToString(onGround, (int)input - 97, page, isUnloading));
				tempItemNumToPickupOrMove = ((int)input - 97)*(page+1);
				getMoveItemListed = true;
			}
			else{
				//input should be to place an object
				tempPlaceToPutObject = (int)(input - 97) ;
				//pick up object tempItemNumToPickupOrMove and put it tempPlaceToPutObject 
				boolean itemOnGround = false;
				boolean itemIsLoaded = true;
				currentEnvironment.getCurrentActivePCAgent().getThisInventory().moveItemInInventory(tempItemNumToPickupOrMove, tempPlaceToPutObject, itemOnGround, itemIsLoaded);
				tacticalMissionUnloadingAWeapon = false;
				currentEnvironment.addToUnitMessages("Done");
				currentEnvironment.displayTacticalEnvironment(0);
				getMoveItemListed = false;
				
			}
			
			
			
			
		}
	}
	/*
	 * should deal with moving an item around in the inventory
	 */
	private static void tacticalMissionMoveItem(char input) {
		
		if(input==' '){
			tacticalMissionMovingAnItem = true;
			currentEnvironment.addToUnitMessages("entering move item");	
			boolean showTUCostToGround = false;
			boolean isUnloading = false;
			currentEnvironment.addToUnitMessages(currentEnvironment.getCurrentActivePCAgent().getThisInventory().allInventoryToStringForDroppingMoving(showTUCostToGround, 0, isUnloading));
		}
		
		else if(input==escapeKey){
			tacticalMissionMovingAnItem=false;
			getMoveItemListed = false;
			currentEnvironment.addToUnitMessages("leaving move item");	
		}
		//if it's between a and z
		else if(((int)input - 97 > -1) && ((int)input - 122 < 1)){
			
			
			
			
			
			boolean onGround = false;
			int page = 0;
			if(!getMoveItemListed){
					//list the objects
				boolean isUnloading = false;
			currentEnvironment.addToUnitMessages(currentEnvironment.getCurrentActivePCAgent().getThisInventory().whereThisItemCanGoToString(onGround, (int)input - 97, page, isUnloading));
				tempItemNumToPickupOrMove = ((int)input - 97)*(page+1);
				getMoveItemListed = true;
			}
			else{
				//input should be to place an object
				tempPlaceToPutObject = (int)(input - 97) ;
				//pick up object tempItemNumToPickupOrMove and put it tempPlaceToPutObject 
				boolean itemOnGround = false;
				boolean itemIsLoaded = false;
				currentEnvironment.getCurrentActivePCAgent().getThisInventory().moveItemInInventory(tempItemNumToPickupOrMove, tempPlaceToPutObject, itemOnGround,  itemIsLoaded );
				tacticalMissionMovingAnItem = false;
				currentEnvironment.addToUnitMessages("Done");
				currentEnvironment.displayTacticalEnvironment(0);
				getMoveItemListed = false;
				
			}
			
			
			
			
		}
	}
	/*
	 * should deal with getting an item from the ground
	 */
	private static void tacticalMissionGetItem(char input) {
		
		
		
		if(input==' '){
			tacticalMissionGettingAnItem = true;
			currentEnvironment.addToUnitMessages("Get item from ground:");	
			int page = 0;
			currentEnvironment.addToUnitMessages(currentEnvironment.getCurrentActivePCAgent().getThisInventory().showItemsOnGroundWithAlpha(page));
		}
		
		else if(input==escapeKey){
			tacticalMissionGettingAnItem = false;
			currentEnvironment.addToUnitMessages("Done");
			getMoveItemListed = false;
		}
		//if it's between a and z
		else if(((int)input - 97 > -1) && ((int)input - 122 < 1)){
			boolean onGround = true;
			int page = 0;
			if(!getMoveItemListed){
					//list the objects
				boolean isUnloading = false;
			currentEnvironment.addToUnitMessages(currentEnvironment.getCurrentActivePCAgent().getThisInventory().whereThisItemCanGoToString(onGround, (int)input - 97, page,  isUnloading ));
				tempItemNumToPickupOrMove = ((int)input - 97)*(page+1);
				getMoveItemListed = true;
			}
			else{
				//input should be to place an object
				tempPlaceToPutObject = (int)(input - 97) ;
				//pick up object tempItemNumToPickupOrMove and put it tempPlaceToPutObject 
				boolean itemOnGround = true;
				boolean itemIsLoaded = false;
				currentEnvironment.getCurrentActivePCAgent().getThisInventory().moveItemInInventory(tempItemNumToPickupOrMove, tempPlaceToPutObject, itemOnGround, itemIsLoaded);
				tacticalMissionGettingAnItem = false;
				currentEnvironment.addToUnitMessages("Done");
				currentEnvironment.displayTacticalEnvironment(0);
				getMoveItemListed = false;
				
				//currentEnvironment.getCurrentActivePCAgent().move
			}
			//tacticalMissionMoveItem(' '); //keep looping until escaped
		}
	}


	/*
	 * should start a new turn!
	 */
	private static void newTurn() {
		
		/* (new turn)
		 * 
				environment.displayTacticalEnvironment(0);
		 */
		currentEnvironment.newTurn();
		currentEnvironment.displayTacticalEnvironment(0);
	}
	
	/*
	 * deselects a unit, selecting the next unit forward
	 */
	private static void tacticalMissionDeselectUnit() {
		
		//currentEnvironment.addToUnitMessages("cycle unit forward deslect");
		currentEnvironment.selectNextPCUnitDeselectCurrent();
		currentEnvironment.displayTacticalEnvironment(0);
	}
	/*
	 * cycle units backward
	 */
	private static void tacticalMissionCycleUnitBackward() {
		
		currentEnvironment.selectPreviousUnitPC();
		currentEnvironment.displayTacticalEnvironment(0);
		//currentEnvironment.addToUnitMessages("cycle unit back");
	}
	/*
	 * cycle units forward
	 */
	private static void tacticalMissionCycleUnitForward() {
		
			
			//next
		currentEnvironment.selectNextUnitPC();
			
		currentEnvironment.displayTacticalEnvironment(0);
		
	}
	/*
	 * space means this the screen is loading, shows the inventory screen and takes all relevent commands for it
	 */
	private static void tacticalMissionViewInventoryScreen(char input) {
		//show the intial help stuff!
		if(input==' '){
			tacticalMissionInInventoryScreen = true;
			currentEnvironment.addToUnitMessages("Inventory: "+tacticalMissionGetKey+": To pick up an item, "+tacticalMissionDropKey+": Drops an item, \n"+tacticalMissionMoveKey+": To move an item, "+tacticalMissionUnloadKey+": to unload a wielded weapon, "+escapeKey+": If done.\n");
			currentEnvironment.addToUnitMessages(currentEnvironment.getCurrentActivePCAgent().getThisInventory().inventoryToString());
			
		}
		else if(input == tacticalMissionGetKey){
			currentEnvironment.addToUnitMessages("In inventory: Getting Item From Ground");
			if(tacticalMissionGettingAnItem)
				tacticalMissionGetItem(input);
			else
				tacticalMissionGetItem(' ');
			
		}
		else if(input == tacticalMissionDropKey){
			currentEnvironment.addToUnitMessages("In inventory: Dropping item to ground");
			if(tacticalMissionDroppingAnItem)
				tacticalMissionDropItem(input);
			else
				tacticalMissionDropItem(' ');
			
		}
		else if(input == tacticalMissionMoveKey){
			currentEnvironment.addToUnitMessages("In inventory: Moving item in inventory");
			if(tacticalMissionMovingAnItem)
				tacticalMissionMoveItem(input);
			else
				tacticalMissionMoveItem(' ');
			
		}
		else if(input == tacticalMissionUnloadKey){
			currentEnvironment.addToUnitMessages("In inventory: Unloading a wielded weapon");
			if(tacticalMissionUnloadingAWeapon)
				tacticalMissionUnloadWeapon(input);
			else
				tacticalMissionUnloadWeapon(' ');
			
		}
		else if(input == escapeKey){
			tacticalMissionInInventoryScreen = false;
			getMoveItemListed = false;
			tacticalMissionGettingAnItem = false;
			tacticalMissionMovingAnItem = false;
			tacticalMissionDroppingAnItem=false;
			tacticalMissionUnloadingAWeapon = false;
			currentEnvironment.addToUnitMessages("Leaving inventory Screen");	
		}
		
	}
	/*
	 * toggles stealth
	 */
	private static void tacticalMissionStealthToggle() {
		
		currentEnvironment.addToUnitMessages("Toggle Stealth");
		currentEnvironment.getCurrentActivePCAgent().getTheseStats().toggleStealthOn();
		currentEnvironment.displayTacticalEnvironment(0);
		
	}
	/*
	 * lowers a unit's stance
	 */
	private static void tacticalMissionStanceDown() {
		/*
		 * 	case 'c':
			//crouch
			environment.stanceDown();
			environment.displayTacticalEnvironment(0);
			break;
		 */
		// TODO Auto-generated method stub
		currentEnvironment.addToUnitMessages("stance down");
		currentEnvironment.getCurrentActivePCAgent().getTheseStats().stanceDown();
		currentEnvironment.displayTacticalEnvironment(0);
	}
	/*
	 * increases a unit's stance
	 */
	private static void tacticalMissionStanceUp() {
		/*
		 * case 's':
			//stand
			environment.stanceUp();
			environment.displayTacticalEnvironment(0);
			break;
		 */
		// TODO Auto-generated method stub
		currentEnvironment.addToUnitMessages("stance up");
		currentEnvironment.getCurrentActivePCAgent().getTheseStats().stanceUp();
		currentEnvironment.displayTacticalEnvironment(0);
	}

	/*
	 * starts everything to use an item, space means it's starting
	 */
	private static void tacticalMissionUseItem(char input){ 
	
	
	
	//show the intial help stuff!
	if(input==' '){
		tacticalMissionUsingAnItem=true;
		currentEnvironment.addToUnitMessages("entering use item");	
	}
	else if(input==escapeKey){
		tacticalMissionUsingAnItem=false;
		currentEnvironment.addToUnitMessages("leaving use item");	
	}
		
		/*
		 * 	//toggle look
		case 'F':
			useItem();
			environment.displayTacticalEnvironment(0);
			environment.clearErrors();
			break;
		 */
		// TODO Auto-generated method stub
		
	}
	/*
	 * move down stairs
	 */
	private static void tacticalMissionMoveUnitUpDownStairs(char input) {
		int sendVal = -1;
		if (input == tacticalMissionUnitDownKey)
			sendVal = 1;
		else sendVal = 0;
		currentEnvironment.getCurrentActivePCAgent().getThisMovement().upDownStairs(sendVal/*1 = down, 0 = up*/);
		currentEnvironment.displayTacticalEnvironment(0);
	}
	/*
	 * move look cursor commands, a space means it's starting
	 */
	private static void tacticalMissionMoveLookCursor(int i) {
		currentEnvironment.getCurrentActivePCAgent().getThisMovement().lookDirectionChange(i, false);
		currentEnvironment.displayTacticalEnvironment(0);
		//tacticalMissionLookToggleOn=!tacticalMissionLookToggleOn;
		/*
		 * case 'l':
			environment.lookToggle();
			environment.displayTacticalEnvironment(0);
			break;
		 */
		/*
		 * do stuff here
		 */
		
	}
	/*
	 * move target cursor, a space means it's just starting
	 */
	private static void moveTargetCursor(int i) {
		// TODO Auto-generated method stub
		
	}
	/*
	 * should call methods to move a unit 'i' direction
	 */
	private static void tacticalMissionMoveUnit(int i) {
		// TODO Auto-generated method stub
		currentEnvironment.addToUnitMessages("move: "+i);
		if(currentEnvironment.getCurrentActivePCAgent() != null){
			currentEnvironment.getCurrentActivePCAgent().getThisMovement().moveUnitDirectionNumpad(i);
			currentEnvironment.displayTacticalEnvironment(0);
		}
		
	}
	/*
	 * sets teh tactical environment
	 */
	public static void setEnvironment(EnvironmentTactical tacticalEnvironment) {
		if(tacticalEnvironment!=null)
		currentEnvironment=tacticalEnvironment;
		
	}
}
