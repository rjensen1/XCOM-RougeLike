package beta4.Environment;

import java.awt.Color;

import beta4.DisplayColorChars;

public class EnvironmentTileBirthing {
	
	public static void changeType(EnvironmentTile toChange, int newType){
		
		
		
		
		
		//TODO implement following two things
	int[][] horizObstruction = null; //first is the obstruction, then second[0] = opacity second[1] = is physical obstruction(perhaps this number's variance equates to TU cost?) second[2] = left/upper bounds in CM second[3] = right/lower bounds in CM 
	int[][] vertObstruction = null;
	/*
	 * by definition, at least for now, environmenttiles are all 1,1,1 (dimension)
	 */
	int supportsWeight = 0;
	char[][] repChar = new char[][]{{'?'}};
	boolean isPassableHorizontal = false;
	boolean isPassableVertical = false;
	String name = "";
	boolean isStairs = false;
	
	Color tempBack = Color.pink;
	Color tempFront = Color.black;
	int percentageMovement = 0;
	int height = 0;
	int cover = 0;
	int howFlammable = -1;
	int howLongBurns = -1;
	int burnsTo = -1; 
	int explodesTo = -1;
	int hitPoints = -1;
	int weightKG = -1;
	
	switch(newType){
	case 0:
		//air
		supportsWeight=0;
		
		repChar = new char[][]{{' '}};
		isPassableHorizontal=true;
		isPassableVertical=true;
		name="air";
		tempBack = DisplayColorChars.Pale_Blue;
		tempFront = Color.black;
		percentageMovement = 50;
		height = 0;
		cover = 0;
		howFlammable = -1;
		howLongBurns = -1;
		burnsTo = -1; 
		explodesTo = -1;
		hitPoints = -1;
		weightKG = 1;
		//canMoveVert = VertMove.FLOAT;
		break;

	case 1:
		//packed soil
		supportsWeight=999999999;
		repChar = new char[][]{{'.'}};
		vertObstruction = new int[1][4]; //1 obstruction
		vertObstruction[0] = new int[]{ /*opac in percent 100=no light 0 = clear*/100, /*obstacle TU cost -1 = complete, not yet implemented*/4, /*leftUp boundaries in CM with left being 0,*/ 1, /*RightDown boundaries in CM with bottom being zero*/0};
		//hh
		isPassableHorizontal = true;
		isPassableVertical = false;
		name="packed earth";
		tempBack = DisplayColorChars.Dark_Brown;
		tempFront = DisplayColorChars.Light_Brown;
		percentageMovement=100;
		height=0;
		cover=0;
		howFlammable=-1;
		howLongBurns=-1;
		burnsTo=-1; 
		explodesTo=-1;
		hitPoints=-1;
		weightKG = 50;
		break;
	case 2:
		//grass
		supportsWeight=999999999;
		repChar = new char[][]{{','}};
		isPassableHorizontal=true;
		isPassableVertical=false;
		name="green grass";
		tempBack = DisplayColorChars.Light_Brown;
		tempFront = Color.GREEN;
		percentageMovement=100;
		height=8;
		cover=15;
		howFlammable=15;
		howLongBurns=3;
		burnsTo=102; 
		explodesTo=102;
		//means has to take 60 HP before that damage converts it to something
		hitPoints=60;
		weightKG = 60;
		break;
	case 3:
		//tall grass
		supportsWeight=999999999;
		repChar = new char[][]{{'\\'}};
		isPassableHorizontal=true;
		isPassableVertical=false;
		name="tall grass";
		tempBack = DisplayColorChars.Light_Brown;
		tempFront = Color.GREEN;
		percentageMovement=120;
		height=60;
		cover=25;
		howFlammable=6;
		howLongBurns=20;
		burnsTo=103; 
		explodesTo=203; //would start on fire
		hitPoints=120;
		weightKG = 65;
		break;
	case 4:
		//sand
		supportsWeight=999999999;
		repChar = new char[][]{{'~'}};
		isPassableHorizontal=true;
		isPassableVertical=false;
		name="sand";
		tempBack = DisplayColorChars.Light_Brown;
		tempFront = Color.WHITE;
		percentageMovement=180;
		height=0;
		cover=0;
		howFlammable=-1;
		howLongBurns=-1;
		burnsTo=-1; 
		explodesTo=-1;
		hitPoints=-1;
		weightKG = 100;
		break;
	case 5:
		//cornfield
		supportsWeight=999999999;
		repChar = new char[][]{{'/'}};
		isPassableHorizontal=true;
		isPassableVertical=false;
		name="Corn stalks";
		tempBack = DisplayColorChars.Light_Brown;
		tempFront = Color.YELLOW;
		percentageMovement=150;
		height=220;
		cover=15;
		howFlammable=8;
		howLongBurns=15;
		burnsTo=105; 
		explodesTo=105;
		hitPoints=-1;
		weightKG = 100;
		break;
	case 6:
		//pavement
		supportsWeight=999999999;
		repChar = new char[][]{{'='}};
		isPassableHorizontal=true;
		isPassableVertical=false;
		name="pavement";
		tempBack = Color.BLACK;
		tempFront = Color.GRAY;
		percentageMovement=100;
		height=0;
		cover=0;
		howFlammable=-1;
		howLongBurns=-1;
		burnsTo=-1; 
		explodesTo=206;
		hitPoints=150;
		weightKG = 350;
		break;
		//housing stuff
	case 25:
		//standard housing wall
		supportsWeight=50000;
		repChar = new char[][]{{'#'}};
		isPassableHorizontal=false;
		isPassableVertical=false;
		name="standard wall";
		tempBack = Color.BLACK;
		tempFront = Color.WHITE;
		percentageMovement=-1;
		height=10;
		cover=100;
		howFlammable=10;
		howLongBurns=50;
		burnsTo=125; 
		explodesTo=125;
		hitPoints=400;
		weightKG = 250;
		break;
	case 26:
		//carpet
		supportsWeight=20000;
		repChar = new char[][]{{'_'}};
		isPassableHorizontal=false;
		isPassableVertical=false;
		name="standard carpet";
		tempBack = Color.GRAY;
		tempFront = Color.WHITE;
		percentageMovement=100;
		height=0;
		cover=0;
		howFlammable=3;
		howLongBurns=10;
		burnsTo=126; 
		explodesTo=126;
		hitPoints=400;
		weightKG = 150;
		break;
	case 27:
		//closed door, would toggle to open, would have boolean to open any adjacent door sat the same time
		supportsWeight=10000;
		repChar = new char[][]{{'+'}};
		isPassableHorizontal=false;
		isPassableVertical=false;
		name = "Door";
		tempBack = DisplayColorChars.Light_Brown;
		tempFront = Color.BLACK;
		percentageMovement=0;
		height=10;
		cover=100;
		howFlammable=15;
		howLongBurns=8;
		burnsTo=127; 
		explodesTo=127;
		hitPoints=300;
		weightKG = 50;
		break;
		//ship stuff
	case 50:
		supportsWeight=10000;
		repChar = new char[][]{{'+'}};
		isPassableHorizontal=false;
		isPassableVertical=false;
		name="X-Com craft door";
		tempBack = Color.GRAY;
		tempFront = Color.yellow;
		percentageMovement=100;
		height=10;
		cover=100;
		howFlammable=15;
		howLongBurns=8;
		burnsTo=127; 
		explodesTo=127;
		hitPoints=300;
		weightKG = 90;
		break;
	case 51:
		
		break;
	case 52:
		supportsWeight=10000;
		repChar = new char[][]{{'!'}};
		isPassableHorizontal=true;
		isPassableVertical=false;
		name="Ship Unit safe zone";
		tempBack = Color.GRAY;
		tempFront = Color.YELLOW;
		percentageMovement=100;
		height=0;
		cover=0;
		howFlammable=15;
		howLongBurns=8;
		burnsTo=127; 
		explodesTo=127;
		hitPoints=300;
		weightKG = 150;
		break;
	case 53:
		supportsWeight=10000;
		repChar = new char[][]{{'%'}};
		tempBack = Color.GRAY;
		tempFront = Color.BLACK;
		isPassableHorizontal=false;
		isPassableVertical=false;
		name="Ship bulkhead";
		percentageMovement=0;
		height=10;
		cover=100;
		howFlammable=15;
		howLongBurns=8;
		burnsTo=127; 
		explodesTo=127;
		hitPoints=300;
		weightKG = 300;
		break;
	case 54:
		supportsWeight=10000;
		repChar = new char[][]{{'<'}};
		isPassableHorizontal=true;
		isPassableVertical=false;
		name="Ship up stairs";
		isStairs = true;
		tempBack = Color.lightGray;
		tempFront = Color.WHITE;
		percentageMovement=50;
		height=10;
		cover=40;
		howFlammable=15;
		howLongBurns=8;
		burnsTo=127; 
		explodesTo=127;
		hitPoints=300;
		weightKG = 170;
		//canMoveVert = VertMove.STAIRSUP; 
		break;
	case 55:
		supportsWeight=10000;
		repChar = new char[][]{{'>'}};
		isPassableHorizontal=true;
		isPassableVertical=false;
		name="Ship down stairs";
		isStairs = true;
		tempBack = Color.lightGray;
		tempFront = Color.WHITE;
		percentageMovement=50;
		height=10;
		cover=40;
		howFlammable=15;
		howLongBurns=8;
		burnsTo=127; 
		explodesTo=127;
		hitPoints=300;
		weightKG = 170;
		//canMoveVert = VertMove.STAIRSDOWN;
		break;
	case 56:
		supportsWeight = 10000;
		repChar = new char[][]{{'_'}};
		isPassableHorizontal=true;
		isPassableVertical=false;
		name="Ship floor";
		isStairs = true;
		tempBack = Color.gray;
		tempFront = Color.black;
		percentageMovement = 100;
		height=0;
		cover=0;
		howFlammable=15;
		howLongBurns=8;
		burnsTo=127; 
		explodesTo=127;
		hitPoints=300;
		weightKG = 170;
		//canMoveVert = VertMove.STAIRSDOWN;
		break;
	//burned types 100-199
	case 102:
		//charred grass
		break;
	case 103:
		//charred tall grass
		//exploded types 200-299
		break;
	case 105:
		//charred corn stalks
		break;
	case 127:
		//broken ship pieces
		repChar = new char[][]{{'B'}};
		break;
	case 203:
		//destroyed tall grass
		break;
	case 206:
		//damaged pavement
		break;
	default:
		newType = -1;
		break;
	}
	
	//set all of these variables
	
	//int[][] horizObstruction = null; //first is the obstruction, then second[0] = opacity second[1] = is physical obstruction(perhaps this number's variance equates to TU cost?) second[2] = left/upper bounds in CM second[3] = right/lower bounds in CM 
	//int[][] vertObstruction = null;
	toChange.isStairsSet(isStairs);
	toChange.supportsWeightSet(supportsWeight);
	
	toChange.blocksPassableHorizontalSet(!isPassableHorizontal);
	toChange.blocksPassableVerticalSet(!isPassableVertical);
	toChange.thisNameSet(name);
	toChange.objectTypeSet(newType);
	
	DisplayColorChars[][] composite = DisplayColorChars.makeColorCharComposite(repChar, tempFront, tempBack);
	toChange.theseRepresentingCharsSet(composite);
	toChange.percentageMovementSet(percentageMovement);
	toChange.heightCMSet(height);
	toChange.coverSet((byte)cover);
	toChange.howFlammableSet(howFlammable);
	toChange.howLongBurnsSet(howLongBurns);
	toChange.burnsToSet(burnsTo); 
	toChange.explodesToSet(explodesTo);
	toChange.hitPointsSet(hitPoints);
	toChange.lookDirectionSet(8);
	
	toChange.weightSet(weightKG);
	//TODO acutally define blocks light separately
	toChange.blocksLightSet(!isPassableHorizontal);
	
}

	

}
