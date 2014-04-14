package actions;

import robot.Robot;
import lejos.robotics.navigation.Pose;
import music.Music;

public class ActionFactory {
	/**
	 * Use this to play a music.
	 * @param music the music file.
	 * @param createThread True if you want to create a thread for this task.
	 */
	public static void playMusic(Music music, boolean createThread) {
		PlayMusic.playMusic(music, createThread);
	}
	
	/**
	 * Use this to use the claws.
	 * @param clawsState the openure in % (0.0f to close and 1.0f to open).
	 * @param createThread True if you want to create a thread for this task.
	 */
	public static void useClaws(float clawsState, boolean createThread) {
		new UseClaws(clawsState, createThread);
	}
	
	/**
	 * Use this to rotate with a given angle.
	 * @param angle the given angle.
	 * @param createThread True if you want to create a thread for this task.
	 */
	public static void rotate(float angle, boolean createThread) {
		new Rotate(angle, createThread);
	}
	
	/**
	 * Use this to rotate in order to look in direction of a given pose.
	 * @param pose the given pose.
	 * @param createThread True if you want to create a thread for this task.
	 */
	public static void rotate(Pose pose, boolean createThread) {
		new Rotate(pose, createThread);
	}
	
	/**
	 * Use this to go forward during a given time.
	 * @param duration (int) the time in ms.
	 * @param createThread
	 */
	public static void goForward(int duration, boolean createThread) {
		new GoForward(duration, createThread);
	}
	
	/**
	 * Use this to go forward along a distance.
	 * @param distance (float) The distance in cm.
	 * @param createThread True if you want to create a thread for this task.
	 */
	public static void goForward(float distance, boolean createThread) {
		new GoForward(distance, createThread);
	}
	
	/**
	 * Move along an arc.
	 * if you want to go forward, left = angle, radius > 0. right = angle, radius < 0.
	 * @param angle the angle to go throw around the circle.
	 * @param radius the radius of the circle
	 * @param createThread True if you want to create a thread for this task.
	 */
	public static void arcMove(float angle, float radius, boolean createThread) {
		new ArcMove(angle, radius, createThread);
	}
	
	/**
	 * Do a straight move. first the robot turn himself to look the point, then it go forward,
	 * and then it turn itself to fit the pose.
	 * @param pose the target position.
	 * @param createThread True if you want to create a thread for this task.
	 */
	public static void straightMove(Pose pose, boolean createThread) {
		new StraightMove(pose, createThread);
	}
	
	/**
	 * Use this to go backward.
	 * @param duration (int) this distance in ms.
	 * @param createThread True if you want to create a thread for this task.
	 */
	public static void goBackward(int duration, boolean createThread) {
		new GoBackward(duration, createThread);
	}
	
	/**
	 * Use this to go backward.
	 * @param distance (float) the distance in cm.
	 * @param createThread True if you want to create a thread for this task.
	 */
	public static void goBackward(float distance, boolean createThread) {
		new GoBackward(distance, createThread);
	}
	
	/**
	 * Use this to send you an event in the future !!!
	 * @param duration time in ms.
	 * @param name a string you can get 
	 * @param createThread True if you want to create a thread for this task.
	 */
	public static void wait(int duration, String name, boolean createThread) {
		new Wait(duration, createThread, name);
	}
	
	/**
	 * Use this when you want to stop the wheels, it'll stop any order you did
	 * before and send you an interrupted event for each of them.
	 * @param createThread True if you want to create a thread for this task.
	 */
	public static void stopMotion(boolean createThread) {
		new StopMotion(createThread);
	}
	
	/**
	 * Don't use this. I do not know if it works fine and seriously, you'll not crush anything with those claws.
	 */
	public static void stopClaws() {
		Robot.getInstance().getClaws().getRunnableRobot().interrupt();
	}
}
