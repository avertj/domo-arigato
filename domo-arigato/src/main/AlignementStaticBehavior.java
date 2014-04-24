package main;

import lejos.robotics.navigation.Pose;
import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;
import utils.Geometry;

public class AlignementStaticBehavior extends EventListener {
	int state = 3;
	float offset = 0.0f;
	private int left;
	private double initRotateSpeed;
	private String color;
	
	/**
	 * Use this to turn in order to find a line.
	 * @param left True to turn left (according to the heading, not the board).
	 * @param color White, Yellow(left), Red(right), Blue(top), Green(bottom), BlackX, BlackY.
	 */
	public AlignementStaticBehavior(boolean left, String color) {
		this.color = color;
		this.left = -1;
		if(left)
			this.left = 1;
	}

	public void warn(Event event) {
		String c = "";
		switch(event.getTypeEvent())
		{
		case WHITE_DETECTED :
			c = "White";
		case COLOR_DETECTED :
			if(event.getName().equals(color)) {
				if(state == 3) {
					state = 4;
				}
				else ignore();
			}
			else
				ignore();
			break;
		case GOFORWARD_END :
			if(state == 3) {
				state = 5;
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
			break;
		default:
			ignore();
			break;
		}
	}

	public void act() {
		if(state == 3) {
			initRotateSpeed = (float) Robot.getInstance().getMotion().getPilot().getRotateSpeed();
			Robot.getInstance().getMotion().getPilot().setRotateSpeed(65);
			ActionFactory.rotate(180 * left, true);
		}
		else if(state == 4) {
			ActionFactory.stopMotion(false);
			Robot.getInstance().getMotion().getPilot().setRotateSpeed(initRotateSpeed);
			stop("Line");
		}
		else if(state == 5) {
			Robot.getInstance().getMotion().getPilot().setRotateSpeed(initRotateSpeed);
			stop("NotLine");
		}
	}

}
