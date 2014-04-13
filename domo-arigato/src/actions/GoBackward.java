package actions;

import lejos.util.Delay;
import robot.Robot;

class GoBackward extends RunnableRobot {
	private int duration;
	private float distance;
	
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
	
	GoBackward(float distance, boolean createThread) {
		this.duration = -1;
		this.distance = distance;
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
		if(duration != -1) {
			Robot.getInstance().getMotion().getPilot().backward();
			Delay.msDelay(duration);
			if(!getInterrupted()) {
				Robot.getInstance().getMotion().getPilot().stop();
				Robot.getInstance().warn(new Event(TypeEvent.GOBACKWARD_END));
			}
			else
				Robot.getInstance().warn(new Event(TypeEvent.INTERRUPTED, TypeEvent.GOBACKWARD_END.toString()));
		}
		else {
			Robot.getInstance().getMotion().getPilot().travel(-distance, true);
			while(Robot.getInstance().getMotion().getPilot().isMoving()) {
				if(!getInterrupted()) {
					Thread.yield();
				}
				else
					break;
			}
			if(!getInterrupted()) {
				Robot.getInstance().warn(new Event(TypeEvent.GOBACKWARD_END));
			}
			else
				Robot.getInstance().warn(new Event(TypeEvent.INTERRUPTED, TypeEvent.GOBACKWARD_END.toString()));
		}
	}
}
