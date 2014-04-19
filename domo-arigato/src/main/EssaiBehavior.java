package main;

import lejos.robotics.navigation.Pose;
import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;

public class EssaiBehavior extends EventListener {
	private enum State {
		START, PLUCK_BUMPED, SCORED, ONLINE, PLUCK_BUMPED2, SCORED2
	};

	private State state = State.START;

	public void warn(Event event) {
		switch (event.getTypeEvent()) {
		case CHILDBEHAVIOR_END:
			if (state == State.START) {
				state = State.PLUCK_BUMPED;
			}
			else if(state == State.PLUCK_BUMPED) {
				state = State.SCORED;
			}
			else if(state == State.SCORED) {
				state = State.ONLINE;
			}
			else if(state == State.ONLINE) {
				state = State.PLUCK_BUMPED2;
			}
			else if(state == State.PLUCK_BUMPED2) {
				state = State.SCORED;
			}
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
			doBehavior(new FollowLineBehavior(100, false));
		} else if(state == State.PLUCK_BUMPED) {
			doBehavior(new ScoreBehavior());
		} else if(state == State.SCORED) {
			//doBehavior(new AlignementToBehavior(new Pose(90, 0, 0), false, "BlackY"));
		} else if(state == State.ONLINE) {
			doBehavior(new FollowLineBehavior(150, true));
		} else if(state == State.PLUCK_BUMPED2) {
			doBehavior(new ScoreBehavior());
		} else if(state == State.SCORED2) {
			stop();
		}
	}
}
