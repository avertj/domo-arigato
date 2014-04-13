package actions;

import lejos.util.Delay;
import robot.Robot;

class ArcMove extends RunnableRobot {
	private boolean forward;
	private float angle;
	private float radius;
	
	ArcMove(boolean forward, float radius, boolean createThread) {
		angle = 0;
		this.radius = radius;
		this.forward = forward;
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
	
	ArcMove(float angle, float radius, boolean createThread) {
		this.radius = radius;
		this.forward = false;
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
		if(angle == 0) {
			if(forward)
				Robot.getInstance().getMotion().getPilot().arcForward(radius);
			else
				Robot.getInstance().getMotion().getPilot().arcBackward(radius);
			Delay.msDelay(10000);
			if(!getInterrupted()) {
				Robot.getInstance().getMotion().getPilot().stop();
				Robot.getInstance().warn(new Event(TypeEvent.ARC_END));
			}
			else
				Robot.getInstance().warn(new Event(TypeEvent.INTERRUPTED, TypeEvent.ARC_END.toString()));
		}
		else {
			Robot.getInstance().getMotion().getPilot().arc(radius, angle, true);
			while(Robot.getInstance().getMotion().getPilot().isMoving()) {
				if(!getInterrupted()) {
					Thread.yield();
				}
				else
					break;
			}
			if(!getInterrupted()) {
				Robot.getInstance().warn(new Event(TypeEvent.ARC_END));
			}
			else
				Robot.getInstance().warn(new Event(TypeEvent.INTERRUPTED, TypeEvent.ARC_END.toString()));
		}
	}
}
