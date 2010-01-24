package beta4;

import java.util.Iterator;
import java.util.LinkedList;

public class AgentSkills {
	
	
	private HasAgency actor;
	
	public static enum Skills{/*stat stuff*/CANHEAR, CAN_SUPER_SUPER, CANSEE, CAN_SUPER_SEE, CANSEEINDARK, CANSMELL, CAN_SUPER_SMELL,/*standard movement*/CANWALK, CANRUN, CANSPRINT, CANCRAWL, CANKNEEL, CANSQUAT, CANSTEALTH, CANFLOAT /*advanced movement*/, CANTUMBLE, CANHANG, CANCLIMBSIMPLE}
	private boolean stealthOn;
	
	private LinkedList<Skills> agentSkills;
	private LinkedList<Skills> agentTempSkills; //would would temporary things, like blindness, limping, etc.
	
	private int stance;
	//private boolean canSeeOtherUnitsVisionIfWithinEyesight;

	
	public AgentSkills(HasAgency actor){
		this.actor = actor;
	}
	public void HUDRefresh() {
		// TODO Auto-generated method stub
		
	}

 	private String tempSkillsToString() {
		String retVal= "";
		Iterator<Skills> it = agentTempSkills.iterator();
		while(it.hasNext()){
			retVal += skillNameToString(it.next())+ " ";
		}
		return retVal;
	}
	private String equipSkillsToString() {
		String retVal= "None";
		//TODO, implement equipment skills
		/**
		Iterator<Skills> it = agentEquipSkills.iterator();
		while(it.hasNext()){
			retVal += getStringOfSkillName(it.next())+ " ";
		}*/
		return retVal;
	}
	private String permSkillsToString() {
		String retVal= "";
		Iterator<Skills> it = agentSkills.iterator();
		while(it.hasNext()){
			retVal += skillNameToString(it.next())+ " ";
		}
		return retVal;
	}
	private String skillNameToString(Skills thisOne){
		String retVal = "";
		switch(thisOne){
		
		//,, , , , , ,, , , , , , ,  , , , 
		/*senses*/
		case CAN_SUPER_SEE:
			retVal = "Can see very well";
			break;
		case CANHEAR:
			retVal = "Can hear things";
			break;
			
		case CANSEE:
			retVal = "Can see";
			break;
		case CANSEEINDARK:
			retVal = "Can see in the dark";
			break;
		case CANSMELL:
			retVal = "Can smell";
			break;
		case CAN_SUPER_SMELL:
			retVal = "Can smell very well";
			break;
			/*standard movement*/
		case CANWALK:
			retVal = "Can move at standard speed";
			break;
		case CANRUN:
			retVal = "Can move at fast speed";
			break;
		case CANSPRINT:
			retVal = "Can move at very fast speed";
			break;
		case CANCRAWL:
			retVal = "Can crawl";
			break;
		case CANKNEEL:
			retVal = "Can kneel";
			break;
		case CANSQUAT:
			retVal = "Can move while squatting";
			break;
		/*advanced movement*/
		case CANSTEALTH:
			retVal = "Can operate stealthily";
			break;
		case CANFLOAT:
			retVal = "Can float";
			break;
		case CANTUMBLE:
			retVal = "Can tumble if necessary";
			break;
		case CANHANG:
			retVal = "Can hang from ledges";
			break;
		case CANCLIMBSIMPLE:
			retVal = "Can climb easy easy things";
			break;
		/*case :
			retVal = "";
			break;
		case :
			retVal = "";
			break;*/
			
		default:
			retVal = thisOne.toString();
			break;
		}
		return retVal;
	}
	public String allSkillsToString(){
		String retVal = "Innate: /n";
		retVal += permSkillsToString();
		retVal += "Equipment dependant /n";
		retVal += equipSkillsToString();
		retVal += "Temporary /n";
		retVal += tempSkillsToString();
		
		return retVal;
	}

	/*
	 * returns true if it has this skill
	 */
	public boolean agentHasThisSkillPermanent(Skills toCheck){
		boolean retVal = false;
		if(agentSkills != null){
			
			Iterator<Skills> it = agentSkills.iterator();
			while(it.hasNext() || !retVal)
				retVal = it.next() == toCheck;
		}
		return retVal;
	}
	/*
	 * returns 
	 */
	public boolean agentHasThisSkillTemp(Skills toCheck){
		boolean retVal = false;
		if(agentTempSkills != null){
			Iterator<Skills> it = agentTempSkills.iterator();
			while(it.hasNext() || !retVal){
				retVal = it.next() == toCheck;
			}
		}
		return retVal;
	}
	
	/*
	 * attempts to add skills, will return any skills that cannot be added for whatever reason
	 */
	public LinkedList<Skills> skillsAdd(LinkedList<Skills> toAdd, boolean permanent){
		LinkedList<Skills> retVal = new LinkedList<Skills>();
		LinkedList<Skills> toAddTo;
		if(permanent){
			toAddTo = agentSkills;
			
		}
		else{
			toAddTo = agentTempSkills;
		}
		if(toAddTo == null){
			toAddTo = new LinkedList<Skills>();
		}
		//only add this if it isn't already there
		Iterator<Skills> itIn = toAdd.iterator();
		
		while(itIn.hasNext()){
			Skills temp = itIn.next();
			if(!agentHasThisSkillPermanent(temp) && !agentHasThisSkillTemp(temp)){
				toAddTo.add(temp);
				itIn.remove();
			}
			else{
				retVal.add(temp);
				itIn.remove();
			}
		}
		return retVal;
		
	}

	public void newTurn() {
		// TODO Auto-generated method stub
		
	}

	

}
