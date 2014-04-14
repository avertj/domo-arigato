package tests;

import lejos.robotics.navigation.Pose;
import main.ScoreBehavior;
import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;

public class TestScore extends EventListener {
	int state = 0;

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case BUMP :
			if(state == 0) {
				state = 1;
			}
			else
				ignore();
			break;
		case CHILDBEHAVIOR_END :
			if(state == 1) {
				state = 2;
			}
			else
				ignore();
			break;
		case ROTATE_END :
			if(state == 2) {
				state = 3;
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
			Robot.getInstance().getOdometryPoseProvider().setPose(new Pose(100, 0, 180));
			ActionFactory.goForward(10000, true);
		}
		else if(state == 1) {
			ActionFactory.useClaws(0.0f, true);
			doBehavior(new ScoreBehavior());
		}
		else if(state == 2) {
			ActionFactory.rotate(180, true);
		}
		else {
			stop();
		}
	}
}
