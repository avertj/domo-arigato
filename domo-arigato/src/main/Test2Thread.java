package main;

import lejos.robotics.navigation.Pose;
import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;

public class Test2Thread extends EventListener {
	int state = 0;
	boolean debut = true;
	boolean ignore = false;

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case BUMP :
			if(state == 0)
				state = 1;
			else {
				ignore = true;
			}
			System.out.println("Bump!");
			break;
		case SHUTDOWN :
			stop();
			break;
		case USECLAWS_END :
			System.out.println("State : "+state);
			state++;
			break;
		case ROTATE_END :
			System.out.println("State : "+state);
			state++;
			break;
		case GOFORWARD_END :
			System.out.println("State : "+state);
			state++;
			break;
		case GOBACKWARD_END :
			System.out.println("State : "+state);
			state++;
			break;
		case STRAIGHTMOVE_END :
			System.out.println("State : "+state);
			state++;
			break;
		case ARC_END :
			System.out.println("State : "+state);
			state++;
			break;
		case INTERRUPTED :
			ignore = true;
			System.out.println("Interompu : "+event.getName());
			break;
		default:
			break;
		}
	}

	public void act() {
		if(ignore) {
			ignore = false;
		}
		else {
			if(debut) {
			Robot.getInstance().getMotion().getPilot().setRotateSpeed(100);
				debut = false;
				ActionFactory.goForward(10000, true);
			}
			else {
				switch(state) {
				case 1 :
					ActionFactory.useClaws(0.0f, true);
					break;
				case 2 :
					ActionFactory.arcMove(-45, -20, true);
					break;
				case 3 :
					ActionFactory.arcMove(45, 20, true);
					break;
				case 4 :
					ActionFactory.straightMove(new Pose(20, 120, 90), true);
					break;
				case 5 :
					ActionFactory.useClaws(1.0f, true);
					break;
				case 6 :
					ActionFactory.goBackward(10.0f, true);
					break;
				case 7 :
					ActionFactory.straightMove(new Pose(0, -120, 90), true);
					break;
				case 8 :
					stop();
				}
			}
		}
	}
}
