package Strategie;

import java.util.ArrayList;
import java.util.Random;

import Game.EnumActionJeu;
import Game.PlantVSZombieGame;

public class StrategyRandom implements Strategy {

	private Random random = new Random();
	
	@Override
	public EnumActionJeu chooseAction(PlantVSZombieGame state) {
		
		ArrayList<EnumActionJeu> actionpossible=state.actionPossible();
		return actionpossible.get(random.nextInt(actionpossible.size()));
	}

	@Override
	public void update(PlantVSZombieGame state, EnumActionJeu action, PlantVSZombieGame new_state, int reward,
			PlantVSZombieGame game) {
		// TODO Auto-generated method stub
		
	}
	

}
