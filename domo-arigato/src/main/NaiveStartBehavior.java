package main;

import actions.ActionFactory;
import actions.Event;
import robot.EventListener;

public class NaiveStartBehavior extends EventListener {
	private enum State {
		START, PLUCK_BUMPED, PLUCK_CAUGHT, ROBOT_ROTATED, PLUCK_SCORED
	};

	private State state = State.START;

	public void warn(Event event) {
		switch (event.getTypeEvent()) {
		case BUMP:
			if (state == State.START)
				state = State.PLUCK_BUMPED;
			else
				ignore();
			break;
		case USECLAWS_END:
			if (state == State.PLUCK_BUMPED)
				state = State.PLUCK_CAUGHT;
			else
				ignore();
			break;
		case ROTATE_END:
			if (state == State.PLUCK_CAUGHT)
				state = State.ROBOT_ROTATED;
			else
				ignore();
			break;
		case GOFORWARD_END:
			// ne devrais jamais se produire
			ignore();
			break;
		case CHILDBEHAVIOR_END:
			if (state == State.ROBOT_ROTATED)
				state = State.PLUCK_SCORED;
			else
				ignore();
			break;
		default:
			ignore();
			break;
		}
	}

	@Override
	protected void act() {
		if(state == State.START) {
			// on avance
			ActionFactory.goForward(70, true);
		} else if(state == State.PLUCK_BUMPED) {
			// on ferme les griffes
			ActionFactory.useClaws(0.0f, true);
		} else if(state == State.PLUCK_CAUGHT) {
			//on tourne pour que le scorebehavior fasse un arc
			ActionFactory.rotate(90, true);
		} else if(state == State.ROBOT_ROTATED) {
			doBehavior(new ScoreBehavior());
		} else if(state == State.PLUCK_SCORED) {
			stop(); // on a fini de rammener le premier palet
		}
	}

}
