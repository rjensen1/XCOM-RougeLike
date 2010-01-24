package beta4;


import javax.swing.*;
import java.awt.*;


/*
 * Russ jensen Project 1 COSC 321
 * this class constructs the JFrame window and was taken from the following website:
 * 
* A few utilities that simplify testing of windows in Swing.
 *  1998 Marty Hall, http://www.apl.jhu.edu/~hall/java/Java2D-Tutorial.html
 */
public class WindowUtilities{

	  /** Tell system to use native look and feel, as in previous
	   *  releases. Metal (Java) LAF is the default otherwise.
	   */

	public static void setNativeLookAndFeel(){
	    try {
	    	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    }catch(Exception e) {
	    	System.out.println("Error setting native LAF: " + e);
	    }
	}

	  /** A simplified way to see a JPanel or other Container.
	   *  Pops up a JFrame with specified Container as the content pane.
	   */

	public static JFrame openInJFrame(Container contentTop, int width, int height,String title,Color bgColor){
		  
		  JFrame frame = new JFrame(title);
		  frame.setBackground(bgColor);
		  contentTop.setBackground(bgColor);
		  
		  frame.setSize(width, height);
		  frame.setContentPane(contentTop);
		 
		  frame.addWindowListener(new ExitListener());
		  frame.setVisible(true);
		  return(frame);
	}

	  /** Uses Color.white as the background color. */

	public static JFrame openInJFrame(Container contentTop,int width,int height, String title){
		  
		return(openInJFrame(contentTop, width, height, title, Color.white));
	}
}