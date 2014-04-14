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
	int MAX_BLACK = 40;
	int MIN_WHITE = 50;
	
	public EyesRun(ColorEyes eyes) {
		this.eyes = eyes;
	}
	
	@Override
	public void run() {
		while(!getInterrupted()) {
			Delay.msDelay(5);
			//color = eyes.getColor();
			lightValue = eyes.getLightValue();
			TypeEvent curr = null;
			// < 50 = noir
			if(lightValue < MAX_BLACK) {
				curr = TypeEvent.BLACK_DETECTED;
			} else if(lightValue > MIN_WHITE){
				curr = TypeEvent.WHITE_DETECTED;
			}
			
			if(curr != null) {
				if(prev == null || prev != curr) {
					Robot.getInstance().warn(new Event(curr));
					prev = curr;
				}
			}
		}
			//System.out.println("light value : " + lightValue);
			//Robot.getInstance().warn(new Event(TypeEvent.));
	}
}
