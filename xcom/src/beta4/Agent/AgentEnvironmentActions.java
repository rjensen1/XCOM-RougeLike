package beta4.Agent;

import beta4.HasAgency;
import beta4.Tools;
import beta4.Environment.EnvironmentTile;

public class AgentEnvironmentActions {
	/*
	 * takes care of jumping, opening doors, etc
	 */
	
	private HasAgency actor;
	
	
	
	
	public AgentEnvironmentActions(HasAgency actor){
		this.actor = actor;
	}
	
	public void openDoor(boolean trueOpenFalseClose, int direction) {
		//check for available TUs
	if(actor.theseStats.TUHas(4)){
		boolean error = false;
			//can't open/close a door that you're standing in
		if(direction == 5)
			error = true;
		
		else
		{
			int[] xyzTemp = actor.locationXYZTripletGet();
			int[] locationAdjustment = Tools.getLocationAdjustment(direction);
			EnvironmentTile temp  = actor.currentEnvironment.getTileAtThisLocation(xyzTemp[0] + locationAdjustment[0], xyzTemp[1] + locationAdjustment[1], xyzTemp[2]);
			if(temp == null)
				error = true;
			else{
				if(trueOpenFalseClose){
					//open
					
					
						//attemp to open
					
					error = !temp.open(true);
					
					
				}
				else{
					//close
					
					error = !temp.close(true);
					
				}
			}
		}
		
		//error messages nothing to open there
		if(error){
			String errorMes = "There is nothing to ";
			if(trueOpenFalseClose)
				errorMes +="open here.";
			else
				errorMes += "close here.";
			
			actor.theseMessages.errorMessageAdd(errorMes);
		}
		else{	//subtract TUs if there was no error and refresh tact display
			actor.theseStats.TUUse(4);
			actor.refreshVisual();
		}
	}
	else
		actor.theseMessages.errorMessageAdd("Not enough time units.");
	
	}

	public void newTurn() {
		// TODO Auto-generated method stub
		
	}

}
