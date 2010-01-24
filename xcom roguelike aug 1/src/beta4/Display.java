package beta4;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/*
 * is sent char arrays to display from tacticalenvironment, etc.
 */
public class Display {
	
		public Display(){
			
		
	}
		/*
		 * create the display object with its bounds
		 */
	public Display(int x, int y){

		if( x>0 && y >0){
			
			connectedScreenHUD = new OutPutOld(x,y, "HUD");
			connectedScreenMessages = new OutPutOld(x,y, "Error + Messages");
			connectedScreenTact = new OutPutNew((short)20, (short)20, (short)Tools.sizeCellXTact, (short)Tools.sizeCellYTact, "X-Com Tactical");
			makeKeyListener();
			connectedScreenHUD.addKeyListener(listener);
			connectedScreenTact.addKeyListener(listener);
			connectedScreenMessages.addKeyListener(listener);
		}	
	}
	/*
	 * Should send this method a string to display as an error or message
	 */
	public void outPutMessageErrors(String errorMessageText){
		connectedScreenMessages.displayThisText(errorMessageText);
	}
	/*
	 * should call the display HUD method of the current unit (or statically, if it automatically swaps when a new unit is selected
	 */
	public void outputHUD(int HUDType){
		
		connectedScreenHUD.displayThisText(HUD.displayHUD(HUDType));
	}
	/*
	 * take an charArray and parse it, send it to the swing window, would need to change it for panning around, perhaps
	 */
	public void outputTact(DisplayColorChars[][] charArrayToDisplay){
		if(charArrayToDisplay == null)
			System.out.println("|||||||||||||||");
		connectedScreenTact.setColorCharsArray(charArrayToDisplay);
		connectedScreenTact.repaint();
				
			
	}
	/*
	 * creates/sets up a keylistener
	 */
	private static void makeKeyListener(){
		listener = new KeyListener(){
		      public void keyPressed(KeyEvent e) {
		    	  //send the key typed to the method that figures out what to do with it.
		    	  InputManager.incomingCommand(e.getKeyChar());
		      }
		  	
			  public void keyReleased(KeyEvent e) {}
			      
			  public void keyTyped(KeyEvent e) {
			     
			  }
		};
	}
	
	private static KeyListener listener;
	private OutPutOld connectedScreenHUD;
	private OutPutNew connectedScreenTact;
	private OutPutOld connectedScreenMessages;
	public void clearErrorsAndMessages() {
		// TODO Auto-generated method stub
		
	}
	
	
}

