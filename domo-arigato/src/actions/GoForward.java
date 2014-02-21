package actions;

import lejos.util.Delay;
import robot.Robot;

class GoForward implements Runnable {
	private int duration;
	
	GoForward(int duration, boolean createThread) {
		this.duration = duration;
		if(createThread) {
			Thread thread = new Thread(this);
			thread.start();
		}
		else
			run();
	}
	
	public void run() {
		Robot.getInstance().getMotion().getPilot().forward();
		Delay.msDelay(duration);
		Robot.getInstance().getMotion().getPilot().stop();
		Robot.getInstance().warn(new Event(TypeEvent.GOFORWARDEND));
	}
}
