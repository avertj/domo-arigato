package main;

import lejos.robotics.navigation.Pose;
import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;
import utils.Tuple;

public class TestDodo extends EventListener {
	int state = 0;
	boolean end = false;

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case SHUTDOWN :
			stop();
			break;
		case WAITEND :
			break;
		case INTERRUPTED :
			System.out.println("interrupted");
			break;
		default:
			break;
		}
	}

	public void act() {
		if(state == 0) {
			System.out.println("debut");
			ActionFactory.goForward(10000, true);
			ActionFactory.wait(1000, "dodo", true);
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
