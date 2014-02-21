package actions;

import lejos.util.Delay;
import robot.Robot;

class Wait implements Runnable {
	private int duration;
	
	Wait(int duration, boolean createThread) {
		this.duration = duration;
		if(createThread) {
			Thread thread = new Thread(this);
			thread.start();
		}
		else
			run();
	}
	
	public void run() {
		Delay.msDelay(duration);
		Robot.getInstance().warn(new Event(TypeEvent.WAITEND));
	}
}