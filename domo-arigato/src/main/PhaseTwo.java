package main;

import field.EnumPuck;
import field.Field;
import lejos.robotics.navigation.Pose;
import music.MarioBros;
import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;

public class PhaseTwo extends EventListener {
	private int state = 0;
	private String out;
	private Pose[] poses = new Pose[2];

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case CHILDBEHAVIOR_END :
			state++;
			break;
		default:
			ignore();
			break;
		}
	}

	protected void act() {
		if(state == 0) {
			ActionFactory.playMusic(new MarioBros(), true);
			poses[0] = new Pose(Field.getInstance().getPosition(EnumPuck.NW).x+50, Field.getInstance().getPosition(EnumPuck.NW).y-30, 90);
			poses[1] = new Pose(Field.getInstance().getPosition(EnumPuck.SE).x-50, Field.getInstance().getPosition(EnumPuck.SE).y+30, 90);
			doBehavior(new PhaseTwoPart(poses[state]));
		}
		else if(state == 1) {
			doBehavior(new PhaseTwoPart(poses[state]));
		}
		else if(state == 2) {
			stop();
		}
	}
}
