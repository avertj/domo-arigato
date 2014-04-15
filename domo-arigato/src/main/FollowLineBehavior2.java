package main;

import lejos.robotics.navigation.Pose;
import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;
import utils.Geometry;

public class FollowLineBehavior2 extends EventListener {
	private int distance;
	private int state = 0;
	private float radiusCalibration;
	private float angleCalibration;
	private Pose start;
	
	public FollowLineBehavior2(int distance) {
		this.distance = distance;
		radiusCalibration = 300.0f;
		angleCalibration = 30.0f;
		start = Robot.getInstance().getOdometryPoseProvider().getPose();
	}

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case WHITE_DETECTED :
			state = 1;
			break;
		case BLACK_DETECTED :
			state = 2;
			break;
		default:
			ignore();
			break;
		}
	}

	protected void act() {
		if(state == -1) {
			doBehavior(new DodgeBehavior());
		}
		else if(state == 0) {
			// On cherche a savoir si on es bien sur une ligne.
			Pose myPose = Robot.getInstance().getOdometryPoseProvider().getPose();
			float h = myPose.getHeading();
			float x = myPose.getX();
			float y = myPose.getY();
			if(!(((Geometry.barelyEqualsHeading(h, 0.0f, 2.0f) || Geometry.barelyEqualsHeading(h, 180.0f, 2.0f)) && (
					Geometry.barelyEqualsCoord(Math.abs(y), 0.0f, 3.0f) || 
					Geometry.barelyEqualsCoord(Math.abs(y), 60.0f, 3.0f) || 
					Geometry.barelyEqualsCoord(Math.abs(y), 120.0f, 3.0f)))
				|| 
				((Geometry.barelyEqualsHeading(h, 90.0f, 2.0f) || Geometry.barelyEqualsHeading(h, 270.0f, 2.0f)) && (
						Geometry.barelyEqualsCoord(Math.abs(x), 0.0f, 3.0f) || 
						Geometry.barelyEqualsCoord(Math.abs(x), 50.0f, 3.0f)))
					)) {
				System.out.println("I am not along a line !!");
				stop();
			}
			else {
				angleCalibration = 10.0f;
				Robot.getInstance().getMotion().getPilot().arcForward(-radiusCalibration);
			}
		}
		else if(state == 1) {
			Robot.getInstance().getMotion().getPilot().arcForward(radiusCalibration);
			if(Robot.getInstance().getOdometryPoseProvider().getPose().distanceTo(start.getLocation()) > distance) {
				ActionFactory.stopMotion(true);
				stop();
				System.out.println("fin");
			}
				
		}
		else if(state == 2) {
			Robot.getInstance().getMotion().getPilot().arcForward(-radiusCalibration);
			if(Robot.getInstance().getOdometryPoseProvider().getPose().distanceTo(start.getLocation()) > distance) {
				ActionFactory.stopMotion(true);
				stop();
				System.out.println("fin");
			}
		}
	}
}
