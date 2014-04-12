package robot;

import actions.Event;
import actions.TypeEvent;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FeatureListener;
import lejos.robotics.objectdetection.TouchFeatureDetector;

public class Bumper implements FeatureListener {
	private TouchSensor ts;
	private TouchFeatureDetector tfd;

	public void initBumper(SensorPort bumper) {
		ts = new TouchSensor(bumper);
		tfd = new TouchFeatureDetector(ts);
		tfd.addListener(this);
		tfd.setDelay(5);
	}

	/**
	 * Send an event to the robot.
	 */
	public void featureDetected(Feature feature, FeatureDetector detector) {
		Robot.getInstance().warn(new Event(TypeEvent.BUMP));
	}

}
