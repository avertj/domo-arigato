package main;

import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;
import lejos.robotics.navigation.Pose;
import music.BipRobot;

public class DodgeBehavior extends EventListener {
	int state = 0;
	int nbDetectionRobot = 0;
	float distEnnemie;
	boolean clawsOpen;
	boolean left = false;
	
	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case END_ROBOT_DETECTED :
			if(state == 0)
				state = 5;
			else if(state == 1)
				state = 4;
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
			ActionFactory.wait(6000, "Dodge", true);
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
				ActionFactory.arcMove(90, 20, true);
			}
			else {
				ActionFactory.arcMove(-90, -20, true);
			}
		}
		else if(state == 4) {
			if(clawsOpen)
			{
				ActionFactory.useClaws(1,true);
			}
			state = 5;
		}
		if(state == 5)
			stop();
	}
	
	private boolean frontOfWall()
	{
		Pose myPose = Robot.getInstance().getOdometryPoseProvider().getPose();
		if(myPose.getX() > 60 && myPose.getHeading()%360 < 145)
			return true;
		else if(myPose.getX() < -60 && (myPose.getHeading()-180)%360 < 145)
			return true;
		else if(myPose.getY() < -120 && (myPose.getHeading()-180)%360 < 55)
			return true;
		else if(myPose.getY() > 120 && myPose.getHeading()%360 < 55)
			return true;
		return false;
	}
}
