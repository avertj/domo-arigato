package main;

import lejos.robotics.navigation.Pose;
import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;

public class CatchPluck extends EventListener {
	private int state = 0;
	private double initRotateSpeed;
	private Pose initPose;
	private String str = "Nothing";

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case ROTATE_END :
			if(state == 0)
				state = 2;
			else
				ignore();
			break;
		case POSSIBLE_PLUCK_DETECTED :
			if(state == 0)
				state = 1;
			else
				ignore();
			break;
		case BUMP :
			state = 2;
			str = "BUMP";
			break;
		case WALL_DETECTED :
		case ROBOT_DETECTED :
		case GOFORWARD_END :
			if(state == 1)
				state = 2;
			else
				ignore();
			break;
		default:
			ignore();
			break;
		}
	}

	protected void act() {
		if(state == 0) {
			initPose = Robot.getInstance().getOdometryPoseProvider().getPose();
			initRotateSpeed = Robot.getInstance().getMotion().getPilot().getRotateSpeed();
			Robot.getInstance().getMotion().getPilot().setRotateSpeed(30);
			ActionFactory.rotate(360, true);
		}
		else if(state == 1) {
			ActionFactory.rotate(-7, false);
			ActionFactory.goForward(Robot.getInstance().getSonar().getMinDist() + 5, true);
		}
		else if(state == 2) {
			Robot.getInstance().getMotion().getPilot().setRotateSpeed(initRotateSpeed);
			stop(str);
		}
	}
}
