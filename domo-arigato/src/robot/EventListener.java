package robot;

import actions.Event;

public abstract class EventListener implements Runnable {
	private static final Object lock = new Object();
	private boolean end = false;
	private volatile boolean ignore = false;
	
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
	
	public void ignore() {
		ignore = true;
	}
	
	public void run() {
		act();
		while(!end) {
			Thread.yield();
		}
	}
	
	void synchronizedWarn(Event event) {
		synchronized(lock) {
			warn(event);
			if(!ignore)
				act();
			ignore = false;
		}
	}
	
	/**
	 * Call this method to end the infinite loop of the EventListener
	 */
	public void stop() {
		end = true;
		Robot.getInstance().changeEventListener(null);
	}
}
