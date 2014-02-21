package actions;

import robot.Robot;

class Rotate implements Runnable {
	private float angle;
	
	Rotate(float angle, boolean createThread) {
		this.angle = angle;
		if(createThread) {
			Thread thread = new Thread(this);
			thread.start();
		}
		else
			run();
	}
	
	public void run() {
		Robot.getInstance().getMotion().getPilot().rotate(angle, false);
		Robot.getInstance().warn(new Event(TypeEvent.ROTATEEND));
	}
}
