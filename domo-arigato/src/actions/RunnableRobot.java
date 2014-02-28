package actions;

public abstract class RunnableRobot implements Runnable{
	private volatile boolean interrupted = false;
	
	public void interrupt() {
		interrupted = true;
	}
	
	public boolean getInterrupted() {
		return interrupted;
	}
}
