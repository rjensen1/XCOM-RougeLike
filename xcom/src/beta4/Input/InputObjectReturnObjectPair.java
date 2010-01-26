package beta4.Input;

public class InputObjectReturnObjectPair {
	
	private InputObject thisInputObject;
	private Object thisReturnedObject;
	
	public InputObjectReturnObjectPair(InputObject newInputObject, Object newReturnedObject){
		
		setThisInputObject(newInputObject);
		setThisReturnedObject(newReturnedObject);
		
	}

	public void setThisReturnedObject(Object thisReturnedObject) {
		this.thisReturnedObject = thisReturnedObject;
	}

	public Object getThisReturnedObject() {
		return thisReturnedObject;
	}

	public void setThisInputObject(InputObject thisInputObject) {
		this.thisInputObject = thisInputObject;
	}

	public InputObject getThisInputObject() {
		return thisInputObject;
	}
	

}
