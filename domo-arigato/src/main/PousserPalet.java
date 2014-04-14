package main;

import actions.ActionFactory;
import actions.Event;
import robot.EventListener;

public class PousserPalet extends EventListener {	
	boolean debut = true;
	@Override
	public void warn(Event event) {
		
	}

	@Override
	protected void act() {
		if(debut) {
			debut = false;
			ActionFactory.useClaws(0,false);
			stop();
		}
	}

}
