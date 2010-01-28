package beta4.Input;

public class HoldReleaseThisAndParentPairBoolean{
	   
    private boolean holdMyParentInItsParentObject;
    private boolean holdMeInMyParentsActiveObject;
    private InputObject myOwnReference;
   
    public HoldReleaseThisAndParentPairBoolean(InputObject myReference, boolean holdMyParentInItsParentObject, boolean holdMeInMyParentsActiveObject) throws Exception{
        //stop-gap logic
        if(holdMeInMyParentsActiveObject && !holdMyParentInItsParentObject && myReference!=null){
            throw new Exception();
        }else{
            this.holdMeInMyParentsActiveObject = holdMeInMyParentsActiveObject;
            this.holdMyParentInItsParentObject = holdMyParentInItsParentObject;
            this.myOwnReference = myReference;
        }
       
    }


    public boolean isHoldMyParentInItsParentObject() {
        return holdMyParentInItsParentObject;
    }
    public boolean isHoldMeInMyParentsActiveObject() {
        return holdMeInMyParentsActiveObject;
    }
    public InputObject getMyOwnReference() {
        return myOwnReference;
    }
}
