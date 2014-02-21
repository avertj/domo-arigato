package main;

import actions.ActionFabrique;
import actions.Event;
import robot.EventListener;

public class Test2Thread extends EventListener {
	int state = 0;
	boolean robotMoving = false;

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case SHUTDOWN :
			stop();
			break;
		case GOFORWARDEND :
			state = 1;
			robotMoving = false;
			break;
		case GOBACKWARDEND :
			state = 0;
			robotMoving = false;
			break;
		default:
			break;
		}
	}

	public void act() {
		if(!robotMoving) {
			robotMoving = true;
			switch(state)
			{
			case 0 :
				ActionFabrique.goForward(500, true);
				break;
			case 1 :
				ActionFabrique.goBackward(500, true);
				break;
			}
		}
	}
}
