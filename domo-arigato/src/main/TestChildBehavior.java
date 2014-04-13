package main;

import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;
import utils.Tuple;

public class TestChildBehavior extends EventListener {
	int state = 0;
	boolean fin = false;

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case GOBACKWARD_END :
			if(state == 0) {
				state++;
			}
			else {
				ignore();
			}
			break;
		case CHILDBEHAVIOR_END :
			if(state == 1) {
				state++;
			}
			else {
				ignore();
			}
			break;
		case GOFORWARD_END :
			if(state == 2) {
				fin = true;
			}
			else {
				ignore();
			}
			break;
		default:
			ignore();
			break;
		}
	}

	public void act() {
		if(!fin){
			if(state == 0) {
				ActionFactory.goBackward(5.0f, true);
			}
			else if(state == 1) {
				doBehavior(new TrouverPalet());
			}
			else if(state == 2) {
				ActionFactory.goForward(15.0f, true);
			}
		}
		else
			stop();
	}

}
