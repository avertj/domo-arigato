package music;

import lejos.nxt.Sound;

public class NoteThread implements Runnable {
	int note;
	int[] instrument;
	int duration;

	public NoteThread(int[] instrument, int note, int duration) {
		this.instrument = instrument;
		this.note = note;
		this.duration = duration;
	}

	public void run() {
    	Sound.playNote(instrument, note, duration);
	}
}
