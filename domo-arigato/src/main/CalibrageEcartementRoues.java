package main;

import actions.ActionFactory;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import robot.Robot;
import robot.StartPosition;

public class CalibrageEcartementRoues {
    public static void main(String[] args) {
    	Robot robot = Robot.getInstance();
    	robot.initSensors(SensorPort.S1, SensorPort.S2);
    	robot.initMotors(Motor.C, Motor.A, Motor.B, StartPosition.midle);

    	float distance1 = 10.0f;
    	float distance2 = 20.0f;
    	ActionFactory.goForward(distance1, false);
		Button.ENTER.waitForPressAndRelease();
    	ActionFactory.goForward(distance2, false);
    }
}