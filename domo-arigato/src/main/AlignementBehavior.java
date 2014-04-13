package main;

import lejos.robotics.navigation.Pose;
import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;

public class AlignementBehavior extends EventListener {
	int state = 0;
	Pose pose;
	float offset = 0.0f;

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case WHITE_DETECTED :
			ignore();
			break;
		case BLACK_DETECTED :
			if(state == 1) {
				state = 2;
			}
			else if(state == 5) {
				state = 6;
			}
			else {
				ignore();
			}
			break;
		case GOFORWARD_END :
			if(state == 3) {
				state = 4;
			}
			else
				ignore();
			break;
		default:
			ignore();
			break;
		}
	}

	public void act() {
		if(state == 0) {
			Robot.getInstance().getMotion().getPilot().setRotateSpeed(90);
			state = 1;
			ActionFactory.goForward(10000, true);
		}
		else if(state == 2) {
			pose = Robot.getInstance().getOdometryPoseProvider().getPose();
			ActionFactory.stopMotion(false);
			Pose nouvellePose = Robot.getInstance().getOdometryPoseProvider().getPose();
			offset = nouvellePose.distanceTo(pose.getLocation());
			ActionFactory.goForward(8.5f - offset, true);
			state = 3;
		}
		else if(state == 4) {
			ActionFactory.rotate(180, true);
			state = 5;
		}
		else if(state == 6) {
			ActionFactory.stopMotion(false);
			stop();
		}
	}

}
