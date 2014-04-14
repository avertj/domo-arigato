package tests;

import main.DodgeBehavior;
import robot.EventListener;
import actions.Event;

public class TestDodgeBehavior extends EventListener {
		int state = 0;
		boolean fin = false;

		public void warn(Event event) {
				ignore();
		}

		public void act() {
			doBehavior(new DodgeBehavior());
		}


}
