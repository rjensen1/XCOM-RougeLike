package beta4.Input;

public class InputTacticalDirectionalControls extends InputObject {

	
		
		public InputTacticalDirectionalControls(){
			this(InputObject.keyNotNeeded, InputObject.keyNotNeeded, getTacticalRootDescription(), getTacticalRootChildObjects(), true)
		}
		
		public InputTacticalDirectionalControls(char defaultKey, char currentKey,
			String newDiscription, InputObject[] theseChildInputObjects,
			boolean isRoot) throws ExceptionNoDescriptionForKeyCommand,
			ExceptionRootObjectWithNoChildren {
		super(defaultKey, currentKey, newDiscription, theseChildInputObjects, isRoot);
		// TODO Auto-generated constructor stub
	}
		private static InputObject[] getTacticalRootChildObjects() {
			InputObject[] tempObj = new InputObject[]{new InputTacticalDirectionalControls()};
			return tempObj;
		}
		private static String getTacticalRootDescription() {
			
			return "Tactical mission controls: ";
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
