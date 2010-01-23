package beta4;

import java.awt.Color;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

import beta4.Environment.EnvironmentTile;
import beta4.Environment.EquipmentEtc;



public class PackageShip extends PackageAddToEnvironment{
//needs to carry units... should assign them a location when units are added to the ship.
	
	
	private LinkedList<HasAgency> shipsAgents;
	//{
		//position where all extra equipment is stacked. Should all of this be done in such a way so that the equip is just placed on the floor... could be anywhere? or perhaps if ou're in the shup, you have access to it
	private int[] equipLocation;
	private LinkedList<EquipmentEtc> shipsEquipment;
	//}
	//private int weightKG;
	private int spaceLeftForEquipment;
	private int spaceLeftForAgents;
		//would not change
	private int totalAgentSpace;
	private int totalEquipSpace;
	private int shipColumns;
	private int shipRows;
	private int shipLevels;
	private int shipType;
	private EnvironmentTile[] validTileTypesForInitialAgentPlacement;
	
	
	
	public PackageShip(ShipCreationObject ship){
		super(ship.getXDim(), ship.getYDim(), ship.getZDim(), ship.getExternalZoomedOutEnvironmentToBeRepresented());
			
		
		
			
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
	
	public LinkedList<HasAgency> theseAgentsToShipAdd(LinkedList<HasAgency> toAdd){
		
		toAdd = sortLargestToSmallestByRepCharSize(toAdd);
		LinkedList<HasAgency> wontFitAnywhere = new LinkedList<HasAgency>();
		//GREEDY ALGORITHM
		if(toAdd != null){
			//if nothing is here, make a new LL
			if (shipsAgents == null)
			{
					 
				shipsAgents = new LinkedList<HasAgency>();
			}
				//iterate through list, adding them to shipsEquipment, subtract item space from space for equip, when no more space, return the val (removing them as we go)
			Iterator<HasAgency> iterator = toAdd.iterator();
		
			
			while(iterator.hasNext() && (spaceLeftForAgents != 0))
			{
				HasAgency tempAgent = iterator.next();
					//if this item will fit, add it to the ship, remove it from the list, subtract the used space
			
					//assign this agent a relative position
				HasAgency returned = assignAgentPositionsGreedy(tempAgent, validTileTypesForInitialAgentPlacement);
				wontFitAnywhere.add(returned);
				
				shipsAgents.add(tempAgent);
				iterator.remove();
				spaceLeftForAgents -= tempAgent.getSize();
			}
		}

		return wontFitAnywhere;
	}
	private LinkedList<HasAgency> sortLargestToSmallestByRepCharSize(LinkedList<HasAgency> toAdd) {
		
		LinkedList<HasAgency> sortedList = new LinkedList<HasAgency>();
		
		if(toAdd !=null){
			
			while(!toAdd.isEmpty()){
				Iterator<HasAgency> currentIterator = toAdd.iterator();
				int largestSize = 0;
				HasAgency largestAgent = null;
				
				while(currentIterator.hasNext()){
					
					largestAgent =  currentIterator.next();
					currentIterator.remove();
					
					largestSize = getDimensionsOfObject(largestAgent.getTheseRepresentingChars());
					
					while(currentIterator.hasNext()){
						HasAgency tempAgent =  currentIterator.next();
						int thisSize = getDimensionsOfObject(largestAgent.getTheseRepresentingChars());
						if(thisSize > largestSize){
							toAdd.add(largestAgent);
							largestSize = thisSize;
							largestAgent = tempAgent;
							
						}
						
					}
					sortedList.add(largestAgent);
					currentIterator = toAdd.iterator();
					
				}
				
			}
			
			
		}
		return toAdd;
	}
	private int getDimensionsOfObject(Object[][] twoDObject){
		int retVal = 0;
		if(twoDObject != null){
			//max*2 + min
			retVal = Math.max(twoDObject.length, twoDObject[0].length)*2 + Math.min(twoDObject.length, twoDObject[0].length);
		}
		return retVal;
		
	}
	private HasAgency assignAgentPositionsGreedy(HasAgency agentToPlace, EnvironmentTile[] validPlacesAgentCanBePlaced){

		return agentToPlace;
	}
	/*
	/*
	 * figures out which place this agent goes in, then assigns it to that location... 
	 * should return the object if no spot can be found
	 * 
	 *
	private HasAgency agentPositionAssignOld(HasAgency tempAgent) {
		HasAgency wontFit = null;
		
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
							tempAgent.placeObjectHere(new int[]{x, y, z}, true);
							tempAgent.thisMovement.lookDirectionChange((int)shipSt.charAt(pointer)-48, true);
							return wontFit;
						}
						else
							exclemCounter++;
					}
								
				}
			}
		}
		return wontFit;
	}*/

	
	
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
	 * reutnrs a triplet of the ship's dimensions
	 */
	public int[] shipDimenSionsColRowLevelGet(){
		
		return new int[]{shipColumns, shipRows, shipLevels};
	}
	
	
	
	
	
	
}
