package robot;

import actions.RunnableRobot;
import lejos.nxt.NXTRegulatedMotor;
import lejos.util.Delay;

/**
 * The claws of the robot (supposed to be opened at the beginning).
 */
public class Claws {
	private final float TOTAL_DELAY = 210;
	private float state;
	private NXTRegulatedMotor clawsMotor;
	private RunnableRobot thread;
	
	Claws() {
		state = 1.0f;
	}
	
	/**
	 * Use this to know the current state of the claws.
	 * @return the opening percentage.</br> 0.0f for close and 1.0f for open.
	 */
	public float getState() {
		return state;
	}
	
	/**
	 * Use this to initialize the claws. Call this before using the claws.
	 * @param clawsMotor The NXTRegulatedMotor linked to the claws.
	 */
	public void setClawsMotor(NXTRegulatedMotor clawsMotor) {
		this.clawsMotor = clawsMotor;
	}
	
	/**
	 * Use this to change the closure or the opening of the claws.
	 * @param state the new state of the claws. 0.0f for close and 1.0f for open.
	 * @param waitStop true if you don't want to wait the end of the stop, false otherwise.
	 */
	public void setState(float newState, boolean waitStop) {
		if(newState < 0.0f)
			newState = 0.0f;
		else if(newState > 1.0f)
			newState = 1.0f;
		if(state > newState)
			clawsMotor.backward();
		else
			clawsMotor.forward();
    	Delay.msDelay((long) (TOTAL_DELAY * (Math.abs(state - newState))));
    	state = newState;
    	clawsMotor.stop(waitStop);
	}
	
	/**
	 * Use this to close the claws.
	 * @param immediateReturn true if you don't want to wait the end of the closure, false otherwise.
	 */
	public void close(boolean immediateReturn) {
		setState(0.0f, immediateReturn);
	}

	/**
	 * Use this to open the claws.
	 * @param immediateReturn true if you don't want to wait the end of the opening, false otherwise.
	 */
	public void open(boolean immediateReturn) {
		setState(1.0f, immediateReturn);
	}
	
	public void setRunnableRobot(RunnableRobot thread) {
		this.thread = thread;
	}
	
	public RunnableRobot getRunnableRobot() {
		return thread;
	}
}
