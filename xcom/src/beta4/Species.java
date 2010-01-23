package beta4;

import java.awt.Color;
import java.util.LinkedList;

import beta4.Agent.AgentSkills;
import beta4.Environment.EquipmentEtc;



public class Species {
	
	/*
	 * the following are variables that are defined per species, etc
	 */
			private   int[] TUStanceMoveCost = null;
	
			private   int[] ENStanceMoveCost = null;
			
	/*
	 * the following are variables that are defined at character creation (like health, etc)
	 */
			private int currentTimeUnits;
			private int maxTimeUnits;

			private HasAgencyBiography personalBiography;
			private boolean stealthOn;
			// have field of view be affected by stance, IE, sprinting might give tunnel vision, while kneeling/standing might give considerably more.
			private int agentFOVHoriz;
			private int agentFOVVert;
			private LinkedList<AgentSkills.Skills> agentSkills;
			private LinkedList<AgentSkills.Skills> agentTempSkills; //would would temporary things, like blindness, limping, etc.
			private LinkedList<EquipmentEtc> [] unitCubbyLLArray; //holds all the linkedlists for equipment
			private String[] inventorySlotNames;
			
			private short[] equipMaxSizeArray; //holds the max size of an item that each array slot can fit
			private short[] equipMaxNumberArray; //holds max total inventory size available for each inventory slots
				//holds the grid array with the TU costs to move items from one spot to another
			int[][] unitMoveItemCostArray;
			
			private int currentUnitHeightCM;
			private int stance;
					//represents a Human X-com agent (including scientists and engineers?
			//private int thisUnitType;
			
			private int currentEnergy;
			private int currentHealth;
			private int currentStrength;
			private int maxEnergy;
			private int maxHealth;
			private int maxStrength;
			private int thisAgentSex;
			private boolean canRemember;
			private boolean canSeeOtherUnitsVisionAlways;
			private int experience;
			private int rank;
					//private boolean canSeeOtherUnitsVisionIfWithinEyesight;
			private double[] stanceHeightModArray;//TODO instantiate this!!! will hold the values use to modify the height based on the unit's stance
			private boolean[] stanceAvailable;
			private int sightDistance;
			private double[] stanceFOVAffectArray;
			private String[] rankArray;
			private String[] stanceNameStringArray;
				//holes the number of compartments (should always be the first ones) that are 'wieldable'
			private int numCompartmentsWieldable;

			private int stanceMinForMobility;

			private int[] stanceENCostSwitchUpArray;

			private int[] stanceTUCostSwitchUpArray;

			private int[] stanceENCostSwitchDownArray;

			
			private int[] stanceTUCostSwitchDownArray;
			private int TUCostUnloading;
			private int TUCostLoading;
			
			private char[][] representingChar;
		private Color standardColorFront;
		private Color standardColorBack;
		private String thisName;
		//1 = one square, Default north facing direction will be the 'front' there could theoretically be a creature that is two z levels tall [x][y][z]
		private short[] dimensions;
		private int supportsWeight;
			//what percentage movement is multiplied when a unit moves through this square
		private int percentageMovement;
		//how much percentage each square of this material has to block a unit shorter than its height, 
		private int cover;
		//flammability, how long a flame must be adjacent to this bit before it catches fire, -1 means immune
		private int howFlammable;
		//how many turns it burns before it decomposes
		private int howLongBurns;
		//this is what will replace it once it has burned... done via a complete swamp of stuff, so it's essentially a new tile, would equal the switch/case 
		private int burnsTo; 
		//what something is converted to on an explosion. -1 means it stays the same (switch case)
		private int explodesTo;
		private int hitPoints;
		private int objectType;
		private boolean blocksPassableHorizontal;
		private boolean blocksPassableVertical;
		private int weightKG;
		private int heightCM;
		private boolean blocksLight;

		private String speciesName;
				 

	
	public Species(){	
				speciesName = "Human";
		/*
		 * These will be the raw baselines for everything, all others will be a double that will multiply whatever comes from this, or will add to something (IE, a list of tags)
		 */
		/*
		 * the following are variables that are defined per species, etc
		 */
				//these must all be the same length
				TUStanceMoveCost = new int[]{0, 0, 10, 0, 4, 4, 3, 3};
				stanceNameStringArray = new String[]{"dead"/*0*/, "unconscious"/*1*/, "crawling/prone"/*2*/, "kneeling"/*3*/, "crouching"/*4*/, "standing"/*5*/, "running"/*6*/, "sprinting"/*7*/};;
				ENStanceMoveCost = new int[]{0, 0, 12, 0, 4, 2, 3, 4};
				stanceMinForMobility = 2;
					//cost to move to this stance from a lower stance
				stanceENCostSwitchUpArray = new int[]{0, 0, 0, 6, 1, 3, 2, 3};
				stanceTUCostSwitchUpArray = new int[]{0, 0, 0, 4, 2, 6, 2, 2};
					//cost to move to this stance from a higher stance
				stanceENCostSwitchDownArray = new int[]{0, 0, 4, 1, 2, 2, 2, 0};
				stanceTUCostSwitchDownArray = new int[]{0, 0, 2, 1, 2, 2, 2, 0};
				stanceHeightModArray = new double[]{.2, .2, .5, .7, .8, 1.0, 1.05, 1.1};
				stanceAvailable = new boolean[]{true, true, true,true,true,true,true,true};
				stanceFOVAffectArray = new double[]{0.0, .05, 1.1, 1.3, 1.2, 1.0, .8, .5};
				stance = 5;
				if(!((stanceFOVAffectArray.length == TUStanceMoveCost.length) == (stanceNameStringArray.length == ENStanceMoveCost.length)  == (stanceENCostSwitchUpArray.length == stanceTUCostSwitchUpArray.length) == (stanceENCostSwitchDownArray.length == stanceTUCostSwitchDownArray.length) == (stanceHeightModArray.length == stanceAvailable.length)))
					System.out.println("Error, stance arrays not all the same length");
				
				
		/*
		 * the following are variables that are defined at character creation (like health, etc)
		 */
				
				maxTimeUnits = currentTimeUnits = Tools.getRandInt(50, 60,1);
				thisAgentSex = HasAgencyBirthing.randomSex(65,35);
				
				stealthOn = false;
				// have field of view be affected by stance, IE, sprinting might give tunnel vision, while kneeling/standing might give considerably more.
				agentFOVHoriz = 100;
				agentFOVVert = 90;
				//private LinkedList<Skills> agentSkills;
				//private LinkedList<Skills> agentTempSkills; //would would temporary things, like blindness, limping, etc.
				 //holds all the linkedlists for equipment
				inventorySlotNames = new String[]{"Left Hand", "Right Hand", "Left Leg", "Right Leg", "Belt", "Left Shoulder", "Right Shoulder", "Pack"};
				numCompartmentsWieldable = 2;
				
				
				//largest object that will fit in a slot
				{
				short leftHandMaxSize = 6;
				short 				rightHandMaxSize = 6;
				short leftLegMaxSize = 1;
				short rightLegMaxSize = 1;
				short beltMaxSize = 4;
				short backPackMaxSize = 9;
				short leftShoulderMaxSize = 1;
				short rightShoulderMaxSize = 1;
				
				equipMaxSizeArray = new short[]{leftHandMaxSize, rightHandMaxSize, leftLegMaxSize, rightLegMaxSize, beltMaxSize, leftShoulderMaxSize, rightShoulderMaxSize, backPackMaxSize, };
				}
					//total number of objects that can be in a spot
				short leftHandMaxNumber = 1;
				short rightHandMaxNumber = 1;
				short leftLegMaxNumber = 2;
				short rightLegMaxNumber = 2;
				short beltMaxNumber = 6;
				short backPackMaxNumber = 9;
				short leftShoulderMaxNumber = 2;
				short rightShoulderMaxNumber = 2;
				
				equipMaxNumberArray = new short[]{leftHandMaxNumber, rightHandMaxNumber, leftLegMaxNumber, rightLegMaxNumber, beltMaxNumber,  leftShoulderMaxNumber, rightShoulderMaxNumber, backPackMaxNumber, };
		
				LinkedList<EquipmentEtc> leftHand = new LinkedList<EquipmentEtc>();
				LinkedList<EquipmentEtc> rightHand = new LinkedList<EquipmentEtc>();
				
				LinkedList<EquipmentEtc> leftLeg = new LinkedList<EquipmentEtc>();
				LinkedList<EquipmentEtc>rightLeg = new LinkedList<EquipmentEtc>();
				LinkedList<EquipmentEtc> belt = new LinkedList<EquipmentEtc>();
				LinkedList<EquipmentEtc> leftShoulder = new LinkedList<EquipmentEtc>();
				LinkedList<EquipmentEtc> rightShoulder = new LinkedList<EquipmentEtc>();
				LinkedList<EquipmentEtc> backPack = new LinkedList<EquipmentEtc>();
				
				unitCubbyLLArray = new LinkedList[]{leftHand, rightHand,leftLeg,rightLeg,belt,leftShoulder,rightShoulder,backPack};
				
				
				unitMoveItemCostArray = new int[][]
		                    //{0 lhand,   1 rhand,    2lleg,   3rleg,   4belt,   5lshould,6rshould,7backp, 8ground}
			    {/*0lhand*/  {0,		  4,		 8,	   	   10,	    8,		 10,      10,	     14,	     2}
				,/*1rhand*/  {4,		  0,		 10,	   8,	    8,		 10,	  10,	     14,	     2}
				,/*2lleg*/   {4,	      6,	     0,	      10,	    10,	     10,      10,        18,         6}
				,/*3rleg*/   {6,	      4,	     10,	   0,	    10,	     10,      10,        18,         6}
				
				,/*4belt*/   {4,	      4,		 10,	   10,	    0,		 12,	  12,        16,         6}
				,/*5lshould*/{3,		  3,		 12,	   12,	    10,	     0,       9,	     16,	     4}
				,/*6rshould*/{3,		  3,		 12,	   12,	    10,	     9,       0,	     16,	     4}
				
				,/*7backp*/  {8,		  8,		 16,	   16,	    12,		 14,	  14,	     0,	         10}
				,/*8ground*/ {8,	      8,        10,       10,      12,       12,      12,        20,         0}};
				
				
				
				AgentSkills.Skills[]tempSkills = new AgentSkills.Skills[]{AgentSkills.Skills.CANCLIMBSIMPLE, AgentSkills.Skills.CANCRAWL, AgentSkills.Skills.CANHANG, AgentSkills.Skills.CANHEAR, AgentSkills.Skills.CANKNEEL, AgentSkills.Skills.CANRUN, AgentSkills.Skills.CANSEE, AgentSkills.Skills.CANSMELL, AgentSkills.Skills.CANSPRINT, AgentSkills.Skills.CANSQUAT, AgentSkills.Skills.CANSTEALTH, AgentSkills.Skills.CANTUMBLE, AgentSkills.Skills.CANWALK};
				agentSkills = new LinkedList<AgentSkills.Skills>();
				
				for(int i = 0; i < tempSkills.length; i ++){
					agentSkills.add(tempSkills[i]);
				}
				
				
				heightCM = HasAgencyBirthing.getRandomUnitHeight(0,thisAgentSex);
				
						//represents a Human X-com agent (including scientists and engineers?
				//private int thisUnitType;
				
				maxEnergy = currentEnergy=Tools.getRandInt(40, 70,1);
				maxHealth = currentHealth=Tools.getRandInt(25, 40,1);
				maxStrength = currentStrength=Tools.getRandInt(20, 40,1);
				
				canRemember = true;
				canSeeOtherUnitsVisionAlways = true;;
				experience = 0;
				rank = 2;
						//private boolean canSeeOtherUnitsVisionIfWithinEyesight;
				
				sightDistance = 10;
				canRemember = true;
				
				rankArray = new String[]{"Engineer", "Scientist", "Rookie", "Squaddie", "Sergent", "Captain", "Colonel", "Commander"};
					
				
				
					//holes the number of compartments (should always be the first ones) that are 'wieldable'
				numCompartmentsWieldable = 2;
	
				
				TUCostUnloading = 8;
				TUCostLoading = 15;
				
				
				 	representingChar = new char[][]{{'@','@'},{'@','@'}};
				//representingChar = new char[][]{{'@'}};
				 	
				 	blocksLight = true;
				 	
		standardColorFront = Color.WHITE;
		standardColorBack = Color.GREEN;
		thisName = HasAgencyBirthing.generateNames(0, thisAgentSex);
		//1 = one square, Default north facing direction will be the 'front' there could theoretically be a creature that is two z levels tall [x][y][z]
		dimensions = new short[]{1,1,1};
		supportsWeight = weightKG + maxStrength;
			//what percentage movement is multiplied when a unit moves through this square
		percentageMovement = 0 ;
		//how much percentage each square of this material has to block a unit shorter than its height, 
		cover = 50;
		//flammability, how long a flame must be adjacent to this bit before it catches fire, -1 means immune
		howFlammable = 5;
		//how many turns it burns before it decomposes
		howLongBurns = 15;
		//this is what will replace it once it has burned... done via a complete swamp of stuff, so it's essentially a new tile, would equal the switch/case 
		burnsTo = -1; 
		//what something is converted to on an explosion. -1 means it stays the same (switch case)
		explodesTo = -1;
		hitPoints = maxHealth;
		objectType = 0 + Tools.HasAgencyBaseObjectNumber;
		blocksPassableHorizontal = true;
		blocksPassableVertical = true;
		weightKG = HasAgencyBirthing.getRandomUnitWeightKG(0,thisAgentSex, heightCM);
		
	}
	public HasAgency intializeThisAgent(HasAgency thisOne){HasAgencyBirthing.thisAgentPersonalBiography(thisOne);
		
		{
			thisOne.theseCosts.setTUStanceMoveCost(TUStanceMoveCost);
			thisOne.theseStats.setStanceNameStringArray(stanceNameStringArray);
			thisOne.theseCosts.setENStanceMoveCost(ENStanceMoveCost);
			thisOne.theseStats.setStanceMinForMobility(stanceMinForMobility);
			
			thisOne.theseCosts.setStanceENCostSwitchUpArray(stanceENCostSwitchUpArray);
			thisOne.theseCosts.setStanceTUCostSwitchUpArray(stanceTUCostSwitchUpArray);
				//cost to move to this stance from a higher stance
			thisOne.theseCosts.setStanceENCostSwitchDownArray(stanceENCostSwitchDownArray);
			thisOne.theseCosts.setStanceTUCostSwitchDownArray(stanceTUCostSwitchDownArray);
			thisOne.theseStats.setStanceHeightModArray(stanceHeightModArray);
			thisOne.theseStats.setStanceAvailable(stanceAvailable);
			thisOne.thisVision.setStanceFOVAffectArray(stanceFOVAffectArray);
			thisOne.theseStats.setStance(stance);
			thisOne.theseStats.setMaxTimeUnits(maxTimeUnits);
			thisOne.theseStats.setThisAgentSex(thisAgentSex);
			thisOne.blocksLightSet(blocksLight);
			
			thisOne.theseStats.setStealthOn(stealthOn);
			// have field of view be affected by stance, IE, sprinting might give tunnel vision, while kneeling/standing might give considerably more.
			thisOne.thisVision.setAgentFOVHoriz(agentFOVHoriz);
			//thisOne.getThisVision().setAgentFOVVert(agentFOVVert);
			//agentSkills;
			//agentTempSkills; //would would temporary things, like blindness, limping, etc.
			 //holds all the linkedlists for equipment
			thisOne.thisInventory.setInventorySlotNames(inventorySlotNames);
			thisOne.thisInventory.setNumCompartmentsWieldable(numCompartmentsWieldable);
			thisOne.thisInventory.setEquipMaxSizeArray(equipMaxSizeArray);
			thisOne.thisInventory.setEquipMaxNumberArray(equipMaxNumberArray);
			thisOne.thisInventory.setUnitCubbyLLArray(unitCubbyLLArray);
			thisOne.thisInventory.setUnitMoveItemCostArray(unitMoveItemCostArray);
			thisOne.heightCMSet(heightCM);
			thisOne.theseStats.setMaxEnergy(maxEnergy);
			thisOne.theseStats.setMaxHealth(maxHealth);
			thisOne.theseStats.setMaxStrength(maxStrength);
			thisOne.thisVision.setCanRemember(canRemember);
			thisOne.thisVision.setCanSeeOtherUnitsVisionAlways(canSeeOtherUnitsVisionAlways);
			thisOne.theseStats.setExperience(experience);
			thisOne.theseStats.setRank(rank);
			thisOne.thisVision.setSightDistance(sightDistance);
			thisOne.theseStats.setRankArray(rankArray);	//holes the number of compartments (should always be the first ones) that are 'wieldable'
			thisOne.thisInventory.setNumCompartmentsWieldable(numCompartmentsWieldable);
			thisOne.theseCosts.setTUCostUnloading(TUCostUnloading);
			thisOne.theseCosts.setTUCostLoading(TUCostLoading);
			DisplayColorChars[][] composite = DisplayColorChars.makeColorCharComposite(representingChar, Color.white, Color.black);
			thisOne.setTheseRepresentingChars(composite);
			thisOne.thisNameSet(thisName);
			
			thisOne.supportsWeightSet(supportsWeight);
			thisOne.percentageMovementSet(percentageMovement); 
			thisOne.coverSet((byte)cover);
			thisOne.howFlammableSet(howFlammable);
			thisOne.howLongBurnsSet(howLongBurns); 
			thisOne.burnsToSet(burnsTo); 
			thisOne.explodesToSet(explodesTo);
			thisOne.hitPointsSet(hitPoints);
			thisOne.objectTypeSet(objectType);
			thisOne.blocksPassableHorizontalSet(blocksPassableHorizontal);
			thisOne.blocksPassableVerticalSet(blocksPassableVertical);
			thisOne.weightSet(weightKG);
		}

		return thisOne;
	}
	public String speciesNameGet(){
		return speciesName;
	}
}
