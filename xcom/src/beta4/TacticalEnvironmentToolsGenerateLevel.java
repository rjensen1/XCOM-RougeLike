package beta4;

import beta4.Environment.EnvironmentTile;


public class TacticalEnvironmentToolsGenerateLevel {
	public static enum landscapeTags {TEST};
	private static int uniqueRegionNum = 40;
	private static enum shape {SQUARE};
	
	
	/*
	 * toBeTheLevel cannot be null, (as in, it must have dimensions)
	 */
	public static void genLevelRecusively(EnvironmentTile[][][] toBeTheLevel, landscapeTags tags){
		//for now, assume that there is no Z... just air, everthing on the ground level.
		//create a second array, of the same size, while will be used to hold regions, etc
		int[][][] regionZoneArray = new int[toBeTheLevel.length][toBeTheLevel[0].length][toBeTheLevel[0][0].length];
		
		//do a recursive flood fill of the entire ground z-level with 0
		int nextRegionNum = getNextRegionNum();
		
		fillToEdgesOfRegion(regionZoneArray, 0,0,0, 0, nextRegionNum);
		
		
		if(tags == landscapeTags.TEST){
			int recurseFade = 1;
			int recurseChance = 100;
			buildRegionsRecursively(regionZoneArray, 0,0,0,shape.SQUARE, recurseFade, recurseChance);
		}
		printRegions(regionZoneArray);
	}
	/*
	 * inputs an int array, xpz position (which must be inside of this shape and a shape, recurseChanceFade (which decrements recurse chance) chanceRecurse is out of 100
	 */
	private static void buildRegionsRecursively(int[][][] regionZoneArray, int xPos, int yPos, int zPos, shape thisShape, int recurseChanceFade, int chanceToRecurse) {
		//if random < chanceToRecurse
		if(Tools.getRandInt(0, 100, 0) <= chanceToRecurse){
			
			chanceToRecurse -= recurseChanceFade;
			recurseChanceFade *= 2;
			
			if(thisShape == shape.SQUARE){
				//for squares!!
				
				int[] xyVerticalTopBottom = getxyVerticalTopBottom(regionZoneArray,xPos,yPos,zPos);
				int[] xyHorizontalLeftRight = getxyHorizontalLeftRight(regionZoneArray,xPos,yPos,zPos);
				int yDim = xyVerticalTopBottom[3] - xyVerticalTopBottom[1];
				int xDim = xyHorizontalLeftRight[2] - xyHorizontalLeftRight[0];
				
				//would put min xy dimension check here?
				int horizOrVert = Tools.getRandInt(0, 9, 0);
					//these two variables will hold both sides of whichever line is drawn, to be sent as coordinates to the recursive function.
				//int[] xyRecursionA = new int[2];
				//int[] xyRecursionB = new int[2];
				if(horizOrVert < 5 && yDim > 2){
					//horiz line + recursion!
					//get y pos
					int yCoord = Tools.getRandInt(xyVerticalTopBottom[1]+2, xyVerticalTopBottom[3]-2, 0);
					
					System.out.println(yCoord);
				}
				else if(horizOrVert > 4 && xDim > 2){
					//vertical line + recursion!
					//get x pos
					int xCoord = Tools.getRandInt(xyHorizontalLeftRight[0]+2, xyHorizontalLeftRight[2]-2, 0);
					System.out.println(xCoord);
				}
				
			}
		}
		
	}
	/*
	 * inputs an array and position, measures and returns the xyxy coords (top/bottom) of the line that would fit inside of this shape
	 */
	private static int[] getxyVerticalTopBottom(int[][][] regionZoneArray,int posX, int posY, int posZ) {
		int thisRegion = regionZoneArray[posX][posY][posZ];
		int[] retVal = new int[]{posX,posY,posX,posY};
		//up
		while(!(retVal[1]-1 < 0) && regionZoneArray[retVal[0]][retVal[1]-1][posZ] == thisRegion){
			retVal[1]--;
		}
		//down
		while((retVal[3]+1) <  (regionZoneArray[0].length-1) && regionZoneArray[retVal[2]][retVal[3]+1][posZ] == thisRegion){
			retVal[3]++;
		}
		
		
		
		return retVal;
	}
	/*
	 * inputs an array and position, measures and returns the xyxy coords (top/bottom) of the line that would fit inside of this shape
	 */
	private static int[] getxyHorizontalLeftRight(int[][][] regionZoneArray,int posX, int posY, int posZ) {
		int thisRegion = regionZoneArray[posX][posY][posZ];
		int[] retVal = new int[]{posX,posY,posX,posY};
		//left
		while(!(retVal[0] - 1 < 0) && regionZoneArray[retVal[0]-1][retVal[1]][posZ] == thisRegion){
			retVal[0]--;
		}
		//right
		while((retVal[2] + 1) <  (regionZoneArray.length-1) && regionZoneArray[retVal[2] + 1][retVal[3]][posZ] == thisRegion){
			retVal[2]++;
		}
		
		return retVal;
	}
	/*
	 * does a flood fill out to the edges of this region, (replaces all the squares matching the starting square, with hte nextRegionNum
	 */
	private static void fillToEdgesOfRegion(int[][][] regionZoneArray, int x, int y, int z, int oldRegionNum, int newRegionNum) {
			//base case
		
		
			regionZoneArray[x][y][z] = newRegionNum;
			
			//recurse
				//top
				if( !(y == 0) && regionZoneArray[x][y-1][z] != newRegionNum )
			fillToEdgesOfRegion(regionZoneArray, x, y-1, z, oldRegionNum, newRegionNum);
				//bottom
				if( (y < regionZoneArray[0].length -1) && regionZoneArray[x][y+1][z] != newRegionNum)
			fillToEdgesOfRegion(regionZoneArray, x, y+1, z, oldRegionNum, newRegionNum);
				//left
				if( !(x == 0) && regionZoneArray[x-1][y][z] != newRegionNum)
			fillToEdgesOfRegion(regionZoneArray, x-1, y, z, oldRegionNum, newRegionNum);
				//right
				if( (x < regionZoneArray.length -1) && regionZoneArray[x+1][y][z] != newRegionNum)
			fillToEdgesOfRegion(regionZoneArray, x+1, y, z, oldRegionNum, newRegionNum);
			
		return;
	}
	/*
	 * increments and returns the next region number
	 */
	private static int getNextRegionNum(){
		uniqueRegionNum++;
		
		return uniqueRegionNum;
	}
	private static void printRegions(int[][][] regions){
		for(int y = 0; y < regions[0].length; y++){
			for(int x = 0; x < regions.length; x ++){
				System.out.print((char)regions[x][y][0]);
			}
			System.out.println();
		}
	}
}
