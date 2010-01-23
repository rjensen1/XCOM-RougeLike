package beta4;

import java.util.LinkedList;
import java.util.Random;

public class Tools {
	private static Random generator;
	private static double[][] degreeLookUpTable;
		//must be evenly divided... 1, .5, .25 works. but not .2 or .1
	public static final double steppingForDegreeLOS = 1;
	//TODO deal with things in one z level being taller than this following height
	public static final int heightOfSquareInCentimeters = 250; //8.2 feet
	public static final int widthOfSquareInCentimeters = 100;
	public static final int sizeCellXTact = 20;
	public static final int sizeCellYTact = 20;
	public static final int EnvirTileBaseObjectNumber = 0;
	public static final int EquipEtcBaseObjectNumber = 1000;
	public static final int HasAgencyBaseObjectNumber = 10000;
	public static void initializeTools(){
		generator = new Random();
	
	}
	/*
	 * type:
	 * 	0 = flat
	 *  1 = average of two rolls
	 *  2 = 60/40 average two rolls
	 *  3 = 70/30 average two rolls
	 *  4 = 80/20 average two rolls
	 *  5 = weighted high
	 *  6 = weighted low
	 */
	static{
		degreeLookUpTable = new double[(int) (360.0/steppingForDegreeLOS)][2];
		
		for(double degreeDEG = 0.0; degreeDEG < 360.0; degreeDEG += steppingForDegreeLOS){
			double degree = degreeDEG*0.0174532925;
			double[] riseRun = new double[2];
			

			if(degreeDEG == 90.0){
				riseRun[0] = -1.0; //y
				riseRun[1] = 0.0; //x
			}else if(degreeDEG == 270.0){
				riseRun[0] = +1.0; //y
				riseRun[1] = 0.0; //x
				
			}else if(degreeDEG == 180.0){
					riseRun[0] = 0.0; //y
					riseRun[1] = -1.0; //x
			}else if(degreeDEG == 0.0){
				riseRun[0] = 0.0; //y
				riseRun[1] = 1.0; //x
		
			}else if((degreeDEG < 90.0) && (degreeDEG > 0.0)){
				riseRun[0] = -Math.tan(degree); //y
				riseRun[1] = 1.0; //x
			}else if((degreeDEG > 90.0) && (degreeDEG < 180.0)){
				riseRun[0] = Math.tan(degree); //y
				riseRun[1] = -1.0; //x
			}else if((degreeDEG > 180.0) && (degreeDEG < 270.0)){
				riseRun[0] = Math.tan(degree); //y
				riseRun[1] = -1.0; //x
				
				
			}
			else if((degreeDEG > 270.0) && (degreeDEG < 360.0)){
				riseRun[0] = -Math.tan(degree); //y
				riseRun[1] = 1.0; //x
			
			}
			if(riseRun[0] > 1 ){
				riseRun[1] = riseRun[1] / Math.abs(riseRun[0]);
				riseRun[0] = 1;
			}else if(riseRun[0] < -1){
				riseRun[1] = riseRun[1] / Math.abs(riseRun[0]);
				riseRun[0] = -1;
			}
			degreeLookUpTable[(int)(degreeDEG/steppingForDegreeLOS)] = riseRun;
			
		}
			
		/*
		double degree = degreeDEG*0.0174532925;
		double[] riseRun = new double[2];
		
		if(degreeDEG == 90.0){
			riseRun[0] = -1.0; //y
			riseRun[1] = 0.0; //x
		}else if(degreeDEG == 270.0){
			riseRun[0] = +1.0; //y
			riseRun[1] = 0.0; //x
			
		}else if(degreeDEG == 180.0){
				riseRun[0] = 0.0; //y
				riseRun[1] = -1.0; //x
		}else if(degreeDEG == 0.0){
			riseRun[0] = 0.0; //y
			riseRun[1] = 1.0; //x
	
		}else if((degreeDEG < 90.0) && (degreeDEG > 0.0)){
			riseRun[0] = -Math.tan(degree); //y
			riseRun[1] = 1.0; //x
		}else if((degreeDEG > 90.0) && (degreeDEG < 180.0)){
			riseRun[0] = Math.tan(degree); //y
			riseRun[1] = -1.0; //x
		}else if((degreeDEG > 180.0) && (degreeDEG < 270.0)){
			riseRun[0] = Math.tan(degree); //y
			riseRun[1] = -1.0; //x
			
			
		}
		else if((degreeDEG > 270.0) && (degreeDEG < 360.0)){
			riseRun[0] = -Math.tan(degree); //y
			riseRun[1] = 1.0; //x
		
		}
		if(riseRun[0] > 1 ){
			riseRun[1] = riseRun[1] / Math.abs(riseRun[0]);
			riseRun[0] = 1;
		}else if(riseRun[0] < -1){
			riseRun[1] = riseRun[1] / Math.abs(riseRun[0]);
			riseRun[0] = -1;
		}
		*/
		
		
		
	}
	public static int getRandInt(int minVal, int maxVal, int typeRandom){
		int retVal = generator.nextInt(maxVal-minVal+1)+minVal;
		
		switch(typeRandom){
		case 0:
			//flat
			break;
		case 1:
			//50/50 avg
			retVal+=(generator.nextInt(maxVal-minVal+1)+minVal);
			retVal/=2;
			break;
		case 2:
			//60/40 avg
			retVal=(int)((retVal*.6 + (generator.nextInt(maxVal-minVal+1)+minVal)*.4)/2.0);
			break;
		case 3:
			//70/30 avg
			break;
		case 4:
			//80/20 avg
			break;
		case 5:
			// weighted high
			break;
		case 6:
			//weighted low
			break;
		}
		
		
		return retVal;
	}
	/*
	 * inputs a string and makes sure that only the first letter of each word is capitalized
	 */
	public static String normalizeAllCaps(String inAllCapsOrOtherwiseGoofedUp){
		if(inAllCapsOrOtherwiseGoofedUp!=null){
			String retVal=inAllCapsOrOtherwiseGoofedUp.substring(0, 1);
			retVal+=inAllCapsOrOtherwiseGoofedUp.substring(1).toLowerCase();
			return retVal;
		}
		return null;
		
	}
	/*
	 * returns the xy location adjust based on the directions from the num pad
	 */
	public static int[] getLocationAdjustment(int directionNumPad) 
	{
		int[] xyz = new int[3];
		switch(directionNumPad){
		case 1:
			xyz[0] = -1;
			xyz[1] = 1;
			break;
		case 2:
			xyz[0] = 0;
			xyz[1] = 1;
			break;
		case 3:
			xyz[0] = 1;
			xyz[1] = 1;
			break;
		case 4:
			xyz[0] = -1;
			xyz[1] = 0;
			break;
		case 6:
			xyz[0] = 1;
			xyz[1] = 0;
			break;
		case 7:
			xyz[0] = -1;
			xyz[1] = -1;
			break;
		case 8:
			xyz[0] = 0;
			xyz[1] = -1;
			break;
		case 9:
			xyz[0] = 1;
			xyz[1] = -1;
			break;
		}
		xyz[2] = 0;
		return xyz;
	}
	/*
	 * must use the PSF stepping variable, or else will be messed up!
	 */
	public static double[] getSlopeRiseRunFromDegree(double degreeDEG){
		/*
		double degree = degreeDEG*0.0174532925;
		double[] riseRun = new double[2];
		while(degreeDEG < 0){
			degreeDEG += 360.0;
		}
		while(degreeDEG >= 360.0){
			degreeDEG -= 360.0;
		}
		if(degreeDEG == 90.0){
			riseRun[0] = -1.0; //y
			riseRun[1] = 0.0; //x
		}else if(degreeDEG == 270.0){
			riseRun[0] = +1.0; //y
			riseRun[1] = 0.0; //x
			
		}else if(degreeDEG == 180.0){
				riseRun[0] = 0.0; //y
				riseRun[1] = -1.0; //x
		}else if(degreeDEG == 0.0){
			riseRun[0] = 0.0; //y
			riseRun[1] = 1.0; //x
	
		}else if((degreeDEG < 90.0) && (degreeDEG > 0.0)){
			riseRun[0] = -Math.tan(degree); //y
			riseRun[1] = 1.0; //x
		}else if((degreeDEG > 90.0) && (degreeDEG < 180.0)){
			riseRun[0] = Math.tan(degree); //y
			riseRun[1] = -1.0; //x
		}else if((degreeDEG > 180.0) && (degreeDEG < 270.0)){
			riseRun[0] = Math.tan(degree); //y
			riseRun[1] = -1.0; //x
			
			
		}
		else if((degreeDEG > 270.0) && (degreeDEG < 360.0)){
			riseRun[0] = -Math.tan(degree); //y
			riseRun[1] = 1.0; //x
		
		}
		if(riseRun[0] > 1 ){
			riseRun[1] = riseRun[1] / Math.abs(riseRun[0]);
			riseRun[0] = 1;
		}else if(riseRun[0] < -1){
			riseRun[1] = riseRun[1] / Math.abs(riseRun[0]);
			riseRun[0] = -1;
		}
		return riseRun;
		*/
		
		
		while(degreeDEG < 0.0){
			degreeDEG += 360.0;
		}
		while(degreeDEG >= 360.0){
			degreeDEG -= 360.0;
		}
		
		return degreeLookUpTable[(int)(degreeDEG/steppingForDegreeLOS)];
	}
	public static float getDistance(int[] agentLocation, int[] lastCoordsXYZ) {
		float retVal =  (float)Math.sqrt((Math.pow((agentLocation[0]-lastCoordsXYZ[0]),2) +  Math.pow((agentLocation[1]-lastCoordsXYZ[1]),2)));
		
		return retVal;
	}
                       
	public  static LinkedList addArrayToThisList(LinkedList listToAddTo, Object[] array){
		if(listToAddTo == null)
			listToAddTo = new LinkedList();
		
		for(int i = 0; i < array.length; i++){
			listToAddTo.add(array[i]);
		}
		
		return listToAddTo;
	}
	/*
	 * inputs a source and destination (2d), returns a 3 cubby int, with the first two being rise, run, and then the distance that this will be when incremented. Rise/run must not be greater than 1
	 */
	public static double[] getSlopeAndDistance(double[] source, double[] destination) {
		
		double[] retVal = new double[3];
		retVal[0] = destination[0] - source[0];
		retVal[1] = destination[1] - source[1];
		if(retVal[0] == 0 && retVal[1] == 0)
			System.out.println();
		
		//extract the sign(s)
		int intSign[] = new int[]{1,1};
		
		if(retVal[0] < 0){
			intSign[0] = -1;
			retVal[0] *= -1;
		}
		if(retVal[1] < 0){
			intSign[1] = -1;
			retVal[1] *= -1;
		}
		
		// now to make sure that neither is  > 1
		if(retVal[0] > 1){
			
			retVal[1] /= retVal[0];
			retVal[0] = 1;
		}if(retVal[1] > 1){
			
			retVal[0] /= retVal[1];
			retVal[1] = 1;
		}
		
		//if(retVal[0] > 1.0 && retVal[1] == 1 || retVal[1] > 1 && retVal[0] == 1)
			//System.out.println("snapper!");
		
		//putting the signs back
		retVal[0] *= intSign[0];
		retVal[1] *= intSign[1];
		retVal[2] = (float) Math.sqrt((retVal[0]*retVal[0]) + (retVal[1]*retVal[1]));
		//if(retVal[0] > 0 && retVal[1] > 0)
			//System.out.println();
		return retVal;
	}

	
	
}
