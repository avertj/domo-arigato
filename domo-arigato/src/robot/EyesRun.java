package robot;

import lejos.util.Delay;
import actions.Event;
import actions.RunnableRobot;
import actions.TypeEvent;

public class EyesRun extends RunnableRobot {
	ColorEyes eyes;
	lejos.robotics.Color color;
	int lightValue;
	TypeEvent prev;
	int BLACK_WHITE_DIFFERENCE = 45;
	
	public EyesRun(ColorEyes eyes) {
		this.eyes = eyes;
	}
	
	@Override
	public void run() {
		while(!getInterrupted()) {
			Delay.msDelay(5);
			//color = eyes.getColor();
			lightValue = eyes.getLightValue();
			TypeEvent curr;
			// < 50 = noir
			if(lightValue < BLACK_WHITE_DIFFERENCE) {
				curr = TypeEvent.BLACK_DETECTED;
			} else {
				curr = TypeEvent.WHITE_DETECTED;
			}
			
			if(prev == null || prev != curr) {
				Robot.getInstance().warn(new Event(curr));
				prev = curr;
			}
		}
			//System.out.println("light value : " + lightValue);
			//Robot.getInstance().warn(new Event(TypeEvent.));
	}
}
