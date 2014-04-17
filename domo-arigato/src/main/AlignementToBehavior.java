package main;

import lejos.robotics.navigation.Pose;
import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;
import utils.Geometry;

public class AlignementToBehavior extends EventListener {
	int state = 0;
	float offset = 0.0f;
	private int left;
	private Pose goal;
	private float initRotateSpeed;
	private String color;
	
	/**
	 * Use this to go to a line and turn on it.
	 * @param goal The point on the line.
	 * @param left True to turn left (according to the heading, not the board).
	 * @param color White, Yellow(left), Red(right), Blue(top), Green(bottom), BlackX, BlackY.
	 */
	public AlignementToBehavior(Pose goal, boolean left, String color) {
		this.color = color;
		this.goal = goal;
		this.left = -1;
		if(left)
			this.left = 1;
		initRotateSpeed = (float) Robot.getInstance().getMotion().getPilot().getRotateSpeed();
	}

	public void warn(Event event) {
		String c = "";
		switch(event.getTypeEvent())
		{
		case WHITE_DETECTED :
			c = "White";
		case COLOR_DETECTED :
			Pose myPose = Robot.getInstance().getOdometryPoseProvider().getPose();
			if(event.getName().equals("Low")) {
				float[] distance = new float[2];
				distance[0] = Math.abs(myPose.getX() - 50);
				distance[1] = Math.abs(myPose.getX() + 50);
				if(distance[0] < distance[1])
					c = "Red";
				else
					c = "Yellow";
			}
			else if(event.getName().equals("High")) {
				float[] distance = new float[4];
				distance[0] = Math.abs(myPose.getX());
				distance[1] = Math.abs(myPose.getY());
				distance[2] = Math.abs(myPose.getY() - 60);
				distance[3] = Math.abs(myPose.getY() + 60);
				float min = distance[0];
				int i = 0;
				c = "BlackX";
				for(i = 1; i < 4; i++) {
					if(distance[i] < min) {
						min = distance[i];
						switch(i) {
						case 1 :
							c = "BlackY";
							break;
						case 2 :
							c = "Blue";
							break;
						case 3 :
							c = "Green";
							break;
						}
					}
				}
			}
			if(c.equals(color)) {
				if(state == 1) {
					state = 2;
				}
				else if(state == 3) {
					state = 4;
				}
				else ignore();
			}
			else
				ignore();
			break;
		case GOFORWARD_END :
			if(state == 2) {
				state = 3;
			}
			else
				ignore();
			break;
		case ROTATE_END :
			if(state == 0) {
				state = 1;
			}
			else
				ignore();
		case ROBOT_DETECTED :
			if(state == 1)
				state = -1;
			else
				ignore();
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

	public void act() {
		if(state == -1) {
			doBehavior(new DodgeBehavior());
		}
		if(state == 0) {
			Robot.getInstance().getMotion().getPilot().setRotateSpeed(90);
			ActionFactory.rotate(goal, true);
		}
		else if(state == 1) {
			ActionFactory.goForward(10000, true);
		}
		else if(state == 2) {
			Pose pose = Robot.getInstance().getOdometryPoseProvider().getPose();
			ActionFactory.stopMotion(false);
			Pose nouvellePose = Robot.getInstance().getOdometryPoseProvider().getPose();
			offset = nouvellePose.distanceTo(pose.getLocation());
			ActionFactory.goForward(8.5f - offset, true);
		}
		else if(state == 3) {
			ActionFactory.rotate(180 * left, true);
		}
		else if(state == 4) {
			ActionFactory.stopMotion(false);
			Geometry.adjustHeading();
			stop();
		}
	}

}
