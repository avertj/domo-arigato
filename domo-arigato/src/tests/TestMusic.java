package tests;

import music.MarioBros;
import actions.ActionFactory;
import actions.Event;
import robot.EventListener;

public class TestMusic extends EventListener {
	boolean debut = true;
	boolean fin = false;

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case PLAYMUSIC_END :
			fin = true;
			break;
		default:
			ignore();
			break;
		}
	}

	public void act() {
		if(debut) {
			debut = false;
			ActionFactory.wait(500, "", false);
			ActionFactory.playMusic(new MarioBros(), true);
		}
		else if(fin) {
			stop();
		}
	}
}
