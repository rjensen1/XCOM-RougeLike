package beta4;


import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;



//import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;



/*
 * should handle all of the things that a unit does, including equipping stuff, etc.
 */
public class HasConsciousness extends HasAgency{
	
				//agent specific stuff
	
	
	
	
	
		//would keep track of all the relationships, basically a liked list of information coupled with an actually AnAgent reference.
	private HasConsciousnessRelationships personalRelationships;	//would hold personal life related stuff? could spill over into the other
	private HasConsciousnessRelationships professionalRelationships; //would hold x-com related stuff
	private int[] moods;

		//should hold all of the enums for how to refrence things.
	
	
	
	public HasConsciousness(){
		super();
		//personalSymbol=signifier;
	}

	
	
	
	/*
	 * set the personal relationships at the beginning of the game, perhaps this method should only work if current relationships are null, so that old ones don't get removed?
	 */
	public void personalRelationshipsSet(HasConsciousnessRelationships newRelationships){
		if(newRelationships!=null)
			personalRelationships=newRelationships;
	}
	
	/*
	 * set the professional relationships at the beginning of the game, perhaps this method should only work if current relationships are null, so that old ones don't get removed?
	 */
	public void professionalRelationshipsSet(HasConsciousnessRelationships newRelationships) {
		// if(newRelationships!=null)
		professionalRelationships = newRelationships;
		
	}
	public void moodsSet(int[] newMoods){
		moods = newMoods;
	}
	
	

	/*
	 * refreshes the HUD to that of this unit
	 */
	
	/*
	 * returns a string version of the current stance
	 */
	
	
	/*
	 * creates a string of the inventory
	 */
	
	
	
	
		

}
