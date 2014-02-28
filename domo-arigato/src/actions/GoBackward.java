package actions;

import lejos.util.Delay;
import robot.Robot;

class GoBackward extends RunnableRobot {
	private int duration;
	
	GoBackward(int duration, boolean createThread) {
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
		Robot.getInstance().getMotion().getPilot().backward();
		Delay.msDelay(duration);
		if(!getInterrupted()) {
			Robot.getInstance().getMotion().getPilot().stop();
			Robot.getInstance().warn(new Event(TypeEvent.GOBACKWARDEND));
		}
		else
			Robot.getInstance().warn(new Event(TypeEvent.INTERRUPTED, TypeEvent.GOBACKWARDEND.toString()));
	}
}
