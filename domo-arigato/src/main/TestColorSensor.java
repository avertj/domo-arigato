package main;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import robot.Robot;
import robot.StartPosition;

public class TestColorSensor {
    public static void main(String[] args) {
    	Robot robot = Robot.getInstance();
    	robot.initSensors(SensorPort.S1, SensorPort.S4);
    	robot.initMotors(Motor.C, Motor.A, Motor.B, StartPosition.midle);
    	
    	while(true) {
    		System.out.println("Red : "+robot.getEyes().getColor().getRed());
    		System.out.println("Green : "+robot.getEyes().getColor().getGreen());
    		System.out.println("Blue : "+robot.getEyes().getColor().getBlue());
    		System.out.println(robot.getEyes().cs.getColorID());
    		Button.ENTER.waitForPressAndRelease();
    	}
    }
}