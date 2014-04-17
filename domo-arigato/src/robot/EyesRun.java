package robot;

import lejos.nxt.SensorPort;
import lejos.util.Delay;
import actions.Event;
import actions.RunnableRobot;
import actions.TypeEvent;

public class EyesRun extends RunnableRobot {
	ColorEyes eyes;
	SensorPort port;
	lejos.robotics.Color color;
	int lightValue;
	int WHITE = 430;
	int LOW_COLORS = 480;
	int HIGH_COLORS = 600;
	int prev;
	int state;
	
	public EyesRun(ColorEyes eyes, SensorPort port) {
		prev = -1;
		this.port = port;
		this.eyes = eyes;
	}
	
	@Override
	public void run() {
		while(!getInterrupted()) {
			Delay.msDelay(5);
			//color = eyes.getColor();
			lightValue = port.readRawValue();
			// < 50 = noir
			if(lightValue < WHITE) {
				state = 0;
			} else if(lightValue < LOW_COLORS){
				state = 1;
			} else if(lightValue > HIGH_COLORS){
				state = 2;
			} else {
				state = 3;
			}
			
			if(prev == -1 || prev != state) {
				Event e;
				switch(state)
				{
				case 0 :
					e = new Event(TypeEvent.WHITE_DETECTED);
					break;
				case 1 :
					e = new Event(TypeEvent.COLOR_DETECTED, "Low");
					break;
				case 2 :
					e = new Event(TypeEvent.COLOR_DETECTED, "High");
					break;
				default :
					e = new Event(TypeEvent.NOISE_DETECTED);
					break;
				}
				Robot.getInstance().warn(e);
				prev = state;
			}
		}
			//System.out.println("light value : " + lightValue);
			//Robot.getInstance().warn(new Event(TypeEvent.));
	}
}
