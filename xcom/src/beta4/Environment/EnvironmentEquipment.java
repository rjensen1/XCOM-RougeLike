package beta4.Environment;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;

import beta4.DisplayColorChars;

public class EnvironmentEquipment {
	
	private LinkedList<EquipmentEtc>[][][] equipListArray;
	private EnvironmentTactical thisEnvironment;
	
	
	
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public EnvironmentEquipment(int xDim, int yDim, int zDim, EnvironmentTactical newEnvironment) {
		equipListArray = new LinkedList[xDim][yDim][zDim];
		thisEnvironment = newEnvironment; 
	}
	/*
	 * just adding some random items to the ground
	 */
	public void placeRandomItems(int numItems) {
		/*
		for(int i = 0; i < numItems; i++){
				//item of random type
			EquipmentEtc item = new EquipmentEtc(Tools.getRandInt(0, 10, 0));
			int x = Tools.getRandInt(0, xDim-1, 0);
			int y = Tools.getRandInt(0, yDim-1, 0);
			
			this.dropItemToGround(item, x, y, 0);
		}
		*/
		
	}
	/*
	 * adds the items at a particular location to the viewscreen
	 */
	public DisplayColorChars placeItemsOnViewScreenAtLocation(int x, int y, int xMin, int yMin, boolean subLevel, boolean hasGodVision){
		//place items, makes the largest item visible
		//TODO make sure this handles multi-sized objects
		DisplayColorChars retVal = null;
		
		//if(hasGodVision || (!hasGodVision && (thisEnvironment.getCurrentActivePCAgent() !=null && thisEnvironment.getCurrentActivePCAgent().getThisVision().whatThisAgentCanSeeGet()[x][y][thisEnvironment.getViewPortZFocus()]))){
			if(equipListArray[x][y][thisEnvironment.getViewPortZFocus()] != null){
				retVal = new DisplayColorChars('?', Color.white, null);
				Iterator<EquipmentEtc> tempIt = equipListArray[x][y][thisEnvironment.getViewPortZFocus()].iterator();
				EquipmentEtc tempOuterOB = null;
				
				
				while(tempIt.hasNext()){
					EquipmentEtc tempInnerOB = tempIt.next();
					if((tempOuterOB == null) || (tempInnerOB.getSize() > tempOuterOB.getSize())){
						tempOuterOB = tempInnerOB;
					}
				}
					//if the item demands a background color, set it, otherwise only change the foreground color
				retVal = tempOuterOB.getDisplayColorChars(x, y, thisEnvironment.getViewPortZFocus());
				/*if(tempOuterOB.representingColorBackGet(EnvironmentObject.CharDisplayTypes.STANDARD, null) != null){
					retVal.setBackColor(tempOuterOB.representingColorBackGet(EnvironmentObject.CharDisplayTypes.STANDARD, null));
						//= new ColorChars(tempOuterOB.getRepresenting(EnvironmentObject.CharDisplayTypes.STANDARD, null),  tempOuterOB.getRepresentingColorFront(EnvironmentObject.CharDisplayTypes.STANDARD, null), tempOuterOB.getRepresentingColorFront(EnvironmentObject.CharDisplayTypes.STANDARD, null) )
				}
				{	//always change the char and the front color
					retVal.setTextColor(tempOuterOB.representingColorFrontGet(EnvironmentObject.CharDisplayTypes.STANDARD, null));
					retVal.setRepChar(new char[]{tempOuterOB.representingCharGet(EnvironmentObject.CharDisplayTypes.STANDARD, null)});
				}*/
			}
			return retVal;
		}
	
	/*
	 * adds an item to the ground at this location
	 */
	public void dropItemToGround(EquipmentEtc toAddToGround, int x, int y, int z){
		if(thisEnvironment.withinXYZBounds(x, y, z)){
			if(equipListArray[x][y][z] != null){
				equipListArray[x][y][z].add(toAddToGround);
			}
			else{
				equipListArray[x][y][z] = new LinkedList<EquipmentEtc>();
				equipListArray[x][y][z].add(toAddToGround);
			}
			
		}
		else{
			System.out.println("Out of bounds in drop");
		}
			
	}
	/*
	 * adds an item to the ground at this location
	 */
	public EquipmentEtc getItemFromGroundAtLocationAndIntPostionInList( int x, int y, int z, int slotLocation){
		EquipmentEtc retVal = null;
		if(thisEnvironment.withinXYZBounds(x, y, z)){
			if(equipListArray[x][y][z] != null){
				Iterator<EquipmentEtc> tempIt = equipListArray[x][y][z].iterator();
				//check to see if size of list is greater than the slot 
				if(equipListArray[x][y][z].size() > slotLocation)
				{
				
					for( int i = 0; i < slotLocation; i ++){
						tempIt.next();
					}
					retVal = tempIt.next();
					tempIt.remove();
					if(equipListArray[x][y][z].size() == 0)
						equipListArray[x][y][z] = null;
				}
			}
			else{
				System.out.println("Nothing on the ground");
			}
			
		}
		else{
			System.out.println("Out of bounds");
		}
		return retVal;
	}
	/*
	 * returns a int twin of the object's size and ID at a specific place and slotLocation
	 */
	public int[] getSizeIDTypeItemFromGroundAtLocationAndIntPostionInList( int x, int y, int z, int slotLocation){
		int[] retVal = new int[]{-1,-1,-1};
		if(thisEnvironment.withinXYZBounds(x, y, z)){
			if(equipListArray[x][y][z] != null){
				Iterator<EquipmentEtc> tempIt = equipListArray[x][y][z].iterator();
				//check to see if size of list is greater than the slot 
				if(equipListArray[x][y][z].size() > slotLocation)
				{
				
					for( int i = 0; i < slotLocation-1; i ++){
						tempIt.next();
					}
					EquipmentEtc temp = tempIt.next();
						//placing size and id in the return value
					retVal[0] = temp.getSize();
					retVal[1] = temp.getObjId();
					retVal[2] = temp.getObjType();
					
				}
			}
			else{
				System.out.println("Nothing on the ground");
			}
			
		}
		else{
			System.out.println("Out of bounds");
		}
		return retVal;
	}
	/*
	 * returns how many objects on on the ground in a spot
	 */
	public int getNumberOfObjectsAtThisLocation( int x, int y, int z){
		int retVal = 0;
		if(thisEnvironment.withinXYZBounds(x, y, z)){
			if(equipListArray[x][y][z] != null){
				retVal =  equipListArray[x][y][z].size();
			}
		}
		else{
			System.out.println("Out of bounds");
		}
		return retVal;
	}
	/*
	 * returns a string list of everything on the ground on this area
	 */
	public String getItemGroundNameList( int x, int y, int z, boolean prefixWithAlphaCharacters, int page){
		String retVal = "";
		
		if(thisEnvironment.withinXYZBounds(x, y, z)){
			if(equipListArray[x][y][z] != null){
				Iterator<EquipmentEtc> tempIt = equipListArray[x][y][z].iterator();
				//check to see if size of list is greater than the slot 
				
				//are we skipping any?
				if(page > 0 && equipListArray[x][y][z].size() > 26*(page+1)){
					for(int i = 0; i < page+1; i ++)
						tempIt.next();
				}
				int alpha = 97;
				while(tempIt.hasNext() && alpha != 123){
					if(prefixWithAlphaCharacters){
						retVal += (char)alpha+": ";
						alpha++;
					}
					retVal+=tempIt.next().toString()+"\n";
				}
					
			}
			else{
				retVal = "Nothing on the ground.";
			}
			
		}
		else{
			System.out.println("Out of bounds in ground item query");
		}
		return retVal;
	}
	/*
	 * returns the single name of an item at xyz position, page, and 0-25 int
	 */
	public String getSingleItemOnGroundName( int x, int y, int z, int page, int position){
		String retVal = "";
		
		if(thisEnvironment.withinXYZBounds(x, y, z)){
			if(equipListArray[x][y][z] != null){
				Iterator<EquipmentEtc> tempIt = equipListArray[x][y][z].iterator();
				//check to see if size of list is greater than the slot 
				
				//are we skipping any?
				if(page > 0 && equipListArray[x][y][z].size() >= (26*(page+1)+position)){
					for(int i = 0; i < (page+1)*26; i ++)
						tempIt.next();
				
				
					
				}
				for(int i = 0; i <= position && tempIt.hasNext(); i ++){
					retVal = tempIt.next().toString();
				}	
			}
			else{
				retVal = "Nothing on the ground.";
			}
			
		}
		else{
			System.out.println("Out of bounds in ground item query");
		}
		return retVal;
	}
	public LinkedList<EquipmentEtc> dropItemToGround(LinkedList<EquipmentEtc> listToAdd, int x, int y, int z) {
		//check to see if spot is null, if so, just copy, otherwise, iterate through and add
		if(equipListArray[x][y][z] == null){
			equipListArray[x][y][z] = listToAdd;
		}
		else{
			Iterator<EquipmentEtc> it = listToAdd.iterator();
			while(it.hasNext()){
				dropItemToGround(it.next(), x, y, z);
				it.remove();
			}
		}
		return listToAdd;
	}
	public int weightCheck(int x, int y, int z) {
		int retVal = 0;
		if(equipListArray[x][y][z] != null){
			Iterator<EquipmentEtc> tempIt = equipListArray[x][y][z].iterator();
			while(tempIt.hasNext()){
				retVal += tempIt.next().weightGet();
			}
		}
		return retVal;
		
	}
	public void objectsInFreeFall(int x, int y, int z) {
		if(equipListArray[x][y][z] != null){
			//drop these items.
					
			int toMoveTo = EquipmentEtc.itemsAreInFreeFall( this, x, y, z, equipListArray[x][y][z]);
			if(equipListArray[x][y][z-toMoveTo] == null){
				equipListArray[x][y][z-toMoveTo] = equipListArray[x][y][z];
			}
			else
				equipListArray[x][y][z-toMoveTo].addAll(equipListArray[x][y][z]);
			if(toMoveTo != 0)
				equipListArray[x][y][z] = null;
		}
		
	}

}
