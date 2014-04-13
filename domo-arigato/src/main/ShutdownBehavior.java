package main;

import actions.Event;
import robot.EventListener;

public class ShutdownBehavior extends EventListener {

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case SHUTDOWN :
			stop();
			break;
		default:
			break;
		}
	}

	public void act() {
		
	}
}
