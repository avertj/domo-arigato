package actions;

import lejos.util.Delay;
import robot.Robot;

class GoForward extends RunnableRobot {
	private int duration;
	
	GoForward(int duration, boolean createThread) {
		this.duration = duration;
		if(createThread) {
			if(Robot.getInstance().getMotion().getRunnableRobot() != null)
				Robot.getInstance().getMotion().getRunnableRobot().interrupt();
			Robot.getInstance().getMotion().setRunnableRobot(this);
			Thread thread = new Thread(this);
			thread.start();
		}
		else
			run();
	}
	
	public void run() {
		Robot.getInstance().getMotion().getPilot().forward();
		Delay.msDelay(duration);
		if(!getInterrupted()) {
			Robot.getInstance().getMotion().getPilot().stop();
			Robot.getInstance().warn(new Event(TypeEvent.GOFORWARDEND));
		}
	}
}
