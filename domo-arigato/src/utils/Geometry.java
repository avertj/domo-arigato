package utils;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import lejos.robotics.navigation.Pose;
import robot.Robot;
import field.EnumPuck;
import field.Field;

public class Geometry {
	private static ArrayList<Point> inside(Point a, Point b) {
		Point a1 = new Point(Math.min(a.x, b.x), Math.min(a.y, b.y));
		Point b1 = new Point(Math.max(a.x, b.x), Math.max(a.y, b.y));
		ArrayList<Point> result = new ArrayList<Point>();
		Rectangle rect = new Rectangle(a1.x - Field.PUCK_RADIUS, a1.y - Field.PUCK_RADIUS, b1.x - Field.PUCK_RADIUS, b1.y - Field.PUCK_RADIUS);
		for(int i = 0; i < 9; ++i) {
			EnumPuck puck = EnumPuck.values()[i];
			if(Field.getInstance().isPresent(puck) && rect.contains(Field.getInstance().getPuck(puck)))
				result.add(Field.getInstance().getPuck(puck));
		}
		return result;
	}
	
	public static ArrayList<Point> getPath(Point a, Point b) {
		ArrayList<Point> result = new ArrayList<Point>();
		ArrayList<Point> pucks = inside(a, b);
		return result;
	}
	
	public static EnumPuck closest(Point a) {
		EnumPuck result = null;
		for(int i = 0; i < 9; ++i) {
			EnumPuck puck = EnumPuck.values()[i];
			if(Field.getInstance().getPuck(puck).distance(a) < 10)
				result = puck;
		}
		return result;
	}
	
	/**
	 * Use this to know if the coordinates a and b are barely equals (difference <= offset)
	 * @param a (float)
	 * @param b (float)
	 * @param offset (float)
	 * @return true if they are barely equals.
	 */
	public static boolean barelyEqualsCoord(float a, float b, float offset) {
		return Math.abs(a-b) <= offset;
	}
	
	/**
	 * Use this to know if the headings a and b are barely equals (difference <= offset).
	 * this is like barelyEqualsCoord, but it will say that 355 and 0 are barely equals with an offset >= 1.
	 * @param a (float)
	 * @param b (float)
	 * @param offset (float)
	 * @return true if they are barely equals.
	 */
	public static boolean barelyEqualsHeading(float a, float b, float offset) {
		if(a%360 > 90 && a%360 < 270)
			return Math.abs(a%360-b%360) <= offset;
		else
			return Math.abs((a+180)%360-(b+180)%360) <= offset;
	}
	
	/**
	 * Call this during or just at the end of a Follow Line.
	 */
	public static void adjustHeading() {
		Pose myPose = Robot.getInstance().getOdometryPoseProvider().getPose();
		float heading = myPose.getHeading();
		if(Geometry.barelyEqualsHeading(heading, 0, 45)) {
			Robot.getInstance().getOdometryPoseProvider().setPose(new Pose(myPose.getX(), myPose.getY(), 0));
			adjustY();
		}
		else if(Geometry.barelyEqualsHeading(heading, 90, 45)) {
			Robot.getInstance().getOdometryPoseProvider().setPose(new Pose(myPose.getX(), myPose.getY(), 90));
			adjustX();
		}
		else if(Geometry.barelyEqualsHeading(heading, 180, 45)) {
			Robot.getInstance().getOdometryPoseProvider().setPose(new Pose(myPose.getX(), myPose.getY(), 180));
			adjustY();
		}
		else if(Geometry.barelyEqualsHeading(heading, 270, 45)) {
			Robot.getInstance().getOdometryPoseProvider().setPose(new Pose(myPose.getX(), myPose.getY(), 270));
			adjustX();
		}
	}
	
	public static void adjustX() {
		/*Pose myPose = Robot.getInstance().getOdometryPoseProvider().getPose();
		float offset = (float) (12.5*Math.cos(myPose.getHeading()*Math.PI/180));
		float[] distances = new float[3];
		distances[0] = Math.abs(myPose.getX() + 50);
		distances[1] = Math.abs(myPose.getX());
		distances[2] = Math.abs(myPose.getX() - 50);
		float val = distances[0];
		int min = 0;
		for(int i = 1; i < 3; i++) {
			if(distances[i] < val) {
				min = i;
				val = distances[i];
			}
		}
		switch(min)
		{
		case 0 :
			Robot.getInstance().getOdometryPoseProvider().setPose(new Pose(-50 - offset, myPose.getY(), myPose.getHeading()));
			break;
		case 1 :
			Robot.getInstance().getOdometryPoseProvider().setPose(new Pose(-offset, myPose.getY(), myPose.getHeading()));
			break;
		case 2 :
			Robot.getInstance().getOdometryPoseProvider().setPose(new Pose(50 - offset, myPose.getY(), myPose.getHeading()));
			break;
		}*/
	}
	
	public static void adjustY() {
		/*Pose myPose = Robot.getInstance().getOdometryPoseProvider().getPose();
		float offset = (float) (12.5*Math.sin(myPose.getHeading()*Math.PI/180));
		float[] distances = new float[5];
		distances[0] = Math.abs(myPose.getX() + 120);
		distances[1] = Math.abs(myPose.getX() + 60);
		distances[2] = Math.abs(myPose.getX());
		distances[3] = Math.abs(myPose.getX() - 60);
		distances[4] = Math.abs(myPose.getX() - 120);
		float val = distances[0];
		int min = 0;
		for(int i = 1; i < 5; i++) {
			if(distances[i] < val) {
				min = i;
				val = distances[i];
			}
		}
		switch(min)
		{
		case 0 :
			Robot.getInstance().getOdometryPoseProvider().setPose(new Pose(myPose.getX(), -120 - offset, myPose.getHeading()));
			break;
		case 1 :
			Robot.getInstance().getOdometryPoseProvider().setPose(new Pose(myPose.getX(), -60 - offset, myPose.getHeading()));
			break;
		case 2 :
			Robot.getInstance().getOdometryPoseProvider().setPose(new Pose(myPose.getX(), -offset, myPose.getHeading()));
			break;
		case 3 :
			Robot.getInstance().getOdometryPoseProvider().setPose(new Pose(myPose.getX(), 60 - offset, myPose.getHeading()));
			break;
		case 4 :
			Robot.getInstance().getOdometryPoseProvider().setPose(new Pose(myPose.getX(), 120 - offset, myPose.getHeading()));
			break;
		}*/
	}
}
