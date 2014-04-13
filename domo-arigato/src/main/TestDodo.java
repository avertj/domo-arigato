package main;

import lejos.robotics.navigation.Pose;
import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;
import utils.Tuple;

public class TestDodo extends EventListener {
	boolean debut = true;
	boolean black = false;
	Pose pose;

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case WHITE_DETECTED :
			System.out.println("White");
			break;
		case BLACK_DETECTED :
			System.out.println("Black");
			black = true;
			break;
		default:
			ignore();
			break;
		}
	}

	public void act() {
		if(debut) {
			debut = false;
			ActionFactory.goForward(10000, true);
		}
		if(black) {
			pose = Robot.getInstance().getOdometryPoseProvider().getPose();
			ActionFactory.stopMotion(false);
			Pose nouvellePose = Robot.getInstance().getOdometryPoseProvider().getPose();
			System.out.println(nouvellePose.distanceTo(pose.getLocation()));
			stop();
		}
		/*
		if(state == 0) {
			System.out.println("debut");
			ActionFactory.goForward(10000, true);
			ActionFactory.wait(10000, "dodo", true);
			state++;
		}
		else if(state == 1) {
			ActionFactory.rotate(90, true);
			state++;
		}
		else if(state == 2) {
			end = true;
		}/*
		if(state < 100) {
			//for(int i = 0; i < 6; i++) System.out.println();
			Tuple<Float, Boolean> res = Robot.getInstance().getSonar().getMinDistance();
			if(res.getY())
				System.out.println("IT S MOVING !!!");
			//System.out.println("distance = "+res.getX());
		}*/
	}
}
