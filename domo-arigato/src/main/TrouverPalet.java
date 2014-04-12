package main;

import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;
import utils.Tuple;

public class TrouverPalet extends EventListener {
	int state = -1;
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
			else
				ignore();
			break;
		case WAITEND :
			if(state == -1) {
				state = 0;
			}
			else if(state != 1)
				ignore();
			break;
		default:
			ignore();
			break;
		}
	}

	public void act() {
		if(!end){
			if(state == -1) {
				System.out.println("debut");
				ActionFactory.wait(1000, "", true);
			}
			else if(state == 0) {
				Tuple<Float, Boolean> res = Robot.getInstance().getSonar().getMinDistance();
				if(res.getX()>20 && res.getX()<60){
					System.out.println("deja la");
					stop();
				}
				else {
					//Robot.getInstance().getMotion().getPilot().setRotateSpeed(100);
					System.out.println("speed : "+Robot.getInstance().getMotion().getPilot().getRotateSpeed());
					ActionFactory.rotate(180, true);
					state++;
					ActionFactory.wait(200, "", true);
				}
			}
			else if(state == 1) {
				Tuple<Float, Boolean> res = Robot.getInstance().getSonar().getMinDistance();
				if(res.getX()>20 && res.getX()<60){
					ActionFactory.goForward(100.f, true);
					state=2;
				}
				else
					ActionFactory.wait(20, "", true);
			}
			else if(state == 3){
				ActionFactory.stopMotion(true);
				ActionFactory.useClaws(0.0f, true);
				state++;
			}
		}
		else
			stop();
	}

}
