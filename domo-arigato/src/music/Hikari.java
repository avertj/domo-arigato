package music;

public class Hikari extends Music{

	public Hikari() {
		super(600);
		name = "Hikari";
	}
	
	public void playMusic() {
		loop1();
		loop2();
		loop1();
		loop3();
		loop4();
		loop5();
		loop6();
		loop7();
	}
	
	private void loop1() {
		play(PIANO, MIb[1], HEIGHT, false);
		play(PIANO, SIb[1], HEIGHT, true);
		play(PIANO, SOL[2], HEIGHT, false);
		play(PIANO, MIb[2], QUARTER, true);
		play(PIANO, RE[3], QUARTER, false);
		play(PIANO, MIb[2], HEIGHT + SIXTEENTH, true);
		play(PIANO, DO[3], HEIGHT, false);
		play(PIANO, SIb[2], SIXTEENTH, false);
		play(PIANO, MIb[2], QUARTER + SIXTEENTH, true);
		play(PIANO, LA[2], HEIGHT + SIXTEENTH, false);
		play(PIANO, SIb[2], HEIGHT, false);
	}
	
	private void loop2() {
		play(PIANO, SOL[0], HEIGHT, false);
		play(PIANO, RE[1], HEIGHT, true);
		play(PIANO, SOL[2], HEIGHT, false);
		play(PIANO, SOL[1], QUARTER, true);
		play(PIANO, RE[3], QUARTER, false);
		play(PIANO, SIb[1], HEIGHT + SIXTEENTH, true);
		play(PIANO, DO[3], HEIGHT, false);
		play(PIANO, SIb[2], SIXTEENTH, false);
		play(PIANO, SIb[1], QUARTER + SIXTEENTH, true);
		play(PIANO, LA[2], HEIGHT + SIXTEENTH, false);
		play(PIANO, SIb[2], HEIGHT, false);
	}
	
	private void loop3() {
		play(PIANO, SIb[0], HEIGHT, false);
		play(PIANO, FA[2], HEIGHT, true);
		play(PIANO, FA[1], HEIGHT, false);
		play(PIANO, FA[2], HEIGHT, true);
		play(PIANO, SIb[1], HEIGHT, false);
		play(PIANO, FA[2], HEIGHT, true);
		play(PIANO, FA[1], HEIGHT, false);
		play(PIANO, RE[2], QUARTER, true);
		play(PIANO, FA[2], HEIGHT, false);
		play(PIANO, SIb[2], HEIGHT, false);
		play(PIANO, FA[1], QUARTER, true);
		play(PIANO, LA[2], HEIGHT, false);
		play(PIANO, SIb[2], HEIGHT, false);
	}
	
	private void loop4() {
		play(PIANO, SOL[2], HEIGHT + SIXTEENTH, true);
		play(PIANO, MIb[3], HEIGHT + SIXTEENTH, true);
		play(PIANO, SOL[0], HEIGHT, false);
		play(PIANO, RE[1], SIXTEENTH, false);
		play(PIANO, RE[3], HEIGHT + SIXTEENTH, true);
		play(PIANO, RE[1], SIXTEENTH, false);
		play(PIANO, SOL[1], HEIGHT, false);
		play(PIANO, SIb[1], HEIGHT + HALF, true);
		play(PIANO, SIb[2], HEIGHT, false);
		play(PIANO, LA[2], HEIGHT + SIXTEENTH, false);
		play(PIANO, LA[2], HEIGHT + SIXTEENTH, false);
		play(PIANO, SIb[2], HEIGHT, false);
	}
	
	private void loop5() {
		play(PIANO, LA[2], HEIGHT + SIXTEENTH, true);
		play(PIANO, RE[3], HEIGHT + SIXTEENTH, true);
		play(PIANO, FA[3], HEIGHT + SIXTEENTH, true);
		play(PIANO, FA[0], HEIGHT, false);
		play(PIANO, RE[1], SIXTEENTH, false);
		play(PIANO, MIb[3], HEIGHT + SIXTEENTH, true);
		play(PIANO, RE[1], SIXTEENTH, false);
		play(PIANO, FA[1], HEIGHT, false);
		play(PIANO, LA[0], HEIGHT + HALF, true);
		play(PIANO, RE[3], HEIGHT, false);
		play(PIANO, DO[3], HEIGHT, false);
		play(PIANO, DO[3], SIXTEENTH, false);
		play(PIANO, RE[3], HEIGHT + SIXTEENTH, false);
		play(PIANO, MIb[3], HEIGHT, false);
	}
	
	private void loop6() {
		play(PIANO, SOL[2], HEIGHT + QUARTER, true);
		play(PIANO, SIb[2], HEIGHT + QUARTER, true);
		play(PIANO, RE[3], HEIGHT + QUARTER, true);
		play(PIANO, MIb[0], HEIGHT, false);
		play(PIANO, SIb[0], HEIGHT, false);
		play(PIANO, MIb[1], HEIGHT, false);
		play(PIANO, SOL[1], HEIGHT + HALF, true);
		play(PIANO, SOL[2], SIXTEENTH, false);
		play(PIANO, SIb[2], SIXTEENTH, false);
		play(PIANO, MIb[2], HEIGHT + SIXTEENTH, false);
		play(PIANO, MIb[2], QUARTER + SIXTEENTH, false);
	}
	
	private void loop7() {
		play(PIANO, SOL[2], HEIGHT + QUARTER, true);
		play(PIANO, RE[3], HEIGHT + QUARTER, true);
		play(PIANO, MIb[2], QUARTER, false);
		play(PIANO, MIb[2], HEIGHT, false);
		play(PIANO, MIb[2], HEIGHT, true);
		play(PIANO, MIb[3], SIXTEENTH, false);
		play(PIANO, DO[3], SIXTEENTH, false);
		play(PIANO, MIb[2], HEIGHT, true);
		play(PIANO, DO[3], HEIGHT, false);
		play(PIANO, MIb[2], HEIGHT, true);
		play(PIANO, SIb[2], HEIGHT, false);
		play(PIANO, MIb[2], QUARTER, true);
		play(PIANO, SIb[2], SIXTEENTH, false);
		play(PIANO, LA[2], HEIGHT + SIXTEENTH, false);
	}
}
