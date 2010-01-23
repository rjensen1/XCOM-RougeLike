package beta4.Environment;

import java.util.Iterator;
import java.util.LinkedList;

import beta4.HasAgency;

public class EnvironmentTacticalUnits {

	LinkedList<HasAgency> pcAgentList;
	private LinkedList<HasAgency> pcAgentActive;
	private Iterator<HasAgency> currentUnitIterator;
	HasAgency currentUnit;
	private EnvironmentTactical owner;
	
	public EnvironmentTacticalUnits(EnvironmentTactical newOwner){
		owner = newOwner;
	}
	
	

	public LinkedList<HasAgency> getNeutralNPC() {
		// TODO Auto-generated method stub
		return null;
	}

	public LinkedList<HasAgency> getFoePC() {
		// TODO Auto-generated method stub
		return null;
	}

	public LinkedList<HasAgency> getFriendlyPC() {
		// TODO Auto-generated method stub
		return pcAgentList;
	}
	/*
	 * should tell if an agent at said spot is blocking the light
	 */
	public boolean doAgentsBlockLight(int[] xyzLoc, EnvironmentObject[] toExcludeFromBlocksLightCheck) {
		
		Iterator <HasAgency> tempIt = pcAgentList.iterator();
		boolean retVal = false;
		while(tempIt.hasNext() && !retVal){
			HasAgency tempAgent = tempIt.next();
			if(!EnvironmentObject.thisObjectIsIncludedInThisArray(tempAgent, toExcludeFromBlocksLightCheck))
				retVal = tempAgent.getBlocksLight(xyzLoc);
		}
		
		return retVal;
	}
	public HasAgency getCurrentActivePCAgent(){
		return currentUnit;
	}
	
	 
	/*
	 * this should be connected to the current unit.
	 */
	public void addToUnitMessages(String string) {
		
		currentUnit.theseMessages.messageAdd(string);
		
	}
public void selectNextPCUnitDeselectCurrent() {
		
		if(pcAgentActive.size()==0)
			return;
				//case 2: unit is the last of the units in the list
		else if (pcAgentActive.size()==1){
			pcAgentActive.clear();
			currentUnit = null;
			currentUnitIterator = null;
			owner.refreshHUD();
		}		//case 1: unit is at end of list
		else if(!currentUnitIterator.hasNext()){
			currentUnitIterator.remove();
			currentUnitIterator = pcAgentActive.iterator();
			currentUnit = currentUnitIterator.next();
			owner.centerScreen(currentUnit.locationXYZTripletGet());
		}
		else{
			currentUnitIterator.remove();
			currentUnit = currentUnitIterator.next();
			owner.centerScreen(currentUnit.locationXYZTripletGet());
		}
		owner.refreshHUD();
		
	}

/*
 * cycles to the previous unit in the active unit list
 */
public void selectPreviousUnitPC() {
	if(currentUnitIterator == null)
		return;
	
	if(pcAgentActive.size()==1)
		return;
	
	else if(currentUnit == pcAgentActive.element()){ //is this the first?
		currentUnitIterator = pcAgentActive.iterator();
			//fast forward to end of the list
		while(currentUnitIterator.hasNext())
			currentUnit = currentUnitIterator.next();
	}
	else{
		HasAgency tempUnit = currentUnit;
		currentUnitIterator = pcAgentActive.iterator();
		HasAgency lastUnit;
		HasAgency nextUnit = lastUnit = currentUnitIterator.next();
		
			//starting the iterator over
		
		
		
		while(nextUnit != tempUnit){
			lastUnit = nextUnit;
			 nextUnit = currentUnitIterator.next();
			
		}
		currentUnit = lastUnit;
	}
	owner.centerScreen(currentUnit.locationXYZTripletGet());
	owner.refreshHUD();
}

/*
 * cycles forward through the unit list, assuming that the active list isn't empty
 */
public void selectNextUnitPC() {
	if(currentUnitIterator == null)
		return;
	
	
	if(currentUnitIterator.hasNext()){
		currentUnit = currentUnitIterator.next();
		owner.centerScreen(currentUnit.locationXYZTripletGet());
		owner.refreshHUD();
	}
	else if(!pcAgentActive.isEmpty()){
		currentUnitIterator = pcAgentActive.iterator();
		currentUnit = currentUnitIterator.next();
		owner.centerScreen(currentUnit.locationXYZTripletGet());
		owner.refreshHUD();
	}
}
/*
 * makes a new iterator that has all of the active agents in it, also, selects the first agent and refreshes its HUD
 */
@SuppressWarnings("unchecked") void resetPCAgentIterators(){
	//copy pcagentlist into the active linked list
	pcAgentActive = (LinkedList<HasAgency>) pcAgentList.clone();
	//reset the current unit iterator
	currentUnitIterator = pcAgentActive.iterator();
	currentUnit = currentUnitIterator.next();
	owner.centerScreen(currentUnit.locationXYZTripletGet());
	owner.refreshHUD();
}
void newTurn(){
	
	Iterator<HasAgency> tempPcUnitIterator = pcAgentList.iterator();
	while(tempPcUnitIterator.hasNext()){
		tempPcUnitIterator.next().newTurn();
	}
	
	resetPCAgentIterators();
	
}
}
