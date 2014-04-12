package robot;

import java.util.ArrayList;

import utils.Tuple;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.Pose;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FeatureListener;
import lejos.robotics.objectdetection.RangeFeatureDetector;

public class Sonar implements FeatureListener{
	private final float MAX_RANGE = 400.0f;
	private final int TIMER_DETECTION = 10;
	private UltrasonicSensor us;
	private RangeFeatureDetector rfd;
	private ArrayList<ArrayList<Float>> lectures;
	private ArrayList<Pose> poses;
	private static final Object lock = new Object();
	private static final int SIZE_LECTURE = 5;
	private long time = 0;
	
	Sonar() {
		lectures = new ArrayList<ArrayList<Float>>();
		poses = new ArrayList<Pose>();
	}
	
	void initSonar(SensorPort sonar) {
		us = new UltrasonicSensor(sonar);
		rfd = new RangeFeatureDetector(us, MAX_RANGE, TIMER_DETECTION);
		rfd.addListener(this);
	}
	
	public void featureDetected(Feature feature, FeatureDetector detector) {
		synchronized(lock) {
			ArrayList<Float> list = new ArrayList<Float>();
			for(int i = 0; i < feature.getRangeReadings().size(); i++)
				list.add(feature.getRangeReadings().getRange(i));
			lectures.add(list);
			poses.add(Robot.getInstance().getOdometryPoseProvider().getPose());
			if(lectures.size() > SIZE_LECTURE) {
				lectures.remove(0);
				poses.remove(0);
			}
			time = System.currentTimeMillis();
		}
	}
	
	public float getMinDist() {
		if(System.currentTimeMillis()-time > 2*TIMER_DETECTION) {
			return 0.0f;
		}
		if(lectures.size() == 0 || lectures.get(lectures.size()-1).size() == 0)
			return 0.0f;
		float min = lectures.get(lectures.size() - 1).get(0);
		for(int i = 1; i < lectures.get(lectures.size() - 1).size(); i++)
			min = Math.min(min, lectures.get(lectures.size()-1).get(i));
		return min;
	}
	
	/**
	 * Use this to know the minimum distance between all object in front of the robot and the robot.
	 * @return a Tuple<Float, Boolean>, where the float is the distance and
	 * the boolean is true only if the object detected is moving(it's the ennemy).
	 */
	public Tuple<Float, Boolean> getMinDistance() {
		if(lectures.size() < SIZE_LECTURE) {
			if(lectures.size() == 0 || lectures.get(lectures.size()-1).size() == 0)
				return new Tuple<Float, Boolean>(0.0f, false);
			float min = lectures.get(lectures.size() - 1).get(0);
			for(int i = 1; i < lectures.get(lectures.size() - 1).size(); i++)
				min = Math.min(min, lectures.get(lectures.size()-1).get(i));
			return new Tuple<Float, Boolean>(min, false);
		}
		float min1;
		float min2;
		float distance;
		synchronized(lock) {
			if(lectures.get(lectures.size()-1).size() == 0)
				return new Tuple<Float, Boolean>(-1.0f, false);
			min1 = lectures.get(lectures.size()-1).get(0);
			if(lectures.get(0).size() == 0)
				return new Tuple<Float, Boolean>(min1, false);
			min2 = lectures.get(0).get(0);
			for(int i = 1; i < lectures.get(lectures.size()-1).size(); i++)
				min1 = Math.min(min1, lectures.get(lectures.size()-1).get(i));
			for(int i = 1; i < lectures.get(0).size(); i++)
				min2 = Math.min(min2, lectures.get(0).get(i));
			float X = poses.get(poses.size()-1).getX() - poses.get(0).getX();
			float Y = poses.get(poses.size()-1).getY() - poses.get(0).getY();
			distance = (float) Math.sqrt(X*X + Y*Y);
		}
		Boolean b = true;
		float x = Math.abs(Math.abs(min2 - min1) - distance);
		if(poses.get(poses.size()-1).getHeading() - poses.get(0).getHeading() < 1.0f)
			b = false;
		if(x <= 2.0f || x >= 10.0f)
			b = false;
		return new Tuple<Float, Boolean>(min1, b);
	}
	
	/*
	public ArrayList<Float> getObjets() { //We assume that we just go forward here
		ArrayList<Float> result = new ArrayList<Float>();
		synchronized(lock) {
			float X = poses.get(poses.size()-1).getX() - poses.get(0).getX();
			float Y = poses.get(poses.size()-1).getY() - poses.get(0).getY();
			float distance = (float) Math.sqrt(X*X + Y*Y);
			int min = 15;
			for(int i = 0; i < lectures.size(); i++) {
				if(min > lectures.get(i).size()) {
					min = lectures.get(i).size();
				}
			}
		}
		return result;
	}*/
}
