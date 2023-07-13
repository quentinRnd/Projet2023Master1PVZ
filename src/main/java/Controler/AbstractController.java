package Controler;

import Game.PlantVSZombieGame;

public abstract class AbstractController {
    public PlantVSZombieGame Jeu;
    
    public AbstractController()
    {} 

    public void restart(){

        this.Jeu.pause();
        this.Jeu.init();
        this.Jeu.launch();
        
    }
    public void step()
    {
    	 this.Jeu.step();
    }
    public void play()
    {
    	this.Jeu.launch(); 
    }
    public void pause()
    {
    	this.Jeu.pause();
    }
    public void setSpeed(double speed)
    {
        this.Jeu.setTime((long)speed);
    }
}
