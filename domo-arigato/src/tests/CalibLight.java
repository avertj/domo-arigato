package tests;

import lejos.robotics.navigation.Pose;
import main.FollowLineBehavior;
import main.FollowLineBehavior2;
import main.GoToBehavior;
import robot.EventListener;
import actions.Event;

public class CalibLight extends EventListener {
	int state = 0;

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case WHITE_DETECTED :
			System.out.println("White");
			ignore();
			break;
		case BLACK_DETECTED :
			System.out.println("Black");
		default:
			ignore();
			break;
		}
	}

	public void act() {
	}
}
