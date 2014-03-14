package robot;

import actions.RunnableRobot;
import lejos.nxt.ColorSensor;
import lejos.nxt.ColorSensor.Color;
import lejos.nxt.SensorPort;

public class ColorEyes {
	private ColorSensor cs;
	RunnableRobot thread;
	
	ColorEyes() {
		thread = null;
	}
	
	void initEyes(SensorPort eyes) {
		cs = new ColorSensor(eyes);
		thread = new EyesRun(this);
		Thread t = new Thread(thread);
		t.start();
	}
	
	public Color getColor() {
		return cs.getColor();
	}
	
	public void closeEyes() {
		if(thread != null)
			thread.interrupt();
	}
}
