package tests;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorHTSensor;
import lejos.util.Delay;
import robot.Robot;
import robot.StartPosition;

public class TestColorSensor {
    public static void main(String[] args) {
    	Robot robot = Robot.getInstance();
    	//robot.initSensors(SensorPort.S1, SensorPort.S4);
    	robot.initMotors(Motor.C, Motor.A, Motor.B, StartPosition.middle);
    	ColorHTSensor cs = new ColorHTSensor(SensorPort.S4);
        Button.ENTER.waitForPressAndRelease();
    	System.out.println(cs.initBlackLevel());
        Button.ENTER.waitForPressAndRelease();
        System.out.println(cs.initWhiteBalance());
        Button.ENTER.waitForPressAndRelease();
        System.out.println(cs.getColor().getRed());
        Button.ENTER.waitForPressAndRelease();
        System.out.println(cs.getColor().getRed());
        Button.ENTER.waitForPressAndRelease();
        System.out.println(cs.getColor().getRed());
    	while(true) {
        	Button.ENTER.waitForPressAndRelease();
    		LightSensor ls = new LightSensor(SensorPort.S4);
    		System.out.println(SensorPort.S4.readRawValue());
    		System.out.println(SensorPort.S4.readValue());
    	}
    }
}