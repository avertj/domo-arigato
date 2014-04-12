package field;

import lejos.geom.Point;
import lejos.robotics.navigation.Waypoint;

public class FieldStatus {
	private boolean[] present;
	private Waypoint robotPosition;
	private Point enemyRobotPosition;
	
	public FieldStatus() {
		this.present=new boolean[9];
		for(int i=0;i<9;i++){
			this.present[i]=true;
		}
	}
	
	public void setPresent(EnumPuck pos, boolean p){
		this.present[pos.getNum()]=p;
	}
	
	public boolean isPresent(EnumPuck p){
		return this.present[p.getNum()];
	}
	
	public Waypoint getRobotPosition() {
		return robotPosition;
	}
	
	public void setRobotPosition(Waypoint robotPosition) {
		this.robotPosition = robotPosition;
	}
	
	public Point getEnemyRobotPosition() {
		return enemyRobotPosition;
	}
	
	public void setEnemyRobotPosition(Point enemyRobot) {
		this.enemyRobotPosition = enemyRobot;
	}
}
