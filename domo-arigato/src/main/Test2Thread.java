package main;

import lejos.robotics.navigation.Pose;
import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;

public class Test2Thread extends EventListener {
	int state = 0;
	boolean robotMoving = false;
	boolean debut = true;
	boolean end = false;
	Pose a = new Pose(30, 30, 225);
	Pose b = new Pose(-30, 30, 315);
	Pose c = new Pose(-30, -30, 45);
	Pose d = new Pose(30, -30, 135);

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case SHUTDOWN :
			stop();
			break;
		case STRAIGHTMOVEEND :
			state = (state+1);
			robotMoving = false;
		default:
			break;
		}
	}

	public void act() {
		if(debut) {
			debut = false;
	    	Pose p = new Pose(0, 0, 0);
	    	Robot.getInstance().getOdometryPoseProvider().setPose(p);
			robotMoving = true;
			ActionFactory.straightMove(a, true);
		}
		else if(!robotMoving) {
			Pose p = null;String s=null;
			switch(state%4) {
			case 0 :
				p = a;s = "a";
				break;
			case 1 :
				p = b;s = "b";
				break;
			case 2 :
				p = c;s = "c";
				break;
			case 3 :
				p = d;s = "d";
				break;
			}
			robotMoving = true;
			System.out.println("Next Pose : "+s);
			System.out.println("X : "+Robot.getInstance().getOdometryPoseProvider().getPose().getX());
			System.out.println("Y : "+Robot.getInstance().getOdometryPoseProvider().getPose().getY());
			System.out.println("X : "+Robot.getInstance().getOdometryPoseProvider().getPose().getHeading());
			ActionFactory.straightMove(p, true);
		}
		if(state == 10) {
			end = true;
		}
	}
}
