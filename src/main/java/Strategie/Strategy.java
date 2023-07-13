package Strategie;
import Game.PlantVSZombieGame;
import Game.EnumActionJeu;

public interface Strategy {
	
	public EnumActionJeu chooseAction(PlantVSZombieGame state);

	public void update(PlantVSZombieGame state, EnumActionJeu action,PlantVSZombieGame new_state, int reward, PlantVSZombieGame game);
	
}
