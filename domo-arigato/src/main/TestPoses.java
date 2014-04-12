package main;

import lejos.robotics.navigation.Pose;
import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;

public class TestPoses extends EventListener {
	int state = 0;
	boolean debut = true;

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case STRAIGHTMOVEEND :
			state++;
			break;
		default:
			break;
		}
	}

	public void act() {
		if(debut) {
			Robot.getInstance().getMotion().getPilot().setRotateSpeed(150);
			debut = false;
			ActionFactory.straightMove(new Pose(0.0f, 0.0f, 180.0f), true);
		}
		else {
			if(state == 1) {
				ActionFactory.straightMove(new Pose(50.0f, 60.0f, 0.0f), true);
			}
			else if(state == 2) {
				ActionFactory.straightMove(new Pose(-50.0f, 60.0f, 45.0f), true);
			}
			else if(state == 3) {
				ActionFactory.straightMove(new Pose(0.0f, 0.0f, 180.0f), true);
			}
			else if(state == 4) {
				ActionFactory.straightMove(new Pose(0.0f, -60.0f, 0.0f), true);
			}
		}
	}
}
