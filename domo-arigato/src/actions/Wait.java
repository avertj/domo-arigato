package actions;

import lejos.util.Delay;
import robot.Robot;

class Wait extends RunnableRobot {
	private int duration;
	private String name;
	
	Wait(int duration, boolean createThread, String name) {
		this.duration = duration;
		this.name = name;
		if(createThread) {
			Thread thread = new Thread(this);
			thread.start();
		}
		else
			run();
	}
	
	public void run() {
		Delay.msDelay(duration);
		if(!getInterrupted()) {
			Robot.getInstance().warn(new Event(TypeEvent.WAIT_END, name));
		}
		else
			Robot.getInstance().warn(new Event(TypeEvent.INTERRUPTED, TypeEvent.WAIT_END.toString()));
	}
}