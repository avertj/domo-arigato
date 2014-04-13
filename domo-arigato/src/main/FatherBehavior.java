package main;

import actions.Event;
import lejos.geom.Point;
import field.EnumPuck;
import field.Field;
import robot.EventListener;
import robot.Robot;

public class FatherBehavior extends EventListener {

	
	
	public Point getNextPuck(int y) {
		Point posR = Robot.getInstance().getOdometryPoseProvider().getPose().getLocation();
		Field field = Field.getInstance();
		EnumPuck W, M, E;
		
		if(y==0)
		{
			W = EnumPuck.NW;
			M = EnumPuck.N;
			E = EnumPuck.NE;
		}
		else if(y==1)
		{
			W = EnumPuck.W;
			M = EnumPuck.M;
			E = EnumPuck.E;
		}
		else
		{
			W = EnumPuck.SW;
			M = EnumPuck.S;
			E = EnumPuck.SE;
		}
		
		
		if(posR.x<0)
		{
			if(field.isPresent(W))
			{
				return new Point(-50, y*60-60);
			}
			else if(field.isPresent(M))
			{
				return new Point(0, y*60-60);
			}
			else if(field.isPresent(E))
			{
				return new Point(50, y*60-60);
			}
		}
		else
		{
			if(field.isPresent(E))
			{
				return new Point(50, y*60-60);
			}
			else if(field.isPresent(M))
			{
				return new Point(0, y*60-60);
			}
			else if(field.isPresent(W))
			{
				return new Point(-50, y*60-60);
			}
		}
		
		return null;
	}

	@Override
	public void warn(Event event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void act() {
		// TODO Auto-generated method stub
		
	}
}
