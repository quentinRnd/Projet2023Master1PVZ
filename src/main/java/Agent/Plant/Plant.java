package Agent.Plant;

import java.util.ArrayList;

import Agent.Agent;
import Agent.Item.Projectile;
import Game.EnumActionJeu;
import Game.PlantVSZombieGame;
import Utils.Position;



public abstract class Plant extends Agent{

    //enum qui sert a l'affichage des different type de snake
    public static enum TypePlant{
        Pois,Dragon
    }

    public EnumActionJeu action;
    public boolean relierAction;

    public Plant(long PV, Position p) {
        super(PV, p);
        relierAction=false;
    }
    public Plant(long PV, Position p,EnumActionJeu a) {
        super(PV, p);
        this.action=a;
        relierAction=true;
    }


    public abstract ArrayList<Projectile> takeTurn(PlantVSZombieGame pvz);
    public abstract long cout();
    public abstract TypePlant type();
    

   
    
}
