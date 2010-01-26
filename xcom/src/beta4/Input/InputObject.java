package beta4.Input;

public abstract class InputObject {
	

	//any incoming input is sent to this object if it is not null, otherwise, stuff is executed
	
	//holds the standard keys
	private char defaultKeyToPress;
		//holds user-set keys
	private char currentKeyToPress;
	private String description;
	
	//roots don't need default/current keys, but must have theseInputObjects[]
	private boolean isRoot;
	private boolean passesBackWriteBackObjectToParentCommand;
	
	public static final char keyNotNeeded = '?';
	
	
	//these are optional
	private  InputObject thisActiveDeeperObject;
	private  InputObject[] theseInputObjects;
	
	private Object writeBackObject;
	
	
	
	public InputObject(char defaultKey, char currentKey, String newDiscription, InputObject[] theseChildInputObjects, boolean isRoot, boolean passesBackObjectToParent) throws ExceptionNoDescriptionForKeyCommand, ExceptionRootObjectWithNoChildren, ExceptionThereAreKeyExceptions{
		
		this.defaultKeyToPress = defaultKey;
		this.currentKeyToPress = currentKey;
		this.description = newDiscription;
		this.thisActiveDeeperObject = null;
		this.theseInputObjects = theseChildInputObjects;
		passesBackWriteBackObjectToParentCommand = passesBackObjectToParent;
		
		writeBackObject = null;
		this.isRoot = isRoot;
		
		
		if(thereAreKeyCollisions()) 
			throw new ExceptionThereAreKeyExceptions();
		if(newDiscription == null || newDiscription == "")
			throw new ExceptionNoDescriptionForKeyCommand();
		
		if(isRoot && (theseInputObjects == null || this.theseInputObjects.length == 0))
			throw new ExceptionRootObjectWithNoChildren();
		
		
	}
	private boolean thereAreKeyCollisions() {
		
		boolean duplicationsFound = false;
		
		if (this.theseInputObjects.length > 1){
			
			//TODO deal with getting a list of keys from a root of commands
			for(int i = 0; i < theseInputObjects.length; i++){
				
				char temp = theseInputObjects[i].currentKeyToPress;	
				for(int m = i+1; m < theseInputObjects.length; m++){
					duplicationsFound = temp == theseInputObjects[m].currentKeyToPress;
				}
				if(duplicationsFound)
					break;
			}
			
		}
		return duplicationsFound ;
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
	public InputObjectReturnObjectPair receiveInput(char incoming) throws ExceptionProblemWithLeftOverActiveDeeperObject{
		
		
		if(!isRoot)
		{
			if(incoming == currentKeyToPress)
			{
				writeBackObject = algorithmsToAlwaysDoFirst(writeBackObject);
				
				if(thisActiveDeeperObject != null)
				{
					writeBackObject = algorithmsBeforeSendingToDeeperObject(writeBackObject);
					thisActiveDeeperObject = thisActiveDeeperObject.receiveInput(incoming).getThisInputObject();
					writeBackObject = algorithmsAfterSendingToDeeperObject(writeBackObject);
					
				}
	
			}
			
			//if returning a null value, meaning no longer in this method, should clear out the writeback;
			boolean commandIsDone = (thisActiveDeeperObject == null);
			
			if(commandIsDone)
			{
				clearWriteBackWhenLeavingCommand();
			}
			
			if(thisActiveDeeperObject != null && thisActiveDeeperObject == null)
				throw new ExceptionProblemWithLeftOverActiveDeeperObject();
		
		}
		return ;
		
	}
	
	private void clearWriteBackWhenLeavingCommand() {
		writeBackObject = null;
		
	}
	
	
	
	//feel free to make custom objects and cast them, knowing that they will only be accessed by these methods
	protected abstract Object algorithmsToAlwaysDoFirst(Object writeBackObject2);
	
	protected abstract Object algorithmsBeforeSendingToDeeperObject(Object writeBackObject2);

	protected abstract Object algorithmsAfterSendingToDeeperObject(Object writeBackObject2);

	

	

}
