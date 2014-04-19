package tests;

import lejos.robotics.navigation.Pose;
import main.FollowLineBehavior;
import main.FollowLineBehavior;
import main.GoToBehavior;
import robot.EventListener;
import robot.Robot;
import actions.ActionFactory;
import actions.Event;
import actions.TypeEvent;

public class CalibLightLine extends EventListener {

	public void warn(Event event) {
		if(!event.getTypeEvent().equals(TypeEvent.WAIT_END))
			ignore();
	}

	public void act() {
		System.out.println(Robot.getInstance().getEyes().getLightValue());
		ActionFactory.wait(1000, "", true);
	}
}
