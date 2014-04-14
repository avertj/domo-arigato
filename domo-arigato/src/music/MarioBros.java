package music;

public class MarioBros extends Music {
	private static final int speed=600;
	
	public MarioBros() {
		super(speed);
		name = "Mario Bros";
	}
	
	public void playMusic() {
		play(PIANO, LA[2], QUARTER, false);
		play(PIANO, LA[2], HALF, false);
		play(PIANO, LA[2], HALF, false);
		play(PIANO, FA[2], QUARTER, false);
		play(PIANO, LA[2], HALF, false);
		play(PIANO, DO[3], HALF, false);
		try {
			Thread.sleep(speed+speed/2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

