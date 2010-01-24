package beta4;

import java.awt.Color;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;



public class PackageShip extends PackageAddToEnvironment{
//needs to carry units... should assign them a location when units are added to the ship.
	
	private EnvironmentTile[][][] shipLayout;
	private LinkedList<HasAgency> shipsAgents;
		private int[] equipLocation;
	private LinkedList<EquipmentEtc> shipsEquipment;
	private int weightKG;
	private int spaceLeftForEquipment;
	private int spaceLeftForAgents;
		//would not change
	private int totalAgentSpace;
	private int shipColumns;
	private int shipRows;
	private int shipLevels;
	private int shipType;
	
	
	
	public PackageShip(int ship, EnvironmentTactical thisOne){
		//super('?', Color.GREEN, Color.WHITE);
			currentEnvironment = thisOne;
		
		String shipString = "";
		
		//TODO set default sort method if sort object is null
		switch(ship){
			case 0:
				//interceptor
				break;
			case 1:
				//skyranger
				shipColumns = 6;
				shipRows = 11;
				shipLevels = 1;
					//arbitrary
				spaceLeftForEquipment = 50;
					//actual
				spaceLeftForAgents = totalAgentSpace = 14;
				
				shipString = shipStringGet(1);
				shipType = 1;
				
				break;
			default:
				break;
		}
		/*super.setRepChar('<');
		super.setRepBackColor(Color.WHITE);
		super.setRepFrontColor(Color.orange);*/
		
		/*
		 * 50 = + ship door
		 * 51 = ! ship unit/equip start
		 * 52 = % ship bulkhead
		 */
			shipLayout = parseShip(shipString);
			//define x, y and z dimensions
			
			//cargo space 
			
	}
	
	
	
	public boolean thisShipsEnvironmentChange(EnvironmentTactical newOne){
		currentEnvironment = newOne;
		return true;
		
	}
	/*
	 * add this linked list of equipment, returns anything that won't fit
	 */
	public LinkedList<EquipmentEtc> equipmentToShipAdd(LinkedList<EquipmentEtc> toAdd)
	{
		
		LinkedList<EquipmentEtc> retVal = toAdd;
		
		if(toAdd != null){
			//if nothing is here, make a new LL
			if (shipsEquipment==null)
			{
					
				shipsEquipment = new LinkedList<EquipmentEtc>();
			}
				//iterate through list, adding them to shipsEquipment, subtract item space from space for equip, when no more space, return the val (removing them as we go)
			Iterator<EquipmentEtc> iterator = retVal.iterator();
			boolean notDone = true;
			
			while(iterator.hasNext() && (spaceLeftForEquipment != 0) && notDone)
			{
				EquipmentEtc tempItem = iterator.next();
					//if this item will fit, add it to the ship, remove it from the list, subtract the used space
				if(tempItem.getSize() <= spaceLeftForEquipment)
				{
					shipsEquipment.add(tempItem);
					iterator.remove();
					spaceLeftForEquipment -= tempItem.getSize();
				}
				else
					notDone = false;
			}
		}

		return retVal;
		
	}
	/*
	 * add this linked list of agents to the ship, return any that won't fit, also, assigns a relative location to each agent
	 * based on the top left.. for now, all agents are size = 1
	 */
	
	public LinkedList<HasAgency>theseAgentsToShipAdd(LinkedList<HasAgency> toAdd){
		
		LinkedList<HasAgency> retVal = toAdd;
		
		if(toAdd != null){
			//if nothing is here, make a new LL
			if (shipsAgents == null)
			{
					
				shipsAgents = new LinkedList<HasAgency>();
			}
				//iterate through list, adding them to shipsEquipment, subtract item space from space for equip, when no more space, return the val (removing them as we go)
			Iterator<HasAgency> iterator = retVal.iterator();
		
			
			while(iterator.hasNext() && (spaceLeftForAgents != 0))
			{
				HasAgency tempAgent = iterator.next();
					//if this item will fit, add it to the ship, remove it from the list, subtract the used space
				
						//assign this agent a relative position
					agentPositionAssign(tempAgent);
					
					shipsAgents.add(tempAgent);
					iterator.remove();
					spaceLeftForAgents --;
			}
		}

		return retVal;
	}
	/*
	 * figures out which place this agent goes in, then assigns it to that location
	 */
	private void agentPositionAssign(HasAgency tempAgent) {
		
		int agentNumber = totalAgentSpace - spaceLeftForAgents;
		String shipSt = shipStringGet(shipType);
		
		int exclemCounter = 0; //keeps track of how many exclemations have been found
		for(int z = 0, pointer = 0; z < shipLevels; z++ ){
			
			for(int y = 0; y < shipRows; y ++){
					
				for(int x = 0; (x < shipColumns) && (pointer < shipSt.length()); x++, pointer++){
					//if((shipSt.charAt(pointer) =='!'))
							//if is 1-9, not 5, put a person here and make them face this direction.
					if((int)shipSt.charAt(pointer) > 48 &&  (int)shipSt.charAt(pointer) < 58 && ((int)shipSt.charAt(pointer) != 53))
					{
						if(exclemCounter == agentNumber){
							tempAgent.locationXYZTripletSet(x, y, z);
							tempAgent.getThisMovement().lookDirectionChange((int)shipSt.charAt(pointer)-48, true);
							return;
						}
						else
							exclemCounter++;
					}
								
				}
			}
		}
		
		
		
	}

	/*
	 * removes the agent from this index, rotates all agents after this one slot forward, 
	 * so that new ones added will be added at the end and empty slots won't need to be found
	 * returns the agent
	 */
	public LinkedList<HasAgency> removeThisAgent(int indexOfAgent){
		// TODO Auto-generated method stub
		return null;
	}
	/*
	 * removes and returns the piece of equipment at this index. (this might get ugly..)
	 */
	public LinkedList<EquipmentEtc> removeThisEquip(int indexOfEquip){
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
		
	/*
	 * returns the shipstring
	 */
	private String shipStringGet(int i) {
		String retVal = "";
		switch(i){
		case 1:
			//skyranger
			
			retVal = /*("..<<.." +
					  ".%..%." +
					  "......" +
					  "......" +
					  "......" +
					  "......" +
					  "......" +
					  "......" +
					  "......" +
					  "..%%.." +
					  "......" +
					//second level
						"%.>>.%" +
						"%%++%%" +
						"%%82%%" +
						"%%88%%" +
						".%88%." +
						".%88%." +
						".%88%." +
						".%88%." +
						".%88%." +
						".%%%%." +
						"..%%.."+
					//third level
						"_...._" +
						"______" +
						"______" +
						"______" +
						".____." +
						".____." +
						".____." +
						".____." +
						".____." +
						".____." +
						"..__..");
						*/
			//second level
			("%....%" +
			"%%++%%" +
			"%%82%%" +
			"%%88%%" +
			".%88%." +
			".%88%." +
			".%88%." +
			".%88%." +
			".%88%." +
			".%%%%." +
			"..%%..");
			break;
		}
		
		return retVal;
	}


	/*
	 * should input a string, parse it according to its dimensions, and create a EnvironmentTile array of 3 dimensions,
	 * returning this
	 */
	private EnvironmentTile[][][] parseShip(String shipString) {
						//x,y,z
		int[] equipXYZ = new int[3];
		boolean equipSet = false;
		EnvironmentTile temp[][][] = new EnvironmentTile[shipColumns][shipRows][shipLevels];
		for(int z = 0, pointer = 0; z < shipLevels; z++ ){
			for(int y = 0; y < shipRows; y ++){
					//pointer keeps track of where we are in the String
				for(int x = 0; x < shipColumns; x ++, pointer++){
					
					int envTile = envTileIntFromShipCharGet(shipString.charAt(pointer));
						//if the equip hasn't been assigned and this character is an !, put it here!
					if(!equipSet && (envTile == 52 )){
						equipXYZ[0] = x;
						equipXYZ[1] = y;
						equipXYZ[2] = z;
						equipSet = true;
					}
					
							//empty space, stays null
					if(envTile != -1)	
						temp[x][y][z] = new EnvironmentTile(envTile, currentEnvironment);
				}
			}
		}
		equipLocation = equipXYZ;
		return temp;
	}
	/*
	 * returns the already created ship layout envtile array
	 */
	public EnvironmentTile[][][] shipLayoutGet(){
		return shipLayout;
	}
	/*
	 * returns the ship's equipment, which will be placed based off of a different method, delete the stuff from inside of the ship
	 */
	public LinkedList<EquipmentEtc> allShipEquipmentGet(){
		LinkedList<EquipmentEtc> tempShipsEquip = shipsEquipment;
		
		shipsEquipment = null;
		
		return tempShipsEquip;
		
	}
	/*
	 * remove all of the agents, (transferring them to a battlescape, for example, 
	 */
	public LinkedList<HasAgency> allShipAgentGet(){
		LinkedList<HasAgency> tempShipsAgents = shipsAgents;
		
		shipsAgents = null;
		return tempShipsAgents;
		
	}
	/*
	 * reutrns an xyz triplet that is relative to the top left corner, which is where all of the unused ship's equipment will appear
	 * mostly because location is not stored in an equipment object
	 */
	public int[] equipLocationXYZTripletRelativeToTopLeftCornerOfShipEquipGet(){
		
		return equipLocation;
	}
	

	/*
	 * returns an int corresponding to the env tile
	 */
	private int envTileIntFromShipCharGet(char charAt) {
		int retVal = -1;
		switch(charAt){
		case '%':
				//bulkheap
			retVal = 53;
			break;
		case '<':
				//up stairs
			retVal = 54;
			break;
		case '>':
				//down stairs
			retVal = 55;
			break;
			/*
		case '!':
			//starting spot for equip/anAgent
		retVal = 52;
		break;*/
		case '_':
			retVal = 56;
			break;
		case '.':
				//empty space
			retVal = -1;
			break;
		case '+':
				//door
			retVal = 50;
			break;
		default:
			retVal = -2;
		}
		
		if(retVal == -2){
			if((int)charAt >48 &&  (int)charAt < 58 && ((int)charAt != 53)){
				retVal = 52;
			}
			
		}
		return retVal;
	}
	/*
	 * reutnrs a triplet of the ship's dimensions
	 */
	public int[] shipDimenSionsColRowLevelGet(){
		
		return new int[]{shipColumns, shipRows, shipLevels};
	}
	
	
	
	
	
	
}
