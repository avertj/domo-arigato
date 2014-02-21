package actions;

import lejos.util.Delay;
import robot.Robot;

class GoBackward implements Runnable {
	private int duration;
	
	GoBackward(int duration, boolean createThread) {
		this.duration = duration;
		if(createThread) {
			Thread thread = new Thread(this);
			thread.start();
		}
		else
			run();
	}
	
	public void run() {
		Robot.getInstance().getMotion().getPilot().backward();
		Delay.msDelay(duration);
		Robot.getInstance().getMotion().getPilot().stop();
		Robot.getInstance().warn(new Event(TypeEvent.GOBACKWARDEND));
	}
}
