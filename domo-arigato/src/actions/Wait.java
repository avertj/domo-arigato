package actions;

import lejos.util.Delay;
import robot.Robot;

class Wait implements Runnable {
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
		Robot.getInstance().warn(new Event(TypeEvent.WAITEND, name));
	}
}