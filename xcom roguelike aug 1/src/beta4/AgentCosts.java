package beta4;

public class AgentCosts {
	private   int[] TUStanceMoveCost = null;
	private   int[] ENStanceMoveCost = null;
	private int[] stanceENCostSwitchUpArray;

	private int[] stanceTUCostSwitchUpArray;

	private int[] stanceENCostSwitchDownArray;

	
	private int[] stanceTUCostSwitchDownArray;
	private int TUCostUnloading;
	private int TUCostLoading;
	
	private HasAgency actor;
	
	
	
	public AgentCosts(HasAgency actor){
		
		this.actor = actor;
		
	}
	public void HUDRefresh() {
		// TODO Auto-generated method stub
		//HUD.updated the stuff
		
		
	}
	public void newTurn() {
		// TODO Auto-generated method stub
		
	}
	public int costOpenDoorGet(){
		return 4;
	}
	public void setTUStanceMoveCost(int[] tUStanceMoveCost) {
		TUStanceMoveCost = tUStanceMoveCost;
	}
	public void setENStanceMoveCost(int[] eNStanceMoveCost) {
		ENStanceMoveCost = eNStanceMoveCost;
	}
	public void setStanceENCostSwitchUpArray(int[] stanceENCostSwitchUpArray) {
		this.stanceENCostSwitchUpArray = stanceENCostSwitchUpArray;
	}
	public void setStanceTUCostSwitchUpArray(int[] stanceTUCostSwitchUpArray) {
		this.stanceTUCostSwitchUpArray = stanceTUCostSwitchUpArray;
	}
	public void setStanceENCostSwitchDownArray(int[] stanceENCostSwitchDownArray) {
		this.stanceENCostSwitchDownArray = stanceENCostSwitchDownArray;
	}
	public void setStanceTUCostSwitchDownArray(int[] stanceTUCostSwitchDownArray) {
		this.stanceTUCostSwitchDownArray = stanceTUCostSwitchDownArray;
	}
	public void setTUCostUnloading(int tUCostUnloading) {
		TUCostUnloading = tUCostUnloading;
	}
	public void setTUCostLoading(int tUCostLoading) {
		TUCostLoading = tUCostLoading;
	}
	public void setActor(HasAgency actor) {
		this.actor = actor;
	}
	public int getTUStanceMoveCost() {
		return TUStanceMoveCost[actor.getTheseStats().getStance()];
	}
	public int getENStanceMoveCost() {
		return ENStanceMoveCost[actor.getTheseStats().getStance()];
	}
	public int[] getStanceENCostSwitchUpArray() {
		return stanceENCostSwitchUpArray;
	}
	public int[] getStanceTUCostSwitchUpArray() {
		return stanceTUCostSwitchUpArray;
	}
	public int[] getStanceENCostSwitchDownArray() {
		return stanceENCostSwitchDownArray;
	}
	public int[] getStanceTUCostSwitchDownArray() {
		return stanceTUCostSwitchDownArray;
	}
	public int getTUCostUnloading() {
		return TUCostUnloading;
	}
	public int getTUCostLoading() {
		return TUCostLoading;
	}
	public HasAgency getActor() {
		return actor;
	}
	public int getStanceENCostSwitchDownArray(int stance) {
		
		return this.stanceENCostSwitchDownArray[stance];
	}
	public int getStanceTUCostSwitchDownArray(int stance) {
		
		return this.stanceTUCostSwitchDownArray[stance];
	}
	public int getStanceENCostSwitchUpArray(int stance) {
		// TODO Auto-generated method stub
		return this.stanceENCostSwitchUpArray[stance];
	}
	public int getStanceTUCostSwitchUpArray(int stance) {
		// TODO Auto-generated method stub
		return stanceTUCostSwitchUpArray[stance];
	}
	


}
