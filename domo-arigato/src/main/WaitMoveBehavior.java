package main;

import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;
import lejos.robotics.navigation.Pose;
import music.BipRobot;

public class WaitMoveBehavior extends EventListener {
	int state = 0;
	int nbDetectionRobot = 0;
	float distEnnemie;
	boolean clawsOpen = false;
	boolean left = false;
	String result = "Dodged";
	
	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case END_ROBOT_DETECTED :
			if(state == 0 || state == 1) {
				state = 5;
				result = "Stayed";
			}
			else
				ignore();
			break;
		case WAIT_END :
			if(state == 0)
				state = 1;
			else
				ignore();
			break;
		case GOFORWARD_END :
			if(state == 1)
				state = 2;
			else
				ignore();
			break;
		case ROTATE_END :
			if(state == 2)
				state = 3;
			else if(state == 4)
				state = 5;
			else
				ignore();
			break;
		case ARC_END :
			if(state == 3)
				state = 4;
			else
				ignore();
			break;
		default:
			ignore();
			break;
		}
	}

	protected void act() {
		if(state == 0) {
			ActionFactory.stopMotion(true);
			ActionFactory.wait(30000, "Dodge", true);
		}
		else if(state == 1) {
			if(Robot.getInstance().getClaws().getState()==1.0f)
			{
				clawsOpen = true;
				//on ferme les pinces pour éviter de se casser le bras en touchant le robot adverse
				ActionFactory.useClaws(0,false);
			}
			else
			{
				clawsOpen = false;
			}

			//on s'approche à 10cm du robot adverse
			distEnnemie = Robot.getInstance().getSonar().getMinDist();
			System.out.println(distEnnemie);
			ActionFactory.goForward(distEnnemie - 16, true);
		}
		else if(state == 2) {
			//on joue un petit son devant le robot ennemie pour le narguer
			BipRobot bip = new BipRobot();
			ActionFactory.playMusic(bip, true);
			
			Pose myPose = Robot.getInstance().getOdometryPoseProvider().getPose();
			if(myPose.getX() > 60 && myPose.getHeading()%360 < 145 || myPose.getX() < -60 && (myPose.getHeading()-180)%360 < 145)
				left = true;
			if(left)
				ActionFactory.rotate(90, true);
			else
				ActionFactory.rotate(-90, true);
		}
		else if(state == 3) {
			if(!left) {
				ActionFactory.arcMove(180, 40, true);
			}
			else {
				ActionFactory.arcMove(-180, -40, true);
			}
		}
		else if(state == 4) {
			if(left)
				ActionFactory.rotate(90, true);
			else
				ActionFactory.rotate(-90, true);
		}
		else if(state == 5) {
			if(clawsOpen)
			{
				ActionFactory.useClaws(1,true);
			}
			stop(result);
		}
	}
}
