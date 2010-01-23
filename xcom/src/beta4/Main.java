package beta4;

import java.util.Iterator;
import java.util.LinkedList;

import beta4.Environment.Environment;
import beta4.Environment.EnvironmentTactical;
import beta4.Environment.EquipmentEtc;

	
public class Main{
	
	public static void main(String[] args){
		
	initialize();
	//InputManager.currentTypeSet(InputManager.InputTypes.TACTICAL);
	while(true){
			
		}
	}
	/*
	 * just used for testing purposes, would be taken out in as more things are able to be done in the game.
	 */
	private static void newMission() {
			//	setting the type should only be done when the game first loads, the default type.
		InputManager.currentTypeSet(InputManager.InputTypes.TACTICAL);
		
		//GenerateLevel.genLevelRecusively(new EnvironmentTile[10][10][1], GenerateLevel.landscapeTags.TEST);
		
			//creating the tactical environment, this would normally be done when there is a new mission
		Environment environment = null;
		PackageShip tempShip = new PackageShip(new ShipCreationObject(1, environment));
		tempShip.theseAgentsToShipAdd(createExampleSquad(2));
		tempShip.equipmentToShipAdd(createExampleEquip(5,0,10));
		
		currentTacticalEnvironment = new EnvironmentTactical(50,50,3, 25, 14, null, display, tempShip);
		InputManager.currentEnvironmentSet((Environment)currentTacticalEnvironment);
		tempShip.representsWhenZoomedOut.tacticalEnvironmentSet(currentTacticalEnvironment);
		currentTacticalEnvironment.displayTacticalEnvironment(0);
		
		EquipmentEtc tempOb = new EquipmentEtc(0, currentTacticalEnvironment);
		tempOb.loadAmmo(new EquipmentEtc(1, currentTacticalEnvironment));
		currentTacticalEnvironment.thisEnvironmentTacticalUnits.getCurrentActivePCAgent().thisInventory.equipThisObject(tempOb, 1);
		
		
		
	}

	/*
	 * starts up the display objects, etc...
	 */
	private static void initialize(){
		display = new Display(45,70);
		Tools.initializeTools();
		
		newMission();
		
	}
	/*
	 * this would not be needed in the normal game, just used as an example
	 */
	private static LinkedList<HasAgency> createExampleSquad(int num){

		HasAgency.instantiateSpecies();
		Species temp = HasAgency.speciesGet("Human");
		LinkedList<HasAgency> unitList = new LinkedList<HasAgency>();
		
		for(int i = 0; i < num; i ++){
			//nitList.add(CharGen.characterGeneration(new HasConsciousness(), CharGen.SPECIES.HUMAN, CharGen.CLASS.XCOM_SOLDIER, CharGen.SUB_CLASS.STANDARD, CharGen.TUCOST_STRUCTURE.STANDARD_XCOM, new int[]{0,0,0}));
			unitList.add(temp.intializeThisAgent(new HasAgency()));
		}
			//just printing them out for show-n-tell
		Iterator<HasAgency> iterator = unitList.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
				
		return unitList;
	}
	/*
	 * generates num random pieces of equipment from lowRand to highRand (creation type)
	 */
	private static LinkedList<EquipmentEtc> createExampleEquip(int num, int lowRand, int highRand){


		LinkedList<EquipmentEtc> equipList = new LinkedList<EquipmentEtc>();
		
		for(int i = 0; i < num; i ++){
			equipList.add(new EquipmentEtc(Tools.getRandInt(lowRand, highRand, 0), currentTacticalEnvironment));
		}
			//just printing them out for show-n-tell
		Iterator<EquipmentEtc> iterator = equipList.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
				
		return equipList;
	}
	
	private static Display display;
	private static EnvironmentTactical currentTacticalEnvironment;
	
}
