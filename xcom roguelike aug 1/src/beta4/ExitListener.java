package beta4;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/* 
  * Russ Jensen E00874319
 * project 1 for COSC 321
 * March 10 2009 
 * 
 * This code was taken from the following:
 * A few utilities that simplify testing of windows in Swing.
 *  1998 Marty Hall, http://www.apl.jhu.edu/~hall/java/Java2D-Tutorial.html
 */

public class ExitListener extends WindowAdapter  {
	
	  	  public void windowClosing(WindowEvent event) {
			    System.exit(0);
		  }
}