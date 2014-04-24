package main;

import lejos.robotics.navigation.Pose;
import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;
import utils.Geometry;

public class FollowLineBehavior extends EventListener {
	private int distance;
	private int state = 0;
	private float radiusCalibration;
	private Pose start;
	private int left;
	
	public FollowLineBehavior(int distance, boolean left) {
		this.left = 1;
		if(left)
			this.left = -1;
		this.distance = distance;
		radiusCalibration = 300.0f;
		start = Robot.getInstance().getOdometryPoseProvider().getPose();
	}

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case ROBOT_DETECTED :
			if(state > -1)
				state = -1;
			else
				ignore();
			break;
		case WHITE_DETECTED :
		case COLOR_DETECTED :
			state = 1;
			break;
		case NOISE_DETECTED :
			state = 2;
			break;
		case BUMP :
			state = 3;
			break;
		case CHILDBEHAVIOR_END :
			if(state == -1)
				state = 0;
			else
				ignore();
			break;
		default:
			ignore();
			break;
		}
	}

	protected void act() {
		if(state == -1) {
			doBehavior(new WaitMoveBehavior()); //Pas encore g�r�, c'est a faire.
		}
		else if(state == 0) {
			// On cherche a savoir si on es bien sur une ligne.
			if(Robot.getInstance().getEyes().onNoise()) {
				stop("I am not along a line !!");
			}
			else {
				Robot.getInstance().getMotion().getPilot().arcForward(-radiusCalibration * left);
			}
		}
		else if(state == 1) {
			Robot.getInstance().getMotion().getPilot().arcForward(-radiusCalibration * left);
			if(Robot.getInstance().getOdometryPoseProvider().getPose().distanceTo(start.getLocation()) > distance) {
				Geometry.adjustHeading();
				ActionFactory.stopMotion(true);
				stop("End");
			}
				
		}
		else if(state == 2) {
			Robot.getInstance().getMotion().getPilot().arcForward(radiusCalibration * left);
			if(Robot.getInstance().getOdometryPoseProvider().getPose().distanceTo(start.getLocation()) > distance) {
				Geometry.adjustHeading();
				ActionFactory.stopMotion(true);
				stop("End");
			}
		}
		else if(state == 3) {
			Geometry.adjustHeading();
			Geometry.adjustXBump();
			stop("BUMP");
		}
	}
}
