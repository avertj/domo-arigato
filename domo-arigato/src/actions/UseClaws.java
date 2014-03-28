package actions;

import robot.Robot;

class UseClaws extends RunnableRobot {
	private float clawsState;
	
	UseClaws(float clawsState, boolean createThread) {
		this.clawsState = clawsState;
		if(createThread) {
			if(Robot.getInstance().getClaws().getRunnableRobot() != null)
				Robot.getInstance().getClaws().getRunnableRobot().interrupt();
			Robot.getInstance().getClaws().setRunnableRobot(this);
			Thread thread = new Thread(this);
			thread.start();
		}
		else
			run();
	}
	
	public void run() {
		Robot.getInstance().getClaws().setState(clawsState, false);
		if(!getInterrupted()) {
			Robot.getInstance().warn(new Event(TypeEvent.USECLAWSEND));
		}
		else
			Robot.getInstance().warn(new Event(TypeEvent.INTERRUPTED, TypeEvent.USECLAWSEND.toString()));
	}
}
