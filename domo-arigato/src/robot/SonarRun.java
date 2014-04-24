package robot;

import java.util.ArrayList;

import utils.Geometry;
import lejos.robotics.navigation.Pose;
import lejos.util.Delay;
import actions.Event;
import actions.RunnableRobot;
import actions.TypeEvent;

public class SonarRun extends RunnableRobot {
	private Sonar sonar;
	private static final int DELAY = 10;
	private static final int WALL_DETECTION = 40;
	private float lastMinDist = 60.0f;
	private ArrayList<Float> distances = new ArrayList<Float>();
	private boolean wall;
	
	public SonarRun(Sonar sonar) {
		this.sonar = sonar;
		wall = false;
	}
	
	@Override
	public void run() {
		while(!getInterrupted()) {
			Delay.msDelay(DELAY);
			float min = sonar.getMinDist();
			if(min > 220 && distances.size() > 0) {
				distances.add(distances.get(distances.size() - 1));
			}
			else
				distances.add(min);
			if(distances.size() == 5) {
				min = 0;
				for(int i = 0; i < 5; i++) {
					min += distances.remove(0);
				}
				min = min/5;
				if(min <= sonar.getMinDistPallet() && lastMinDist > sonar.getMinDistPallet()) {
					Pose pose = Robot.getInstance().getOdometryPoseProvider().getPose();
					int h = ((int) pose.getHeading())%360;
					if(h < 0)
						h += 360;
					float y = pose.getY();
					if((Math.abs(150 - y) < WALL_DETECTION && (h > 20 && h < 160))) {
						wall = true;
					}
					else
						Robot.getInstance().warn(new Event(TypeEvent.ROBOT_DETECTED));
				}
				else if(min > sonar.getMinDistPallet() && lastMinDist <= sonar.getMinDistPallet()) {
					if(wall)
						wall = false;
					else
						Robot.getInstance().warn(new Event(TypeEvent.END_ROBOT_DETECTED));
				}
				lastMinDist = min;
			}
		}
	}
}
