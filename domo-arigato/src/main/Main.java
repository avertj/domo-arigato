package main;

import actions.ActionFabrique;
import actions.Event;
import actions.TypeEvent;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import robot.Robot;

public class Main {
    public static void main(String[] args) {
    	Robot robot = Robot.getInstance();
    	robot.initSensors(SensorPort.S1);
    	robot.initMotors(Motor.C, Motor.A, Motor.B);
    	
    	TestThread behavior = new TestThread();
    	robot.changeEventListener(behavior);
    	
    	Button.ENTER.waitForPressAndRelease();
    	ActionFabrique.useClaws(1.0f, false);
    	robot.warn(new Event(TypeEvent.SHUTDOWN));
    }
}