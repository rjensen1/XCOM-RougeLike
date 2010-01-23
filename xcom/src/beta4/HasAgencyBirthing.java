package beta4;

public class HasAgencyBirthing {
	

	public static void thisAgentPersonalBiography(HasAgency currentAgent) {
		
		
			//set up this stuff and then use these setters.
		
		//currentAgent.personalHistorySet(new HasAgencyBiography(currentAgent));
		
		
		
		
	}
	public static void thisAgentPersonalBiography(HasConsciousness currentAgent) {
	
		
		//set up this stuff and then use these setters.
		//currentAgent.personalRelationshipsSet(new HasConsciousnessRelationships());
		//currentAgent.professionalRelationshipsSet(new HasConsciousnessRelationships());currentAgent.personalHistorySet(new HasAgencyBiography(currentAgent));
			//cast it and send it to the general agent
		HasAgencyBirthing.thisAgentPersonalBiography((HasAgency)currentAgent);

	}
	/*
	 * returns a balanced weight for a unit based on its species, gender and heigt
	 */
	public static int getRandomUnitWeightKG(int species, int sex1Male2Female, int heightCM) {
		int retVal=0;
		switch(species){
		case 0:
			//human
			if(sex1Male2Female==1/*male*/)
				retVal=(Tools.getRandInt((int)(heightCM*.37), (int)(heightCM*.52),1));
			else if(sex1Male2Female==2/*female*/)
				retVal=(int)(.95*Tools.getRandInt((int)(heightCM*.37), (int)(heightCM*.52),1));
			break;
		default :
			break;
		}
		return retVal;
	}
	/*
	 * generates a random height using the random tools, species and gender
	 */
	public static int getRandomUnitHeight(int species, int agentSex) {
		int height=0;
		switch(species){
		case 0: 
			//human male
			if(agentSex==1)
						//avg=177
				height = Tools.getRandInt(155, 199,1);
			//human female
			else if(agentSex==2)
						//avg=163ish
				height = Tools.getRandInt(145, 185,1);
			break;
		default:
			break;
		}
		return height;
	}
	/*
	 * will return a first and last name (or otherwise) depending on species and sex
	 * also, have 'regions', which are expandable and affects the naming of agents based on how close the 'recruiting'
	 * base is to the epicenters of each region. so that recruiting in europe will encounter european names... intermixing of first and last names?
	 * 
	 */
	public static String generateNames(int thisAgentSpecies, int thisAgentSex) {
		String retVal="";
		switch(thisAgentSpecies){
			case 0:
				//human
				if(thisAgentSex==1)//male
					retVal=getRandomNameMaleHumanAmerican();
				else if(thisAgentSex==2)//female
					retVal=getRandomNameFemaleHumanAmerican();
				break;
			default:
				break;
		}
		return retVal;
	}
	/*
	 * returns a first/last female name
	 */
	public static String getRandomNameFemaleHumanAmerican() {
		String retVal="";
			//http://names.mongabay.com/female_names.htm first 40 most popular
		String[] nameArray=new String[]{"MARY", "PATRICIA", "LINDA", "BARBARA", "ELIZABETH", "JENNIFER", "MARIA", "SUSAN", "MARGARET", 
				"DOROTHY", "LISA", "NANCY", "KAREN", "BETTY", "HELEN", "SANDRA", "DONNA", "CAROL", "RUTH", "SHARON", "MICHELLE","LAURA",
				"SARAH", "KIMBERLY", "DEBORAH", "JESSICA", "SHIRLEY", "CYNTHIA", "ANGELA", "MELISSA", "BRENDA", "AMY", "ANNA", "REBECCA", 
				"VIRGINIA", "KATHLEEN", "PAMELA", "MARTHA", "DEBRA", "AMANDA"};
		
			//picking a first name
		retVal= nameArray[Tools.getRandInt(0, nameArray.length-1,0)];
		return Tools.normalizeAllCaps(retVal) + " "+getRandomNameLastHumanAmerican();
	}
	/*
	 * returns a first/last male name
	 */
	public static String getRandomNameMaleHumanAmerican() {
		String retVal="";
		//http://names.mongabay.com/male_names.htm first 40 so far
		String[] nameArray=new String[]{"JAMES", "JOHN", "ROBERT", "MICHAEL", "WILLIAM", "DAVID", "RICHARD", "CHARLES", 
				"JOSEPH", "THOMAS", "CHRISTOPHER", "DANIEL", "PAUL", "MARK", "DONALD", "GEORGE", "KENNETH", "STEVEN", "EDWARD", "BRIAN", 
				"RONALD", "ANTHONY", "KEVIN", "JASON", "MATTHEW", "GARY", "TIMOTHY", "JOSE", "LARRY", "JEFFREY", "FRANK", "SCOTT", "ERIC", 
				"STEPHEN", "ANDREW", "RAYMOND", "GREGORY", "JOSHUA", "JERRY", "DENNIS"};	
		retVal= nameArray[Tools.getRandInt(0, nameArray.length-1,0)];
		return Tools.normalizeAllCaps(retVal) + " " + getRandomNameLastHumanAmerican();
	}
	/*
	 * returns a last name, also implement randomly generated last names, perhaps, taken from first, middle and last parts?
	 */
	public static String getRandomNameLastHumanAmerican() {
		String retVal="";
		//http://names.mongabay.com/most_common_surnames.htm so far, first 60 (american)
		
				//american, maybe have an int modifier that can get different ethnicities
		String[] nameArray=new String[]{"SMITH", "JOHNSON", "WILLIAMS", "JONES", "BROWN", "DAVIS", "MILLER", "WILSON", "MOORE", "TAYLOR",
				"ANDERSON", "THOMAS", "JACKSON", "WHITE", "HARRIS", "MARTIN", "THOMPSON", "GARCIA", "MARTINEZ", "ROBINSON",
				"CLARK", "RODRIGUEZ", "LEWIS", "LEE", "WALKER", "HALL", "ALLEN", "YOUNG", "HERNANDEZ", "KING", "WRIGHT", "LOPEZ",
				"HILL", "SCOTT", "GREEN", "ADAMS", "BAKER", "GONZALEZ", "NELSON", "CARTER",	"MITCHELL", "PEREZ", "ROBERTS", 
				"TURNER", "PHILLIPS", "CAMPBELL", "PARKER", "EVANS", "EDWARDS", "COLLINS", "STEWART", "SANCHEZ", "MORRIS", "ROGERS",
				"REED", "COOK", "MORGAN", "BELL", "MURPHY", "BAILEY"};
		retVal= nameArray[Tools.getRandInt(0, nameArray.length-1,0)];
		return Tools.normalizeAllCaps(retVal);
	}
	/*
	 * generates a sex, if the inputs are greater than 100, returns a 50/50 sex ratio. if it is less than 100, the extra will be non-gendered
	 * 1=male, 2=female, 0=asexual 
	 */
	public static int randomSex(int chanceMale,int chanceFemale) {
		int retVal=0;
		if(chanceMale+chanceFemale>100)
			Tools.getRandInt(1, 2,0);
		else{
			int temp = Tools.getRandInt(0, 100,0);
			
			if(temp<=chanceMale)
				retVal=1;
			else if(temp<=chanceFemale+chanceMale)
				retVal=2;
		}
		return retVal;
	}

}
