package main;

import actions.ActionFactory;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.Pose;
import robot.Robot;
import robot.StartPosition;

public class CalibEcartRoues {
    public static void main(String[] args) {
    	Robot robot = Robot.getInstance();
    	robot.initSensors(SensorPort.S1, SensorPort.S4);
    	robot.initMotors(Motor.C, Motor.A, Motor.B, StartPosition.midle);

    	Test2Thread behavior = new Test2Thread();
    	robot.changeEventListener(behavior);
    	
    	Pose p = new Pose(0, 0, 0);
    	Robot.getInstance().getOdometryPoseProvider().setPose(p);
    	ActionFactory.rotate(45.0f, false);
		Button.ENTER.waitForPressAndRelease();
    	ActionFactory.rotate(45.0f, false);
		Button.ENTER.waitForPressAndRelease();
    	ActionFactory.rotate(45.0f, false);
		Button.ENTER.waitForPressAndRelease();
    	ActionFactory.rotate(45.0f, false);
		Button.ENTER.waitForPressAndRelease();
    	ActionFactory.rotate(45.0f, false);
    	ActionFactory.rotate(45.0f, false);
    	ActionFactory.rotate(45.0f, false);
    	ActionFactory.rotate(45.0f, false);
    	System.out.println("X : " + robot.getOdometryPoseProvider().getPose().getX());
    	System.out.println("Y : " + robot.getOdometryPoseProvider().getPose().getY());
    	System.out.println("Heading : " + robot.getOdometryPoseProvider().getPose().getHeading());
		Button.ENTER.waitForPressAndRelease();
    }
}