package actions;

import lejos.robotics.navigation.Pose;
import robot.Robot;

class StraightMove extends RunnableRobot {
	private Pose pose;
	
	StraightMove(Pose pose, boolean createThread) {
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
		Robot.getInstance().getMotion().getPilot().rotate(Robot.getInstance().getOdometryPoseProvider().getPose().angleTo(pose.getLocation()), true);
		while(Robot.getInstance().getMotion().getPilot().isMoving()) {
			if(!getInterrupted()) {
				Thread.yield();
			}
			else
				break;
		}
		if(!getInterrupted()) {
			Robot.getInstance().getMotion().getPilot().travel(Robot.getInstance().getOdometryPoseProvider().getPose().distanceTo(pose.getLocation()), true);
			while(Robot.getInstance().getMotion().getPilot().isMoving()) {
				if(!getInterrupted()) {
					Thread.yield();
				}
				else
					break;
			}
			if(!getInterrupted()) {
				Robot.getInstance().getMotion().getPilot().rotate(pose.getHeading() - Robot.getInstance().getOdometryPoseProvider().getPose().getHeading(), true);
				while(Robot.getInstance().getMotion().getPilot().isMoving()) {
					if(!getInterrupted()) {
						Thread.yield();
					}
					else
						break;
				}
				if(!getInterrupted()) {
					Robot.getInstance().warn(new Event(TypeEvent.STRAIGHTMOVEEND));
				}
				else
					Robot.getInstance().warn(new Event(TypeEvent.INTERRUPTED, TypeEvent.GOFORWARDEND.toString()));
			}
			else
				Robot.getInstance().warn(new Event(TypeEvent.INTERRUPTED, TypeEvent.GOFORWARDEND.toString()));
		}
		else
			Robot.getInstance().warn(new Event(TypeEvent.INTERRUPTED, TypeEvent.GOFORWARDEND.toString()));
	}
}
