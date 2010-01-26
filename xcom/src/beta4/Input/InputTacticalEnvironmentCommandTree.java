package beta4.Input;

public class InputTacticalEnvironmentCommandTree extends InputObject{

	
	
	
	public InputTacticalEnvironmentCommandTree() throws ExceptionNoDescriptionForKeyCommand, ExceptionRootObjectWithNoChildren{
		this(InputObject.keyNotNeeded, InputObject.keyNotNeeded, getTacticalRootDescription(), getTacticalRootChildObjects(), true);
	}
	
	
	
	private static InputObject[] getTacticalRootChildObjects() {
		InputObject[] tempObj = new InputObject[]{new InputTacticalDirectionalControls()};
		return tempObj;
	}
	private static String getTacticalRootDescription() {
		
		return "Tactical mission controls: ";
	}
	
	
	
	
	
	
	
	
	
	
	public InputTacticalEnvironmentCommandTree(char defaultKey,
			char currentKey, String newDiscription,
			InputObject[] theseChildInputObjects, boolean isRoot)
			throws ExceptionNoDescriptionForKeyCommand,
			ExceptionRootObjectWithNoChildren {
		super(defaultKey, currentKey, newDiscription, theseChildInputObjects, isRoot);
		
	}

	@Override
	protected Object algorithmsAfterSendingToDeeperObject(Object writeBackObject2) {
		
		return null;
	}

	@Override
	protected Object algorithmsBeforeSendingToDeeperObject(Object writeBackObject2) {
		
		return null;
	}

	@Override
	protected Object algorithmsToAlwaysDoFirst(Object writeBackObject2) {
		
		return null;
	}
	
	

}
