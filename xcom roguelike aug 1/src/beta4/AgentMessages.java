package beta4;

public class AgentMessages {
	
	
	private HasAgency actor;
	
	private String thisUnitsLatestErrors;
	private String thisUnitsLatestMessages;
	
	
	public AgentMessages(HasAgency actor){
		this.actor = actor;
	}
	public void HUDRefresh() {
		// TODO Auto-generated method stub
		
	}
	public void newTurn() {
		// TODO Auto-generated method stub
		
	}
	/*
	 * should handle adding error messages to wherever they go
	 */
	public void errorMessageAdd(String errorMes) {
		
		thisUnitsLatestErrors += "\n" + errorMes;
		actor.currentEnvironment.displayErrorsAndMessages();
	}
	/*
	 * adds a message to the unit's messages
	 */
	public void messageAdd(String mes){
		
		thisUnitsLatestMessages += "\n"+ mes;
		actor.currentEnvironment.displayErrorsAndMessages();
	}
	/*
	 * should return errors and messages
	 */
	public String errorsGet() {
		/*
		 * private String thisUnitsLatestErrors;
		 *	private String thisUnitsLatestMessages;
		 */
		return thisUnitsLatestErrors + "\n" + thisUnitsLatestMessages;
	}

}
