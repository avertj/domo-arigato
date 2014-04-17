package main;

import actions.ActionFactory;
import actions.Event;
import actions.TypeEvent;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import robot.Robot;
import robot.StartPosition;
import tests.CalibLight;
import tests.Test3Thread;
import tests.TestChildBehavior;
import tests.TestDodgeBehavior;
import tests.TestFollowLineBehavior;
import tests.TestGoToBehavior;
import tests.TestMusic;
import tests.TestScore;

public class Main {
    public static void main(String[] args) {
    	Robot robot = Robot.getInstance();
    	robot.initSensors(SensorPort.S1, SensorPort.S4, SensorPort.S3);
    	robot.initMotors(Motor.C, Motor.A, Motor.B, StartPosition.middle);
    	
    	NaiveStartBehavior behavior = new NaiveStartBehavior();
    	//CalibLight behavior = new CalibLight();
    	//TestFollowLineBehavior behavior = new TestFollowLineBehavior();
    	//TestGoToBehavior behavior = new TestGoToBehavior();
    	//TestMusic behavior = new TestMusic();
    	//TestDodgeBehavior behavior = new TestDodgeBehavior();
    	//TestScore behavior = new TestScore();
    	//TestChildBehavior behavior = new TestChildBehavior();
    	//Alignement behavior = new Alignement();
    	//Test3Thread behavior = new Test3Thread();
    	//TestDodo behavior = new TestDodo();
    	//TrouverPalet behavior = new TrouverPalet();
    	//PousserPalet behavior=new PousserPalet();
    	robot.changeEventListener(behavior);
    	
    	Button.ENTER.waitForPressAndRelease();
    	ActionFactory.useClaws(1.0f, false);
    	robot.warn(new Event(TypeEvent.SHUTDOWN));
    }
}