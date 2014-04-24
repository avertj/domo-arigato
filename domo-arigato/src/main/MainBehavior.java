package main;

import java.util.ArrayList;

import field.EnumPuck;
import field.Field;

import lejos.robotics.navigation.Pose;
import robot.EventListener;
import robot.Robot;
import utils.Geometry;
import actions.ActionFactory;
import actions.Event;

public class MainBehavior extends EventListener {
	private enum State {
		START, SCORE_FIRST_PUCK, GO_TO_MIDDLE, GET_MIDDLE_PUCK, SCORE_MIDDLE_PUCK, 
		GO_TO_LOW, GET_LOW_PUCK, SCORE_LOW_PUCK, GO_TO_HIGH, GET_HIGH_PUCK, SCORE_HIGH_PUCK, FINAL
	}
	private State state = State.START;
	private boolean sensAlignement = false;
	
	public void warn(Event event) {
		Field field = Field.getInstance();
		Pose pose=null;
		switch(event.getTypeEvent())
		{
		case CHILDBEHAVIOR_END:
			if (state == State.START) {
				pose = Robot.getInstance().getOdometryPoseProvider().getPose();
				System.out.println("X = " + ((int) (pose.getX())));
				System.out.println("Y = " + ((int) pose.getY()));
				System.out.println("H = " + pose.getHeading());
				Robot.getInstance().getMotion().getPilot().setRotateSpeed(180);
				if(pose.getX()<0)
					sensAlignement=true;
				state = State.SCORE_FIRST_PUCK;
			}
			else if(state == State.SCORE_FIRST_PUCK) {
				state = State.GO_TO_MIDDLE;
			}
			else if(state == State.GO_TO_MIDDLE) {
				state = State.GET_MIDDLE_PUCK;
			}
			else if(state == State.GET_MIDDLE_PUCK) {
				if(event.getName().equals("BUMP"))
					state = State.SCORE_MIDDLE_PUCK;
				else
				{
					if(field.isPresent(EnumPuck.E) || field.isPresent(EnumPuck.M) || field.isPresent(EnumPuck.W))
					{
						pose = Robot.getInstance().getOdometryPoseProvider().getPose();
						if(pose.getX()>-50 && pose.getX()<50)
							state = State.GET_MIDDLE_PUCK;
						else
						{	//demi tour
							ActionFactory.rotate(180, false);
							state = State.GET_MIDDLE_PUCK;
							sensAlignement=!sensAlignement;
						}
					}
					else
					{
						state = State.GO_TO_HIGH;
						if(sensAlignement)
							sensAlignement=false;
						else
							sensAlignement=true;
					}
				}
			}
			else if(state == State.SCORE_MIDDLE_PUCK){
				if(field.isPresent(EnumPuck.E) || field.isPresent(EnumPuck.M) || field.isPresent(EnumPuck.W))
					state = State.GO_TO_MIDDLE;
				else
				{
					state = State.GO_TO_LOW;
					if(sensAlignement)
						sensAlignement=false;
					else
						sensAlignement=true;
				}
			}
			else if(state == State.GO_TO_HIGH) {
				state = State.GET_HIGH_PUCK;
			}
			else if(state == State.GET_HIGH_PUCK) {
				if(event.getName().equals("BUMP"))
					state = State.SCORE_HIGH_PUCK;
				else{
					if(field.isPresent(EnumPuck.S) || field.isPresent(EnumPuck.SE) || field.isPresent(EnumPuck.SW))
					{
						pose = Robot.getInstance().getOdometryPoseProvider().getPose();
						if(pose.getX()>-50 && pose.getX()<50)
							state = State.GET_HIGH_PUCK;
						else
						{	//demi tour
							ActionFactory.rotate(180, false);
							state = State.GET_HIGH_PUCK;
							sensAlignement=!sensAlignement;
						}
					}
					else
					{
						if(field.isPresent(EnumPuck.N) || field.isPresent(EnumPuck.NE) || field.isPresent(EnumPuck.NW))
							state = State.GO_TO_LOW;
						else
							state = State.FINAL;
					}
				}
			}
			else if(state == State.GO_TO_LOW) {
				state = State.GET_LOW_PUCK;
			}
			else if(state == State.GET_LOW_PUCK) {
				if(event.getName().equals("BUMP"))
					state = State.SCORE_LOW_PUCK;
				else{
					if(field.isPresent(EnumPuck.N) || field.isPresent(EnumPuck.NE) || field.isPresent(EnumPuck.NW))
					{
						pose = Robot.getInstance().getOdometryPoseProvider().getPose();
						if(pose.getX()>-50 && pose.getX()<50)
							state = State.GET_LOW_PUCK;
						else
						{	//demi tour
							ActionFactory.rotate(180, false);
							state = State.GET_LOW_PUCK;
							sensAlignement=!sensAlignement;
						}
					}
					else
					{
						if(field.isPresent(EnumPuck.S) || field.isPresent(EnumPuck.SE) || field.isPresent(EnumPuck.SW))
							state = State.GO_TO_HIGH;
						else
							state = State.FINAL;
					}
				}
			}
			else if(state == State.SCORE_HIGH_PUCK) {
				if(field.isPresent(EnumPuck.N) || field.isPresent(EnumPuck.NE) || field.isPresent(EnumPuck.NW))
					state = State.GO_TO_LOW;
				else if (field.isPresent(EnumPuck.S) || field.isPresent(EnumPuck.SE) || field.isPresent(EnumPuck.SW))
					state = State.GO_TO_HIGH;
				else
					state = State.FINAL;
			}
			else if(state == State.SCORE_LOW_PUCK) {
				if(field.isPresent(EnumPuck.N) || field.isPresent(EnumPuck.NE) || field.isPresent(EnumPuck.NW))
					state = State.GO_TO_LOW;
				else if (field.isPresent(EnumPuck.S) || field.isPresent(EnumPuck.SE) || field.isPresent(EnumPuck.SW))
					state = State.GO_TO_HIGH;
				else
					state = State.FINAL;
			}
			else
				ignore();
			break;
		default:
			ignore();
			break;
		}
	}

	public void act() {
		Pose pose = null;
		if(state == State.START) {
			doBehavior(new FollowLineBehavior(100, false));
		}
		else if(state == State.SCORE_FIRST_PUCK) {
			doBehavior(new ScoreBehavior());
		}
		else if(state == State.GO_TO_MIDDLE) {
			pose = Robot.getInstance().getOdometryPoseProvider().getPose();
			if(pose.getX()<-50)
				doBehavior(new AlignementToBehavior(new Pose(-75, 0, 0), sensAlignement, "BlackX"));
			else if(pose.getX()<0)
				doBehavior(new AlignementToBehavior(new Pose(-25, 0, 0), sensAlignement, "BlackX"));
			else if(pose.getX()<50)
				doBehavior(new AlignementToBehavior(new Pose(25, 0, 0), sensAlignement, "BlackX"));
			else
				doBehavior(new AlignementToBehavior(new Pose(75, 0, 0), sensAlignement, "BlackX"));
		}
		else if(state == State.GET_MIDDLE_PUCK) {
			doBehavior(new FollowLineBehavior(50, sensAlignement));
		}
		else if(state == State.SCORE_MIDDLE_PUCK) {
			doBehavior(new ScoreBehavior());
		}
		else if(state == State.GO_TO_LOW) {
			pose = Robot.getInstance().getOdometryPoseProvider().getPose();
			if(pose.getX()<-50)
				doBehavior(new AlignementToBehavior(new Pose(-75, 60, 0), sensAlignement, "Blue"));
			else if(pose.getX()<0)
				doBehavior(new AlignementToBehavior(new Pose(-25, 60, 0), sensAlignement, "Blue"));
			else if(pose.getX()<50)
				doBehavior(new AlignementToBehavior(new Pose(25, 60, 0), sensAlignement, "Blue"));
			else
				doBehavior(new AlignementToBehavior(new Pose(75, 60, 0), sensAlignement, "Blue"));
		}
		else if(state == State.GET_LOW_PUCK) {
			doBehavior(new FollowLineBehavior(50, !sensAlignement));
		}
		else if(state == State.SCORE_LOW_PUCK) {
			doBehavior(new ScoreBehavior());
		}
		else if(state == State.GO_TO_HIGH) {
			pose = Robot.getInstance().getOdometryPoseProvider().getPose();
			if(pose.getX()<-50)
				doBehavior(new AlignementToBehavior(new Pose(-75, -60, 0), sensAlignement, "Green"));
			else if(pose.getX()<0)
				doBehavior(new AlignementToBehavior(new Pose(-25, -60, 0), sensAlignement, "Green"));
			else if(pose.getX()<50)
				doBehavior(new AlignementToBehavior(new Pose(25, -60, 0), sensAlignement, "Green"));
			else
				doBehavior(new AlignementToBehavior(new Pose(75, -60, 0), sensAlignement, "Green"));
		}
		else if(state == State.GET_HIGH_PUCK) {
			doBehavior(new FollowLineBehavior(50, !sensAlignement));
		}
		else if(state == State.SCORE_HIGH_PUCK) {
			doBehavior(new ScoreBehavior());
		}
		else if(state == State.FINAL) {
			doBehavior(new FinalBehavior());
		}
		else {
			System.out.println("I'M LOST!!!");
		}
	}

}
