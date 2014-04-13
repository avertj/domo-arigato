package main;

import java.awt.Point;

import lejos.robotics.navigation.Pose;
import actions.Event;
import robot.EventListener;
import robot.Robot;

public class ScoreBehavior extends EventListener {
	private int state = 0;
	private boolean over = false;
	@Override
	public void warn(Event event) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void act() {
		if(!over) {
			if(state == 0) {
				Pose myPose = Robot.getInstance().getOdometryPoseProvider().getPose();
				Point frontPose = new Point((int)myPose.getX(), (int)myPose.getY());
				frontPose.translate((int)Math.cos(myPose.getHeading()), (int)Math.sin(myPose.getHeading()));
			}
		}
	}

}
