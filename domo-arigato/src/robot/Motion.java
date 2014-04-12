package robot;

import actions.RunnableRobot;
import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 *The legs of the robot.
 */
public class Motion {
	private NXTRegulatedMotor leftWheel;
	private NXTRegulatedMotor rightWheel;
	private float wheelDiameter;
	private float trackWidth;
	private DifferentialPilot pilot;
	private RunnableRobot thread;
	
	Motion() {
		wheelDiameter = 5.6f;
		trackWidth = 11.37f;
	}

	/**
	 * Use this to initialize the wheels. Call this before using the wheels.
	 * @param leftWheel The NXTRegulatedMotor linked to the left wheel.
	 * @param rightWheel The NXTRegulatedMotor linked to the right wheel.
	 */
	public void setWheelMotors(NXTRegulatedMotor leftWheel, NXTRegulatedMotor rightWheel) {
		this.leftWheel = leftWheel;
		this.rightWheel = rightWheel;
		pilot = new DifferentialPilot(wheelDiameter, trackWidth, leftWheel, rightWheel);
	}
	
	/**
	 * Use this if you fon't have our standard size on your track an wheels
	 * @param wheelDiameter The wheel diameter.
	 * @param trackWidth T track width.
	 */
	public void setProperties(float wheelDiameter, float trackWidth) {
		this.wheelDiameter = wheelDiameter;
		this.trackWidth = trackWidth;
		if(leftWheel != null && rightWheel != null)
			pilot = new DifferentialPilot(wheelDiameter, trackWidth, leftWheel, rightWheel);
	}

	public DifferentialPilot getPilot() {
		return pilot;
	}
	
	public void setRunnableRobot(RunnableRobot thread) {
		this.thread = thread;
	}
	
	public RunnableRobot getRunnableRobot() {
		return thread;
	}
}
