package main;

import java.util.ArrayList;

import lejos.robotics.navigation.Pose;
import robot.EventListener;
import robot.Robot;
import utils.Geometry;
import actions.ActionFactory;
import actions.Event;

public class MainBehavior extends EventListener {
	private enum State {
		START, SCORE_FIRST_PUCK, SCORED_1, ONLINE, PLUCK_BUMPED2, SCORED2
	}
	private State state = State.START;
	private ArrayList<Pose> poses = new ArrayList<Pose>();
	private ArrayList<String> chaine = new ArrayList<String>();
	
	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case CHILDBEHAVIOR_END:
			if (state == State.START) {
				Pose pose = Robot.getInstance().getOdometryPoseProvider().getPose();
				System.out.println("X = " + ((int) (pose.getX())));
				System.out.println("Y = " + ((int) pose.getY()));
				System.out.println("H = " + pose.getHeading());
				Robot.getInstance().getMotion().getPilot().setRotateSpeed(180);
				poses.add(new Pose(80, 0, 0));
				chaine.add("BlackY");
				poses.add(new Pose(30, 0, 0));
				chaine.add("BlackY");
				poses.add(new Pose(-20, 0, 0));
				chaine.add("BlackY");
				poses.add(new Pose(-80, 60, 0));
				chaine.add("Blue");
				poses.add(new Pose(-30, 60, 0));
				chaine.add("Blue");
				poses.add(new Pose(20, 60, 0));
				chaine.add("Blue");
				poses.add(new Pose(30, -60, 0));
				chaine.add("Green");
				poses.add(new Pose(-20, -60, 0));
				chaine.add("Green");
				state = State.SCORE_FIRST_PUCK;
			}
			else if(state == State.SCORE_FIRST_PUCK) {
				state = State.SCORED_1;
			}
			else if(state == State.SCORED_1) {
				state = State.;
			}
			else if(state == State.) {
				state = State.;
			}
			else if(state == State.) {
				state = State.;
			}
			else
				ignore();
			break;
		}
	}

	public void act() {
		if(state == State.START) {
			doBehavior(new FollowLineBehavior(100, false));
		}
		else if(state == State.SCORE_FIRST_PUCK) {
			doBehavior(new ScoreBehavior());
		}
		else if(state == State.SCORED_1) {
			doBehavior(new AlignementToBehavior(pose, s.equals("Blue"), s));
		}
		else if(state == ) {
			
		}
	}

}
