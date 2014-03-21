package main;

import actions.ActionFactory;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.Pose;
import robot.Robot;
import robot.StartPosition;

public class CalibTailleRoues {
    public static void main(String[] args) {
    	Robot robot = Robot.getInstance();
    	robot.initSensors(SensorPort.S1, SensorPort.S4);
    	robot.initMotors(Motor.C, Motor.A, Motor.B, StartPosition.midle);

    	float distance1 = 60.0f;
    	float distance2 = 120.0f;
    	ActionFactory.goForward(distance1, false);
		Button.ENTER.waitForPressAndRelease();
    	ActionFactory.goForward(distance2, false);
		Button.ENTER.waitForPressAndRelease();
    	Pose p = new Pose(0, 0, 0);
    	robot.getOdometryPoseProvider().setPose(p);
    	p = new Pose(30, 30, 100);
    	ActionFactory.straightMove(p, false);
    	System.out.println("X : " + robot.getOdometryPoseProvider().getPose().getX());
    	System.out.println("Y : " + robot.getOdometryPoseProvider().getPose().getY());
    	System.out.println("Heading : " + robot.getOdometryPoseProvider().getPose().getHeading());
		Button.ENTER.waitForPressAndRelease();
    }
}