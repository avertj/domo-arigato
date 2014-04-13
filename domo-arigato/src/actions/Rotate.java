package actions;

import lejos.robotics.navigation.Pose;
import robot.Robot;

class Rotate extends RunnableRobot {
	private float angle;
	private Pose pose;
	private boolean pointCall;
	
	Rotate(float angle, boolean createThread) {
		pointCall = false;
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
	
	Rotate(Pose pose, boolean createThread) {
		pointCall = true;
		this.pose = pose;
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
		if(pointCall)
			Robot.getInstance().getMotion().getPilot().rotate(Robot.getInstance().getOdometryPoseProvider().getPose().relativeBearing(pose.getLocation()), true);
		else
			Robot.getInstance().getMotion().getPilot().rotate(angle, true);
		while(Robot.getInstance().getMotion().getPilot().isMoving()) {
			if(!getInterrupted()) {
				Thread.yield();
			}
			else
				break;
		}
		if(!getInterrupted()) {
			Robot.getInstance().warn(new Event(TypeEvent.ROTATE_END));
		}
		else
			Robot.getInstance().warn(new Event(TypeEvent.INTERRUPTED, TypeEvent.ROTATE_END.toString()));
	}
}
