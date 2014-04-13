package utils;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import field.EnumPuck;
import field.Field;

public class Geometry {
	private static ArrayList<Point> inside(Point a, Point b) {
		Point a1 = new Point(Math.min(a.x, b.x), Math.min(a.y, b.y));
		Point b1 = new Point(Math.max(a.x, b.x), Math.max(a.y, b.y));
		ArrayList<Point> result = new ArrayList<Point>();
		Rectangle rect = new Rectangle(a1.x - Field.PUCK_RADIUS, a1.y - Field.PUCK_RADIUS, b1.x - Field.PUCK_RADIUS, b1.y - Field.PUCK_RADIUS);
		for(int i = 0; i < 10; ++i) {
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
}
