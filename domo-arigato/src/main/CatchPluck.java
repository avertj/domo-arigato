package main;

import actions.ActionFactory;
import actions.Event;
import robot.EventListener;
import robot.Robot;

public class CatchPluck extends EventListener {
	private int state = 0;
	private double initRotateSpeed;
	private String str = "Nothing";

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case ROTATE_END :
			if(state == 0) // Si on a fait un tour complet, on met fin a l'algorithme.
				state = 2;
			else
				ignore();
			break;
		case POSSIBLE_PLUCK_DETECTED : // Si on voit quelque chose qui n'est pas en dessous de la distance minimum, on fonce dessus.
			if(state == 0)
				state = 1;
			else
				ignore();
			break;
		case BUMP : // Si on bump, on fini l'algorithme en préparant la valeur de retour à "BUMP".
			state = 2;
			str = "BUMP";
			break;
		case WALL_DETECTED : // Si on pense voir un mur dans notre course,
		case ROBOT_DETECTED : // Ou un ennemi,
		case GOFORWARD_END : // Ou qu'on a avancé mais qu'on a rien attrapé, on met fin a l'algorithme.
			if(state == 1)
				state = 2;
			else
				ignore();
			break;
		default:
			ignore();
			break;
		}
	}

	protected void act() {
		if(state == 0) { // On change sa vitesse de rotation et on tourne tout doucement.
			initRotateSpeed = Robot.getInstance().getMotion().getPilot().getRotateSpeed();
			Robot.getInstance().getMotion().getPilot().setRotateSpeed(30);
			ActionFactory.rotate(360, true);
		}
		else if(state == 1) { // Si on a vu quelque chose, on fonce dessus.
			ActionFactory.rotate(-7, false);
			ActionFactory.goForward(Robot.getInstance().getSonar().getMinDist() + 5, true);
		}
		else if(state == 2) { // On retablie la vitesse de rotation et on met fin a l'algorithme.
			Robot.getInstance().getMotion().getPilot().setRotateSpeed(initRotateSpeed);
			stop(str);
		}
	}
}
