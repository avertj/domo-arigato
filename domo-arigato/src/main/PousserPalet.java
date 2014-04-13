package main;

import actions.ActionFactory;
import actions.Event;
import robot.EventListener;

public class PousserPalet extends EventListener {	
	@Override
	public void warn(Event event) {
		
	}

	@Override
	protected void act() {
		ActionFactory.useClaws(0,false);
		stop();
	}

}
