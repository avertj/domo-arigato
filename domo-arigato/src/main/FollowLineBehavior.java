package main;

import lejos.robotics.navigation.Pose;
import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;
import utils.Geometry;

public class FollowLineBehavior extends EventListener {
	private Pose goal;
	private int state = 0;
	private boolean rightOffset;
	private float radiusCalibration;
	private float angleCalibration;
	
	public FollowLineBehavior(Pose goal, boolean rightOffset) {
		this.rightOffset = rightOffset;
		this.goal = goal;
		radiusCalibration = 30.0f;
	}

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case WHITE_DETECTED :
			if(state == 0)
				state = 1;
			else
				ignore();
			break;
		case BLACK_DETECTED :
			if(state == 1)
				state = 2;
			else
				ignore();
			break;
		case ARC_END :
			if(state == 2)
				state = 3;
			else
				ignore();
			break;
		case GOFORWARD_END :
			state = 4;
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
					Geometry.barelyEqualsCoord(Math.abs(y), 0.0f, 1.0f) || 
					Geometry.barelyEqualsCoord(Math.abs(y), 60.0f, 1.0f) || 
					Geometry.barelyEqualsCoord(Math.abs(y), 120.0f, 1.0f)))
				|| 
				((Geometry.barelyEqualsHeading(h, 90.0f, 2.0f) || Geometry.barelyEqualsHeading(h, 270.0f, 2.0f)) && (
						Geometry.barelyEqualsCoord(Math.abs(x), 0.0f, 1.0f) || 
						Geometry.barelyEqualsCoord(Math.abs(x), 50.0f, 1.0f)))
					)) {
				System.out.println("I am not along a line !!");
				stop();
			}
			angleCalibration = 10.0f;
			ActionFactory.goForward(myPose.distanceTo(goal.getLocation()), true);
		}
		else if(state == 1) {
			if(rightOffset)
				ActionFactory.arcMove(angleCalibration, radiusCalibration, true);
			else
				ActionFactory.arcMove(-angleCalibration, -radiusCalibration, true);
		}
		else if(state == 2) {
			float shortRadius = 10.0f;
			Pose myPose = Robot.getInstance().getOdometryPoseProvider().getPose();
			float currentAngle = (((myPose.getHeading()+45)%90)-45) / 2;
			Robot.getInstance().getOdometryPoseProvider().setPose(new Pose(myPose.getX(), myPose.getY(), myPose.getHeading()-currentAngle));
			
			if(rightOffset)
				ActionFactory.arcMove(-Math.abs(currentAngle), -shortRadius, true);
			else
				ActionFactory.arcMove(Math.abs(currentAngle), shortRadius, true);
		}
		else if(state == 3) {
			ActionFactory.goForward(Robot.getInstance().getOdometryPoseProvider().getPose().distanceTo(goal.getLocation()), true);
		}
		else if(state == 4) {
			stop();
		}
	}
}
