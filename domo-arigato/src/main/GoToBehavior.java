package main;

import lejos.robotics.navigation.Pose;
import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;

public class GoToBehavior extends EventListener {
	private Pose goal;
	private int state = 0;
	
	public GoToBehavior(Pose goal) {
		this.goal = goal;
	}

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case ROBOT_DETECTED :
			if(state == 1)
				state = -1;
			else
				ignore();
			break;
		case ROTATE_END :
			if(state == 0)
				state = 1;
			else if(state == 2)
				state = 3;
			else
				ignore();
			break;
		case GOFORWARD_END :
			if(state == 1)
				state = 2;
			else
				ignore();
			break;
		case CHILDBEHAVIOR_END :
			if(state == -1)
				state = 0;
			else
				ignore();
			break;
		default:
			ignore();
			break;
		}
	}

	protected void act() {
		if(state == -1) {
			doBehavior(new DodgeBehavior());
		}
		else if(state == 0) {
			float rotation = Robot.getInstance().getOdometryPoseProvider().getPose().relativeBearing(goal.getLocation())%360;
			if(rotation > 180) {
				rotation = -(360 - rotation);
			}System.out.println(rotation);
			ActionFactory.rotate(rotation, true);
		}
		else if(state == 1) {
			ActionFactory.goForward(Robot.getInstance().getOdometryPoseProvider().getPose().distanceTo(goal.getLocation()), true);
		}
		else if(state == 2) {
			float rotation = (goal.getHeading() - Robot.getInstance().getOdometryPoseProvider().getPose().getHeading())%360;
			if(rotation > 180) {
				rotation = -(360 - rotation);
			}
			ActionFactory.rotate(rotation, true);
		}
		else if(state == 3) {
			stop();
		}
	}
	
}
