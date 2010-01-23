package beta4;

import beta4.Environment.Environment;
import beta4.Environment.EnvironmentObject;
import beta4.Environment.EnvironmentTile;

public class ShipCreationObject {
	
	private  int shipColumns;
	private  int shipRows;
	private  int shipLevels;
	private  int totalEquipSpace;
	private  int spaceLeftForEquipment;
	private  int spaceLeftForAgents;
	private  int totalAgentSpace;
	private  int shipType;
	private  EnvironmentTile[][][] shipLayout;
	private  int[] equipLocation;
	private Environment currentEnvironment;
	
	
	
	public ShipCreationObject(int ship, Environment toBePlacedIn){
		
		ShipCreationObject createdShip = null;
		currentEnvironment = toBePlacedIn;
		
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
						totalEquipSpace = spaceLeftForEquipment = 50;
							//actual
						spaceLeftForAgents = totalAgentSpace = 14;
						
						shipString = shipStringGet(1);
						shipType = 1;
						
						break;
					default:
						break;
				}
			
					shipLayout = parseShip(shipString);

	}
	
	/*
	 * returns the shipstring
	 */
	private static String shipStringGet(int i) {
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
	private  EnvironmentTile[][][] parseShip(String shipString) {
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
	 * returns an int corresponding to the env tile
	 */
	private static int envTileIntFromShipCharGet(char charAt) {
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

	public int getXDim() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getYDim() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getZDim() {
		// TODO Auto-generated method stub
		return 0;
	}

	public EnvironmentObject getExternalZoomedOutEnvironmentToBeRepresented() {
		// TODO Auto-generated method stub
		return null;
	}

}
