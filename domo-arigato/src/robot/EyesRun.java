package robot;

import lejos.nxt.ColorSensor.Color;
import lejos.util.Delay;
import actions.RunnableRobot;

public class EyesRun extends RunnableRobot {
	ColorEyes eyes;
	Color color;
	
	public EyesRun(ColorEyes eyes) {
		this.eyes = eyes;
	}
	
	@Override
	public void run() {
		while(!getInterrupted()) {
			Delay.msDelay(10);
			color = eyes.getColor();
		}
	}

}
