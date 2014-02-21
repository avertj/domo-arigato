package actions;

import music.Music;

class PlayMusic {	
	static void playMusic(Music music, boolean createThread) {
		if(createThread) {
			Thread thread = new Thread(music);
			thread.start();
		}
		else
			music.run();
	}
}
