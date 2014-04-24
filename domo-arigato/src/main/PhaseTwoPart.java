package main;

import field.EnumPuck;
import field.Field;
import lejos.robotics.navigation.Pose;
import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;

public class PhaseTwoPart extends EventListener {
	private int state = 0;
	private String out;
	private Pose goal;
	
	public PhaseTwoPart(Pose goal) {
		this.goal = goal;
	}

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case CHILDBEHAVIOR_END :
			if(state == 0)
				state = 1;
			else if(state == 1) {
				state = 2;
				out = event.getName();
			}
			else if(state == 2)
				state = 3;
			else
				ignore();
			break;
		default:
			ignore();
			break;
		}
	}

	protected void act() {
		if(state == 0) {
			doBehavior(new GoToBehavior(goal));
		}
		else if(state == 1) {
			doBehavior(new CatchPluck());
		}
		else if(state == 2) {
			if(out.equals("BUMP"))
				doBehavior(new ScoreBehavior());
			else {
				state = 3;
				act();
			}
		}
		else if(state == 3) {
			stop();
		}
	}
}
