package beta4.Input;


import java.util.ArrayList;


public abstract class InputObject {
    

    //any incoming input is sent to this object if it is not null, otherwise, stuff is executed
    
    //holds the standard keys
    private char defaultKeyToPress;
        //holds user-set keys
    private char currentKeyToPress;
    private String description;
    
    //roots don't need default/current keys, but must have theseInputObjects[]
    private boolean isRoot;
    
    
    public static final char keyNotNeeded = '?';
    
    
    //these are optional
    private  InputObject thisActiveDeeperObject;
    private  InputObject[] theseInputObjects;
    
    
    
    
    
    public InputObject(char defaultKey, char currentKey, String newDiscription, InputObject[] theseChildInputObjects, boolean isRoot) throws ExceptionNoDescriptionForKeyCommand, ExceptionRootObjectWithNoChildren, ExceptionThereAreKeyExceptions{
        
        this.defaultKeyToPress = defaultKey;
        this.currentKeyToPress = currentKey;
        this.description = newDiscription;
        this.thisActiveDeeperObject = null;
        this.theseInputObjects = theseChildInputObjects;
        
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
        //TODO deal with putting all the keys into one container
        //TODO deal with getting a list of keys from a root of commands
        /*
        if (this.theseInputObjects.length > 1){
            
            
            for(int i = 0; i < theseInputObjects.length; i++){
                
                Character[] tempCharacters = theseInputObjects[i].getCurrentKeyToPress();    
                for(int m = i+1; m < theseInputObjects.length; m++){
                    
                    duplicationsFound = temp == theseInputObjects[m].currentKeyToPress;
                        if(duplicationsFound)
                            break;
                }
                if(duplicationsFound)
                    break;
            }
            
        }*/
        return duplicationsFound ;
    }

    private Character[] getCurrentKeyToPress() {
        Character[] returnArray = null;
        if(!this.isRoot)
        {
            if(this.thisActiveDeeperObject != null){
                returnArray = thisActiveDeeperObject.getCurrentKeyToPress();
            }
            returnArray = new Character[]{this.currentKeyToPress};
        }
        else if(isRoot && theseInputObjects != null)
        {
            ArrayList<Character> tempList= new ArrayList<Character>();
            for(int i = 0; i < theseInputObjects.length; i++){
                tempList.add(theseInputObjects[i].currentKeyToPress);
            }
            
            ArrayList<Character> listArray = new ArrayList<Character>();
            
            
                    
            returnArray = new Character[listArray.size()];
            listArray.toArray(returnArray);

        }
        return returnArray;
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
    public HoldReleaseThisAndParentPairBoolean receiveInput(char incoming) throws Exception {
        
        HoldReleaseThisAndParentPairBoolean returnValueInputObjBoolPair = null;
        
        //is there an active object
        if(thisActiveDeeperObject != null){
            
            //handle an active object
            if(thisActiveDeeperObject.correctKeyCommand(incoming)){
                onActiveDeeperObject();
                returnValueInputObjBoolPair = handleObjectHoldRelease(thisActiveDeeperObject.receiveInput(incoming));
            }else{
                //incorrect key
                //TODO
            }
            
        }else if(theseInputObjects != null){
            
            //there are sub-commands
            onNestedInputObject();
            returnValueInputObjBoolPair = selectProperCommandFromNestedList(incoming);
            
        }else if(!isRoot){
            //this is an leaf
            //check for valid command
            if(incoming == this.currentKeyToPress)
                returnValueInputObjBoolPair = onThisIsALeaf();
            else{
                //invalid key
                //TODO
            }
            
        }else{
            //this is a root with nothing in it, error of sorts
            //TODO
            //returnValueInputObjBoolPair = rootWithNoChildren();
        }
        
        
        return returnValueInputObjBoolPair;
        
    }

    
    
    //must be overridden if needed
    protected HoldReleaseThisAndParentPairBoolean onThisIsALeaf(){
        return null;
    }
    
    private HoldReleaseThisAndParentPairBoolean selectProperCommandFromNestedList(char incoming) {
        // TODO Auto-generated method stub
        return null;
    }
    private void onNestedInputObject() {
        // TODO Auto-generated method stub
        
    }
    private void onActiveDeeperObject() {
        // TODO Auto-generated method stub
        
    }
    private boolean correctKeyCommand(char incoming) {
        
        return incoming == this.currentKeyToPress;
    }
    private HoldReleaseThisAndParentPairBoolean handleObjectHoldRelease(HoldReleaseThisAndParentPairBoolean receiveInput) throws Exception {
        //handles this sort of logic... this method's return value will be returned to this object's parent... input is what was returned from this method's child
        
        
        
        HoldReleaseThisAndParentPairBoolean returnBoolPair = null;
        
        if(receiveInput.isHoldMyParentInItsParentObject() && receiveInput.isHoldMeInMyParentsActiveObject()){
            //being held by parent, also holding child
            this.thisActiveDeeperObject = receiveInput.getMyOwnReference();
            returnBoolPair = new HoldReleaseThisAndParentPairBoolean(this, true, true);
        }else if(receiveInput.isHoldMyParentInItsParentObject() && !receiveInput.isHoldMeInMyParentsActiveObject()){
            //being held by parent, not holding child
            this.thisActiveDeeperObject = null;
            returnBoolPair = new HoldReleaseThisAndParentPairBoolean(this, true, false);
        }else if(!receiveInput.isHoldMyParentInItsParentObject() && !receiveInput.isHoldMeInMyParentsActiveObject()){
            //not being held by parent, not holding child
            this.thisActiveDeeperObject = null;
            returnBoolPair = new HoldReleaseThisAndParentPairBoolean(null, true, false);
        }else if(!receiveInput.isHoldMyParentInItsParentObject() && receiveInput.isHoldMeInMyParentsActiveObject()){
            //not being held by parent, holding child XXXXXXXXXX
            //TODO
        }
        
        
        
        return returnBoolPair;
    }
    private void testCode(){
/*
        
        //are there inputobjects in the array and/or root
        
        //is this an end-node
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
        return ;*/
    }


    

}










   
    