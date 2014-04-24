package utils;

import lejos.nxt.ColorSensor.Color;

public class Utils {
	private static final int RED_THRESHOLD = 200;
	private static final int GREEN_THRESHOLD = 200;
	private static final int BLUE_THRESHOLD = 200;
	private static final int BLACK_THRESHOLD = 30;
	private static final int WHITE_THRESHOLD = 600;
	
	/**
	 * We didn't used this method, we don't have a ColorSensor. Change it with your Color Threshold to make it work.
	 * @param color
	 * @return
	 */
	public static String getColorName(Color color) {
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		int sum = r + g + b;
		if(sum < BLACK_THRESHOLD)
			return "Black";
		if(sum > WHITE_THRESHOLD)
			return "White";
		if(r > RED_THRESHOLD) {
			if(g > GREEN_THRESHOLD)
				return "Yellow";
			return "Red";
		}
		if(g > GREEN_THRESHOLD)
			return "Green";
		if(b > BLUE_THRESHOLD)
			return "Blue";
		return "Noise";
	}
}
