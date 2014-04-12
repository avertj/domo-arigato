package robot;

import lejos.util.Delay;
import actions.Event;
import actions.RunnableRobot;
import actions.TypeEvent;

public class EyesRun extends RunnableRobot {
	ColorEyes eyes;
	lejos.robotics.Color color;
	int lightValue;
	
	public EyesRun(ColorEyes eyes) {
		this.eyes = eyes;
	}
	
	@Override
	public void run() {
		while(!getInterrupted()) {
			Delay.msDelay(50);
			//color = eyes.getColor();
			lightValue = eyes.getLightValue();
			
			System.out.println("light vzlue : " + lightValue);
			//Robot.getInstance().warn(new Event(TypeEvent.));
		}
	}

}
