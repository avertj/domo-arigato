package main;

import actions.ActionFactory;
import actions.Event;
import robot.EventListener;

public class TestThread extends EventListener {
	boolean debut = true;
	int state = 0;
	float nextclawsOpenure = 0.0f;
	boolean clawsMoving = false;
	boolean robotMoving = false;
	boolean end = false;
	boolean attente = false;

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case SHUTDOWN :
			stop();
			break;
		case PLAYMUSICEND :
			end = true;
			break;
		case GOFORWARDEND :
			state = 1;
			robotMoving = false;
			break;
		case GOBACKWARDEND :
			state = 2;
			robotMoving = false;
			break;
		case ROTATEEND :
			state = 0;
			robotMoving = false;
			break;
		case USECLAWSEND :
			clawsMoving = false;
			attente = true;
			nextclawsOpenure = 1.0f - nextclawsOpenure;
			break;
		case WAITEND :
			if(event.getName().equals("end"))
				end = true;
			else
				attente = false;
			break;
		default:
			break;
		}
	}

	public void act() {
		if(debut) {
			debut = false;
			ActionFactory.wait(10000, "end", true);
		}
		if(!clawsMoving) {
			if(attente) {
				ActionFactory.wait(500, "claws", true);
			}
			else {
				clawsMoving = true;
				ActionFactory.useClaws(nextclawsOpenure, true);
			}
		}
		if(!robotMoving) {
			robotMoving = true;
			switch(state)
			{
			case 0 :
				ActionFactory.goForward(500, true);
				break;
			case 1 :
				ActionFactory.goBackward(500, true);
				break;
			case 2 :
				ActionFactory.rotate(90, true);
			}
		}
		if(end) {
			stop();
		}
	}
}
