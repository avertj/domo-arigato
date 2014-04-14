package music;

public class BipRobot extends Music{
	public BipRobot() {
		super(600);
		name = "Bip Robot";
	}
	
	public void playMusic() {
		play(PIANO, LAb[2], HEIGHT, false);
		play(PIANO, SI[2], HEIGHT, false);
		play(PIANO, DO[3], QUARTER, true);
	}
	
	
}
