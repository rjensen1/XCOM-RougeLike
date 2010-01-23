package beta4.Environment;

import java.io.Serializable;
import java.util.LinkedList;

public class Environment  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	
	
	
	
	protected int xDim;
	protected int yDim;
	protected int zDim;
	//TODO put in unidirection light source (sun/moon)
	//TODO put in optional objects/tiles that also create stationary light sources.
	
	
	
	protected EnvironmentTile[][][] environmentTileArray;
	
	
	public Environment(int xDimNew, int yDimNew, int zDimNew){
		
		
		xDim=xDimNew;
		yDim=yDimNew;
		zDim=zDimNew;

		environmentTileArray = new EnvironmentTile[xDim][yDim][zDim];
	}

	
	
	public int[] XYZBoundsGet(){
		return new int[]{xDim,yDim,zDim};
	}

	public EnvironmentTile environmentTileAtThisLocationGet(int x, int y, int z) {
		EnvironmentTile retVal = null;
		
		if(withinXYZBounds(x, y, z))
			retVal =  environmentTileArray[x][y][z];
		return retVal;
	}
	/*
	 * just setting the location for all objects. Does this need to be done?
	 */
	protected void environmentTileArraySetPositions() {
		for(int x = 0; x < environmentTileArray.length; x++){
			for(int y = 0; y < environmentTileArray[1].length; y++){
				for(int z = 0; z < environmentTileArray[1][1].length; z++){
					environmentTileArray[x][y][z].placeObjectHere(new int[]{x, y, z}, true);
					environmentTileArray[x][y][z].tacticalEnvironmentSet(this);
					//TODO deal with larger-than-1 tiles
				}
			}
		}
		
	}
	
	
	
	
	/*
	 * checks the incoming xyz variables against the environment's dimensions, returns false if any one of these is out-of-bounds
	 */
	public boolean withinXYZBounds(int x, int y, int z) {
	
		return withinXYZBounds(new int[]{x,y,z});
	}
	/*
	 * same as above, but accepts xyz triplet
	 */
	public boolean withinXYZBounds(int[] locationXYZTriple) {
		
		return locationXYZTriple[0] > -1 && locationXYZTriple[0] < xDim && locationXYZTriple[1] > -1 && locationXYZTriple[1] < yDim && locationXYZTriple[2] > -1 && locationXYZTriple[2] < zDim;
	}


	/*
	 * retuns the name of the tile in questions
	 */
	public String getEnvironmentTileNameAtLocation(int x, int y, int z) {
		String retVal = "None";
		if(withinXYZBounds(x, y, z))
			retVal =  environmentTileArray[x][y][z].thisNameGet();
		return retVal;
	}
	
	/*
	 * gets the ground tile at this location
	 */
	public EnvironmentTile getTileAtThisLocation( int x, int y, int z){
		EnvironmentTile retVal = null;
		
		if(withinXYZBounds(x, y, z)){
			retVal = environmentTileArray[x][y][z];
		}
		
		return retVal;
	}
	
	/*
	 * just a very simple randomgeneration
	 */
	protected void generateLandscapeRandom() {
		int tile = 1;
		for(int z=0; z<zDim; z++){
			for(int x=0;x<xDim;x++){
				for(int y=0;y<yDim;y++){
					//temp=Tools.getRandInt(1, 6, 0);
					environmentTileArray[x][y][z] = new EnvironmentTile(tile, this);
				}
			}
			tile = 0;
		}
	}

}
