package robot;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FeatureListener;
import lejos.robotics.objectdetection.RangeFeatureDetector;

public class Sonar implements FeatureListener{
	private final float MAX_RANGE = 80.0f;
	private UltrasonicSensor us;
	private RangeFeatureDetector rfd;
	
	Sonar() {
		
	}
	
	void initSonar(SensorPort sonar) {
		us = new UltrasonicSensor(sonar);
		rfd = new RangeFeatureDetector(us, MAX_RANGE, 500);
		rfd.addListener(this);
	}
	
	public void featureDetected(Feature feature, FeatureDetector detector) {
		System.out.println("Range : " + feature.getRangeReading().getRange());
	}
}
