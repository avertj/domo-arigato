package robot;

import actions.Event;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

public class Robot {
	private static Robot INSTANCE = new Robot();
	private Sonar sonar;
	private Claws claws;
	private Motion motion;
	private EventListener eventListener;
	private Thread brain;
	
	public static Robot getInstance() {
		return INSTANCE;
	}
	
	private Robot() {
		sonar = new Sonar();
		claws = new Claws();
		motion = new Motion();
	}
	
	public void initMotors(NXTRegulatedMotor leftWheel, NXTRegulatedMotor rightWheel, NXTRegulatedMotor claws) {
		this.claws.setClawsMotor(claws);
		this.motion.setWheelMotors(leftWheel, rightWheel);
	}
	
	public void initSensors(SensorPort sonar) {
		this.sonar.initSonar(sonar);
	}
	
	public Claws getClaws() {
		return claws;
	}
	
	public Sonar getSonar() {
		return sonar;
	}
	
	public Motion getMotion() {
		return motion;
	}
	
	/**
	 * Use this if you want to use an eventListener.
	 * @param eventListener The Behavior (state machine).
	 */
	public void changeEventListener(EventListener eventListener) {
		if(brain != null && brain.isAlive()) {
			brain.interrupt();
			this.eventListener.stop();
		}
		this.eventListener = eventListener;
		if(eventListener != null) {
			brain = new Thread(eventListener);
			brain.start();
		}
	}
	
	/**
	 * Use this to prevent the eventListener (state machine) of an event.
	 * It might change its state or do something.
	 * @param event The event which just occurred.
	 * @return true if there is an eventListener, false otherwise.
	 */
	public boolean warn(Event event) {
		if(eventListener == null || brain == null)
			return false;
		brain.interrupt();
		eventListener.synchronizedWarn(event);
		return true;
	}
}