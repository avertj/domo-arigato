package main;

import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;
import utils.Tuple;

public class TrouverPalet extends EventListener {
	int state = 0;
	boolean end = false;

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case ROTATEEND:
			end=true;
			break;
		case BUMP:
			if(state==2){
				state=3;
			}
		default:
			break;
		}
	}

	public void act() {
		if(!end){
			if(state == 0) {
				System.out.println("debut");
				ActionFactory.rotate(180, true);
				state++;
			}
			else if(state == 1) {
				Tuple<Float, Boolean> res = Robot.getInstance().getSonar().getMinDistance();
				if(res.getY() && res.getX()>20 && res.getX()<60){
					ActionFactory.goBackward(100.f, true);
					state=2;
				}
			}
			else if(state == 3){
				ActionFactory.stopMotion(true);
				ActionFactory.useClaws(0, true);
				state++;
			}
		}
	}

}
