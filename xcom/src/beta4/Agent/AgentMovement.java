package beta4.Agent;

import beta4.HasAgency;
import beta4.Tools;
import beta4.Environment.EnvironmentDisplayable;
import beta4.Environment.EnvironmentTactical;


public class AgentMovement {
	
	public static enum moveType{WALK, WALKSTAIRS};
	
	private HasAgency actor;
	
	public AgentMovement(HasAgency actor){
		this.actor = actor;
	}
	
	public boolean lookDirectionChange(int numPadDirection, boolean isFreeLook){
		int lookDirectionTemp = actor.lookDirectionGet();
		if(numPadDirection > 0 && numPadDirection < 10 && numPadDirection != 5  && numPadDirection != lookDirectionTemp){
			if(actor.theseStats.TUHas(1 /*+ calcEncumberance()/3*/) || isFreeLook  ){
				
				actor.lookDirectionSet(numPadDirection);
				if(!isFreeLook){
					actor.theseStats.TUUse(1/*+calcEncumberance()/3*/);
				}
				return true;
			}
			else{
				//System.out.println("no TUs not freelok");
				actor.theseMessages.errorMessageAdd(" Not enough time units to change look direction.");

				return false;
			}
		}
		else if(numPadDirection == lookDirectionTemp)
			return true;
		else
			return false;
	}
	/*
	 * does everything dealing with moving this unit on the x,y plane
	 */
	public void moveUnitDirectionNumpad(int directionNumPad) 
	{
		
		int[] xyzDirection = Tools.getLocationAdjustment(directionNumPad);
		
		int[][] xyzTemp = actor.locationXYZTripletGetForAllDimensions();
		boolean isValidPosition = true;
		for(int i = 0 ; i < xyzTemp.length; i ++){
			isValidPosition &= actor.placeObjectHere(new int[]{xyzTemp[i][0] + xyzDirection[0], xyzTemp[i][1] + xyzDirection[1], xyzTemp[i][2] + xyzDirection[2]}, false);
		}
		boolean locationIsPassable = true;
		for(int i =0; i < xyzTemp.length; i++){
			locationIsPassable &= ((EnvironmentTactical)actor.currentEnvironment).isThisLocationPassableHorizontal(xyzTemp[i][0] + xyzDirection[0], xyzTemp[i][1] + xyzDirection[1], xyzTemp[i][2] + xyzDirection[2]);
		}
		if(isValidPosition && TUForMoveIsThisEnough(xyzDirection) && ENForMoveIsThisEnough(xyzDirection) && locationIsPassable)
		{
			TUFromMoveSubtract(xyzDirection);
			ENFromMoveSubtract(xyzDirection);
			actor.updatesLights(true);
			actor.placeObjectHere(new int[] {xyzDirection[0] + xyzTemp[0][0], xyzDirection[1] + xyzTemp[0][1], xyzDirection[2] + xyzTemp[0][2]}, true);
			actor.updatesLights(false);
			lookDirectionChange(directionNumPad, true);
			((EnvironmentDisplayable)actor.currentEnvironment).centerScreen(actor.locationXYZTripletGet());
			//checkToSeeIfGroundWillSupport();
		}
		else if(isValidPosition){
			
			int[] xyzTemp2 = actor.locationXYZTripletGet();
			
			if(!TUForMoveIsThisEnough(xyzDirection))
				actor.theseMessages.errorMessageAdd("Not enough Time Units.");
			else if(!ENForMoveIsThisEnough(xyzDirection))
				actor.theseMessages.errorMessageAdd("Not enough Energy.");
			else if(!((EnvironmentTactical)actor.currentEnvironment).isThisLocationPassableHorizontal(xyzTemp2[0] + xyzDirection[0], xyzTemp2[1] + xyzDirection[1], xyzTemp2[2] + xyzDirection[2])){
				actor.theseMessages.errorMessageAdd("Something is in the way.");
				
			}
			//try to do a look
			if(actor.lookDirectionGet() != directionNumPad)
				lookDirectionChange(directionNumPad, false);
		}
	}
	/*
	 * subtracts the necessary energy to move in this direction
	 */
	private void ENFromMoveSubtract(int[] xyDirection) {
		int[] xyzTemp = actor.locationXYZTripletGet();
		
		actor.theseStats.ENUse(ENMoveCostGet(xyDirection[0] + xyzTemp[0], xyDirection[1] + xyzTemp[1], xyDirection[2] + xyzTemp[2], moveType.WALK));
	}
	/*
	 * calculates how much energy it would cost to move in a particular direction
	 */
	private int ENMoveCostGet(int xToCheck, int yToCheck, int zToCheck, moveType how) 
	{
		int retVal = actor.theseCosts.getENStanceMoveCost();
		
		
		
		switch(how){
			case WALK:
				//modifying for varying terrain
				retVal=((int)(retVal * actor.currentEnvironment.environmentTileAtThisLocationGet(xToCheck, yToCheck, zToCheck).percentageMovementGet()));
				break;
			case WALKSTAIRS:
				retVal=((int)(2* retVal * actor.currentEnvironment.environmentTileAtThisLocationGet(xToCheck, yToCheck, zToCheck).percentageMovementGet()));
				break;
		}
		//if stealth, energy is 150% , should this be compounded on top of midified terrain?
		if(actor.theseStats.isStealthOn())
			retVal *= 1.5;
		return retVal/*+calcEncumberance()*/;
	}
	private boolean ENForMoveIsThisEnough(int[] xyDirection) 
	{
		int[] xyzTemp = actor.locationXYZTripletGet();
		return actor.theseStats.ENHas(ENMoveCostGet(xyDirection[0] + xyzTemp[0], xyDirection[1] + xyzTemp[1], xyDirection[2] + xyzTemp[2], moveType.WALK));
	}
	private void TUFromMoveSubtract(int[] xyDirection) 
	{
		int[] xyzTemp = actor.locationXYZTripletGet();
		actor.theseStats.TUUse(TUMoveCostGet(xyDirection[0]+ xyzTemp[0], xyDirection[1] + xyzTemp[1], xyDirection[2] + xyzTemp[2], moveType.WALK ));
	}
	private boolean TUForMoveIsThisEnough(int[] xyzDirection) {
		int[] xyzTemp = actor.locationXYZTripletGet();
		return actor.theseStats.TUHas(TUMoveCostGet(xyzDirection[0] + xyzTemp[0], xyzDirection[1] + xyzTemp[1], xyzDirection[2] + xyzTemp[2], moveType.WALK));
	}
	/*
	 * returns how manyTUs it will cost at a arbitrary spot... must already be adjusted for offest
	 */
	private int TUMoveCostGet(int xToCheck, int yToCheck, int zToCheck, moveType how){
		int retVal = actor.theseCosts.getTUStanceMoveCost();
		
				//default move cost is multipled by a decimal representing the move cost of the particular tile
		switch(how){
		case WALK:
			System.out.println(xToCheck+" "+yToCheck+" "+zToCheck);
			retVal=((int)(retVal * actor.currentEnvironment.environmentTileAtThisLocationGet(xToCheck, yToCheck, zToCheck).percentageMovementGet()));
			break;
		case WALKSTAIRS:
			retVal=((int)(2 * retVal * actor.currentEnvironment.environmentTileAtThisLocationGet(xToCheck, yToCheck, zToCheck).percentageMovementGet()));
			break;
		}
		//if stealth, TUs are 50% more
		if(actor.theseStats.isStealthOn())
			retVal*=1.5;

		//retVal+=calcEncumberance();
		return retVal;
		
	}
	public void HUDRefresh() {
		// TODO Auto-generated method stub
		
	}
	public String toString(){
		//TODO
		return "";
	}
	public void newTurn() {
		// TODO Auto-generated method stub
		
	}
	/*
	 * inputs 1 or 0, 1 is down, 0 is up
	 * just handles the z coordinate, calls moveZdirection of going up or down
	 */
	public void upDownStairs(int down1up0) {
		int z =0;
		switch(down1up0){
		case 0:
			z = 1;
			break;
		case 1:
			z = -1;
			break;
		}
		if(z != 0)
			moveZdirection(z, moveType.WALKSTAIRS);
		
	}
	/*
	 * should handle moving up or down
	 */
	private void moveZdirection(int z, moveType how) {
		
		int[] xyzTemp = actor.locationXYZTripletGet();
		boolean canMove = false;
		int moveEnCost = 0;
		int moveTUCost = 0;
		switch(how){
			case WALKSTAIRS:
				//check for stairs.
				
				if(z ==1){
					//check for up stairs
					if(((EnvironmentTactical)actor.currentEnvironment).upStairsAt(xyzTemp[0], xyzTemp[1], xyzTemp[2])){
						if(((EnvironmentTactical)actor.currentEnvironment).downStairsAt(xyzTemp[0], xyzTemp[1], xyzTemp[2] + 1)){
								//can't move up/down if the space we're moving into cannot be occupied.
							if(false/*actor.currentEnvironment.getIsPassableHorizontal(xyzTemp[0], xyzTemp[1], xyzTemp[2] + 1) /*&& currentEnvironment.getIsPassableVertical(locationX, locationY, locationZ+1)*/){
								
								canMove = true;
								//get TU/EN costs
								moveEnCost = ENMoveCostGet(xyzTemp[0], xyzTemp[1], xyzTemp[2] + 1, how);
								moveTUCost = TUMoveCostGet(xyzTemp[0], xyzTemp[1], xyzTemp[2] + 1, how);
								
							}
							else{
								actor.theseMessages.errorMessageAdd("Something is in the way.");
							}
						}
						else{
							actor.theseMessages.errorMessageAdd("These stairs lead to nowhere");
						}
					}
					else{
						actor.theseMessages.errorMessageAdd("there are no up stairs here");
						
					}
				}
				else if(z == -1){
					//check for down stairs
					
					if(((EnvironmentTactical)actor.currentEnvironment).downStairsAt(xyzTemp[0], xyzTemp[1], xyzTemp[2])){
						if(((EnvironmentTactical)actor.currentEnvironment).upStairsAt(xyzTemp[0], xyzTemp[1], xyzTemp[2] - 1)){
							if(false/*actor.currentEnvironment.getIsPassableHorizontal(xyzTemp[0], xyzTemp[1], xyzTemp[2] - 1)/* && currentEnvironment.getIsPassableVertical(locationX, locationY, locationZ - 1)*/){
								
								canMove = true;
								//get TU/EN costs
								moveEnCost = ENMoveCostGet(xyzTemp[0], xyzTemp[1], xyzTemp[2] + 1, how);
								moveTUCost =TUMoveCostGet(xyzTemp[0], xyzTemp[1], xyzTemp[2] + 1, how);
								
							}
							else{
								actor.theseMessages.errorMessageAdd("Something is in the way.");
							}
						}
						else{
							actor.theseMessages.errorMessageAdd("there are no down stairs here");
						}
					}
					else{
						actor.theseMessages.errorMessageAdd("these down stairs lead to nowhere");
						
					}
				}
			break;
		default:
		}
		if(canMove){
			
			boolean enoughTUForMove = actor.theseStats.TUHas(moveTUCost);
			boolean enoughENForMove = actor.theseStats.ENHas(moveEnCost);
			
			if(enoughTUForMove && enoughENForMove)
			{
				actor.theseStats.TUUse(moveTUCost);
				actor.theseStats.ENHas(moveEnCost);
				
				
				actor.placeObjectHere(new int[]{xyzTemp[0], xyzTemp[1], xyzTemp[2] + z}, true);
				((EnvironmentDisplayable)actor.currentEnvironment).centerScreen(actor.locationXYZTripletGet());
				//checkToSeeIfGroundWillSupport();
			}
			else{
				if(!enoughTUForMove)
					actor.theseMessages.errorMessageAdd("Not enough Time Units.");
				if(!enoughENForMove)
					actor.theseMessages.errorMessageAdd("Not enough Energy.");
				
			}
		}
		
	}

	
	

}
