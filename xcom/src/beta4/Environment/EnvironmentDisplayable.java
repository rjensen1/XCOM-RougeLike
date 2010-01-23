package beta4.Environment;

import beta4.Display;
import beta4.DisplayColorChars;

public class EnvironmentDisplayable extends Environment{

	

	protected Display displayToUse;
	
	protected int viewPortXDim;
	protected int viewPortYDim;
	protected int viewPortXFocus;
	protected int viewPortYFocus;
	protected int viewPortZFocus;
	
	protected DisplayColorChars[][] tacticalColorCharsViewportRepresentsYX;
	
	
	public EnvironmentDisplayable(int xDimNew, int yDimNew, int zDimNew, int viewPortX, int viewPortY,  Display toUse)
	{
		super(xDimNew, yDimNew, zDimNew);
		
		
		
		if(viewPortX > xDim || viewPortY > yDim )
			System.out.println("Error, viewport is larger than the environment");
		else{
			viewPortYDim = viewPortY;
			viewPortXDim = viewPortX;
				//viewport must be odd number
			if(viewPortYDim % 2 ==0)
				viewPortYDim -= 1;
			if(viewPortXDim % 2 ==0)
				viewPortXDim -= 1;
			
			setDisplay(toUse);
		}
	}
		
	public int getViewPortZFocus() {
		
		return viewPortZFocus;
	}
	
	/*
	 * sets the display
	 */
	private void setDisplay(Display toUse){
		displayToUse = toUse;
	}
	
	/*
	 * inputs a xy coordinate adjustment and shifts the viewport in this direction, if possible, followed by Z adjustment
	 */
	public void scroll(int[] locationAdjustment, int zAdjust) {
		
		//System.out.println("scroll");
			//change the focus if it's valid
		centerScreen(new int[]{viewPortXFocus + locationAdjustment[0], viewPortYFocus + locationAdjustment[1], viewPortZFocus += zAdjust});
	}
	/*
	 * attempts to center the screen around xyz point
	 */
	public void centerScreen(int[] xyz){
			//if the z dim is out of bounds, do nothing!
		if(!(xyz[2] < 0 || xyz[2] >= zDim)){
		
		int minXPos = viewPortXDim/2;
		int maxXPos = xDim - (viewPortXDim/2 + 1);
		int minYPos = viewPortYDim/2;
		int maxYPos = yDim - (viewPortYDim/2 + 1);
		
		if(xyz[0] < minXPos)
			xyz[0] = minXPos;
		if(xyz[0] > maxXPos)
			xyz[0] = maxXPos;
		if(xyz[1] < minYPos)
			xyz[1] = minYPos;
		if(xyz[1] > maxYPos)
			xyz[1] = maxYPos;
		
		viewPortXFocus = xyz[0];
		viewPortYFocus = xyz[1];
		viewPortZFocus = xyz[2];
		
		}
	}
	
}

	
	
	
	
	 


