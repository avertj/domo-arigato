package robot;

import lejos.util.Delay;
import actions.RunnableRobot;

public class EyesRun extends RunnableRobot {
	ColorEyes eyes;
	lejos.robotics.Color color;
	
	public EyesRun(ColorEyes eyes) {
		this.eyes = eyes;
	}
	
	@Override
	public void run() {
		while(!getInterrupted()) {
			Delay.msDelay(50);
			color = eyes.getColor();
		}
	}

}
