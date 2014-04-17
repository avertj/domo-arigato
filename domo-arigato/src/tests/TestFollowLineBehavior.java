package tests;

import lejos.robotics.navigation.Pose;
import main.FollowLineBehavior;
import main.FollowLineBehavior;
import main.GoToBehavior;
import robot.EventListener;
import actions.Event;

public class TestFollowLineBehavior extends EventListener {
	int state = 0;

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case CHILDBEHAVIOR_END :
			state = 1;
			break;
		default:
			ignore();
			break;
		}
	}

	public void act() {
		if(state == 0) {
			doBehavior(new FollowLineBehavior(60, true));
		}
		else if(state == 1) {
			System.out.println("Fin");
			stop();
		}
	}
}
