package music;

import lejos.util.Delay;

public class MarioBros extends Music {
	private static final int speed=300;
	
	public MarioBros() {
		super(speed);
		name = "Mario Bros";
	}
	
	public void playMusic() {
		play(PIANO, MI[3], HEIGHT, false);
		play(PIANO, MI[3], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, MI[3], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, DO[3], HEIGHT, false);
		play(PIANO, MI[3], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, SOL[3], QUARTER, false);
		Delay.msDelay(QUARTER);
		play(PIANO, SOL[2], QUARTER, false);
		Delay.msDelay(QUARTER);
		loop1();
		loop1();
		loop2();
		loop2();
	}
	
	private void loop1() {
		play(PIANO, DO[3], QUARTER, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, SOL[2], HEIGHT, false);
		Delay.msDelay(QUARTER);
		play(PIANO, MI[2], QUARTER, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, LA[2], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, SI[2], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, SIb[2], HEIGHT, false);
		play(PIANO, LA[2], QUARTER, false);
		play(PIANO, SOL[2], TRIPLET*2, false);
		play(PIANO, MI[3], TRIPLET*2, false);
		play(PIANO, SOL[3], TRIPLET*2, false);
		play(PIANO, LA[3], QUARTER, false);
		play(PIANO, FA[3], HEIGHT, false);
		play(PIANO, SOL[3], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, MI[3], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, DO[3], HEIGHT, false);
		play(PIANO, RE[3], HEIGHT, false);
		play(PIANO, SI[2], HEIGHT, false);
		Delay.msDelay(QUARTER);
	}
	
	private void loop2() {
		Delay.msDelay(QUARTER);
		play(PIANO, SOL[3], HEIGHT, false);
		play(PIANO, SOLb[3], HEIGHT, false);
		play(PIANO, FA[3], HEIGHT, false);
		play(PIANO, MIb[3], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, MI[3], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, LAb[2], HEIGHT, false);
		play(PIANO, LA[2], HEIGHT, false);
		play(PIANO, DO[3], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, LA[2], HEIGHT, false);
		play(PIANO, DO[3], HEIGHT, false);
		play(PIANO, RE[3], HEIGHT, false);
		Delay.msDelay(QUARTER);
		play(PIANO, SOL[3], HEIGHT, false);
		play(PIANO, SOLb[3], HEIGHT, false);
		play(PIANO, FA[3], HEIGHT, false);
		play(PIANO, MIb[3], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, MI[3], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, DO[4], QUARTER, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, DO[4], QUARTER, false);
		play(PIANO, DO[4], QUARTER, false);
		Delay.msDelay(QUARTER);
		Delay.msDelay(QUARTER);
		play(PIANO, SOL[3], HEIGHT, false);
		play(PIANO, SOLb[3], HEIGHT, false);
		play(PIANO, FA[3], HEIGHT, false);
		play(PIANO, MIb[3], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, MI[3], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, LAb[2], HEIGHT, false);
		play(PIANO, LA[2], HEIGHT, false);
		play(PIANO, DO[3], HEIGHT, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, LA[2], HEIGHT, false);
		play(PIANO, DO[3], HEIGHT, false);
		play(PIANO, RE[3], HEIGHT, false);
		Delay.msDelay(QUARTER);
		play(PIANO, MIb[3], QUARTER, false);
		Delay.msDelay(HEIGHT);
		play(PIANO, RE[3], QUARTER, false);
		Delay.msDelay(QUARTER);
		play(PIANO, DO[3], QUARTER, false);
		Delay.msDelay(QUARTER);
		Delay.msDelay(HALF);
	}
}

