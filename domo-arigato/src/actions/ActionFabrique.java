package actions;

import music.Music;

public class ActionFabrique {
	public static void playMusic(Music music, boolean createThread) {
		PlayMusic.playMusic(music, createThread);
	}
	
	public static void useClaws(float clawsState, boolean createThread) {
		new UseClaws(clawsState, createThread);
	}
	
	public static void rotate(float angle, boolean createThread) {
		new Rotate(angle, createThread);
	}
	
	public static void goForward(int duration, boolean createThread) {
		new GoForward(duration, createThread);
	}
	
	public static void goBackward(int duration, boolean createThread) {
		new GoBackward(duration, createThread);
	}
	
	public static void wait(int duration, String name, boolean createThread) {
		new Wait(duration, createThread, name);
	}
}
