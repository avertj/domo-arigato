package music;

import lejos.util.Delay;

public class MarioBros extends Music {
	private static final int speed = 300;
	private static final int octave = 2;
	
	public MarioBros() {
		super(speed);
		name = "Mario Bros";
	}
	
	public void playMusic() {
		play(PIANO, MI[octave], HEIGHT, false);
		play(PIANO, MI[octave], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, MI[octave], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, DO[octave], HEIGHT, false);
		play(PIANO, MI[octave], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, SOL[octave], QUARTER, false);
		Delay.msDelay(QUARTER);
		play(PIANO, SOL[octave-1], QUARTER, false);
		Delay.msDelay(QUARTER);
		loop1();
		loop1();
		loop2();
		loop2();
	}
	
	private void loop1() {
		play(PIANO, DO[octave], QUARTER, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, SOL[octave-1], HEIGHT, false);
		Delay.msDelay(QUARTER);
		play(PIANO, MI[octave-1], QUARTER, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, LA[octave-1], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, SI[octave-1], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, SIb[octave-1], HEIGHT, false);
		play(PIANO, LA[octave-1], QUARTER, false);
		play(PIANO, SOL[octave-1], TRIPLET*2, false);
		play(PIANO, MI[octave], TRIPLET*2, false);
		play(PIANO, SOL[octave], TRIPLET*2, false);
		play(PIANO, LA[octave], QUARTER, false);
		play(PIANO, FA[octave], HEIGHT, false);
		play(PIANO, SOL[octave], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, MI[octave], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, DO[octave], HEIGHT, false);
		play(PIANO, RE[octave], HEIGHT, false);
		play(PIANO, SI[octave-1], HEIGHT, false);
		Delay.msDelay(QUARTER);
	}
	
	private void loop2() {
		Delay.msDelay(QUARTER);
		play(PIANO, SOL[octave], HEIGHT, false);
		play(PIANO, SOLb[octave], HEIGHT, false);
		play(PIANO, FA[octave], HEIGHT, false);
		play(PIANO, MIb[octave], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, MI[octave], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, LAb[octave-1], HEIGHT, false);
		play(PIANO, LA[octave-1], HEIGHT, false);
		play(PIANO, DO[octave], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, LA[octave-1], HEIGHT, false);
		play(PIANO, DO[octave], HEIGHT, false);
		play(PIANO, RE[octave], HEIGHT, false);
		Delay.msDelay(QUARTER);
		play(PIANO, SOL[octave], HEIGHT, false);
		play(PIANO, SOLb[octave], HEIGHT, false);
		play(PIANO, FA[octave], HEIGHT, false);
		play(PIANO, MIb[octave], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, MI[octave], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, DO[octave+1], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, DO[octave+1], HEIGHT, false);
		play(PIANO, DO[octave+1], QUARTER, false);
		Delay.msDelay(QUARTER);
		Delay.msDelay(QUARTER);
		play(PIANO, SOL[octave], HEIGHT, false);
		play(PIANO, SOLb[octave], HEIGHT, false);
		play(PIANO, FA[octave], HEIGHT, false);
		play(PIANO, MIb[octave], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, MI[octave], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, LAb[octave-1], HEIGHT, false);
		play(PIANO, LA[octave-1], HEIGHT, false);
		play(PIANO, DO[octave], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, LA[octave-1], HEIGHT, false);
		play(PIANO, DO[octave], HEIGHT, false);
		play(PIANO, RE[octave], HEIGHT, false);
		Delay.msDelay(QUARTER);
		play(PIANO, MIb[octave], QUARTER, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, RE[octave], QUARTER, false);
		Delay.msDelay(QUARTER);
		play(PIANO, DO[octave], QUARTER, false);
		Delay.msDelay(QUARTER);
		Delay.msDelay(HALF);
	}
}

