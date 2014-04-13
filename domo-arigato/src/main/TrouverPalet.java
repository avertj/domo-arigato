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
		case USECLAWS_END :
		case ROTATE_END :
			end=true;
			break;
		case BUMP :
			if(state==2){
				state=3;
			}
			else
				ignore();
			break;
		case WAIT_END :
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
				float res = Robot.getInstance().getSonar().getMinDist();
				if(res>20 && res<80){
					System.out.println("deja la");
					stop();
				}
				else {
					Robot.getInstance().getMotion().getPilot().setRotateSpeed(30);
					//ActionFactory.rotate(180, true);
					state++;
					ActionFactory.wait(5, "", true);
				}
			}
			else if(state == 1) {
				System.out.println("distance : "+Robot.getInstance().getSonar().getMinDist());
				ActionFactory.wait(1000, "", true);
				/*float res = Robot.getInstance().getSonar().getMinDist();
				if(res>20 && res<50){
					System.out.println("distance : "+res);
					ActionFactory.goForward(100.f, true);
					state=2;
				}
				else
					ActionFactory.wait(5, "", true);*/
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
