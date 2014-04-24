package tests;

import lejos.robotics.navigation.Pose;
import main.AlignementBehavior;
import main.FollowLineBehavior;
import actions.Event;
import robot.EventListener;
import robot.Robot;
import utils.Geometry;

public class Test3Thread extends EventListener {
	boolean debut = true;
	int state = 0;

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case CHILDBEHAVIOR_END :
			state++;
			break;
		default:
			ignore();
			break;
		}
	}

	public void act() {
		if(debut) {
			debut = false;
			Robot.getInstance().getOdometryPoseProvider().setPose(new Pose(13, 10, 170));
		}
		if(state == 0) {
			doBehavior(new AlignementBehavior(false));
		}
		else if(state == 1) {
			doBehavior(new FollowLineBehavior(100, true));
		}
		else {
			Geometry.adjustHeading();
			Pose myPose = Robot.getInstance().getOdometryPoseProvider().getPose();
			System.out.println("X : "+myPose.getX());
			System.out.println("Y : "+myPose.getY());
			System.out.println("H : "+myPose.getHeading());
			stop();
		}
	}
}
