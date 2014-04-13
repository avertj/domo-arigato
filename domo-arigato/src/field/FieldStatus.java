package field;

import robot.Robot;
import lejos.geom.Point;
//import lejos.robotics.navigation.Waypoint;

public class FieldStatus {
	private boolean[][] present;
	/*private Waypoint robotPosition;*/
	private Point enemyRobotPosition;
	
	public FieldStatus() {
		this.present=new boolean[3][3];
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				this.present[i][j]=true;
			}
		}
	}
	
	public void setPresent(Point p, boolean b){
		this.present[(int)p.x][(int)p.y]=b;
	}
	
	public boolean isPresent(Point p){
		return this.present[(int)p.x][(int)p.y];
	}
	
	/*public Waypoint getRobotPosition() {
		return robotPosition;
	}
	
	public void setRobotPosition(Waypoint robotPosition) {
		this.robotPosition = robotPosition;
	}*/
	
	public Point getEnemyRobotPosition() {
		return enemyRobotPosition;
	}
	
	public void setEnemyRobotPosition(Point enemyRobot) {
		this.enemyRobotPosition = enemyRobot;
	}
	
	public Point getPuck(int y) {
		Point posR = Robot.getInstance().getOdometryPoseProvider().getPose().getLocation();
		
		if(present[0][y] || present[1][y] || present[2][y])
		{	
			if(posR.x<-50)
			{
				if(present[0][y])
				{
					return Field.getInstance().
				}
			}
			else if(posR.x<2)
			{
				
			}
			else if(posR.x<3)
			{
				
			}
			else
			{
				
			}
	}
		
		return this.positions[p.getNum()];
	}
}
