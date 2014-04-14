package robot;

import actions.Event;
import actions.TypeEvent;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.Pose;

/**
 * This class represent the Robot and this is a singleton accessible from everywhere by getInstance()
 * you must initiate initMotors, initSensor and changeEventListener to make it work.
 */
public class Robot {
	private static Robot INSTANCE = new Robot();
	private Sonar sonar;
	private Claws claws;
	private Motion motion;
	private EventListener eventListener;
	private Thread brain;
	private OdometryPoseProvider opp;
	private ColorEyes eyes;
	private Bumper bumper;
	
	public static Robot getInstance() {
		return INSTANCE;
	}
	
	private Robot() {
		sonar = new Sonar();
		claws = new Claws();
		motion = new Motion();
		eyes = new ColorEyes();
		bumper = new Bumper();
		
	}
	
	/**
	 * This call initiate the Motors of the robot.
	 * @param leftWheel leftWheel port.
	 * @param rightWheel rightWheel port.
	 * @param claws claws port.
	 * @param position left, middle or right.
	 */
	public void initMotors(NXTRegulatedMotor leftWheel, NXTRegulatedMotor rightWheel, NXTRegulatedMotor claws, StartPosition position) {
		this.claws.setClawsMotor(claws);
		this.motion.setWheelMotors(leftWheel, rightWheel);
		opp = new OdometryPoseProvider(motion.getPilot());
		Pose pose;
		switch(position)
		{
		case left :
			pose = new Pose(-50.0f, -120.0f, 90.0f);
			break;
		case middle :
			pose = new Pose(0.0f, -120.0f, 90.0f);
			break;
		default :
			pose = new Pose(50.0f, -120.0f, 90.0f);
			break;
		}
		opp.setPose(pose);
	}
	
	public OdometryPoseProvider getOdometryPoseProvider() {
		return opp;
	}
	
	/**
	 * This call initiate the sensors of the robot.
	 * @param sonar sonar port.
	 * @param colorEyes colorEyes port.
	 * @param bumper bumper port.
	 */
	public void initSensors(SensorPort sonar, SensorPort colorEyes, SensorPort bumper) {
		this.sonar.initSonar(sonar);
		this.eyes.initEyes(colorEyes);
		this.bumper.initBumper(bumper);
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
	
	public ColorEyes getEyes() {
		return eyes;
	}
	
	public Bumper getBumper() {
		return bumper;
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
		if(event.getTypeEvent().equals(TypeEvent.SHUTDOWN)) {
			eyes.closeEyes();
			sonar.closeSonar();
		}
		if(eventListener == null || brain == null)
			return false;
		brain.interrupt();
		eventListener.synchronizedWarn(event);
		return true;
	}
}