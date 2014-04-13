package tests;

import lejos.robotics.navigation.Pose;
import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;

public class Test3Thread extends EventListener {
	int state = 0;
	boolean robotMoving = false;
	boolean debut = true;
	boolean end = false;

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case SHUTDOWN :
			stop();
			break;
		case ROTATE_END :
			state = 1 - state;
			robotMoving = false;
		default:
			break;
		}
	}

	public void act() {
		if(debut) {
			debut = false;
	    	Pose p = new Pose(0, 0, 0);
	    	Robot.getInstance().getOdometryPoseProvider().setPose(p);
			robotMoving = true;
			ActionFactory.rotate(45, true);
		}
		else if(state == 10) {
			end = true;
		}
		else if(!robotMoving) {
			Pose p = null;String s=null;
			switch(state) {
			case 0 :
				ActionFactory.rotate(45, true);
				break;
			case 1 :
				ActionFactory.rotate(-45, true);
				break;
			}
			robotMoving = true;
		}
	}
}
