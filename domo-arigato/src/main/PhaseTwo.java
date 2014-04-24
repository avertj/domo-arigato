package main;

import field.EnumPuck;
import field.Field;
import lejos.robotics.navigation.Pose;
import music.MarioBros;
import actions.ActionFactory;
import actions.Event;
import robot.EventListener;

public class PhaseTwo extends EventListener {
	private int state = 0;
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
		// Dans l'état 0 on initialise deux points puis on lance l'algorithme PhaseTwoPart au premier point.
		if(state == 0) {
			ActionFactory.playMusic(new MarioBros(), true);
			poses[0] = new Pose(Field.getInstance().getPosition(EnumPuck.NW).x+50, Field.getInstance().getPosition(EnumPuck.NW).y-30, 90);
			poses[1] = new Pose(Field.getInstance().getPosition(EnumPuck.SE).x-50, Field.getInstance().getPosition(EnumPuck.SE).y+30, 90);
			doBehavior(new PhaseTwoPart(poses[state]));
		}
		else if(state == 1) {
			// On lance l'algorithme PhaseTwoPart au second point.
			doBehavior(new PhaseTwoPart(poses[state]));
		}
		else if(state == 2) {
			stop();
		}
	}
}
