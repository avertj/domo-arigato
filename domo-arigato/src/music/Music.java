package music;

import actions.Event;
import actions.RunnableRobot;
import actions.TypeEvent;
import robot.Robot;
import lejos.nxt.Sound;

public abstract class Music extends RunnableRobot {
	protected int[] DO = new int[5];
	protected int[] REb = new int[5];
	protected int[] RE = new int[5];
	protected int[] MIb = new int[5];
	protected int[] MI = new int[5];
	protected int[] FA = new int[5];
	protected int[] SOLb = new int[5];
	protected int[] SOL = new int[5];
	protected int[] LAb = new int[5];
	protected int[] LA = new int[5];
	protected int[] SIb = new int[5];
	protected int[] SI = new int[5];
	protected int WHOLE;
	protected int HALF;
	protected int QUARTER;
	protected int HEIGHT;
	protected int SIXTEENTH;
	protected int TRIPLET;
	protected int[] PIANO = Sound.PIANO;
	protected int[] FLUTE = Sound.FLUTE;
	protected int[] XYLOPHONE = Sound.XYLOPHONE;
	protected String name;
	
	public Music(int QuarterDuration) {
		WHOLE = QuarterDuration * 4;
		HALF = WHOLE / 2;
		QUARTER = HALF / 2;
		HEIGHT = QUARTER / 2;
		SIXTEENTH = HEIGHT / 2;
		TRIPLET = QUARTER / 3;
		int i = 0;
		DO[i] = 65;
		REb[i] = 69;
		RE[i] = 73;
		MIb[i] = 78;
		MI[i] = 82;
		FA[i] = 87;
		SOLb[i] = 92;
		SOL[i] = 98;
		LAb[i] = 104;
		LA[i] = 110;
		SIb[i] = 117;
		SI[i] = 123;
		i++;
		DO[i] = 131;
		REb[i] = 139;
		RE[i] = 147;
		MIb[i] = 156;
		MI[i] = 165;
		FA[i] = 175;
		SOLb[i] = 185;
		SOL[i] = 196;
		LAb[i] = 208;
		LA[i] = 220;
		SIb[i] = 233;
		SI[i] = 247;
		i++;
		DO[i] = 262;
		REb[i] = 277;
		RE[i] = 294;
		MIb[i] = 311;
		MI[i] = 330;
		FA[i] = 349;
		SOLb[i] = 370;
		SOL[i] = 392;
		LAb[i] = 415;
		LA[i] = 440;
		SIb[i] = 466;
		SI[i] = 494;
		i++;
		DO[i] = 523;
		REb[i] = 554;
		RE[i] = 587;
		MIb[i] = 622;
		MI[i] = 659;
		FA[i] = 698;
		SOLb[i] = 740;
		SOL[i] = 784;
		LAb[i] = 831;
		LA[i] = 880;
		SIb[i] = 932;
		SI[i] = 988;
		i++;
		DO[i] = 1046;
		REb[i] = 1109;
		RE[i] = 1175;
		MIb[i] = 1244;
		MI[i] = 1319;
		FA[i] = 1397;
		SOLb[i] = 1480;
		SOL[i] = 1568;
		LAb[i] = 1661;
		LA[i] = 1760;
		SIb[i] = 1865;
		SI[i] = 1976;
	}
	
	protected void play(int[] instrument, int note, int duration, boolean immediateReturn) {
		if(!immediateReturn) {
			Sound.playNote(instrument, note, duration);
		}
		else {
			Thread thread = new Thread(new NoteThread(instrument, note, duration));
			thread.start();
		}
	}
	
	public void run() {
		playMusic();
		if(!getInterrupted()) {
			Robot.getInstance().warn(new Event(TypeEvent.PLAYMUSICEND, name));
		}
	}
	
	protected abstract void playMusic();
}
