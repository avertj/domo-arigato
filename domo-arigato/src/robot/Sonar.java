package robot;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FeatureListener;
import lejos.robotics.objectdetection.RangeFeatureDetector;

public class Sonar implements FeatureListener{
	private final float MAX_RANGE = 200.0f;
	private final int TIMER_DETECTION = 5;
	private UltrasonicSensor us;
	private RangeFeatureDetector rfd;
	
	Sonar() {
	}
	
	void initSonar(SensorPort sonar) {
		us = new UltrasonicSensor(sonar);
		rfd = new RangeFeatureDetector(us, MAX_RANGE, TIMER_DETECTION);
		rfd.addListener(this);
	}
	
	public void featureDetected(Feature feature, FeatureDetector detector) {
		/*System.out.println("Nombre : " + feature.getRangeReadings().size());
		for(int i = 0; i < feature.getRangeReadings().size(); i++) {
			System.out.println("distance "+i+" :" + feature.getRangeReadings().getRange(i));
		}*/
	}
}
