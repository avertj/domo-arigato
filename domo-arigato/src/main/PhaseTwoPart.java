package main;

import lejos.robotics.navigation.Pose;
import actions.Event;
import robot.EventListener;

public class PhaseTwoPart extends EventListener {
	private int state = 0;
	private String out;
	private Pose goal;
	
	public PhaseTwoPart(Pose goal) {
		this.goal = goal;
	}

	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case CHILDBEHAVIOR_END :
			if(state == 0) // On s'est rendu au point de recherche, on passe donc a l'état suivant.
				state = 1;
			else if(state == 1) { // Si on a fini l'algorithme qui attrape le palet, on change d'état et on se souvient de la valeur de retour.
				state = 2;
				out = event.getName();
			}
			else if(state == 2) // On a rammené le palet, on peut sortir de l'algorithme.
				state = 3;
			else
				ignore();
			break;
		default:
			ignore();
			break;
		}
	}

	protected void act() {
		if(state == 0) {
			// Dans cette etat on se rend au point de recherche
			doBehavior(new GoToBehavior(goal));
		}
		else if(state == 1) { // Dans cette etat on lance l'algorithme qui tourne sur place et attrape le palet.
			doBehavior(new CatchPluck());
		}
		else if(state == 2) { // Soit on a attrapé un palet, donc on le ramene, sinon on en a fini avec cette algorithme et on se rend a la sortie.
			if(out.equals("BUMP"))
				doBehavior(new ScoreBehavior());
			else {
				state = 3;
				act();
			}
		}
		else if(state == 3) {
			stop();
		}
	}
}
