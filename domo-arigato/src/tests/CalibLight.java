package tests;

import robot.EventListener;
import robot.Robot;
import actions.ActionFactory;
import actions.Event;

public class CalibLight extends EventListener {
	int state = -1;
	int min;
	int max;

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case GOFORWARD_END :
			state = 2;
			break;
		case WAIT_END :
			if(state == -1)
				state = 0;
			else
				state = 1;
			break;
		default:
			ignore();
			break;
		}
	}

	public void act() {
		if(state == -1) {
			ActionFactory.wait(500, "", true);
		}
		else if(state == 0) {
			ActionFactory.goForward(60.0f, true);
			int light = Robot.getInstance().getEyes().getLightValue();
			min = light;
			max = light;
			ActionFactory.wait(5, "", true);
		}
		else if(state == 1) {
			ActionFactory.wait(5, "", true);
			int light = Robot.getInstance().getEyes().getLightValue();
			if(light < min)
				min = light;
			if(light > max)
				max = light;
		}
		else if(state == 2) {
			System.out.println("min = "+min);
			System.out.println("max = "+max);
			stop();
		}
	}
}
