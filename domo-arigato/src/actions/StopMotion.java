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
		if(Robot.getInstance().getMotion().getRunnableRobot() != null)
			Robot.getInstance().getMotion().getRunnableRobot().interrupt();
		Robot.getInstance().getMotion().setRunnableRobot(null);
		Robot.getInstance().getMotion().getPilot().stop();
	}
}
