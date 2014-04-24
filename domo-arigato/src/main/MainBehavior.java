package main;

import java.awt.Point;
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
					removePluckBehind();
					if(field.isPresent(EnumPuck.E) || field.isPresent(EnumPuck.M) || field.isPresent(EnumPuck.W))
					{
						pose = Robot.getInstance().getOdometryPoseProvider().getPose();
						if(pose.getX()>-50 && pose.getX()<50)
						{
							state = State.GO_TO_MIDDLE;
						}
						else
						{	//demi tour
							ActionFactory.rotate(160, false);
							state = State.GO_TO_MIDDLE;
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
					removePluckBehind();
					if(field.isPresent(EnumPuck.S) || field.isPresent(EnumPuck.SE) || field.isPresent(EnumPuck.SW))
					{
						pose = Robot.getInstance().getOdometryPoseProvider().getPose();
						if(pose.getX()>-50 && pose.getX()<50)
							state = State.GO_TO_HIGH;
						else
						{	//demi tour
							ActionFactory.rotate(160, false);
							state = State.GO_TO_HIGH;
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
					removePluckBehind();
					if(field.isPresent(EnumPuck.N) || field.isPresent(EnumPuck.NE) || field.isPresent(EnumPuck.NW))
					{
						pose = Robot.getInstance().getOdometryPoseProvider().getPose();
						if(pose.getX()>-50 && pose.getX()<50)
							state = State.GO_TO_LOW;
						else
						{	//demi tour
							ActionFactory.rotate(160, false);
							state = State.GO_TO_LOW;
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
			pose = Robot.getInstance().getOdometryPoseProvider().getPose();
			if(pose.getX()<0)
				sensAlignement=true;
			System.out.println("X = " + ((int) (pose.getX())));
			System.out.println("Y = " + ((int) pose.getY()));
			System.out.println("H = " + pose.getHeading());
			Robot.getInstance().getMotion().getPilot().setRotateSpeed(100);
			doBehavior(new FollowLineBehavior(100, false));
		}
		else if(state == State.SCORE_FIRST_PUCK) {
			doBehavior(new ScoreBehavior());
		}
		else if(state == State.GO_TO_MIDDLE) {
			pose = Robot.getInstance().getOdometryPoseProvider().getPose();
			if(Geometry.barelyEqualsCoord(pose.getY(), 0, 2)) {
				if(Robot.getInstance().getEyes().onNoise()) {
					doBehavior(new AlignementStaticBehavior(!sensAlignement, "BlackY"));
				}
				else {
					state = State.GET_MIDDLE_PUCK;
					doBehavior(new FollowLineBehavior(40, sensAlignement));
				}
			}
			else {
				if(pose.getX()<-50)
					sensAlignement = true;
				else if(pose.getX()>50)
					sensAlignement = false;
				int decalage = 5;
				if(sensAlignement)
					decalage = -5;
				if(pose.getX()<-50)
					doBehavior(new AlignementToBehavior(new Pose(-80, 0, 0), sensAlignement, "BlackY"));
				else if(pose.getX()<0)
					doBehavior(new AlignementToBehavior(new Pose(-25+decalage, 0, 0), sensAlignement, "BlackY"));
				else if(pose.getX()<50)
					doBehavior(new AlignementToBehavior(new Pose(25+decalage, 0, 0), sensAlignement, "BlackY"));
				else
					doBehavior(new AlignementToBehavior(new Pose(85, 0, 0), sensAlignement, "BlackY"));
			}
		}
		else if(state == State.GET_MIDDLE_PUCK) {
			pose = Robot.getInstance().getOdometryPoseProvider().getPose();
			if(Geometry.barelyEqualsHeading(pose.getHeading(), 0, 5)) {
				ActionFactory.rotate(15, false);
				state = State.GO_TO_MIDDLE;
				act();
			}
			else
				doBehavior(new FollowLineBehavior(40, sensAlignement));
		}
		else if(state == State.SCORE_MIDDLE_PUCK) {
			doBehavior(new ScoreBehavior());
		}
		else if(state == State.GO_TO_LOW) {
			pose = Robot.getInstance().getOdometryPoseProvider().getPose();
			if(Geometry.barelyEqualsCoord(pose.getY(), 60, 2)) {
				if(Robot.getInstance().getEyes().onNoise()) {
					doBehavior(new AlignementStaticBehavior(!sensAlignement, "Blue"));
				}
				else {
					state = State.GET_MIDDLE_PUCK;
					doBehavior(new FollowLineBehavior(40, sensAlignement));
				}
			}
			else {
				if(pose.getX()<-50)
					sensAlignement = true;
				else if(pose.getX()>50)
					sensAlignement = false;
				int decalage = 5;
				if(sensAlignement)
					decalage = -5;
				if(pose.getX()<-50)
					doBehavior(new AlignementToBehavior(new Pose(-80, 60, 0), sensAlignement, "Blue"));
				else if(pose.getX()<0)
					doBehavior(new AlignementToBehavior(new Pose(-25+decalage, 60, 0), sensAlignement, "Blue"));
				else if(pose.getX()<50)
					doBehavior(new AlignementToBehavior(new Pose(25+decalage, 60, 0), sensAlignement, "Blue"));
				else
					doBehavior(new AlignementToBehavior(new Pose(80, 60, 0), sensAlignement, "Blue"));
			}
		}
		else if(state == State.GET_LOW_PUCK) {
			doBehavior(new FollowLineBehavior(40, !sensAlignement));
		}
		else if(state == State.SCORE_LOW_PUCK) {
			doBehavior(new ScoreBehavior());
		}
		else if(state == State.GO_TO_HIGH) {
			pose = Robot.getInstance().getOdometryPoseProvider().getPose();
			if(Geometry.barelyEqualsCoord(pose.getY(), -60, 2)) {
				if(Robot.getInstance().getEyes().onNoise()) {
					doBehavior(new AlignementStaticBehavior(!sensAlignement, "Green"));
				}
				else {
					state = State.GET_MIDDLE_PUCK;
					doBehavior(new FollowLineBehavior(40, sensAlignement));
				}
			}
			else {
				if(pose.getX()<-50)
					sensAlignement = true;
				else if(pose.getX()>50)
					sensAlignement = false;
				int decalage = 5;
				if(sensAlignement)
					decalage = -5;
				if(pose.getX()<-50)
					doBehavior(new AlignementToBehavior(new Pose(-80, -60, 0), sensAlignement, "Green"));
				else if(pose.getX()<0)
					doBehavior(new AlignementToBehavior(new Pose(-25+decalage, -60, 0), sensAlignement, "Green"));
				else if(pose.getX()<50)
					doBehavior(new AlignementToBehavior(new Pose(25+decalage, -60, 0), sensAlignement, "Green"));
				else
					doBehavior(new AlignementToBehavior(new Pose(80, -60, 0), sensAlignement, "Green"));
			}
		}
		else if(state == State.GET_HIGH_PUCK) {
			doBehavior(new FollowLineBehavior(40, !sensAlignement));
		}
		else if(state == State.SCORE_HIGH_PUCK) {
			doBehavior(new ScoreBehavior());
		}
		else if(state == State.FINAL) {
			doBehavior(new PhaseTwo());
		}
		else {
			System.out.println("I'M LOST!!!");
		}
	}
	
	private void removePluckBehind() {
		Pose myPose = Robot.getInstance().getOdometryPoseProvider().getPose();
		Point behindPose = new Point((int)myPose.getX(), (int)myPose.getY());
		behindPose.translate((int)(-1*(Math.cos(myPose.getHeading()) * 20.0)), 0);
		EnumPuck puck = Geometry.closest(behindPose);
		if(puck != null) {
			System.out.println("on retire");
			Field.getInstance().setPresent(puck, false);
		}
		else
			System.out.println("pas trouvé");
	}
}
