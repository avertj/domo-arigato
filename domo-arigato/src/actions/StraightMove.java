package actions;

import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.Pose;
import robot.Robot;

class StraightMove extends RunnableRobot {
	private Pose pose;
	private OdometryPoseProvider odo;
	
	StraightMove(Pose pose, boolean createThread) {
		this.pose = pose;
		odo = Robot.getInstance().getOdometryPoseProvider();
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
		Robot.getInstance().getMotion().getPilot().rotate(odo.getPose().relativeBearing(pose.getLocation()), true);
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
					Robot.getInstance().warn(new Event(TypeEvent.STRAIGHTMOVE_END));
				}
				else
					Robot.getInstance().warn(new Event(TypeEvent.INTERRUPTED, TypeEvent.STRAIGHTMOVE_END.toString()));
			}
			else
				Robot.getInstance().warn(new Event(TypeEvent.INTERRUPTED, TypeEvent.STRAIGHTMOVE_END.toString()));
		}
		else
			Robot.getInstance().warn(new Event(TypeEvent.INTERRUPTED, TypeEvent.STRAIGHTMOVE_END.toString()));
	}
}
