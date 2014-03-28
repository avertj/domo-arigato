package actions;

import robot.Robot;
import lejos.robotics.navigation.Pose;
import music.Music;

public class ActionFactory {
	public static void playMusic(Music music, boolean createThread) {
		PlayMusic.playMusic(music, createThread);
	}
	
	public static void useClaws(float clawsState, boolean createThread) {
		new UseClaws(clawsState, createThread);
	}
	
	public static void rotate(float angle, boolean createThread) {
		new Rotate(angle, createThread);
	}
	
	public static void rotate(Pose pose, boolean createThread) {
		new Rotate(pose, createThread);
	}
	
	public static void goForward(int duration, boolean createThread) {
		new GoForward(duration, createThread);
	}
	
	public static void goForward(float distance, boolean createThread) {
		new GoForward(distance, createThread);
	}
	
	public static void straightMove(Pose pose, boolean createThread) {
		new StraightMove(pose, createThread);
	}
	
	public static void goBackward(int duration, boolean createThread) {
		new GoBackward(duration, createThread);
	}
	
	public static void goBackward(float distance, boolean createThread) {
		new GoBackward(distance, createThread);
	}
	
	public static void wait(int duration, String name, boolean createThread) {
		new Wait(duration, createThread, name);
	}
	
	public static void stopMotion() {
		Robot.getInstance().getMotion().getPilot().stop();
		Robot.getInstance().getMotion().getRunnableRobot().interrupt();
	}
	
	public static void stopClaws() {
		Robot.getInstance().getClaws().getRunnableRobot().interrupt();
	}
}
