package actions;

import lejos.util.Delay;
import robot.Robot;

class GoForward extends RunnableRobot {
	private int duration;
	private float distance;
	
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
	
	GoForward(float distance, boolean createThread) {
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
			Robot.getInstance().getMotion().getPilot().forward();
			Delay.msDelay(duration);
			if(!getInterrupted()) {
				Robot.getInstance().getMotion().getPilot().stop();
				Robot.getInstance().warn(new Event(TypeEvent.GOFORWARD_END));
			}
			else
				Robot.getInstance().warn(new Event(TypeEvent.INTERRUPTED, TypeEvent.GOFORWARD_END.toString()));
		}
		else {
			Robot.getInstance().getMotion().getPilot().travel(distance, true);
			while(Robot.getInstance().getMotion().getPilot().isMoving()) {
				if(!getInterrupted()) {
					Thread.yield();
				}
				else
					break;
			}
			if(!getInterrupted()) {
				Robot.getInstance().warn(new Event(TypeEvent.GOFORWARD_END));
			}
			else
				Robot.getInstance().warn(new Event(TypeEvent.INTERRUPTED, TypeEvent.GOFORWARD_END.toString()));
		}
	}
}
