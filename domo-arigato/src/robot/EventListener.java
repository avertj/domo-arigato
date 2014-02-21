package robot;

import actions.Event;

public abstract class EventListener implements Runnable {
	protected int AUTO_AWAKE_TIME = 1000;
	private static final Object lock = new Object();
	private boolean end = false;
	private boolean autoWake;
	
	/**
	 * This method is call after the end of an action or can be call at other moments.
	 * </br>Use it to upload you state.
	 * @param event The event that just happened.
	 */
	public abstract void warn(Event event);
	
	/**
	 * This method is call every AUTO_AWAKE_TIME milliseconds or after every call of warn(Event event).
	 * This is where you should do what you state have to do.
	 */
	protected abstract void act();
	
	public void run() {
		while(!end) {
			act();
			autoWake = true;
	        try {
	            Thread.interrupted();
	            Thread.sleep(AUTO_AWAKE_TIME);
	        }
	        catch (InterruptedException e) {
	        	autoWake = false;
	        }
		}
	}
	
	void synchronizedWarn(Event event) {
		synchronized(lock) {
			warn(event);
		}
	}
	
	/**
	 * Call this method to end the infinite loop of the EventListener
	 */
	public void stop() {
		end = true;
		Robot.getInstance().changeEventListener(null);
	}
	
	public boolean getAutoWake() {
		return autoWake;
	}
}
