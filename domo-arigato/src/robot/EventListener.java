package robot;

import actions.Event;
import actions.TypeEvent;

public abstract class EventListener implements Runnable {
	private static final Object lock = new Object();
	private boolean end = false;
	private boolean ignore = false;
	private EventListener childBehavior = null;
	private EventListener fatherBehavior = null;
	protected String[] args;
	
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
		if(event != null && event.getTypeEvent().equals(TypeEvent.CHILDBEHAVIOR_END)) {
			childBehavior = null;
		}
		synchronized(lock) {
			if(childBehavior != null) {
				childBehavior.synchronizedWarn(event);
			}
			else {
				warn(event);
				if(!ignore)
					act();
				ignore = false;
			}
		}
	}
	
	/**
	 * Call this method to end the infinite loop of the EventListener
	 */
	public void stop() {
		end = true;
		if(fatherBehavior == null)
			Robot.getInstance().changeEventListener(null);
		else
			fatherBehavior.synchronizedWarn(new Event(TypeEvent.CHILDBEHAVIOR_END));
	}
	
	/**
	 * Call this method to end the infinite loop of the EventListener
	 */
	public void stop(String message) {
		end = true;
		if(fatherBehavior == null)
			Robot.getInstance().changeEventListener(null);
		else
			fatherBehavior.synchronizedWarn(new Event(TypeEvent.CHILDBEHAVIOR_END, message));
	}
	
	protected void doBehavior(EventListener child) {
		child.fatherBehavior = this;
		childBehavior = child;
		child.act();
	}
}
