package robot;

import actions.Event;
import actions.TypeEvent;

public abstract class EventListener {
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
	
	/**
	 * Don't cheat !!! only the Robot have the right to use this.
	 */
	void doAct(){
		act();
	}
	
	public void ignore() {
		ignore = true;
	}
	
	/**
	 * Don't use this, the robot use this method to warn you when an action is over.
	 * @param event
	 */
	void synchronizedWarn(Event event) {
		if(event != null && event.getTypeEvent().equals(TypeEvent.CHILDBEHAVIOR_END)) {
			childBehavior = null;
		}
		synchronized(lock) {
			if(!end) {
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
	}
	
	/**
	 * Call this method to end the current Behavior. If it's a child behavior, it will send a CHILDBEHAVIOR_END with no name to his father.
	 */
	protected void stop() {
		if(fatherBehavior == null)
			Robot.getInstance().changeEventListener(null);
		else
			fatherBehavior.synchronizedWarn(new Event(TypeEvent.CHILDBEHAVIOR_END));
		end = true;
	}

	/**
	 * Call this method to end the current Behavior. If it's a child behavior, it will send a CHILDBEHAVIOR_END with a message to his father.
	 * @param message The message.
	 */
	protected void stop(String message) {
		end = true;
		if(fatherBehavior == null)
			Robot.getInstance().changeEventListener(null);
		else
			fatherBehavior.synchronizedWarn(new Event(TypeEvent.CHILDBEHAVIOR_END, message));
	}
	
	/**
	 * See this as an action. You will create a new behavior and every event will be sent to it until it call the stop() method.
	 * When it's done, you will receive a CHILDBEHAVIOR_END warn. The new behavior start with his act() method.
	 * @param child The new behavior to do.
	 */
	protected void doBehavior(EventListener child) {
		child.fatherBehavior = this;
		childBehavior = child;
		child.act();
	}
}
