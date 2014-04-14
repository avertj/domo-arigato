package tests;

import main.DodgeBehavior;
import robot.EventListener;
import actions.ActionFactory;
import actions.Event;

public class TestDodgeBehavior extends EventListener {
	int state = 0;

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case ROBOT_DETECTED :
			if(state == 0)
				state = 1;
			else
				ignore();
			break;
		case GOFORWARD_END :
			state = 2;
			break;
		case CHILDBEHAVIOR_END :
			state = 2;
			break;
		default:
			ignore();
			break;
		}
	}

	public void act() {
		if(state == 0) {
			ActionFactory.goForward(10000, true);
		}
		else if(state == 1) {
			doBehavior(new DodgeBehavior());
		}
		else if(state == 2) {
			System.out.println("Fin");
			stop();
		}
	}
}
