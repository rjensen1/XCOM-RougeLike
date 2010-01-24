package beta4;

import java.awt.Color;
import java.util.Comparator;
import java.util.LinkedList;


/*
 * perhaps use the flyweight mehtod, in that until something changes on a tile, it is just a copy of the default? (changing would duplicate and then change the new one, so that it isn't changing it for other things that are using the same bit
 */


public class EnvironmentTile extends EnvironmentObject{
	/*
	 *
	 *
	public static widthOfTileCM = ;
	public static heightOfTileCM = ;*/
	
	
	//private int thisElementCreationNumber;
	private char tempRepChar;
	private boolean isStairs;
	
	public EnvironmentTile(){
		super();
		tempRepChar = '?';
		isStairs = false;
		
	}
	public EnvironmentTile(int type, EnvironmentTactical thisOne){
		super();
		tempRepChar = '?';
		isStairs = false;
		super.tacticalEnvironmentSet(thisOne);
		EnvironmentTileBirthing.changeType(this, type);
		
	}
	//called either when making a new tile, or when a current tile changes into something else from fire/explosion, etc.
	
	
	
	
	
	//would show that someone had walked on this, pressure activated?
	//private int traces
	/*
	 * returns true if a door was succesfully closed, false if it is already closed or it's not a door
	 */
	public boolean close(boolean checkForNeighboors) {
		if(thisIsADoor() && doorIsOpen()){
			
			int[] xyzTemp = super.locationXYZTripletGet();
			EnvironmentTile[] neighboors = null;
			if(checkForNeighboors){
				neighboors = new EnvironmentTile[] {currentEnvironment.environmentTileAtThisLocationGet(xyzTemp[0] + 1, xyzTemp[1], xyzTemp[2]), currentEnvironment.environmentTileAtThisLocationGet(xyzTemp[0] - 1, xyzTemp[1], xyzTemp[2]), currentEnvironment.environmentTileAtThisLocationGet(xyzTemp[0], xyzTemp[1]+1, xyzTemp[2]). currentEnvironment.environmentTileAtThisLocationGet(xyzTemp[0], xyzTemp[1]-1, xyzTemp[2])};
				for(int i = 0; i < neighboors.length; i ++){
						//try to close adjacent doors (double-wides, etc)
					if(neighboors[i] != null && neighboors[i].thisIsADoor()){
						neighboors[i].close(false);
						
					}
				}
			}
				super.representingCharSet(tempRepChar);
				super.blocksPassableHorizontalSet(true);
				return true;
			
		}
		return false;
	}
	/*
	 * should return true if the door is open
	 */
	private boolean doorIsOpen() {
		return !super.blocksPassableHorizontalGet();
	}
	/*
	 * should return true if this tile is a door
	 */
	private boolean thisIsADoor() {
			//these match the entries above
		int[] doorEntries = new int[]{27,50};
		for(int i = 0; i < doorEntries.length; i++){
			
			if(doorEntries[i] == super.objectTypeGet())
				return true;
		}
		return false;
	}
	/*
	 * returns true if a door was succesfully opened, false if it is already open or it's not a door
	 */
	public boolean open(boolean checkForNeighboors) {
		if(thisIsADoor() && doorIsClosed()){
			
			int[] xyzTemp = super.locationXYZTripletGet();
			EnvironmentTile[] neighboors = null;
			if(checkForNeighboors){
				neighboors = new EnvironmentTile[] {currentEnvironment.environmentTileAtThisLocationGet(xyzTemp[0] + 1, xyzTemp[1], xyzTemp[2]), currentEnvironment.environmentTileAtThisLocationGet(xyzTemp[0] - 1, xyzTemp[1], xyzTemp[2]), currentEnvironment.environmentTileAtThisLocationGet(xyzTemp[0], xyzTemp[1]+1, xyzTemp[2]). currentEnvironment.environmentTileAtThisLocationGet(xyzTemp[0], xyzTemp[1]-1, xyzTemp[2])};
				for(int i = 0; i < neighboors.length; i ++){
					//try to open adjacent doors, double-wides
					if(neighboors != null && neighboors[i] != null && neighboors[i].thisIsADoor()){
						neighboors[i].open(false);
						
					}
				}
			}
			tempRepChar = super.representingCharGet(CharDisplayTypes.STANDARD, new int[]{});
			super.representingCharSet('\'');
			super.blocksPassableHorizontalSet(false);
			return true;
		}
		return false;
	}
	/*
	 * should return true if the door is closed
	 */
	private boolean doorIsClosed() {
		
		return super.blocksPassableHorizontalGet();
	}
	
	public boolean canUnitMoveVertically(boolean usingStairs){
		if(usingStairs)
			return isStairs;
		return false;
		
	}
	
	/*
	 * breaking this tile from weight, should probably have a 'break tile to' not explodes
	 */
	public boolean breakThisObject(){
		/*
		if(!thisNameGet().equals("air")){
			super.breakThisObject();
			changeType(super.explodesToGet());
			super.supportsWeightSet(0);
			super.blocksPassableVerticalSet(true);
			return true;
		}
		//TODO perhaps could create 'item' debris below*/
		return false;
		
	}
	@Override
	public void checkToSeeIfGroundWillSupport() {
		// TODO Auto-generated method stub
		
	}
	public void isStairsSet(boolean isStairs2) {
		isStairs = isStairs2;
		
	}
	
}

