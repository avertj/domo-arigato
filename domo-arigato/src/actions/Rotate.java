package actions;

import robot.Robot;

class Rotate extends RunnableRobot {
	private float angle;
	
	Rotate(float angle, boolean createThread) {
		this.angle = angle;
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
		Robot.getInstance().getMotion().getPilot().rotate(angle, false);
		if(!getInterrupted()) {
			Robot.getInstance().warn(new Event(TypeEvent.ROTATEEND));
		}
	}
}
