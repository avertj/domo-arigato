package main;

import lejos.robotics.navigation.Pose;
import actions.ActionFactory;
import actions.Event;
import robot.EventListener;

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
			robotMoving = true;
			ActionFactory.straightMove(a, true);
		}
		if(!robotMoving) {
			Pose p = a;
			switch(state%4) {
			case 0 :
				p = a;
				break;
			case 1 :
				p = b;
				break;
			case 2 :
				p = c;
				break;
			case 3 :
				p = d;
				break;
			}
			robotMoving = true;
			ActionFactory.straightMove(p, true);
		}
		if(state == 10) {
			stop();
		}
	}
}
