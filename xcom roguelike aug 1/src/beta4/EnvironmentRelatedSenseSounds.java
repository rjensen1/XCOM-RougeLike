package beta4;

public class EnvironmentRelatedSenseSounds {
	
	
	
	
	EnvironmentTactical whereItOccured;
	int[] locationXYZTriplet;
	int dbLevel;
	EnvironmentObject causedBy;
	EnvironmentObject.ActionType actionCausedBy;
	EnvironmentObject.SoundType thisSound;
	
	public EnvironmentRelatedSenseSounds(EnvironmentObject causedByIncoming, EnvironmentTactical whereItOccuredIncoming, int[] locationXYZTripletIncoming, EnvironmentObject.SoundType thisSoundIncoming, int dbLevelIncoming, EnvironmentObject.ActionType actionCausedByIncoming){
		if(whereItOccuredIncoming != null && whereItOccured.withinXYZBounds(locationXYZTripletIncoming)){
			whereItOccured = whereItOccuredIncoming;
			locationXYZTriplet = locationXYZTripletIncoming;
			dbLevel = dbLevelIncoming;
			causedBy = causedByIncoming;
			actionCausedBy = actionCausedByIncoming;
			thisSound = thisSoundIncoming;
		}
		
	}

}
