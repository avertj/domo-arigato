package robot;

import lejos.nxt.ColorSensor;
import lejos.nxt.ColorSensor.Color;
import lejos.nxt.SensorPort;

public class ColorEyes {
	private ColorSensor cs;
	
	ColorEyes() {
	}
	
	void initEyes(SensorPort eyes) {
		cs = new ColorSensor(eyes);
	}
	
	public Color getColor() {
		return cs.getColor();
	}
}
