package beta4;



import javax.swing.*; // For JPanel, etc.

import java.awt.*;           // For Graphics, etc.
import java.awt.event.KeyListener;


import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;




@SuppressWarnings("serial")
public class OutPutOld extends JPanel{
	private int x;
	private int y;
	//private JTextPane textPaneTactical;
	private JTextPane textPane;
	
	
	
	
	/*
	 * Creates the output Object, sets its variables to starting states
	 */
	public OutPutOld(int y, int x, String name){
		this.x = x;
		this.y = y;
		initScreen(name);
	}
	/*
	 * 
	 */

	/*
	 * Inputs a Keylistener and attaches it to the frame
	 * 
	 */
	public void addKeyListener(KeyListener newListener){
		
		if(newListener!=null){
			//textPaneTactical.addKeyListener(newListener);
			textPane.addKeyListener(newListener);
			
		}
	}
	/*
	 * called when the screen needs to be cleared. empties out the linked list and then repaints the frame
	 * also, moves the 'cursor' back to the start
	 */
	/*private void clear() {
		textPaneTactical.setText("");
	}*/
	
	/*
	 * Used once, to instantiate the window.
	 */
	private void initScreen(String name){
		/*textPaneTactical = new JTextPane();
		textPaneTactical.setEditable(false);
	    Font fontTact = new Font("Monospaced", Font.PLAIN, 16);
	    textPaneTactical.setFont(fontTact);*/
	    
	    textPane = new JTextPane();
	    textPane.setEditable(false);
	    Font font = new Font("Monospaced", Font.PLAIN, 16);
	    textPane.setFont(font);
	   // textPaneHUD.setSize(columns*10,8);
	    
	    //textPaneMessagesErrors = new JTextArea();
	    //textPaneMessagesErrors.setEditable(false);
	   // textPaneMessagesErrors.setSize(columns*10,30);
	    
	    //textPaneMessagesErrors.setFont(fontHUD);
	    
	    WindowUtilities.openInJFrame(textPane, x*7, y*12, name);
		// HUD.setSize( );
		 
		 
		 //JFrame Err = WindowUtilities.openInJFrame(new JScrollPane(textPaneMessagesErrors), x*11, y*12,"Messages and Errors");
		 textPane.setAutoscrolls(true);
		 //Err.setLocation(x*7, y*8);
		 
		//JFrame tactical = WindowUtilities.openInJFrame(textPaneTactical, x*6, y*8,"Xcom RL Tactical");
		 setVisible(true);
		 //tactical.setLocation(x*7, 0);
		
	    
	}
	/*
	 * send this method a String to display it to the screen.
	 *//*
	public void displayThisTact(String toDisplay){
		if(toDisplay!=null){
			//clear();
			textPaneTactical.setText(toDisplay);
		}
	}*/
	
	/*public void displayThisTact(ColorChars[][] incoming){
		if(incoming != null){
			textPaneTactical.
		}
	}*/

	public void displayThisText(String toDisplay) {
		
		if(toDisplay!=null){
			//clear();
			textPane.setText(toDisplay);
			//textPane.repaint();
		}
	}

	/*public void displayThisErrMess(String toDisplay) {
		if(toDisplay!=null){
			//clear();
			textPaneMessagesErrors.setText(toDisplay);
		}
	}*/
 
	
}
