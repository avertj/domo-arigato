package main;

import actions.ActionFactory;
import actions.Event;
import actions.TypeEvent;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import robot.Robot;
import robot.StartPosition;
import tests.TestChildBehavior;
import tests.TestDodgeBehavior;
import tests.TestMusic;
import tests.TestScore;

public class Main {
    public static void main(String[] args) {
    	Robot robot = Robot.getInstance();
    	robot.initSensors(SensorPort.S1, SensorPort.S4, SensorPort.S3);
    	robot.initMotors(Motor.C, Motor.A, Motor.B, StartPosition.middle);
    	
    	TestMusic behavior = new TestMusic();
    	//TestDodgeBehavior behavior = new TestDodgeBehavior();
    	//TestScore behavior = new TestScore();
    	//TestChildBehavior behavior = new TestChildBehavior();
    	//Alignement behavior = new Alignement();
    	//Test2Thread behavior = new Test2Thread();
    	//TestDodo behavior = new TestDodo();
    	//TrouverPalet behavior = new TrouverPalet();
    	//PousserPalet behavior=new PousserPalet();
    	robot.changeEventListener(behavior);
    	
    	Button.ENTER.waitForPressAndRelease();
    	ActionFactory.useClaws(1.0f, false);
    	robot.warn(new Event(TypeEvent.SHUTDOWN));
    }
}