package music;

public class LaLettreAEliseOneHanded extends Music{

	public LaLettreAEliseOneHanded() {
		super(600);
		name = "LaLettreAElise";
	}
	
	public void playMusic() {
		loop();
		play(PIANO, LAb[2], HEIGHT, false);
		play(PIANO, SI[2], HEIGHT, false);
		play(PIANO, DO[3], QUARTER, false);
		play(PIANO, LA[1], HEIGHT, false);
		play(PIANO, MI[2], HEIGHT, false);
		loop();
		play(PIANO, DO[3], HEIGHT, false);
		play(PIANO, SI[2], HEIGHT, false);
		play(PIANO, LA[2], HALF, false);
	}
	
	private void loop() {
		play(PIANO, MI[3], HEIGHT, false);
		play(PIANO, MIb[3], HEIGHT, false);
		play(PIANO, MI[3], HEIGHT, false);
		play(PIANO, MIb[3], HEIGHT, false);
		play(PIANO, MI[3], HEIGHT, false);
		play(PIANO, SI[2], HEIGHT, false);
		play(PIANO, RE[3], HEIGHT, false);
		play(PIANO, DO[3], HEIGHT, false);
		play(PIANO, LA[2], QUARTER, false);
		play(PIANO, LA[0], HEIGHT, false);
		play(PIANO, MI[1], HEIGHT, false);
		play(PIANO, LA[1], HEIGHT, false);
		play(PIANO, DO[2], HEIGHT, false);
		play(PIANO, MI[2], HEIGHT, false);
		play(PIANO, LA[2], HEIGHT, false);
		play(PIANO, SI[2], QUARTER, false);
		play(PIANO, LAb[1], HEIGHT, false);
		play(PIANO, MI[2], HEIGHT, false);
	}
}
