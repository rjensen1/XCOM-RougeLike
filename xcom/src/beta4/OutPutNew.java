package beta4;



import javax.swing.*; // For JPanel, etc.

import java.awt.*;           // For Graphics, etc.
import java.awt.event.KeyListener;




@SuppressWarnings("serial")
public class OutPutNew extends JPanel{
		
	private JFrame frame;//holds the frame this object is a part of
		
	private DisplayColorChars[][] characterArray; //will hold the array for the characters to display
	private short xDim;
	private short yDim;
	private short cellDimX;
	private short cellDimY;
	private Font font;
	/*
	 * Creates the output Object, sets its variables to starting states
	 */
	public OutPutNew(short xDimIn, short yDimIn, short cellX, short cellY, String title){
		if(xDimIn > 0 && yDimIn > 0 && cellX > 0 && cellY > 0){
			font = new Font("Monaco", Font.BOLD, (int)Math.min(cellY*.6, cellX*.6));
			xDim = xDimIn;
			yDim = yDimIn;
			cellDimX = cellX;
			cellDimY = cellY;
			frame = WindowUtilities.openInJFrame(this, xDim * cellX + 20, yDim*cellY + 50, title);
			characterArray = new DisplayColorChars[xDim][yDim];
			initCharArray();
		}
	}
	/*
	 * sets a char with a new char (could be passed from an object?)
	 */
	public boolean setCharAt(short x, short y, DisplayColorChars newOne){
		if(newOne !=null && x > -1 && x < xDim && y > -1 && y < yDim){
			characterArray[x][y] = newOne;
			return true;
		}
		return false;
	}
	/*
	 * gets the char here, for some reason?
	 */
	public DisplayColorChars getColorCharAt(short x, short y){
		if(x > -1 && x < xDim && y > -1 && y < yDim){
			return characterArray[x][y];
			
		}
		return null;
	}
	
	
	
	/*
	 * This method is used by the frame when it wants to draw its component
	 * inputs a graphics object, to which the lineSegments are drawn one-at-a-time
	 * 
	 */
	public void paintComponent(Graphics g){
    
		Graphics2D g2d = (Graphics2D)g;
		g.setFont(font);
		
		if(characterArray != null){
			for(int x = 0; x < characterArray.length; x ++){
				for(int y = 0; y < characterArray[x].length; y ++){
						//first the background
					g2d.setColor(characterArray[x][y].getColorBack());
					g2d.fillRect(x*cellDimX, y*cellDimY, cellDimX, cellDimY);
					g2d.setColor(characterArray[x][y].getColorText()) ;
					g2d.drawChars(new char[]{characterArray[x][y].getThisChar()}, 0, 1, (int)(x*cellDimX+cellDimX*.15), (int)(y*cellDimY+(int)cellDimY*.65));
					
					//g2d.setColor()
					
				}
			}
		}
		
    		//getting an iterator and iterating through the end of the list, drawing each line
    /*if(theLines!=null){
    	Iterator<Line2D.Double> iterate =  theLines.iterator();
	    while(iterate.hasNext())
	    	g2d.draw(iterate.next());
    }
    else
    	g2d.draw(null);*/  
	}
	
	/*
	 * Inputs a Keylistener and attaches it to the frame
	 * 
	 */
	public void addKeyListener(KeyListener newListener){
		
		if(newListener!=null)
		  frame.addKeyListener(newListener);
	}
	/*
	 * called when the screen needs to be cleared. empties out the linked list and then repaints the frame
	 * also, moves the 'cursor' back to the start
	 */
	public void clear() {
		initCharArray();
		
	}
	/*
	 * Used to simply refresh the screen. Forces it to repaint everything
	 */
	public void drawGraphics(){
		
		super.repaint();
	}
	/*
	 * sets all the characters to black on black
	 */
	private void initCharArray() {
		
		for(int y = 0; y < yDim; y ++){
			for(int x = 0; x < xDim; x ++){
				characterArray[x][y] = new DisplayColorChars('.', Color.BLACK, Color.BLACK);
				//characterArray[x][y] = new ColorChars('@', ColorChars.getRandomColor(500), ColorChars.getRandomColor(90000));
			}
		}
		
	}
	public boolean setColorCharsArray(DisplayColorChars[][] charArrayToDisplay) {
		
		if(charArrayToDisplay != null){
			characterArray = charArrayToDisplay;
			return true;
		}
		return false;
	}
		
	
}
