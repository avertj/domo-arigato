package robot;

import utils.Geometry;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.Pose;
import lejos.util.Delay;
import actions.Event;
import actions.RunnableRobot;
import actions.TypeEvent;

public class EyesRun extends RunnableRobot {
	ColorEyes eyes;
	SensorPort port;
	lejos.robotics.Color color;
	int lightValue;
	int WHITE = 445;
	public static final int LOW_COLORS = 520;
	public static final int HIGH_COLORS = 595;
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
				Pose myPose = Robot.getInstance().getOdometryPoseProvider().getPose();
				String c;
				float[] distance;
				Event e;
				switch(state)
				{
				case 0 :
					e = new Event(TypeEvent.WHITE_DETECTED);
					Geometry.adjustYWhite();
					break;
				case 1 :
					distance = new float[2];
					distance[0] = Math.abs(myPose.getX() - 50);
					distance[1] = Math.abs(myPose.getX() + 50);
					if(distance[0] < distance[1])
						c = "Red";
					else
						c = "Yellow";
					e = new Event(TypeEvent.COLOR_DETECTED, c);
					;//Geometry.adjustX();
					break;
				case 2 :
					distance = new float[4];
					distance[0] = Math.abs(myPose.getX());
					distance[1] = Math.abs(myPose.getY());
					distance[2] = Math.abs(myPose.getY() - 60);
					distance[3] = Math.abs(myPose.getY() + 60);
					float min = distance[0];
					int i = 0;
					c = "BlackX";
					for(i = 1; i < 4; i++) {
						if(distance[i] < min) {
							min = distance[i];
							switch(i) {
							case 1 :
								c = "BlackY";
								break;
							case 2 :
								c = "Blue";
								break;
							case 3 :
								c = "Green";
								break;
							}
						}
					}
					if(c.equals("BlackX"))
						;//Geometry.adjustX();
					else
						Geometry.adjustY();
					e = new Event(TypeEvent.COLOR_DETECTED, c);
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
