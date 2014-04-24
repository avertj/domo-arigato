package main;

import java.util.ArrayList;

import lejos.robotics.navigation.Pose;
import actions.Event;
import robot.EventListener;
import robot.Robot;

public class EssaiBehavior extends EventListener {
	private enum State {
		START, PLUCK_BUMPED, SCORED, ONLINE, PLUCK_BUMPED2, SCORED2
	}
	private ArrayList<Pose> poses = new ArrayList<Pose>();
	private ArrayList<String> chaine = new ArrayList<String>();

	private State state = State.START;

	public void warn(Event event) {
		switch (event.getTypeEvent()) {
		case CHILDBEHAVIOR_END:
			System.out.println(event.getName());
			if (state == State.START) {
				state = State.PLUCK_BUMPED;
			}
			else if(state == State.PLUCK_BUMPED) {
				state = State.SCORED;
			}
			else if(state == State.SCORED) {
				state = State.ONLINE;
			}
			else if(state == State.ONLINE) {
				state = State.PLUCK_BUMPED2;
			}
			else if(state == State.PLUCK_BUMPED2) {
				state = State.SCORED;
			}
			else
				ignore();
			break;
		default:
			ignore();
			break;
		}
	}

	@Override
	protected void act() {
		if(state == State.START) {
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
			doBehavior(new FollowLineBehavior(100, false));
		} else if(state == State.PLUCK_BUMPED) {
			/*Pose pose = Robot.getInstance().getOdometryPoseProvider().getPose();
			System.out.println("X = " + ((int) (pose.getX())));
			System.out.println("Y = " + ((int) pose.getY()));
			System.out.println("H = " + pose.getHeading());*/
			doBehavior(new ScoreBehavior());
		} else if(state == State.SCORED) {
			if(poses.size() > 0) {
				Pose pose = poses.remove(0);
				String s = chaine.get(0);
				doBehavior(new AlignementToBehavior(pose, s.equals("Blue"), s));
			}
			else
				stop();
		} else if(state == State.ONLINE) {
			String s = chaine.remove(0);
			doBehavior(new FollowLineBehavior(150, s.equals("Green")));
		} else if(state == State.PLUCK_BUMPED2) {
			doBehavior(new ScoreBehavior());
		} else if(state == State.SCORED2) {
			stop();
		}
	}
}
