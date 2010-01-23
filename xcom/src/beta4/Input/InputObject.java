package beta4.Input;

public abstract class InputObject {
	

	//any incoming input is sent to this object if it is not null, otherwise, stuff is executed
	
	//holds the standard keys
	private char defaultKeyToPress;
		//holds user-set keys
	private char currentKeyToPress;
	private String description;
	
	
	//these are optional
	private  InputObject thisActiveDeeperObject;
	private  InputObject[] theseInputObjects;
	
	private Object writeBackObject;
	
	
	
	public InputObject(char defaultKey, char currentKey, String newDiscription, InputObject[] theseChildInputObjects) throws ExceptionNoDescriptionForKeyCommand{
		
		this.defaultKeyToPress = defaultKey;
		this.currentKeyToPress = currentKey;
		this.description = newDiscription;
		this.thisActiveDeeperObject = null;
		this.theseInputObjects = theseChildInputObjects;
		writeBackObject = null;
		if(newDiscription == null || newDiscription == ""){
			throw new ExceptionNoDescriptionForKeyCommand();
		}
		
		
	}
	public void resetKeyToDefault(boolean resetAllKeys){
		if(resetAllKeys && (theseInputObjects != null)){
			for(int i = 0; i < theseInputObjects.length; i++)
				theseInputObjects[i].resetKeyToDefault(resetAllKeys);
		}
		this.currentKeyToPress = this.defaultKeyToPress;
		
	}
	
	public String toString(){
		StringBuilder string = new StringBuilder();
		string.append("Key: "+currentKeyToPress+" Description: "+description+"\n");
		
		if(theseInputObjects != null)
		{
			for(int i = 0; i < theseInputObjects.length; i++){
				
				string.append(theseInputObjects[i]+"\n");
			}
		}
		if(thisActiveDeeperObject != null){
			string.append(thisActiveDeeperObject.toString()+"----\n");
		}
		return string.toString();
	}
	public InputObject receiveInput(char incoming) throws ExceptionProblemWithLeftOverActiveDeeperObject{
		if(incoming == currentKeyToPress)
		{
			writeBackObject = algorithmsToAlwaysDoFirst(writeBackObject);
			
			if(thisActiveDeeperObject != null)
			{
				writeBackObject = algorithmsBeforeSendingToDeeperObject(writeBackObject);
				thisActiveDeeperObject = thisActiveDeeperObject.receiveInput(incoming);
				writeBackObject = algorithmsAfterSendingToDeeperObject(writeBackObject);
			}

		}
		
		//if returning a null value, meaning no longer in this method, should clear out the writeback;
		boolean commandIsDone = thisActiveDeeperObject == null;
		
		if(commandIsDone)
			clearWriteBackWhenLeavingCommand();
		
		if(thisActiveDeeperObject != null && thisActiveDeeperObject == null)
			throw new ExceptionProblemWithLeftOverActiveDeeperObject();
		
		return thisActiveDeeperObject;
	}
	
	private void clearWriteBackWhenLeavingCommand() {
		writeBackObject = null;
		
	}
	//feel free to make custom objects and cast them, knowing that they will only be accessed by these methods
	protected abstract Object algorithmsToAlwaysDoFirst(Object writeBackObject2);
	
	protected abstract Object algorithmsBeforeSendingToDeeperObject(Object writeBackObject2);

	protected abstract Object algorithmsAfterSendingToDeeperObject(Object writeBackObject2);

	

	

}
