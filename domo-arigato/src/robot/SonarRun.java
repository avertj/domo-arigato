package robot;

import java.util.ArrayList;

import lejos.util.Delay;
import actions.Event;
import actions.RunnableRobot;
import actions.TypeEvent;

public class SonarRun extends RunnableRobot {
	private Sonar sonar;
	private static final int DELAY = 10;
	private float lastMinDist = 60.0f;
	private ArrayList<Float> distances = new ArrayList<Float>();
	
	public SonarRun(Sonar sonar) {
		this.sonar = sonar;
	}
	
	@Override
	public void run() {
		while(!getInterrupted()) {
			Delay.msDelay(DELAY);
			float min = sonar.getMinDist();
			if(min > 220) {
				distances.add(distances.get(distances.size() - 1));
			}
			distances.add(min);
			if(distances.size() == 5) {
				min = 0;
				for(int i = 0; i < 5; i++) {
					min += distances.remove(0);
				}
				min = min/5;
				if(min <= sonar.getMinDistPallet() && lastMinDist > sonar.getMinDistPallet()) {
					Robot.getInstance().warn(new Event(TypeEvent.ROBOT_DETECTED));
				}
				else if(min > sonar.getMinDistPallet() && lastMinDist <= sonar.getMinDistPallet()) {
					Robot.getInstance().warn(new Event(TypeEvent.END_ROBOT_DETECTED));
				}
				lastMinDist = min;
			}
		}
	}
}
