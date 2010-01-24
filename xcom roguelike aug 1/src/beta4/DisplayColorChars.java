package beta4;



import java.awt.Color;

public class DisplayColorChars {
	
	/*
	 * what about safety pallette?
	 * 
	 * Safety palette

The safety palette is used to ensure colors are "solid" (not "dithered") in video cards and monitors capable of just 256 colors. If you want your application to look well on these devices, you should use the safety palette.

The safety palette is made up of 216 colors that result from all the possible combinations of the values 0, 51, 102, 153, 204, and 255 (or $00, $33, $66, $99, $CC, and $FF in hex notation) for the red, green and blue components. For example, the color RGB(204,153,102) or $6699CC is one of the safe colors.
	 */
	private char[] thisOne;
	private Color text;
	private Color back;
	
	//some preset colors
	public static final Color Dark_Brown = new Color(139,69,19);
	public static final Color Dark_Gray = new Color(49, 79, 79);
	public static final Color Dark_Blue = new Color(25, 25, 112);
	public static final Color Dark_Green = new Color(0, 100, 0);
	public static final Color Dark_Khaki = new Color(189, 183, 107);
	public static final Color Dark_Goldenrod = new Color(184, 134, 11);
	public static final Color Dark_Olive_Green = new Color(85-107-47);
	public static final Color Olive_Drab = new Color(107, 142, 35);
	public static final Color Light_Brown = new Color(205, 133, 63);
	public static final Color Light_Goldenrod = new Color(238, 221, 130);
	public static final Color Light_Salmon = new Color(255, 160, 122);
	
	public static final Color Light_Gray = new Color(238, 233, 233);
	public static final Color Light_Sea_Green = new Color(32, 178, 170);
	public static final Color Pale_Blue = new Color(175, 238, 238);
	public static final Color Offwhite = new Color(250, 235, 215);
	public static final Color Fired_Brick = new Color(178, 34, 34);
	public static final Color Orange_Red = new Color(255, 69, 0);
	
	public static final Color Maroon = new Color(176, 48, 96);
	
	
	public DisplayColorChars(char initialChar, Color text, Color backGround){
		thisOne = new char[1];
		thisOne[0] = initialChar;
		this.text = text;
		this.back = backGround;
		
		
	}
	/*
	 * must be between 0 and 255, returns true if all vals within this range
	 */
	public boolean setTextColor(Color colorIn){
		if(colorIn != null){
			text = colorIn;
			return true;
		}
		return false;
	}
	public boolean setRepChar(char[] charIn){
		if(charIn != null){
			thisOne = charIn;	
			return true;
			
		}
		return false;
	}
	public boolean setBackColor(Color colorIn){
		if(colorIn != null){
			back = colorIn;
			return true;
		}
		return false;
	}
	public Color getColorBack(){
		return back;
	}
	public Color getColorText(){
		return text;
	}
	public char[] getThisChar(){
		return thisOne;
	}
	/*
	 * gets a random color
	 */
	public static Color getRandomColor(int max){
		return new Color((int)Math.abs(Math.random()*255), (int)Math.abs(Math.random()*255), (int)Math.abs(Math.random()*255));
	}
	/*
	 * invert this color, and if boolean = true, then any of the channels are too close to the middle, change them
	 */
	public static Color invertThisColor(Color in, boolean changeIfTooGray){
		if(in != null){
			
			int newRed = 255 - in.getRed();
			int newGreen = 255 - in.getGreen();
			int newBlue = 255 - in.getBlue();
			
			//if any of these are too close to the midpoint
			if(newRed > 110 && newRed < 150)
				newRed /= 2;
			if(newGreen > 110 && newGreen < 150)
				newGreen /= 2;
			if(newBlue > 110 && newBlue < 150)
				newBlue /= 2;
			in = new Color(newRed, newGreen, newBlue);
		}
		return in;
	}
	public static Color darkenThisColorByPercent(Color in, double percent){
		if(in != null){
			
			if(percent > 1)
				percent = 1.0;
			
			int newRed = (int)(in.getRed()*percent);
			int newGreen = (int)(in.getGreen()*percent);
			int newBlue = (int)(in.getBlue()*percent);
			if(newRed < 0)
				newRed = 0;
			if(newGreen < 0)
				newGreen = 0;
			if(newBlue < 0)
				newBlue = 0;
			in = new Color(newRed, newGreen, newBlue);
		}
		return in;
	}
	/*
	 * inputs two colors. if any of the rgb channels are within 10 ints of the other, they will be adjust 10 apart and then returned.
	 * the valued returned is to replace the 'first' color
	 */
	public static Color returnAColorForFirstColorThatIsMoreDifferentThanSecondColorIfTheyAreSimilar(Color first, Color second){
		Color retVal = first;
		
		int greenFirst = first.getGreen();
		int redFirst = first.getRed();
		int blueFirst = first.getBlue();
		
		int redSecond = second.getRed();
		int greenSecond = second.getGreen();
		int blueSecond = second.getBlue();
			if(Math.abs(greenFirst - greenSecond) < 10){
				if(greenFirst > greenSecond)
					greenFirst += 10;
				else
					greenFirst -= 10;
			}
			if(Math.abs(redFirst - redSecond) < 10){
				if(redFirst > redSecond)
					redFirst += 10;
				else
					redFirst -= 10;
			}
			if(Math.abs(blueFirst - blueSecond) < 10){
				if(blueFirst > blueSecond)
					blueFirst += 10;
				else
					blueFirst -= 10;
			}
			retVal = new Color(redFirst, greenFirst, blueFirst);
		
		
		return retVal;
	}
	//TODO have various static methods in here to do different kinds of color manipulations (dark/light, saturate, etc)

}
