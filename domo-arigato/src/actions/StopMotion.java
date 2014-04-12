package actions;

import robot.Robot;

class StopMotion extends RunnableRobot {
	
	StopMotion(boolean createThread) {
		if(createThread) {
			Thread thread = new Thread(this);
			thread.start();
		}
		else
			run();
	}
	
	public void run() {
		Robot.getInstance().getMotion().getPilot().stop();
		Robot.getInstance().getMotion().getRunnableRobot().interrupt();
	}
}
