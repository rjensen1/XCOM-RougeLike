package beta4;

import beta4.Environment.Environment;
import beta4.Environment.EnvironmentTactical;

public class InputManager {
	
	public static enum InputTypes{TACTICAL}
	public static InputTypes currentType;
	
	/*
	 *  what about having token objects for commands, and they can be modular depending on what is needed for action (would hold focus, request data, etc)
	 */
	//TODO



	public static void incomingCommand(char incoming){
		System.out.println(incoming);
		switch(currentType){
		case TACTICAL:
			InputManagerTactical.incomingCommand(incoming);
			System.out.println(incoming);
			break;
		default:
		}
		
	}
	public static void currentTypeSet(InputTypes currentType) {
		InputManager.currentType = currentType;
		InputManagerTactical.setUpTheKeys();
	}
	public static void currentEnvironmentSet(Environment thisOne){
		switch(currentType){
		case TACTICAL:
			InputManagerTactical.setEnvironment((EnvironmentTactical)thisOne);
			break;
		
		}
	}
	
}
