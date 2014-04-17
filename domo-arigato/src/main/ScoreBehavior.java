package main;

import java.awt.Point;

import field.EnumPuck;
import field.Field;
import lejos.robotics.navigation.Pose;
import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;
import utils.Geometry;

public class ScoreBehavior extends EventListener {
	private int state = 0;
	@Override
	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case ARC_END :
			if(state == 0) {
				state = 1;
			}
			else
				ignore();
			break;
		case WHITE_DETECTED :
			if(state == 1) {
				state = 2;
			}
			else
				ignore();
			break;
		case USECLAWS_END :
			if(state == 2) {
				state = 3;
			}
			else
				ignore();
			break;
		case GOBACKWARD_END :
			if(state == 3) {
				state = 4;
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
		case WAIT_END :
			if(state == 0) {
				state = 5;
			}
			else if(state == 5) {
				state = 1;
			}
			else
				ignore();
			break;
		case ROBOT_DETECTED :
			if(state == 1) {
				state = -1;
			}
			else
				ignore();
			break;
		case CHILDBEHAVIOR_END :
			if(state == -1) {
				state = 1;
			}
			else
				ignore();
			break;
		default :
			ignore();
			break;
		}
	}

	@Override
	protected void act() {
		if(state == -1) {
			doBehavior(new DodgeBehavior());
		}
		else if(state == 0) {
			//Remove puck from Field
			ActionFactory.useClaws(0.0f, true);
			Pose myPose = Robot.getInstance().getOdometryPoseProvider().getPose();
			Point frontPose = new Point((int)myPose.getX(), (int)myPose.getY());
			frontPose.translate((int)Math.cos(myPose.getHeading()) * 8, (int)Math.sin(myPose.getHeading()) * 8);
			EnumPuck puck = Geometry.closest(frontPose);
			if(puck != null) {
				Field.getInstance().setPresent(puck, false);
			}
			
			// remplacer ça par un boolean dans le constructeur ?
			// le père choisis si le palet doit etre ramené en ligne droite ou arc ?
			
			if(myPose.getHeading()%360 >= 175 && myPose.getHeading()%360 <= 185) {
				ActionFactory.arcMove(-90, -33, true);
			}
			else if(myPose.getHeading()%360 >= 355 && myPose.getHeading()%360 <= 5) {
				ActionFactory.arcMove(90, 33, true);
			}
			else {
				if(myPose.getHeading()%360 >= 85 && myPose.getHeading()%360 <= 95) {
					ActionFactory.arcMove(-90, -33, true);
					ActionFactory.wait(600, "", true);
				}
				else {
				ActionFactory.rotate(new Pose(Robot.getInstance().getOdometryPoseProvider().getPose().getX(), 
						Robot.getInstance().getOdometryPoseProvider().getPose().getY() + 100, 90), true);
				}
			}
		}
		else if(state == 1) {
			ActionFactory.goForward(10000, true);
		}
		else if(state == 2) {
			ActionFactory.stopMotion(true);
			ActionFactory.useClaws(1.0f, true);
		}
		else if(state == 3) {
			ActionFactory.goBackward(10.0f, true);
		}
		else if(state == 4) {
			stop();
		}
		else if(state == 5) {
			ActionFactory.arcMove(90, 33, true);
			ActionFactory.wait(600, "", true);
		}
	}

}
