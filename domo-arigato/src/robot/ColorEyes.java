package robot;

import actions.RunnableRobot;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorHTSensor;

public class ColorEyes {
	public ColorHTSensor cs;
	RunnableRobot thread;
	
	ColorEyes() {
		thread = null;
	}
	
	void initEyes(SensorPort eyes) {
		cs = new ColorHTSensor(eyes);if(cs.initWhiteBalance() == 0)System.out.println("cool");else System.out.println("pas cool");
		thread = new EyesRun(this);
		Thread t = new Thread(thread);
		t.start();
	}
	
	public lejos.robotics.Color getColor() {
		return cs.getColor();
	}
	
	public void closeEyes() {
		if(thread != null)
			thread.interrupt();
	}
}
