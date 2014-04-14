package main;

import actions.ActionFactory;
import actions.Event;
import actions.TypeEvent;
import robot.EventListener;
import robot.Robot;
import music.BipRobot;

public class DodgeBehavior extends EventListener {
	int state = 0;
	int nbDetectionRobot = 0;
	float distEnnemie;
	boolean clawsOpen;
	private int MinDistPallet = Robot.getInstance().getSonar().getMinDistPallet();
	
	public void warn(Event event) {
		switch(event.getTypeEvent())
		{
		case ROBOT_DETECTED :
			ignore();
			break;
			
		case END_ROBOT_DETECTED :
			ignore();
			break;
			
		case WAIT_END :
				if(nbDetectionRobot == 6)
				{
					nbDetectionRobot = 0;
					state = 1;
				}
			break;
			
		default:
			ignore();
			break;
		}
	}

	protected void act() {
		if(state == 0) {
			float minDist = Robot.getInstance().getSonar().getMinDist();
			//si on déecte quelque chose de plus proche que la limite de détéction d'un pallet
			//alors c'est le robot adverse, donc on va attendre 3sec en vérifiant toutes les 500ms si il est toujours la
			if(minDist<MinDistPallet)
			{
				if(nbDetectionRobot==0)
				{
					Robot.getInstance().warn(new Event(TypeEvent.ROBOT_DETECTED));
				}
				nbDetectionRobot++;
				ActionFactory.wait(500, "", true);
			}
			//sinon on vérifie toutes les 100ms si il y a quelque chose de trop prés
			else
			{
				ActionFactory.wait(100, "", true);
			}
			
		}
		else if(state == 1) {
			
			if(Robot.getInstance().getClaws().getState()==1.0f)
			{
				clawsOpen = true;
				//on ferme les pinces pour éviter de se casser le bras en touchant le robot adverse
				ActionFactory.useClaws(0,false);
			}
			else
			{
				clawsOpen = false;
			}

			//on s'approche à 10cm du robot adverse
			distEnnemie = Robot.getInstance().getSonar().getMinDist();
			ActionFactory.goForward(distEnnemie - 10, true);
			state = 2;
		}
		
		else if(state == 2) {
			distEnnemie = Robot.getInstance().getSonar().getMinDist();
			//si on a plus l'ennemie devant nous alors on reprends l'automate au début
			if(distEnnemie>MinDistPallet)
			{
				Robot.getInstance().warn(new Event(TypeEvent.END_ROBOT_DETECTED));
				state=0;
				ActionFactory.wait(100, "", true);
			}
			else
			{
				//on joue un petit son devant le robot ennemie pour le narguer
				BipRobot bip = new BipRobot();
				bip.playMusic();
			}
		}
		
		else if(state == 3) {
			float minDist = Robot.getInstance().getSonar().getMinDist();
			
			ActionFactory.rotate(90, true);
			//si on a pas la place de contourner le robot adverse (ou qu'il y a un pallet) alors
			//	on contournera par l'autre coté
			if(minDist<40)												//////////////////// LE 40 EST A VERIFIER !!!
			{
				ActionFactory.rotate(-180, true);
				ActionFactory.arcMove(90, 40, true);					//////////////////// ADAPTER LE RADIUS EN CONSEQUENT !!!
			}
			else
			{
				ActionFactory.arcMove(-90, -40, true);					//////////////////// ADAPTER LE RADIUS EN CONSEQUENT !!!
				
			}
				
			state = 4;
		}
		
		else if(state == 4) {
			if(clawsOpen)
			{
				ActionFactory.useClaws(1,false);
			}
			//on avance au moin suffisament pour etre à coté du robot adverse puis on repart au début de l'automate
			ActionFactory.goForward(15, true);
			Robot.getInstance().warn(new Event(TypeEvent.ROBOT_DETECTED));
			state = 0;
		}
	}
}