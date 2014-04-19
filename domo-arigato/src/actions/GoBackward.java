package actions;

import lejos.util.Delay;
import robot.Robot;

class GoBackward extends RunnableRobot {
	private int duration;
	private float distance;
	private boolean neg;
	
	GoBackward(int duration, boolean createThread) {
		this.duration = duration;
		if(createThread) {
			if(Robot.getInstance().getMotion().getRunnableRobot() != null) {
				System.out.println("BackInter");
				Robot.getInstance().getMotion().getRunnableRobot().interrupt();
			}
			Robot.getInstance().getMotion().setRunnableRobot(this);
			Thread thread = new Thread(this);
			thread.start();
		}
		else
			run();
	}
	
	GoBackward(float distance, boolean createThread, boolean neg) {
		this.neg = neg;
		this.duration = -1;
		this.distance = distance;
		if(createThread) {
			if(Robot.getInstance().getMotion().getRunnableRobot() != null) {
				System.out.println("BackInter");
				Robot.getInstance().getMotion().getRunnableRobot().interrupt();
			}
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
				Robot.getInstance().getMotion().setRunnableRobot(null);
				Robot.getInstance().warn(new Event(TypeEvent.GOBACKWARD_END));
			}
			else {
				Robot.getInstance().warn(new Event(TypeEvent.INTERRUPTED, TypeEvent.GOBACKWARD_END.toString()));
			}
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
				Robot.getInstance().getMotion().setRunnableRobot(null);
				if(!neg) {
					Robot.getInstance().warn(new Event(TypeEvent.GOBACKWARD_END));
					System.out.println("BackEnd");
				}
				else {
					Robot.getInstance().warn(new Event(TypeEvent.GOFORWARD_END));
					System.out.println("BackEndNeg");
				}
			}
			else {
				System.out.println("BackInterrupted");
				if(!neg)
					Robot.getInstance().warn(new Event(TypeEvent.INTERRUPTED, TypeEvent.GOBACKWARD_END.toString()));
				else
					Robot.getInstance().warn(new Event(TypeEvent.INTERRUPTED, TypeEvent.GOFORWARD_END.toString()));
			}
		}
	}
}
