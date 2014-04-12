package main;

import actions.ActionFactory;
import actions.Event;
import actions.TypeEvent;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import robot.Robot;
import robot.StartPosition;

public class Main {
    public static void main(String[] args) {
    	Robot robot = Robot.getInstance();
    	robot.initSensors(SensorPort.S1, SensorPort.S4, SensorPort.S3);
    	robot.initMotors(Motor.C, Motor.A, Motor.B, StartPosition.middle);
    	
    	//Test2Thread behavior = new Test2Thread();
    	//TestDodo behavior = new TestDodo();
    	TrouverPalet behavior = new TrouverPalet();
    	robot.changeEventListener(behavior);
    	
    	Button.ENTER.waitForPressAndRelease();
    	ActionFactory.useClaws(1.0f, false);
    	robot.warn(new Event(TypeEvent.SHUTDOWN));
    }
}