package actions;

import robot.Robot;

class UseClaws implements Runnable {
	private float clawsState;
	
	UseClaws(float clawsState, boolean createThread) {
		this.clawsState = clawsState;
		if(createThread) {
			Thread thread = new Thread(this);
			thread.start();
		}
		else
			run();
	}
	
	public void run() {
		Robot.getInstance().getClaws().setState(clawsState, false);
		Robot.getInstance().warn(new Event(TypeEvent.USECLAWSEND));
	}
}
