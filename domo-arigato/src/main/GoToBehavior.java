package main;

import lejos.robotics.navigation.Pose;
import actions.Event;
import robot.EventListener;

public class GoToBehavior extends EventListener {
	private Pose goal;
	
	public GoToBehavior(Pose goal) {
		this.goal = goal;
	}

	@Override
	public void warn(Event event) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void act() {
		// TODO Auto-generated method stub

	}
	
}
