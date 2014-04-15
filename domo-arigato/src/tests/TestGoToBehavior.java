package tests;

import lejos.robotics.navigation.Pose;
import main.GoToBehavior;
import robot.EventListener;
import actions.Event;

public class TestGoToBehavior extends EventListener {
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
			doBehavior(new GoToBehavior(new Pose(0, 50, 90)));
		}
		else if(state == 1) {
			System.out.println("Fin");
			stop();
		}
	}
}
