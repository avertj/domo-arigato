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
		
		if(posR.x<0)
		{
			if(field.isPresent(EnumPuck.values()[y*3]))
			{
				return new Point(-50, y*60-60);
			}
			else if(field.isPresent(EnumPuck.values()[y*3+1]))
			{
				return new Point(0, y*60-60);
			}
			else if(field.isPresent(EnumPuck.values()[y*3+2]))
			{
				return new Point(50, y*60-60);
			}
		}
		else
		{
			if(field.isPresent(EnumPuck.values()[y*3]))
			{
				return new Point(50, y*60-60);
			}
			else if(field.isPresent(EnumPuck.values()[y*3+1]))
			{
				return new Point(0, y*60-60);
			}
			else if(field.isPresent(EnumPuck.values()[y*3+2]))
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
