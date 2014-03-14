package main;

import actions.ActionFactory;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import robot.Robot;
import robot.StartPosition;

public class CalibTailleRoues {
    public static void main(String[] args) {
    	Robot robot = Robot.getInstance();
    	robot.initSensors(SensorPort.S1, SensorPort.S2);
    	robot.initMotors(Motor.C, Motor.A, Motor.B, StartPosition.midle);

    	ActionFactory.rotate(90.0f, false);
		Button.ENTER.waitForPressAndRelease();
    	ActionFactory.rotate(180.0f, false);
    }
}